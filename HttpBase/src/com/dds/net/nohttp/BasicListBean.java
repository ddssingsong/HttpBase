package com.dds.net.nohttp;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 定制实体列表类
 * @author dds
 *
 */
public class BasicListBean implements Serializable {
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

	public BasicListBean() {

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

	public <E> List<E> parseDataList(Class<E> clazz) {
		List<E> e = null;

		try {
			e = JSON.parseArray(getData(), clazz);
		} catch (Exception e1) {
			// 服务端数据格式错误时，返回data的空构造
			e = null;
		}
		return e;

	}
}
