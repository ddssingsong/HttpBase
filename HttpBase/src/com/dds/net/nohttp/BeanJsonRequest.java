package com.dds.net.nohttp;

import com.alibaba.fastjson.JSON;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.RestRequest;
import com.yolanda.nohttp.request.JsonObjectRequest;
import com.yolanda.nohttp.request.StringRequest;

/**
 * 请求类，返回实体类
 * 
 * @author dds
 *
 * @param <E>
 *            使用泛型表示返回的实体类型
 */
public class BeanJsonRequest<E> extends RestRequest<E> {

	private Class<E> clazz;

	public BeanJsonRequest(String url, Class<E> clazz) {
		this(url, RequestMethod.GET, clazz);
	}

	public BeanJsonRequest(String url, RequestMethod requestMethod, Class<E> clazz) {
		super(url, requestMethod);
		this.clazz = clazz;

	}

	@Override
	public E parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
		String string = StringRequest.parseResponseString(url, responseHeaders, responseBody);
		try {
			return JSON.parseObject(string, clazz);
		} catch (Exception e) {
			E instance = null;
			try {
				// 服务端返回数据格式错误时，返回一个空构造
				// 但是前提是传进来的JavaBean必须提供了默认实现
				instance = clazz.newInstance();
			} catch (Exception e1) {
			}
			return instance;
		}
	}

	@Override
	public String getAccept() {
		return JsonObjectRequest.ACCEPT;
	}

}
