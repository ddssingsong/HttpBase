package com.dds.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btn(View v) {
		switch (v.getId()) {
		case R.id.button1:
			startActivity(new Intent(this, NottpActivity.class));

			break;
		case R.id.button2:
			startActivity(new Intent(this, SoapActivity.class));

			break;
		case R.id.button3:

			break;
		case R.id.button4:

			break;

		case R.id.button5:

			break;
		case R.id.button6:

			break;

		default:
			break;
		}

	}

}
