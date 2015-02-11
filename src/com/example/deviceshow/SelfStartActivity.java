package com.example.deviceshow;

import java.io.InputStream;
import java.util.List;

import com.bosstun.localdata.Book;
import com.bosstun.localdata.BookParser;
import com.bosstun.localdata.PullBookParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SelfStartActivity extends Activity {
	private static final String LOG_TAG = "XML";
	
	@Override
	public void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		getBook();
		//startMainActivity();
	}
	
	public void getBook() {
		try {
			InputStream is = getAssets().open("books.xml");
			BookParser parser = new PullBookParser();
			List<Book> books = parser.parse(is);
			for (Book book : books) {
				Log.i(LOG_TAG, book.toString());
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}
	
	public void startMainActivity(){
        Intent ootStartIntent=new Intent(SelfStartActivity.this,MainActivity.class);
        ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ootStartIntent);
        finish();
	}
}
