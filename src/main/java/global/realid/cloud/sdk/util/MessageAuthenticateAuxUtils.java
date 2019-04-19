package global.realid.cloud.sdk.util;

import global.realid.cloud.sdk.exception.RealIdCloudApiException;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * auxiliary utilities for message authentication
 * @author Benjamin
 */

public class MessageAuthenticateAuxUtils {

	/**
	 * MD5 digest encoded in Base64, the stream will be closed at the end
	 * @param stream the steam to calculate
	 * @return the result of MD5 digest encoded in Base64
	 */
	public static String streamMD5Base64(InputStream stream) {
		try(InputStream inStream = stream) {
			// calculate MD5 digest of stream
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			updateDigest(messageDigest, inStream);
			byte[] digest = messageDigest.digest();
			// Base64
			return Base64.getEncoder().encodeToString(digest);
		} catch (final NoSuchAlgorithmException | IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * update digest using given stream data
	 * @param digest digest instance
	 * @param data the data to update digest
	 * @return digest instance from input parameter
	 * @throws IOException If the first byte cannot be read for any reason other than end of file, or if the input stream has been closed, or if some other I/O error occurs.
	 */
	public static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws IOException {
		final byte[] buffer = new byte[1024];
		int read = data.read(buffer, 0, 1024);
	
		while (read > -1) {
			digest.update(buffer, 0, read);
			read = data.read(buffer, 0, 1024);
		}
	
		return digest;
	}
	
	/**
	 * MD5 digest encoded in Base64
	 * @param content content to digest
	 * @return MD5 digest encoded in Base64
	 */
	public static String byteArrayMD5Base64(byte[] content) {
		try {
			// calculate MD5 digest of stream
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(content);
			byte[] digest = messageDigest.digest();
			// Base64
			return Base64.getEncoder().encodeToString(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new RealIdCloudApiException("MD5 is not supported!!!", e);
		}
	}
	
	/**
	 * concatenate form data as string as Real ID Cloud API Protocol specified, note that file fields should be converted to Base64 string of MD5 digest
	 * @param form key-value pair of string denoting the form data
	 * @return concatenate form data as string
	 */
	public static String concatenateFormData(Map<String, String> form) {
		// empty string if no field in form
		if (form == null || form.isEmpty()) {
			return "";
		}
		
		// sort by field name
		// String implements Comparable in lexicographical order, so nothing else need to be done
		SortedMap<String, String> sortedForm = (form instanceof SortedMap) ? 
				(SortedMap<String, String>) form : new TreeMap<String, String>(form);
		
		// concatenate "fieldName=fieldValue" using "&"
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = sortedForm.entrySet().iterator();
		
		Entry<String, String> entry = iterator.next();
		stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
		
		while(iterator.hasNext()) {
			entry = iterator.next();
			stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		}
		
		return stringBuilder.toString();
	}
}
