package com.bosstun.mbop.view.custom;

import java.text.NumberFormat;




import com.example.deviceshow.R;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomProgressDialog extends CustomDialog
{
  public static final int STYLE_HORIZONTAL = 1;
  public static final int STYLE_SPINNER = 0;
  private boolean mHasStarted;
  private int mIncrementBy;
  private int mIncrementSecondaryBy;
  private boolean mIndeterminate;
  private Drawable mIndeterminateDrawable;
  private int mMax;
  private CharSequence mMessage;
  private TextView mMessageView;
  private ProgressBar mProgress;
  private Drawable mProgressDrawable;
  private TextView mProgressNumber;
  private String mProgressNumberFormat;
  private TextView mProgressPercent;
  private NumberFormat mProgressPercentFormat;
  private int mProgressStyle = 0;
  private int mProgressVal;
  private int mSecondaryProgressVal;
  private Handler mViewUpdateHandler;

  public CustomProgressDialog(Context paramContext)
  {
    this(paramContext, 0);
  }

  public CustomProgressDialog(Context paramContext, int paramInt)
  {
    super(paramContext);
  }

  private void onProgressChanged()
  {
    if (this.mProgressStyle != 1)
      return;
    this.mViewUpdateHandler.sendEmptyMessage(0);
  }

  public static CustomProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, false);
  }

  public static CustomProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, paramBoolean, false, null);
  }

  public static CustomProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, paramBoolean1, paramBoolean2, null);
  }

  public static CustomProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean1, boolean paramBoolean2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    CustomProgressDialog localCustomProgressDialog = new CustomProgressDialog(paramContext);
    localCustomProgressDialog.setMessage(paramCharSequence2);
    localCustomProgressDialog.setIndeterminate(paramBoolean1);
    localCustomProgressDialog.setCancelable(paramBoolean2);
    localCustomProgressDialog.setOnCancelListener(paramOnCancelListener);
    localCustomProgressDialog.setNoTitle();
    localCustomProgressDialog.show();
    return localCustomProgressDialog;
  }

  public int getMax()
  {
    if (this.mProgress != null)
      return this.mProgress.getMax();
    return this.mMax;
  }

  public int getProgress()
  {
    if (this.mProgress != null)
      return this.mProgress.getProgress();
    return this.mProgressVal;
  }

  public int getSecondaryProgress()
  {
    if (this.mProgress != null)
      return this.mProgress.getSecondaryProgress();
    return this.mSecondaryProgressVal;
  }

  public void incrementProgressBy(int paramInt)
  {
    if (this.mProgress != null)
    {
      this.mProgress.incrementProgressBy(paramInt);
      onProgressChanged();
      return;
    }
    this.mIncrementBy = (paramInt + this.mIncrementBy);
  }

  public void incrementSecondaryProgressBy(int paramInt)
  {
    if (this.mProgress != null)
    {
      this.mProgress.incrementSecondaryProgressBy(paramInt);
      onProgressChanged();
      return;
    }
    this.mIncrementSecondaryBy = (paramInt + this.mIncrementSecondaryBy);
  }

  public boolean isIndeterminate()
  {
    if (this.mProgress != null)
      return this.mProgress.isIndeterminate();
    return this.mIndeterminate;
  }

  protected void onCreate(Bundle paramBundle)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    if (this.mProgressStyle != 1)
    {
      View localView = localLayoutInflater.inflate(R.layout.progress_dialog, null);
      this.mProgress = ((ProgressBar)localView.findViewById(R.id.progress));
      this.mMessageView = ((TextView)localView.findViewById(R.id.message));
      setView(localView);
    }
    if (this.mMax > 0)
      setMax(this.mMax);
    if (this.mProgressVal > 0)
      setProgress(this.mProgressVal);
    if (this.mSecondaryProgressVal > 0)
      setSecondaryProgress(this.mSecondaryProgressVal);
    if (this.mIncrementBy > 0)
      incrementProgressBy(this.mIncrementBy);
    if (this.mIncrementSecondaryBy > 0)
      incrementSecondaryProgressBy(this.mIncrementSecondaryBy);
    if (this.mProgressDrawable != null)
      setProgressDrawable(this.mProgressDrawable);
    if (this.mIndeterminateDrawable != null)
      setIndeterminateDrawable(this.mIndeterminateDrawable);
    if (this.mMessage != null)
      setMessage(this.mMessage);
    setIndeterminate(this.mIndeterminate);
    onProgressChanged();
    super.onCreate(paramBundle);
  }

  public void onStart()
  {
    super.onStart();
    this.mHasStarted = true;
  }

  protected void onStop()
  {
    super.onStop();
    this.mHasStarted = false;
  }

  public void setIndeterminate(boolean paramBoolean)
  {
    if (this.mProgress != null)
    {
      this.mProgress.setIndeterminate(paramBoolean);
      return;
    }
    this.mIndeterminate = paramBoolean;
  }

  public void setIndeterminateDrawable(Drawable paramDrawable)
  {
    if (this.mProgress != null)
    {
      this.mProgress.setIndeterminateDrawable(paramDrawable);
      return;
    }
    this.mIndeterminateDrawable = paramDrawable;
  }

  public void setMax(int paramInt)
  {
    if (this.mProgress != null)
    {
      this.mProgress.setMax(paramInt);
      onProgressChanged();
      return;
    }
    this.mMax = paramInt;
  }

  public CustomDialog setMessage(CharSequence paramCharSequence)
  {
    if (this.mProgress != null)
    {
      if (this.mProgressStyle == 1)
      {
        super.setMessage(paramCharSequence);
        return this;
      }
      this.mMessageView.setText(paramCharSequence);
      return this;
    }
    this.mMessage = paramCharSequence;
    return this;
  }

  public void setProgress(int paramInt)
  {
    if (this.mHasStarted)
    {
      this.mProgress.setProgress(paramInt);
      onProgressChanged();
      return;
    }
    this.mProgressVal = paramInt;
  }

  public void setProgressDrawable(Drawable paramDrawable)
  {
    if (this.mProgress != null)
    {
      this.mProgress.setProgressDrawable(paramDrawable);
      return;
    }
    this.mProgressDrawable = paramDrawable;
  }

  public void setProgressNumberFormat(String paramString)
  {
    this.mProgressNumberFormat = paramString;
  }

  public void setProgressStyle(int paramInt)
  {
    this.mProgressStyle = paramInt;
  }

  public void setSecondaryProgress(int paramInt)
  {
    if (this.mProgress != null)
    {
      this.mProgress.setSecondaryProgress(paramInt);
      onProgressChanged();
      return;
    }
    this.mSecondaryProgressVal = paramInt;
  }
}