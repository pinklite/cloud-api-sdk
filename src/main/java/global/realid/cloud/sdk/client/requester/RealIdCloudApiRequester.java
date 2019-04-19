package global.realid.cloud.sdk.client.requester;

import global.realid.cloud.sdk.exception.InvalidResponseStatusException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.Map;

/**
 * Real ID Cloud API requester, it's responsible for handling network requests for Real ID Cloud API
 * @author Benjamin
 */

public interface RealIdCloudApiRequester extends Closeable {

	/**
	 * execute JSON request and get response
	 * @param <R> the type of request data
	 * @param <T> the type of result object
	 * @param url request URL
	 * @param reqData request data object
	 * @param respType response object type
	 * @return response object
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 * @throws InvalidResponseStatusException in case of not getting valid response status code
	 */
	<R, T> T executeJsonRequest(String url, R reqData, Type respType) throws SocketException, IOException;
	
	/**
	 * execute multipart form request and get response
	 * @param <T> the type of result object
	 * @param url url request URL
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @param respType response object type
	 * @return response object
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 * @throws InvalidResponseStatusException in case of not getting valid response status code
	 */
	<T> T executeMultipartFormRequest(String url, Map<String, File> fileFields, Map<String, String> textFields, Type respType) throws SocketException, IOException;
}
