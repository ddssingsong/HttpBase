package com.dds.net.nohttp;

import com.yolanda.nohttp.Response;

public interface HttpListener<T> {

	/**
	 * success
	 */
	void onSucceed(int what, Response<T> response);

	/**
	 * faild
	 */
	//void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);;

}