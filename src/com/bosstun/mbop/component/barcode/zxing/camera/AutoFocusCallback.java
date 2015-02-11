package com.bosstun.mbop.component.barcode.zxing.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

final class AutoFocusCallback
  implements Camera.AutoFocusCallback
{
  private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
  private static final String TAG = AutoFocusCallback.class.getSimpleName();
  private Handler autoFocusHandler;
  private int autoFocusMessage;

  public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
  {
    if (this.autoFocusHandler != null)
    {
      Message localMessage = this.autoFocusHandler.obtainMessage(this.autoFocusMessage, Boolean.valueOf(paramBoolean));
      this.autoFocusHandler.sendMessageDelayed(localMessage, 1500L);
      this.autoFocusHandler = null;
      return;
    }
    Log.d(TAG, "Got auto-focus callback, but no handler for it");
  }

  void setHandler(Handler paramHandler, int paramInt)
  {
    this.autoFocusHandler = paramHandler;
    this.autoFocusMessage = paramInt;
  }
}

/* Location:           D:\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name:     com.newland.mbop.component.barcode.zxing.camera.AutoFocusCallback
 * JD-Core Version:    0.5.4
 */