
package com.dds.soap;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import android.text.TextUtils;

/**
 * 名称：AbAppException.java 描述：公共异常类.
 *
 */
@SuppressWarnings("deprecation")
public class SoapAppException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** 异常消息. */
	private String msg = null;

	/**
	 * 构造异常类.
	 *
	 * @param e
	 *            异常
	 */
	public SoapAppException(Exception e) {
		super();

		try {
			if (e instanceof HttpHostConnectException) {
				msg = SoapAppConfig.UNKNOWN_HOST_EXCEPTION;
			} else if (e instanceof ConnectException) {
				msg = SoapAppConfig.CONNECT_EXCEPTION;
			} else if (e instanceof ConnectTimeoutException) {
				msg = SoapAppConfig.CONNECT_EXCEPTION;
			} else if (e instanceof UnknownHostException) {
				msg = SoapAppConfig.UNKNOWN_HOST_EXCEPTION;
			} else if (e instanceof SocketException) {
				msg = SoapAppConfig.SOCKET_EXCEPTION;
			} else if (e instanceof SocketTimeoutException) {
				msg = SoapAppConfig.SOCKET_TIMEOUT_EXCEPTION;
			} else if (e instanceof NullPointerException) {
				msg = SoapAppConfig.NULL_POINTER_EXCEPTION;
			} else if (e instanceof ClientProtocolException) {
				msg = SoapAppConfig.CLIENT_PROTOCOL_EXCEPTION;
			} else {
				if (e == null || TextUtils.isEmpty(e.getMessage())) {
					msg = SoapAppConfig.NULL_MESSAGE_EXCEPTION;
				} else {
					msg = e.getMessage();
				}
			}
		} catch (Exception e1) {
		}

	}

	/**
	 * 用一个消息构造异常类.
	 *
	 * @param message
	 *            异常的消消息
	 */
	public SoapAppException(String message) {
		super(message);
		msg = message;
	}

	/**
	 * 描述：获取异常信息.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return msg;
	}

}
