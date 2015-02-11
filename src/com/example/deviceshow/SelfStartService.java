package com.example.deviceshow;

import java.io.InputStream;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bosstun.localdata.Book;
import com.bosstun.localdata.BookParser;
import com.bosstun.localdata.PullBookParser;
import com.bosstun.localdata.SettingParams;
import com.bosstun.localdata.SettingParamsParser;

public class SelfStartService extends Service {
	private static final String TAG = "SelfStartService";
	private SettingParams settingParams;
	
	@Override 
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "onStart");
		parseSettingParams();
		if(settingParams.getBSelfStart()){
			startMainActivity();
		}
		stopSelf();
	}
	
	public void parseSettingParams(){
		try {
			InputStream is = getAssets().open("SettingParams.xml");
			SettingParamsParser parser = new SettingParamsParser();
			settingParams = parser.parse(is);
			Log.i(TAG, settingParams.toString());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public void getBook() {
		try {
			InputStream is = getAssets().open("books.xml");
			BookParser parser = new PullBookParser();
			List<Book> books = parser.parse(is);
			for (Book book : books) {
				Log.i(TAG, book.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public void startMainActivity(){
        Intent ootStartIntent=new Intent(getBaseContext(),MainActivity.class);
        ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ootStartIntent);
	}
	
}
