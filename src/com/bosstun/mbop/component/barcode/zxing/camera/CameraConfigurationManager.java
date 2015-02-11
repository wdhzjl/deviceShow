package com.bosstun.mbop.component.barcode.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.regex.Pattern;

final class CameraConfigurationManager
{
  private static final Pattern COMMA_PATTERN;
  private static final int DESIRED_SHARPNESS = 30;
  private static final String TAG = CameraConfigurationManager.class.getSimpleName();
  private static final int TEN_DESIRED_ZOOM = 27;
  private Point cameraResolution;
  private final Context context;
  private int previewFormat;
  private String previewFormatString;
  private Point screenResolution;

  static
  {
    COMMA_PATTERN = Pattern.compile(",");
  }

  CameraConfigurationManager(Context paramContext)
  {
    this.context = paramContext;
  }

  private static int findBestMotZoomValue(CharSequence paramCharSequence, int paramInt)
  {
    int i = 0;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int j = arrayOfString.length;
    int k = 0;
    while (true)
    {
      if (k >= j)
        return i;
      String str = arrayOfString[k].trim();
      try
      {
        double d = Double.parseDouble(str);
        int l = (int)(10.0D * d);
        if (Math.abs(paramInt - d) < Math.abs(paramInt - i))
          i = l;
        ++k;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    
  }

  private static Point findBestPreviewSizeValue(CharSequence paramCharSequence, Point paramPoint)
  {
    int i = 0;
    int j = 0;
    int k = 2147483647;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int l = arrayOfString.length;
    int i1 = 0;
    if (i1 >= l)
    {
      if ((i > 0) && (j > 0))
        label25: return new Point(i, j);
    }
    else
    {
      String str = arrayOfString[i1].trim();
      int i2 = str.indexOf('x');
      if (i2 < 0)
        Log.w(TAG, "Bad preview-size: " + str);
//      while (true)
//      {
//        int i3;
//        int i4;
//        int i5;
//        while (true)
//        {
//          ++i1;
//          break label25:
//          try
//          {
//            i3 = Integer.parseInt(str.substring(0, i2));
//            i4 = Integer.parseInt(str.substring(i2 + 1));
//            i5 = Math.abs(i3 - paramPoint.x) + Math.abs(i4 - paramPoint.y);
//            if (i5 != 0)
//              break label197;
//            i = i3;
//            j = i4;
//          }
//          catch (NumberFormatException localNumberFormatException)
//          {
//            Log.w(TAG, "Bad preview-size: " + str);
//          }
//        }
//        continue;
//        label197: if (i5 >= k)
//          continue;
//        i = i3;
//        j = i4;
//        k = i5;
//      }
    }
    return null;
  }

  private static Point getCameraResolution(Camera.Parameters paramParameters, Point paramPoint)
  {
    String str = paramParameters.get("preview-size-values");
    if (str == null)
      str = paramParameters.get("preview-size-value");
    Point localPoint = null;
    if (str != null)
    {
      Log.d(TAG, "preview-size-values parameter: " + str);
      localPoint = findBestPreviewSizeValue(str, paramPoint);
    }
    if (localPoint == null)
      localPoint = new Point(paramPoint.x >> 3 << 3, paramPoint.y >> 3 << 3);
    return localPoint;
  }

  private void setFlash(Camera.Parameters paramParameters)
  {
//    if ((Build.MODEL.contains("Behold II")) && (CameraManager.SDK_INT == 3))
//      paramParameters.set("flash-value", 1);
//    while (true)
//    {
//      paramParameters.set("flash-mode", "off");
//      return;
//      paramParameters.set("flash-value", 2);
//    }
  }

  private void setZoom(Camera.Parameters paramParameters)
  {
//    String str1 = paramParameters.get("zoom-supported");
//    if ((str1 != null) && (!Boolean.parseBoolean(str1)))
//      return;
//    int i = 27;
//    String str2 = paramParameters.get("max-zoom");
//    if (str2 != null);
//    label60: String str3;
//    try
//    {
//      double d = Double.parseDouble(str2);
//      int i1 = (int)(10.0D * d);
//      if (i > i1)
//        i = i1;
//      str3 = paramParameters.get("taking-picture-zoom-max");
//      label154: if (str3 == null);
//    }
//    catch (NumberFormatException localNumberFormatException2)
//    {
//      String str4;
//      String str5;
//      try
//      {
//        int l = Integer.parseInt(str3);
//        if (i > l)
//          i = l;
//        str4 = paramParameters.get("mot-zoom-values");
//        if (str4 != null)
//          i = findBestMotZoomValue(str4, i);
//        str5 = paramParameters.get("mot-zoom-step");
//        if (str5 == null);
//      }
//      catch (NumberFormatException localNumberFormatException2)
//      {
//        try
//        {
//          int j = (int)(10.0D * Double.parseDouble(str5.trim()));
//          if (j > 1)
//          {
//            int k = i % j;
//            i -= k;
//          }
//          if ((str2 != null) || (str4 != null))
//            paramParameters.set("zoom", String.valueOf(i / 10.0D));
//          if (str3 != null);
//          paramParameters.set("taking-picture-zoom", i);
//          return;
//          localNumberFormatException3 = localNumberFormatException3;
//          Log.w(TAG, "Bad max-zoom: " + str2);
//          break label60:
//          localNumberFormatException2 = localNumberFormatException2;
//          Log.w(TAG, "Bad taking-picture-zoom-max: " + str3);
//        }
//        catch (NumberFormatException localNumberFormatException1)
//        {
//          break label154:
//        }
//      }
//    }
  }

  Point getCameraResolution()
  {
    return this.cameraResolution;
  }

  int getPreviewFormat()
  {
    return this.previewFormat;
  }

  String getPreviewFormatString()
  {
    return this.previewFormatString;
  }

  Point getScreenResolution()
  {
    return this.screenResolution;
  }

  void initFromCameraParameters(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    this.previewFormat = localParameters.getPreviewFormat();
    this.previewFormatString = localParameters.get("preview-format");
    Log.d(TAG, "Default preview format: " + this.previewFormat + '/' + this.previewFormatString);
    Display localDisplay = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
    this.screenResolution = new Point(localDisplay.getWidth(), localDisplay.getHeight());
    Log.d(TAG, "Screen resolution: " + this.screenResolution);
    this.cameraResolution = getCameraResolution(localParameters, this.screenResolution);
    Log.d(TAG, "Camera resolution: " + this.screenResolution);
  }

  void setDesiredCameraParameters(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    Log.d(TAG, "Setting preview size: " + this.cameraResolution);
    localParameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
    setFlash(localParameters);
    setZoom(localParameters);
    paramCamera.setParameters(localParameters);
  }
}