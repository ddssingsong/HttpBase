package com.dds.net;

import java.util.List;

import com.dds.net.bean.NewsInfo;
import com.dds.net.nohttp.BasicListBean;
import com.dds.net.nohttp.BeanJsonRequest;
import com.dds.net.nohttp.CallServer;
import com.dds.net.nohttp.HttpListener;
import com.dds.net.utils.ToastUtil;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;
import com.yolanda.nohttp.cache.CacheMode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NottpActivity extends Activity {
	private TextView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nohttp);
		test = (TextView) findViewById(R.id.test);

		String url = "http://apis.baidu.com/txapi/tiyu/tiyu";

		Request<BasicListBean> request = new BeanJsonRequest<BasicListBean>(url, BasicListBean.class);
		// 1. Http标准协议的缓存，比如响应码是304时,这个不写就可以
		// request.setCacheMode(CacheMode.DEFAULT);
		// 2. 当请求服务器失败、超时、网络差、服务器崩溃时，正常显示数据
		request.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
		// 3.非标准Http协议，改变缓存模式为仅仅读取缓存
		// request.setCacheMode(CacheMode.ONLY_READ_CACHE);
		// 4.非标准Http协议，改变缓存模式为如果发现有缓存直接成功，没有缓存才请求服务器
		// request.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST);

		request.add("num", 10);
		request.add("page", 1);

		CallServer.getRequestInstance().add(this, 1, request, response, true, true);

	}

	HttpListener<BasicListBean> response = new HttpListener<BasicListBean>() {

		@Override
		public void onSucceed(int what, Response<BasicListBean> response) {
			ToastUtil.showToast(NottpActivity.this, "成功返回数据");
			BasicListBean javaBean = response.get();
			if (javaBean.isSuccess()) {
				List<NewsInfo> newslist = (List<NewsInfo>) javaBean.parseDataList(NewsInfo.class);
				test.setText(newslist.get(0).toString());

			} else {
				// 业务处理层数据失败
			}

		}

	};

}
