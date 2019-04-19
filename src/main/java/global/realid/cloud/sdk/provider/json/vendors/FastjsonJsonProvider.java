package global.realid.cloud.sdk.provider.json.vendors;

import global.realid.cloud.sdk.provider.json.JsonProvider;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

/**
 * {@link JsonProvider} base on Fastjson
 * @author Benjamin
 */

public class FastjsonJsonProvider implements JsonProvider {

	@Override
	public <T> T deserialize(byte[] jsonByte, Charset charset, Type type) {
		return JSON.parseObject(jsonByte, 0, jsonByte.length, charset, type);
	}

	@Override
	public <T> T deserialize(String jsonString, Type type) {
		return JSON.parseObject(jsonString, type);
	}

	@Override
	public <T> byte[] serialize(T targetObj, Charset charset) {
		String jsonString = JSON.toJSONString(targetObj);
		return jsonString.getBytes(charset);
	}

	@Override
	public <T> String serialize(T targetObj) {
		return JSON.toJSONString(targetObj);
	}

}
