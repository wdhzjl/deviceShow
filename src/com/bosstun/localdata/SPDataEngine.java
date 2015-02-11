package com.bosstun.localdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SPDataEngine {
	private Editor editor;
	SharedPreferences sp;
	public SPDataEngine(Context ctx){
		this.sp = ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		this.editor = this.sp.edit();
	}
	public String getString(String key, String defValue){
		return sp.getString(key, defValue);
	}
	
	public int getInt(String key, int defValue){
		return sp.getInt(key, defValue);
	}
	
	public Boolean getBoolean(String key, boolean defValue){
		return sp.getBoolean(key, defValue);
	}
	
	public void putString(String key, String value){
		editor.putString(key, value);
	}
	
	public void putBoolean(String key, Boolean value){
		editor.putBoolean(key, value);
	}
	
	public void putCommit(){
		editor.commit();
	}
}
