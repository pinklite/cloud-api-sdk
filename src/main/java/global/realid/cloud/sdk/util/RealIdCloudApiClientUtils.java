package global.realid.cloud.sdk.util;

import global.realid.cloud.sdk.client.RealIdCloudApiClient;
import global.realid.cloud.sdk.client.requester.RealIdCloudApiRequester;

import java.io.Closeable;
import java.io.IOException;

/**
 * Convenience methods for closing {@link RealIdCloudApiRequester requesters} and {@link RealIdCloudApiClient client objects}.
 * @author Benjamin
 */

public class RealIdCloudApiClientUtils {

	/**
	 * Close a {@link RealIdCloudApiClient client}
	 * @param client the client instance
	 */
	public static void closeQuietly(final RealIdCloudApiClient client) {
		closeQuietly((Closeable) client);
	}
	
	/**
	 * Close a {@link RealIdCloudApiRequester requester}
	 * @param requester the requester instance
	 */
	public static void closeQuietly(final RealIdCloudApiRequester requester) {
		closeQuietly((Closeable) requester);
	}
	
	/**
	 * Convenience method for closing {@link Closeable}
	 * @param closeable the cloeable instance
	 */
	public static void closeQuietly(final Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException ignore) {
			}
		}
	}
}
