package com.bosstun.mbop.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ImageProcessUtil
{
  public static int dipToPx(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  public static int pxToDip(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
  }
}
