package com.dds.soap;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressWarnings("deprecation")
public class SoapClient {

	private static Context mContext;

	public static Executor mExecutorService = null;

	private boolean mDotNet = true;

	private SoapParams mParams = null;

	public static final int DEFAULT_SOCKET_TIMEOUT = 10000;

	private int mTimeout = DEFAULT_SOCKET_TIMEOUT;

	public SoapClient(Context context) {
		mContext = context;
		mExecutorService = SoapThreadFactory.getExecutorService();

	}

	public void call(final String url, final String nameSpace, final String methodName, SoapParams Params,
			final SoapListener listener) {
		this.mParams = Params;

		if (!isNetworkAvailable(mContext)) {
			listener.sendFailureMessage(SoapHttpStatus.CONNECT_FAILURE_CODE, SoapAppConfig.CONNECT_EXCEPTION,
					new SoapAppException(SoapAppConfig.CONNECT_EXCEPTION));
			return;
		}

		// listener.sendStartMessage();

		mExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					doCall(url, nameSpace, methodName, mParams, listener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void doCall(String url, String nameSpace, String methodName, SoapParams params, SoapListener listener) {
		try {
			SoapObject request = new SoapObject(nameSpace, methodName);

			List<BasicNameValuePair> paramsList = params.getParamsList();
			for (NameValuePair nameValuePair : paramsList) {
				request.addProperty(nameValuePair.getName(), nameValuePair.getValue());
			}
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = request;
			envelope.dotNet = mDotNet;
			envelope.setOutputSoapObject(request);
			HttpTransportSE httpTransportSE = new HttpTransportSE(url, mTimeout);
			// httpTransportSE.debug = true;

			// action-----------------------------这里需要修改--------------------------------
			String soapAction = nameSpace + "InterfaceServerDefinition/" + methodName;
			httpTransportSE.call(soapAction, envelope);
			Object object = envelope.bodyIn;
			if (object instanceof SoapObject) {
				SoapObject bodyIn = (SoapObject) envelope.bodyIn;
				if (bodyIn != null) {
					listener.sendSuccessMessage(200, bodyIn);
				}
			} else if (object instanceof SoapFault) {
				SoapFault fault = (SoapFault) envelope.bodyIn;
				if (fault != null) {
					listener.sendFailureMessage(SoapHttpStatus.SERVER_FAILURE_CODE, fault);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			listener.sendFailureMessage(SoapHttpStatus.UNTREATED_CODE, SoapAppConfig.UNTREATED_EXCEPTION,
					new SoapAppException(SoapAppConfig.UNTREATED_EXCEPTION));
		} finally {
			listener.sendFinishMessage();
		}
	}

	/**
	 * 描述：判断网络是否有网络.
	 *
	 * @param context
	 *            the context
	 * @return true, if is network available
	 */
	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 描述：设置连接超时时间.
	 *
	 * @param timeout
	 *            毫秒
	 */
	public void setTimeout(int timeout) {
		this.mTimeout = timeout;
	}

	/**
	 * Checks if is dot net.
	 *
	 * @return true, if is dot net
	 */
	public boolean isDotNet() {
		return mDotNet;
	}

	/**
	 * Sets the dot net.
	 *
	 * @param dotNet
	 *            the new dot net
	 */
	public void setDotNet(boolean dotNet) {
		this.mDotNet = dotNet;
	}

}
