package com.horizon.common.util;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonUtil {
	
	private static Logger log = Logger.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();

	public JsonUtil() {

	}
	/**取出json中某个域的值
	 * @param json
	 * @param field
	 * @return
	 */
	public static String getField(String json,String field){
		return JSONObject.parseObject(json).getString(field);
	}
	/**
	 * 通过输出流将对象输出为JSON对象
	 * 
	 * @param out
	 * @param obj
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void writeJsonWithDefaultPretty(OutputStream out, Object obj)
			throws JsonGenerationException, JsonMappingException, IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, obj);
	}

	/**
	 * 通过输出流将对象输出为JSON对象
	 * 
	 * @param out
	 * @param obj
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void writeJson(OutputStream out, Object obj)
			throws JsonGenerationException, JsonMappingException, IOException {
		mapper.writeValue(out, obj);
	}
	/**
	 * 将对象生成JSON字符串
	 * 
	 * @param obj
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String genJsonString(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 将对象生成JSON字符串
	 * 
	 * @param obj
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String genDefaultPrettyJsonString(Object obj)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}

	/**
	 * 将JSON字符串生成对象
	 * 
	 * @param obj
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object genObjectFromJsonString(String json, Class<?> valueType)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.readValue(json, valueType);
	}
}
