package global.realid.cloud.sdk.provider.cryptokey.vendors;

import global.realid.cloud.sdk.provider.cryptokey.CryptoPrivateKeyProvider;
import global.realid.cloud.sdk.util.PemReader;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * work-around RSA, PKCS#1-encoded private key provider by parsing PEM file, for key file whose content starts with "<code>-----BEGIN RSA PRIVATE KEY-----</code>"
 * @author Benjamin
 */

public class PemPkcs1RsaPrivateKeyProvider implements CryptoPrivateKeyProvider {
	private String keyFilePath;

	/**
	 * construct using given file
	 * @param keyFilePath private key file path
	 */
	public PemPkcs1RsaPrivateKeyProvider(String keyFilePath) {
		super();
		this.keyFilePath = keyFilePath;
	}

	/**
	 * get private key file path
	 * @return private key file path
	 */
	public String getKeyFilePath() {
		return keyFilePath;
	}

	/**
	 * set private key file path
	 * @param keyFilePath private key file path
	 */
	public void setKeyFilePath(String keyFilePath) {
		this.keyFilePath = keyFilePath;
	}

	@Override
	public PrivateKey getPrivateKey() throws Exception {
		byte[] keyData = PemReader.readPrivateKey(new File(keyFilePath));
		
		int pkcs1Length = keyData.length;
	    int totalLength = pkcs1Length + 22;
	    byte[] pkcs8Header = new byte[] {
	            0x30, (byte) 0x82, (byte) ((totalLength >> 8) & 0xff), (byte) (totalLength & 0xff), // Sequence + total length
	            0x2, 0x1, 0x0, // Integer (0)
	            0x30, 0xD, 0x6, 0x9, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0xD, 0x1, 0x1, 0x1, 0x5, 0x0, // Sequence: 1.2.840.113549.1.1.1, NULL
	            0x4, (byte) 0x82, (byte) ((pkcs1Length >> 8) & 0xff), (byte) (pkcs1Length & 0xff) // Octet string + length
	    };
	    byte[] pkcs8Data = new byte[keyData.length + pkcs8Header.length];
	    System.arraycopy(pkcs8Header, 0, pkcs8Data, 0, pkcs8Header.length);
	    System.arraycopy(keyData, 0, pkcs8Data, pkcs8Header.length, keyData.length);
		
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(pkcs8Data);
		return kf.generatePrivate(ks);
	}

}
