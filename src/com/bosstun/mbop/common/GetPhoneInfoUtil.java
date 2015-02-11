package com.bosstun.mbop.common;

import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;
import java.util.HashMap;
import java.util.Map;

public class GetPhoneInfoUtil
{
  public static Map<String, Integer> getScreenDisplay(Activity paramActivity)
  {
    Display localDisplay = paramActivity.getWindowManager().getDefaultDisplay();
    HashMap localHashMap = new HashMap();
    localHashMap.put("width", Integer.valueOf(localDisplay.getWidth()));
    localHashMap.put("height", Integer.valueOf(localDisplay.getHeight()));
    return localHashMap;
  }
}