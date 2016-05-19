package com.dds.net.nohttp;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.download.DownloadQueue;

import android.content.Context;

public class CallServer {

	private static CallServer callServer;

	/**
	 * RequestQueue
	 */
	private RequestQueue requestQueue;
	/**
	 * downloadQueue
	 */
	private static DownloadQueue downloadQueue;

	private CallServer() {
		requestQueue = NoHttp.newRequestQueue();
	}

	/**
	 * singleRequestInstance
	 */
	public synchronized static CallServer getRequestInstance() {
		if (callServer == null)
			callServer = new CallServer();
		return callServer;
	}

	/**
	 * singleDownloadInstance
	 */
	public static DownloadQueue getDownloadInstance() {
		if (downloadQueue == null)
			downloadQueue = NoHttp.newDownloadQueue();
		return downloadQueue;
	}

	/**
	 * 添加一个请求到请求队列
	 *
	 * @param context
	 *            context用来实例化dialog
	 * @param what
	 *            用来标志请求，在回调方法中会返回这个what，类似handler的what
	 * @param request
	 *            请求对象
	 * @param callback
	 *            结果回调对象
	 * @param canCancel
	 *            是否允许用户取消请求
	 * @param isLoading
	 *            是否显示dialog
	 */
	public <T> void add(Context context, int what, Request<T> request, HttpListener<T> callback, boolean canCancel,
			boolean isLoading) {
		requestQueue.add(what, request, new HttpResponseListener<T>(context, request, callback, canCancel, isLoading));
	}

	/**
	 * 取消这个sign标记的所有请求
	 */
	public void cancelBySign(Object sign) {
		requestQueue.cancelBySign(sign);
	}

	/**
	 * 取消队列中所有请求
	 */
	public void cancelAll() {
		requestQueue.cancelAll();
	}

	/**
	 * 退出app时停止所有请求
	 */
	public void stopAll() {
		requestQueue.stop();
	}

}