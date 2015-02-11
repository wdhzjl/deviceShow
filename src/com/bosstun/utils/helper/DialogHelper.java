package com.bosstun.utils.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

public class DialogHelper
{
  private Context c;
  private Handler h;
  private ProgressDialog pbar = null;

  public DialogHelper(Context paramContext)
  {
    this.c = paramContext;
  }

  public DialogHelper(Context paramContext, Handler paramHandler)
  {
    this.c = paramContext;
    this.h = paramHandler;
  }

  public static void alert(String paramString1, String paramString2, Context paramContext)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
      }
    });
    localBuilder.setCancelable(true);
    localBuilder.create();
    localBuilder.show();
  }

  public void changeMessage(String paramString1, String paramString2)
  {
    if (this.pbar == null)
      this.pbar = ProgressDialog.show(this.c, paramString1, paramString2, true);
    this.pbar.setMessage(paramString2);
    this.pbar.show();
  }

//  public void changeMessage(String paramString1, String paramString2, Runnable paramRunnable)
//  {
//    if (this.pbar == null)
//      this.pbar = ProgressDialog.show(this.c, paramString1, paramString2, true, true, new DialogInterface.OnCancelListener()
//      {
//        public void onCancel(DialogInterface paramDialogInterface)
//        {
//          if ((DialogHelper.this.h != null) && (this.val$canCancelRun != null));
//          try
//          {
//            DialogHelper.this.h.post(this.val$canCancelRun);
//            return;
//          }
//          catch (RuntimeException localRuntimeException)
//          {
//            localRuntimeException.printStackTrace();
//          }
//        }
//      });
//    this.pbar.setMessage(paramString2);
//    this.pbar.show();
//  }

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
    this.pbar = ProgressDialog.show(this.c, paramString1, paramString2, true);
    this.pbar.show();
  }

//  public void displayMessage(String paramString1, String paramString2, Runnable paramRunnable)
//  {
//    if (this.pbar != null)
//      dismiss();
//    this.pbar = ProgressDialog.show(this.c, paramString1, paramString2, true, true, new DialogInterface.OnCancelListener(paramRunnable)
//    {
//      public void onCancel(DialogInterface paramDialogInterface)
//      {
//        if ((DialogHelper.this.h != null) && (this.val$canCancelRun != null));
//        try
//        {
//          DialogHelper.this.h.post(this.val$canCancelRun);
//          return;
//        }
//        catch (RuntimeException localRuntimeException)
//        {
//          localRuntimeException.printStackTrace();
//        }
//      }
//    });
//    this.pbar.show();
//  }

  public void displayMessageSecond(String paramString1, String paramString2)
  {
    if (this.pbar != null)
      dismiss();
    this.pbar = ProgressDialog.show(this.c, paramString1, paramString2, true);
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
          DialogHelper.this.pbar.dismiss();
        }
      }
    }
    .start();
  }
}