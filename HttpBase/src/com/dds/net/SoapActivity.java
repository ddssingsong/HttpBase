package com.dds.net;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import com.dds.net.utils.ToastUtil;
import com.dds.soap.SoapListener;
import com.dds.soap.SoapParams;
import com.dds.soap.SoapUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SoapActivity extends Activity {
	TextView text;
	SoapUtil soapUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soap);
		text = (TextView) findViewById(R.id.text);

		String nameSpace = "http://tempuri.org/";
		String url = "http://182.92.172.134:33333/MainServices";
		String methodName = "GetCurrentEveryOneStockMarketQuotations";

		soapUtil = SoapUtil.getInstance(this);
		soapUtil.setDotNet(true);
		SoapParams params = new SoapParams();
		params.put("CurrentCustomerName", "");
		params.put("StockID", "150194");

		/**
		 * 请求
		 */
		soapUtil.call(url, nameSpace, methodName, params, listener);

	}

	SoapListener listener = new SoapListener() {

		@Override
		public void onSuccess(int statusCode, SoapObject object) {
			ToastUtil.showToast(SoapActivity.this, "	成功");
			String str = object.getPropertyAsString(0);
			text.setText(str);

		}

		@Override
		public void onFailure(int statusCode, SoapFault fault) {

		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {

		}
	};

}
