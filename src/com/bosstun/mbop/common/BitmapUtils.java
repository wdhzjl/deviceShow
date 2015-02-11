package com.bosstun.mbop.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

public class BitmapUtils
{
  public static Bitmap bytes2Bimap(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 0)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inDither = false;
      localOptions.inPurgeable = true;
      localOptions.inTempStorage = new byte[12288];
      return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, localOptions);
    }
    return null;
  }

  public static Bitmap drawTextAtBitmap(Bitmap paramBitmap, String paramString)
  {
    if (paramBitmap == null)
      return null;
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
    localCanvas.save();
    localPaint.setAntiAlias(true);
    localPaint.setStyle(Paint.Style.FILL);
    localPaint.setColor(-3355444);
    localPaint.setTextSize(30.0F);
    localPaint.setAlpha(100);
    Log.d("MOP", "x/2:" + i / 2 + ", y/2:" + j / 2);
    Point localPoint = new Point(50 + i / 4, j - 100);
    Log.d("MOP", "x:" + localPoint.x + ", y:" + localPoint.y);
    localCanvas.translate(localPoint.x, localPoint.y);
    localCanvas.rotate(-45.0F);
    localCanvas.drawText(paramString, 0.0F, 0.0F, localPaint);
    localCanvas.save(31);
    localCanvas.restore();
    return localBitmap;
  }

  public static Bitmap generatorBitmapWithNum(Bitmap paramBitmap, int paramInt)
  {
    int i = 4 * paramBitmap.getWidth() / 3;
    Bitmap localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    int j = -5 + (i - paramBitmap.getHeight());
    Paint localPaint1 = new Paint();
    localPaint1.setDither(true);
    localPaint1.setFilterBitmap(true);
    localCanvas.drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), new Rect(0, j, paramBitmap.getWidth(), i - 5), localPaint1);
    Paint localPaint2 = new Paint(257);
    localPaint2.setColor(-1);
    localPaint2.setTextSize(14.0F);
    localPaint2.setTextAlign(Paint.Align.CENTER);
    localPaint2.setTypeface(Typeface.DEFAULT_BOLD);
    Paint localPaint3 = new Paint(257);
    localPaint3.setColor(-65536);
    if (paramInt <= 99)
    {
      localCanvas.drawCircle(i - 10, 10.0F, 10, localPaint3);
      localCanvas.drawText(String.valueOf(paramInt), i - 10, 15.0F, localPaint2);
      return localBitmap;
    }
    localCanvas.drawCircle(i - 10, 10.0F, 10, localPaint3);
    localPaint2.setTextSize(18.0F);
    localCanvas.drawText("...", i - 10, 12.0F, localPaint2);
    return localBitmap;
  }

  public static Bitmap mergeBitmap(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    Paint localPaint = new Paint(4);
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap1.getWidth(), paramBitmap1.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawBitmap(paramBitmap1, 0.0F, 0.0F, localPaint);
    localCanvas.drawBitmap(paramBitmap2, paramBitmap1.getWidth() - paramBitmap2.getWidth(), paramBitmap1.getHeight() - paramBitmap2.getHeight(), localPaint);
    return localBitmap;
  }
}