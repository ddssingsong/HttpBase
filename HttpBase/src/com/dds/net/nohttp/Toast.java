package com.dds.net.nohttp;

import com.dds.net.MyApplication;

/**
 * 
 * 
 * @author dds
 *
 */
public class Toast {
	public static void show(CharSequence msg) {
		android.widget.Toast.makeText(MyApplication.getInstance(), msg, android.widget.Toast.LENGTH_LONG).show();
	}

	public static void show(int stringId) {
		android.widget.Toast.makeText(MyApplication.getInstance(), stringId, android.widget.Toast.LENGTH_LONG).show();
	}

}
