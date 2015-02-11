package com.bosstun.adapter;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder
{
  ImageView image;
  TextView textView;

  ViewHolder()
  {
  }
  
  public void setText(CharSequence text){
	  textView.setText(text);
  }
  
  public void setImageResource(int resId){
	  image.setImageResource(resId);
  }
}