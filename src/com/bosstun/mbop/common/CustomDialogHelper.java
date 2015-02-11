package com.bosstun.mbop.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

import com.bosstun.utils.helper.DialogHelper;
import com.bosstun.mbop.view.custom.CustomDialog;
import com.bosstun.mbop.view.custom.CustomProgressDialog;
import com.example.deviceshow.R;


public class CustomDialogHelper extends DialogHelper
{
  private Context c;
  private Handler h;
  private CustomProgressDialog pbar = null;

  public CustomDialogHelper(Context paramContext)
  {
    super(paramContext);
    this.c = paramContext;
  }

  public CustomDialogHelper(Context paramContext, Handler paramHandler)
  {
    super(paramContext, paramHandler);
    this.c = paramContext;
    this.h = paramHandler;
  }

public static void alert(String paramString1, String paramString2, Context paramContext)
  {
    CustomDialog localCustomDialog = new CustomDialog(paramContext);
    localCustomDialog.setTitle(paramString1);
    localCustomDialog.setMessage(paramString2);
    localCustomDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
      }
    });
    localCustomDialog.setCancelable(true);
    //localCustomDialog.create();
    localCustomDialog.show();
  }

  public void changeMessage(String paramString1, String paramString2)
  {
    if (this.pbar == null)
      this.pbar = CustomProgressDialog.show(this.c, paramString1, paramString2, true);
    this.pbar.setMessage(paramString2);
    this.pbar.show();
  }

  public void changeMessage(String paramString1, String paramString2, final Runnable paramRunnable)
  {
	
    if (this.pbar == null)
      this.pbar = CustomProgressDialog.show(this.c, paramString1, paramString2, true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          if ((CustomDialogHelper.this.h != null) && (paramRunnable != null));
          try
          {
            CustomDialogHelper.this.h.post(paramRunnable);
            return;
          }
          catch (RuntimeException localRuntimeException)
          {
            localRuntimeException.printStackTrace();
          }
        }
      });
    this.pbar.setMessage(paramString2);
    this.pbar.show();
  }

  public void dismiss()
  {
    if (this.pbar == null)
      return;
    this.pbar.dismiss();
    this.pbar = null;
  }

  public void displayMessage(String paramString1, String paramString2)
  {
    if (this.pbar != null)
      dismiss();
    this.pbar = CustomProgressDialog.show(this.c, paramString1, paramString2, true);
    this.pbar.show();
  }

  public void displayMessage(String paramString1, String paramString2, final Runnable paramRunnable)
  {
    if (this.pbar != null)
      dismiss();
    this.pbar = CustomProgressDialog.show(this.c, paramString1, paramString2, true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        if ((CustomDialogHelper.this.h != null) && (paramRunnable != null));
        try
        {
          CustomDialogHelper.this.h.post(paramRunnable);
          return;
        }
        catch (RuntimeException localRuntimeException)
        {
          localRuntimeException.printStackTrace();
        }
      }
    });
    this.pbar.show();
  }

  public void displayMessageSecond(String paramString1, String paramString2)
  {
    if (this.pbar != null)
      dismiss();
    this.pbar = CustomProgressDialog.show(this.c, paramString1, paramString2, true);
    this.pbar.show();
    new Thread()
    {
      public void run()
      {
        try
        {
          sleep(2000L);
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          return;
        }
        finally
        {
          CustomDialogHelper.this.pbar.dismiss();
        }
      }
    }
    .start();
  }
}