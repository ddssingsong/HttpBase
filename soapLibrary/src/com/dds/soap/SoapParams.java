package com.dds.soap;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.message.BasicNameValuePair;

@SuppressWarnings("deprecation")
public class SoapParams {
	/** 参数. */
	protected ConcurrentHashMap<String, String> params;

	/**
	 * Instantiates a new ab soap params.
	 */
	public SoapParams() {
		init();
		
	
	}

	/**
	 * Instantiates a new ab soap params.
	 * 
	 * @param obj
	 *            the obj
	 */
	public SoapParams(Object obj) {
		try {
			init();
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				String fieldValue = (String) field.get(obj);
				params.put(fieldName, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用一个map构�?�请求参�?.
	 * 
	 * @param source
	 *            the source
	 */
	public SoapParams(Map<String, String> source) {
		init();

		for (Map.Entry<String, String> entry : source.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 初始�?.
	 */
	private void init() {
		params = new ConcurrentHashMap<String, String>();
	}

	/**
	 * 增加�?对请求参�?.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(String key, String value) {
		if (key != null && value != null) {
			params.put(key, value);
		}
	}

	/**
	 * 删除�?个请求参�?.
	 * 
	 * @param key
	 *            the key
	 */
	public void remove(String key) {
		params.remove(key);
	}

	/**
	 * 获取参数列表.
	 * 
	 * @return the params list
	 */
	public List<BasicNameValuePair> getParamsList() {
		List<BasicNameValuePair> paramsList = new LinkedList<BasicNameValuePair>();
		for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
			paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return paramsList;
	}

}
