package global.realid.cloud.sdk.provider.cryptokey.vendors;

import global.realid.cloud.sdk.provider.cryptokey.CryptoPublicKeyProvider;
import global.realid.cloud.sdk.util.PemReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * public key provider by parsing X.509 certificate file 
 * @author Benjamin
 */

public class X509CertPublicKeyProvider implements CryptoPublicKeyProvider {
	
	private String certFilePath;

	/**
	 * construct using given file
	 * @param certFilePath public key certificate file path
	 */
	public X509CertPublicKeyProvider(String certFilePath) {
		super();
		this.certFilePath = certFilePath;
	}

	/**
	 * get public key certificate file path
	 * @return public key certificate file path
	 */
	public String getCertFilePath() {
		return certFilePath;
	}

	/**
	 * set public key certificate file path
	 * @param certFilePath public key certificate file path
	 */
	public void setCertFilePath(String certFilePath) {
		this.certFilePath = certFilePath;
	}


	@Override
	public PublicKey getPublicKey() throws Exception {
		List<byte[]> certsFromFile = PemReader.readCertificates(new File(certFilePath));
		byte[] targetCert = certsFromFile.get(0);
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509Cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(targetCert));
		
		return x509Cert.getPublicKey();
	}

}
