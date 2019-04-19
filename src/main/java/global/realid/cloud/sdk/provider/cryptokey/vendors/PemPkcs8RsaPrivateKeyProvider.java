package global.realid.cloud.sdk.provider.cryptokey.vendors;

import global.realid.cloud.sdk.provider.cryptokey.CryptoPrivateKeyProvider;
import global.realid.cloud.sdk.util.PemReader;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * RSA, PKCS#8-encoded private key provider by parsing PEM file, for key file whose content starts with "<code>-----BEGIN PRIVATE KEY-----</code>"
 * @author Benjamin
 */

public class PemPkcs8RsaPrivateKeyProvider implements CryptoPrivateKeyProvider {
	private String keyFilePath;

	/**
	 * construct using given file
	 * @param keyFilePath private key file path
	 */
	public PemPkcs8RsaPrivateKeyProvider(String keyFilePath) {
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
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(keyData);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(ks);
	}

}
