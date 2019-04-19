package global.realid.cloud.sdk.provider.http;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * HTTP functionality provider
 * @author Benjamin
 */

public interface HttpProvider extends Closeable {

	/**
	 * send request to URL with JSON string as request body, and get response body as raw bytes
	 * @param url request URL
	 * @param header request headers
	 * @param jsonString request JSON data
	 * @return response result
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 */
	HttpProviderResult<byte[]> sendJsonPostForBytesResponse(String url, Map<String, String> header,
			String jsonString) throws SocketException, IOException;

	/**
	 * send request to URL with multipart form data as request body, and get response body as raw bytes, 
	 * form data is specified by {@code fileFields} as file fields and {@code textFields} as text fields 
	 * @param url request URL
	 * @param header request headers
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @return response result
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 */
	HttpProviderResult<byte[]> sendMultipartFormPostForBytesResponse(String url,
			Map<String, String> header, Map<String, File> fileFields,
			Map<String, String> textFields) throws SocketException, IOException;

	/**
	 * send request to URL with JSON string as request body, and get response body as string, 
	 * using {@code defaultCharset} if underlying HTTP response does not specify character encoding 
	 * @param url request URL
	 * @param header request headers
	 * @param jsonString request JSON data
	 * @param defaultCharsetEncoding default character encoding in case that response does not specify one
	 * @return response result
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 */
	HttpProviderResult<String> sendJsonPostForStringResponse(String url,
			Map<String, String> header, String jsonString, Charset defaultCharsetEncoding) throws SocketException, IOException;

	/**
	 * send request to URL with multipart form data as request body, and get response body as raw bytes, 
	 * form data is specified by {@code fileFields} as file fields and {@code textFields} as text fields, 
	 * using {@code defaultCharset} if underlying HTTP response does not specify character encoding 
	 * @param url request URL
	 * @param header request headers
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @param defaultCharsetEncoding default character encoding in case that response does not specify one
	 * @return response result
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 */
	HttpProviderResult<String> sendMultipartFormPostForStringResponse(String url,
			Map<String, String> header, Map<String, File> fileFields,
			Map<String, String> textFields, Charset defaultCharsetEncoding) throws SocketException, IOException;
}
