package global.realid.cloud.sdk.provider.cryptokey;

import java.security.PrivateKey;

/**
 * cryptography key provider for private key
 * @author Benjamin
 */

public interface CryptoPrivateKeyProvider {
	/**
	 * get private key that can be provided from the class that implements this interface
	 * @return {@link PrivateKey private key} instance
	 * @throws Exception any Exception during the process
	 */
	PrivateKey getPrivateKey() throws Exception;
}
