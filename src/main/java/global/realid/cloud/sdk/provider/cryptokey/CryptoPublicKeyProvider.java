package global.realid.cloud.sdk.provider.cryptokey;

import java.security.PublicKey;

/**
 * cryptography key provider for public key
 * @author Benjamin
 */

public interface CryptoPublicKeyProvider {
	/**
	 * get public key that can be provided from the class that implements this interface
	 * @return {@link PublicKey public key} instance
	 * @throws Exception any Exception during the process
	 */
	PublicKey getPublicKey() throws Exception;
}
