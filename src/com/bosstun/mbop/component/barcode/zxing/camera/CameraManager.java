package com.bosstun.mbop.component.barcode.zxing.camera;

import java.io.IOException;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public final class CameraManager
{
  private static final int MAX_FRAME_HEIGHT = 360;
  private static final int MAX_FRAME_WIDTH = 480;
  private static final int MIN_FRAME_HEIGHT = 240;
  private static final int MIN_FRAME_WIDTH = 240;
  static final int SDK_INT = 18;
  private static final String TAG = CameraManager.class.getSimpleName();
  private static CameraManager cameraManager;
  private final AutoFocusCallback autoFocusCallback;
  private Camera camera;
  private final CameraConfigurationManager configManager;
  private final Context context;
  private Rect framingRect;
  private Rect framingRectInPreview;
  private boolean initialized;
  private final PreviewCallback previewCallback;
  private boolean previewing;
  private final boolean useOneShotPreviewCallback;

//  static
//  {
//    int i;
//    try
//    {
//      int j = Integer.parseInt(Build.VERSION.SDK);
//      i = j;
//      SDK_INT = i;
//      return;
//    }
//    catch (NumberFormatException localNumberFormatException)
//    {
//      i = 10000;
//    }
//  }

  private CameraManager(Context paramContext)
  {
    this.context = paramContext;
    this.configManager = new CameraConfigurationManager(paramContext);
    if (Integer.parseInt(Build.VERSION.SDK) > 3);
//    for (boolean i = 1; ; i = 0)
//    {
      this.useOneShotPreviewCallback = true;
      this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
      this.autoFocusCallback = new AutoFocusCallback();
      return;
//    }
  }

  public static CameraManager get()
  {
    return cameraManager;
  }

  public static void init(Context paramContext)
  {
    if (cameraManager != null)
      return;
    cameraManager = new CameraManager(paramContext);
  }

  public void closeDriver()
  {
//    if (this.camera == null)
//      return;
//    FlashlightManager.disableFlashlight();
//    this.camera.release();
//    this.camera = null;
  }

  public Rect getFramingRect()
  {
	  return new Rect();
//    Point localPoint = this.configManager.getScreenResolution();
//    int i;
//    if (this.framingRect == null)
//    {
//      if (this.camera == null)
//        return null;
//      i = 3 * localPoint.x / 4;
//      if (i >= 240)
//        break label138;
//      i = 240;
//      label44: j = 3 * localPoint.y / 4;
//      if (j >= 240)
//        break label152;
//    }
//    for (int j = 240; ; j = 360)
//      do
//      {
//        int k = (localPoint.x - i) / 2;
//        int l = (localPoint.y - j) / 2;
//        this.framingRect = new Rect(k, l, k + i, l + j);
//        Log.d(TAG, "Calculated framing rect: " + this.framingRect);
//        return this.framingRect;
//        label138: if (i > 480);
//        i = 480;
//        label152: break label44:
//      }
//      while (j <= 360);
  }

  public Rect getFramingRectInPreview()
  {
    if (this.framingRectInPreview == null)
    {
      Rect localRect = new Rect(getFramingRect());
      Point localPoint1 = this.configManager.getCameraResolution();
      Point localPoint2 = this.configManager.getScreenResolution();
      localRect.left = (localRect.left * localPoint1.x / localPoint2.x);
      localRect.right = (localRect.right * localPoint1.x / localPoint2.x);
      localRect.top = (localRect.top * localPoint1.y / localPoint2.y);
      localRect.bottom = (localRect.bottom * localPoint1.y / localPoint2.y);
      this.framingRectInPreview = localRect;
    }
    return this.framingRectInPreview;
  }

  public void openDriver(SurfaceHolder paramSurfaceHolder)
    throws IOException
  {
    if (this.camera != null)
      return;
    this.camera = Camera.open();
    if (this.camera == null)
      throw new IOException();
    this.camera.setPreviewDisplay(paramSurfaceHolder);
    if (!this.initialized)
    {
      this.initialized = true;
      this.configManager.initFromCameraParameters(this.camera);
    }
    this.configManager.setDesiredCameraParameters(this.camera);
  }

  public void requestAutoFocus(Handler paramHandler, int paramInt)
  {
    if ((this.camera == null) || (!this.previewing))
      return;
    this.autoFocusCallback.setHandler(paramHandler, paramInt);
    this.camera.autoFocus(this.autoFocusCallback);
  }

  public void requestPreviewFrame(Handler paramHandler, int paramInt)
  {
//    if ((this.camera != null) && (this.previewing))
//    {
//      this.previewCallback.setHandler(paramHandler, paramInt);
//      if (!this.useOneShotPreviewCallback)
//        break label42;
//      this.camera.setOneShotPreviewCallback(this.previewCallback);
//    }
//    return;
//    label42: this.camera.setPreviewCallback(this.previewCallback);
  }

  public void startPreview()
  {
    if ((this.camera == null) || (this.previewing))
      return;
    this.camera.startPreview();
    this.previewing = true;
  }

  public void stopPreview()
  {
//    if ((this.camera == null) || (!this.previewing))
//      return;
//    if (!this.useOneShotPreviewCallback)
//      this.camera.setPreviewCallback(null);
//    this.camera.stopPreview();
//    this.previewCallback.setHandler(null, 0);
//    this.autoFocusCallback.setHandler(null, 0);
//    this.previewing = false;
  }
}