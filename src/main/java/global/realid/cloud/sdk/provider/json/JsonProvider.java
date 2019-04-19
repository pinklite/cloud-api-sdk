package global.realid.cloud.sdk.provider.json;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * JSON processing functionality provider
 * @author Benjamin
 */

public interface JsonProvider {
	
	/**
	 * deserialize JSON to instance of given type, JSON comes from given bytes and corresponding character set
	 * @param <T> the type of the object from the JSON content 
	 * @param jsonByte byte array of JSON content
	 * @param charset character encoding for JSON content
	 * @param type type of the object from the JSON content 
	 * @return  the instance of given type
	 */
	<T> T deserialize(byte[] jsonByte, Charset charset, Type type);

	/**
	 * deserialize JSON string to instance of given type
	 * @param <T> the type of the object from the JSON content 
	 * @param jsonString the JSON string
	 * @param type type of the object from the JSON content 
	 * @return instance of given type
	 */
	<T> T deserialize(String jsonString, Type type);
	
	/**
	 * serialize object to JSON bytes using given character set
	 * @param <T> the type of the target object
	 * @param targetObj the target object to be serialized
	 * @param charset character encoding for JSON serializing
	 * @return the content after serialization
	 */
	<T> byte[] serialize(T targetObj, Charset charset);

	/**
	 * serialize object to JSON string
	 * @param <T> the type of the target object
	 * @param targetObj the target object to be serialized
	 * @return the content after serialization
	 */
	<T> String serialize(T targetObj);
}
