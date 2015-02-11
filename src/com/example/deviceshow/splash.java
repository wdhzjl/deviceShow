package com.example.deviceshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 3000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					// 启动主应用
					startActivity(new Intent(
							"com.example.deviceshow.MainActivity"));

				}
			}
		};
		splashTread.start();
	}

}