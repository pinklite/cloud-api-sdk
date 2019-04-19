package global.realid.cloud.sdk.provider.http.vendors;

import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.http.HttpProviderResult;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultClientConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link HttpProvider} base on Apache Http Client
 * @author Benjamin
 */

public class ApacheHttpClientHttpProvider implements HttpProvider {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private PoolingHttpClientConnectionManager poolConnManager;

	private CloseableHttpClient httpClient = null;
	/**
	 * maximum number of total connections in pool
	 */
	private final int maxTotal = 500;

	/**
	 * maximum connections per route
	 */
	private final int maxConPerRoute = 200;

	/**
	 * validate connection after inactivity when getting connection from pool,
	 * measured in millisecond
	 */
	private final int validateAfterInactivityMillisecond = 5000;

	/**
	 * evict connections after some time becoming idle, measured in second
	 */
	private final int evictIdleConnectionsSecond = 180;

	/**
	 * maximum time to live for persistent connections, measured in second
	 */
	private final int connectionTimeToLiveSecond = 180;

	/**
	 * timeout for requesting a connection from pool, measured in millisecond
	 */
	private final int connectionRequestTimeout = 3000;

	/**
	 * connect timeout, measured in millisecond
	 */
	private final int connectTimeout = 5000;

	/**
	 * read timeout, measured in millisecond
	 */
	private int socketTimeout = 30000;

	public ApacheHttpClientHttpProvider() {
		if (httpClient != null) {
			return;
		}
		logger.info("initializing http client connect pools......");
		try {
			// SocketFactory registry
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", SSLConnectionSocketFactory.getSystemSocketFactory()).build();

			// HttpConnection factory
			HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
					DefaultHttpRequestWriterFactory.INSTANCE,
					DefaultHttpResponseParserFactory.INSTANCE);

			// DNS resolver
			DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry,
					connFactory, dnsResolver);
			// Increase max total connection to maxTotal
			poolConnManager.setMaxTotal(maxTotal);
			// Increase default max connection per route to maxConPerRoute
			poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
			poolConnManager.setDefaultSocketConfig(socketConfig);
			poolConnManager.setValidateAfterInactivity(validateAfterInactivityMillisecond);

			// default request configuration
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(connectionRequestTimeout)
					.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();

			// customized HttpClient
			httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
					.setConnectionManagerShared(false)
					.evictIdleConnections(evictIdleConnectionsSecond, TimeUnit.SECONDS)
					.evictExpiredConnections()
					.setConnectionTimeToLive(connectionTimeToLiveSecond, TimeUnit.SECONDS)
					.setDefaultRequestConfig(requestConfig)
					.setConnectionReuseStrategy(DefaultClientConnectionReuseStrategy.INSTANCE)
					.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
					// disable retry
					.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();

			// add shutdown hook to close HttpClient
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				HttpClientUtils.closeQuietly(httpClient);
				poolConnManager.close();
			}));

		} catch (Exception e) {
			logger.error("fail to initialize http client connect pool！", e);
			throw e;
		}
		logger.info("complete initializing http client connect pool！");
	}

	/**
	 * send request to server denoted by given URL 
	 * @param httpRequest HTTP request instance
	 * @param url request URL
	 * @param headers header values
	 * @param requestConfig request config
	 * @return HTTP response
     * @throws ClientProtocolException in case of an http protocol error
     * @throws IOException in case of a problem or the connection was aborted
     * @throws SocketException in case of connection problem
	 */
	private CloseableHttpResponse sendForResponse(HttpRequestBase httpRequest, String url, Map<String, String> headers,
			RequestConfig requestConfig) throws ClientProtocolException, SocketException, IOException {
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> headerEntity : headers.entrySet()) {
				httpRequest.addHeader(headerEntity.getKey(), headerEntity.getValue());
			}
		}

		if (requestConfig != null) {
			httpRequest.setConfig(requestConfig);
		}
		
		logger.debug("send {} request to {}...", httpRequest.getMethod(), url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpRequest);
			logger.debug("get response from {}, status line:{}", url, response.getStatusLine());
			return response;
		} catch (HttpHostConnectException e) {
			throw new SocketException(e.getMessage());
		}
	}
	
	/**
	 * parse HTTP response to {@link HttpProviderResult}, and then close the response
	 * @param response HTTP response
	 * @param resultConverter result converter, which is responsible for converting response to corresponding result object
	 * @return result object
	 */
	private <T> HttpProviderResult<T> parseHttpResult(CloseableHttpResponse response, Function<CloseableHttpResponse, T> resultConverter) {
		try {
			HttpProviderResult<T> rst = new HttpProviderResult<>();
			// status code
			rst.setStatusCode(response.getStatusLine().getStatusCode());
			// headers
			Header[] arrHeaders = response.getAllHeaders();
			Map<String, String> headers = Arrays.stream(arrHeaders).collect(Collectors.toMap(Header::getName, Header::getValue));
			rst.setHeaders(headers);
			// content
			rst.setResponseContent(resultConverter.apply(response));
			
			return rst;
		} finally {
			HttpClientUtils.closeQuietly(response);
		}
	}

	@Override
	public HttpProviderResult<byte[]> sendJsonPostForBytesResponse(String url,
			Map<String, String> header, String jsonString) throws SocketException, IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString));
		CloseableHttpResponse response = sendForResponse(post, url, header, null);
		return parseHttpResult(response, r -> {
			try {
				return EntityUtils.toByteArray(r.getEntity());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public HttpProviderResult<byte[]> sendMultipartFormPostForBytesResponse(String url,
			Map<String, String> header, Map<String, File> fileFields,
			Map<String, String> textFields) throws SocketException, IOException {
		HttpPost post = new HttpPost(url);
		
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		if (textFields != null) {
			textFields.forEach((k, v) -> entityBuilder.addTextBody(k, v));
		}
		if (fileFields != null) {
			fileFields.forEach((k, v) -> entityBuilder.addBinaryBody(k, v, ContentType.DEFAULT_BINARY, v.getName()));
		}
		post.setEntity(entityBuilder.build());
		
		CloseableHttpResponse response = sendForResponse(post, url, header, null);
		return parseHttpResult(response, r -> {
			try {
				return EntityUtils.toByteArray(r.getEntity());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public HttpProviderResult<String> sendJsonPostForStringResponse(String url,
			Map<String, String> header, String jsonString, Charset defaultCharsetEncoding)
			throws SocketException, IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString));
		CloseableHttpResponse response = sendForResponse(post, url, header, null);
		return parseHttpResult(response, r -> {
			try {
				return EntityUtils.toString(r.getEntity(), defaultCharsetEncoding);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public HttpProviderResult<String> sendMultipartFormPostForStringResponse(String url,
			Map<String, String> header, Map<String, File> fileFields,
			Map<String, String> textFields, Charset defaultCharsetEncoding) throws SocketException,
			IOException {
		HttpPost post = new HttpPost(url);
		
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		if (textFields != null) {
			textFields.forEach((k, v) -> entityBuilder.addTextBody(k, v));
		}
		if (fileFields != null) {
			fileFields.forEach((k, v) -> entityBuilder.addBinaryBody(k, v, ContentType.DEFAULT_BINARY, v.getName()));
		}
		post.setEntity(entityBuilder.build());
		
		CloseableHttpResponse response = sendForResponse(post, url, header, null);
		return parseHttpResult(response, r -> {
			try {
				return EntityUtils.toString(r.getEntity(), defaultCharsetEncoding);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void close() throws IOException {
		// not necessary, because a shutdown hook has been added during construction
		HttpClientUtils.closeQuietly(httpClient);
		poolConnManager.close();
	}

}
