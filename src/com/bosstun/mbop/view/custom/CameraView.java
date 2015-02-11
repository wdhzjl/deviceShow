package com.bosstun.mbop.view.custom;


import com.example.deviceshow.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public final class CameraView extends View
{
  private Bitmap camera_01;
  private Bitmap camera_02;
  private Bitmap camera_03;
  private Bitmap camera_04;
  private Context context;
  private Rect framingRect;
  private Rect rect;
  private final int resultColor;
  private Point screenResolution;

  public CameraView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    Resources localResources = getResources();
    this.resultColor = localResources.getColor(R.color.viewfinder_frame);
    this.camera_01 = BitmapFactory.decodeResource(localResources, R.drawable.camera_1);
    this.camera_02 = BitmapFactory.decodeResource(localResources, R.drawable.camera_2);
    this.camera_03 = BitmapFactory.decodeResource(localResources, R.drawable.camera_3);
    this.camera_04 = BitmapFactory.decodeResource(localResources, R.drawable.camera_4);
  }

  public Rect getFramingRect()
  {
    Display localDisplay = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
    this.screenResolution = new Point(localDisplay.getWidth(), localDisplay.getHeight());
    if (this.framingRect == null)
    {
      int i = this.screenResolution.x;
      int j = this.screenResolution.y;
      System.out.println(i + "=width" + j + "=height");
      this.framingRect = new Rect(20, 20, i - 20 - this.camera_04.getHeight(), j - 20 - this.camera_04.getHeight());
    }
    return this.framingRect;
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setColor(this.resultColor);
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setAlpha(400);
    this.rect = getFramingRect();
    paramCanvas.drawBitmap(this.camera_01, this.rect.left, this.rect.top, localPaint);
    paramCanvas.drawBitmap(this.camera_02, this.rect.left, this.rect.bottom, localPaint);
    paramCanvas.drawBitmap(this.camera_03, this.rect.right, this.rect.top, localPaint);
    paramCanvas.drawBitmap(this.camera_04, this.rect.right, this.rect.bottom, localPaint);
  }
}