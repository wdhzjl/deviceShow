package com.bosstun.mbop.common;

import android.content.Context;

public class CoreCommonMethod
{
  private static final String SER_OBJ_NAME = "ser_obj";

  public static String getLastLinkedSimReaderDeviceName(Context paramContext)
  {
    return paramContext.getSharedPreferences(SER_OBJ_NAME, 0).getString("SIM_READER_DEVICE_NAME", "");
  }
}