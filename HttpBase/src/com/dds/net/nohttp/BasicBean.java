package com.dds.net.nohttp;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 定制实体类
 * @author dds
 *
 */
public class BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@JSONField(name = "code")
	private int code;
	@JSONField(name = "msg")
	private String msg;
	@JSONField(name = "newslist")
	private String data;

	/**
	 * 业务是否成功
	 */
	public boolean isSuccess() {
		return getCode() == 200;
	}

	public BasicBean() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 你的{@link E}必须提供默认无参构造
	 * 
	 * @param clazz
	 *            要解析的实体类的class
	 * @return 实体类
	 */
	public <E> E parseData(Class<E> clazz) {
		E e = null;

		try {
			e = JSON.parseObject(getData(), clazz);
		} catch (Exception e1) {
			// 服务端数据格式错误时，返回data的空构造
			try {
				e = clazz.newInstance();
			} catch (Exception e2) {
			}
		}
		return e;
	}

}
