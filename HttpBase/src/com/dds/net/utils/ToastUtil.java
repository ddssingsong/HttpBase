package com.dds.net.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	/**
	 * Yes. you just can show one toast
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showToast(Context context, int resId) {
		showToast(context, context.getString(resId));
	}

	public static void showToast(Context context, String s) {
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);

			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	/**
	 * where what you want to showToast
	 * 
	 * @param context
	 * @param resId
	 * @param gravity
	 * @param offX
	 * @param offY
	 */
	public static void showToast(Context context, int resId, int gravity, int offX, int offY) {
		showToast(context, context.getString(resId), gravity, offX, offY);
	}

	public static void showToast(Context context, String s, int gravity, int offX, int offY) {
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.setGravity(gravity, offX, offY);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

}
