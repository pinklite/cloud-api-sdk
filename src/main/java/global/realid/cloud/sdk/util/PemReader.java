package global.realid.cloud.sdk.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PEM format File Reader, modified from <a href="https://netty.io/">Netty</a>
 * @author Benjamin
 */

public final class PemReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PemReader.class);

    private static final Pattern CERT_PATTERN = Pattern.compile(
            "-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+" + // Header
                    "([a-z0-9+/=\\r\\n]+)" +                    // Base64 text
                    "-+END\\s+.*CERTIFICATE[^-]*-+",            // Footer
            Pattern.CASE_INSENSITIVE);
    private static final Pattern KEY_PATTERN = Pattern.compile(
            "-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+" + // Header
                    "([a-z0-9+/=\\r\\n]+)" +                       // Base64 text
                    "-+END\\s+.*PRIVATE\\s+KEY[^-]*-+",            // Footer
            Pattern.CASE_INSENSITIVE);

    /**
     * read certificates in file
     * @param file file instance
     * @return certificates, each element of list denotes one certificate, and the first is the end of certificate chain 
     * @throws CertificateException any exception during process
     */
    public static List<byte[]> readCertificates(File file) throws CertificateException {
        try {
            InputStream in = new FileInputStream(file);

            try {
                return readCertificates(in, false);
            } finally {
                safeClose(in);
            }
        } catch (FileNotFoundException e) {
            throw new CertificateException("could not find certificate file: " + file);
        }
    }

    /**
     * read certificates from input stream
     * @param in the input stream
     * @param shouldCloseInput whether the input steam closed inside the method
     * @return certificates, each element of list denotes one certificate, and the first is the end of certificate chain 
     * @throws CertificateException any exception during process
     */
    public static List<byte[]> readCertificates(InputStream in, boolean shouldCloseInput) throws CertificateException {
        String content;
        try {
            content = readContent(in);
        } catch (IOException e) {
            throw new CertificateException("failed to read certificate input stream", e);
        } finally {
        	if (in != null && shouldCloseInput) {
				safeClose(in);
			}
        }

        List<byte[]> certs = new ArrayList<byte[]>();
        Matcher m = CERT_PATTERN.matcher(content);
        int start = 0;
        for (;;) {
            if (!m.find(start)) {
                break;
            }

            String base64 = m.group(1);
            byte[] der = Base64.getMimeDecoder().decode(base64);
            certs.add(der);

            start = m.end();
        }

        if (certs.isEmpty()) {
            throw new CertificateException("found no certificates in input stream");
        }

        return certs;
    }

    /**
     * read private key from file
     * @param file file instance
     * @return private key content
     * @throws KeyException any exception during process
     */
    public static byte[] readPrivateKey(File file) throws KeyException {
        try {
            InputStream in = new FileInputStream(file);

            try {
                return readPrivateKey(in);
            } finally {
                safeClose(in);
            }
        } catch (FileNotFoundException e) {
            throw new KeyException("could not find key file: " + file);
        }
    }

    /**
     * read private key from input stream
     * @param in input stream
     * @return private key content
     * @throws KeyException any exception during process
     */
    public static byte[] readPrivateKey(InputStream in) throws KeyException {
        String content;
        try {
            content = readContent(in);
        } catch (IOException e) {
            throw new KeyException("failed to read key input stream", e);
        }

        Matcher m = KEY_PATTERN.matcher(content);
        if (!m.find()) {
            throw new KeyException("could not find a PKCS #8 private key in input stream");
        }

        String base64 = m.group(1);
        byte[] der = Base64.getMimeDecoder().decode(base64);
        return der;
    }

    /**
     * read content as String from input stream
     * @param in input stream
     * @return content read
     * @throws IOException If the first byte cannot be read for any reason other than the end of the file, if the input stream has been closed, or if some other I/O error occurs.
     */
    private static String readContent(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[8192];
            for (;;) {
                int ret = in.read(buf);
                if (ret < 0) {
                    break;
                }
                out.write(buf, 0, ret);
            }
            return StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(out.toByteArray())).toString();
        } finally {
            safeClose(out);
        }
    }

    /**
     * close the stream
     * @param stream the stream
     */
    private static void safeClose(Closeable stream) {
        try {
            stream.close();
        } catch (IOException e) {
            LOGGER.warn("Failed to close a stream.", e);
        }
    }

    private PemReader() { }
}
