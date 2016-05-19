package com.dds.net;

import com.yolanda.nohttp.NoHttp;

import android.app.Application;

public class MyApplication extends Application {

	 private static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		NoHttp.init(this);

	}
	
	 public static MyApplication getInstance() {
	        return instance;
	    }

}
