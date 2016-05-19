package com.dds.soap;

import android.content.Context;

public class SoapUtil {
	/** 实例话对象. */
	private SoapClient mClient = null;

	/** AbSoapUtil实例. */
	
	private static SoapUtil mAbSoapUtil;

	/**
	 * 单例 获取AbSoapUtil实例.
	 * 
	 * @param context
	 *            the context
	 * @return single instance of AbSoapUtil
	 */
	public static SoapUtil getInstance(Context context) {
		if (null == mAbSoapUtil) {

			mAbSoapUtil = new SoapUtil(context);
		}
		return mAbSoapUtil;
	}

	/**
	 * AbSoapUtil构�?�方�?.
	 * 
	 * @param context
	 *            the context
	 */
	private SoapUtil(Context context) {
		super();
		this.mClient = new SoapClient(context);
	}

	/**
	 * Call.
	 * 
	 * @param url
	 *            the url
	 * @param nameSpace
	 *            the name space
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 * @param listener
	 *            the listener
	 */
	public void call(String url, String nameSpace, String methodName, SoapParams params, SoapListener listener) {
		mClient.call(url, nameSpace, methodName, params, listener);
	}

	/**
	 * 描述：设置连接超时时间(第一次请求前设置).
	 * 
	 * @param timeout
	 *            毫秒
	 */
	public void setTimeout(int timeout) {
		mClient.setTimeout(timeout);
	}

	/**
	 * Sets the dot net.
	 *
	 * @param dotNet
	 *            the new dot net
	 */
	public void setDotNet(boolean dotNet) {
		mClient.setDotNet(dotNet);
	}
}
