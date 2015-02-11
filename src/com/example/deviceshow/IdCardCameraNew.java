package com.example.deviceshow;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.bosstun.mbop.component.barcode.zxing.camera.CameraManager;
import com.bosstun.mbop.view.custom.CameraView;

public class IdCardCameraNew extends Activity implements SurfaceHolder.Callback {
	public static boolean isColse = false;
	private String ToastStr = "拍照识别";
	private Camera camera;
	private CameraView cameraView;
	private boolean isPreviewRunning = false;
	private boolean isTouchAlready = false;
	private final String TAG = "IdCardCameraNew";
	Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
			Log.i("XWXWXWXWXWXWXWXWXWX", "PIC WRITED");
			IDCardInfoReaderActivity.photoPic = paramArrayOfByte;
			Toast.makeText(IdCardCameraNew.this, IdCardCameraNew.this.ToastStr,
					Toast.LENGTH_SHORT).show();
			Intent localIntent = new Intent();
			localIntent.setClass(IdCardCameraNew.this, EIdPicNew.class);
			IdCardCameraNew.this.startActivity(localIntent);
			IdCardCameraNew.this.finish();
		}
	};
	private Camera.AutoFocusCallback mAutoFocusCallBack = new Camera.AutoFocusCallback() {
		public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
			Log.i("xw=======================>", "begin2 photos");
			if (paramBoolean) {
				Log.i("xw=======================>", "begin3 photos");
				try {
					IdCardCameraNew.this.camera.takePicture(
							IdCardCameraNew.this.mShutterCallback,
							IdCardCameraNew.this.mPictureCallbackRaw,
							IdCardCameraNew.this.jpegCallback);
					return;
				} catch (Exception localException2) {
					localException2.printStackTrace();
					return;
				}
			}
			try {
				IdCardCameraNew.this.camera.takePicture(
						IdCardCameraNew.this.mShutterCallback,
						IdCardCameraNew.this.mPictureCallbackRaw,
						IdCardCameraNew.this.jpegCallback);
				return;
			} catch (Exception localException1) {
				localException1.printStackTrace();
			}
		}
	};
	Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
			Log.i("xw=======================>", "onPictureTaken");
		}
	};
	Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		public void onShutter() {
			Log.i("xw=======================>", "onShutter");
		}
	};
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private Uri targetResource = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

	private void setPictureSize(Camera.Parameters paramParameters) {
		List<Size> localList = paramParameters.getSupportedPictureSizes();
		if (localList == null)
			return;
		for (Size size : localList ) {
			Log.v(TAG, "supported size: "+size.height+"*"+size.width);
		}
	}

	private void setPictureSize(Camera paramCamera) {
		Camera.Parameters localParameters = paramCamera.getParameters();
		localParameters.setPictureFormat(ImageFormat.JPEG);
		List<Size> localList = localParameters.getSupportedPictureSizes();
		if (localList == null)
			return;
		for (Size size : localList ) {
			Log.v(TAG, "supported size: "+size.height+"*"+size.width);
		}
	}

	public boolean isTouchAlready() {
		return this.isTouchAlready;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setFullscreen();
		setNoTitle();
		setTouchAlready(false);
		CameraManager.init(getApplication());
		Toast.makeText(this, "<<<<<正在拍照>>>>>", 0).show();
		getWindow().setFormat(-3);
		setContentView(R.layout.idcard_camera);
		this.cameraView = ((CameraView) findViewById(R.id.camera_view));
		this.cameraView.draw(new Canvas());
		this.surfaceView = ((SurfaceView) findViewById(R.id.preview_view));
		this.surfaceHolder = this.surfaceView.getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setType(3);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "View Pictures").setOnMenuItemClickListener(
				new MenuItem.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem paramMenuItem) {
						Intent localIntent = new Intent(
								"android.intent.action.VIEW",
								IdCardCameraNew.this.targetResource);
						IdCardCameraNew.this.startActivity(localIntent);
						return true;
					}
				});
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == 23)
			;
		try {
			this.camera.autoFocus(this.mAutoFocusCallBack);
			if ((keyCode == 4) && (event.getRepeatCount() == 0)) {
				isColse = true;
				finish();
			}
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return false;
	}

	protected void onStop() {
		super.onStop();
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case 0:
			return true;
		case 1:
			if (!isTouchAlready()) {
				Log.i("xw=======================>", "begin1 photos");
				setTouchAlready(true);
			}
			break;
		default:
			return true;
		}
		try {
			this.camera.autoFocus(this.mAutoFocusCallBack);
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return true;
	}

	public void setFullscreen() {
		requestWindowFeature(1);
		getWindow().setFlags(1024, 1024);
	}

	public void setNoTitle() {
		requestWindowFeature(1);
	}

	public void setTouchAlready(boolean paramBoolean) {
		this.isTouchAlready = paramBoolean;
		if (!paramBoolean)
			return;
		new Thread() {
			public void run() {
				try {
					sleep(3000L);
					IdCardCameraNew.this.isTouchAlready = false;
					return;
				} catch (InterruptedException localInterruptedException) {
					localInterruptedException.printStackTrace();
					IdCardCameraNew.this.isTouchAlready = false;
				}
			}
		}.start();
	}

	public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
			int paramInt2, int paramInt3) {
		if (this.isPreviewRunning)
			this.camera.stopPreview();
		try {
			this.camera.setPreviewDisplay(paramSurfaceHolder);
			this.camera.startPreview();
			this.isPreviewRunning = true;
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
		this.camera = Camera.open();
		Camera.Parameters localParameters = this.camera.getParameters();
		setPictureSize(this.camera);
		localParameters.set("jpeg-quality", 100);
		this.camera.setParameters(localParameters);
	}

	public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
		this.camera.stopPreview();
		this.isPreviewRunning = false;
		this.camera.release();
	}

	public class SizeComparator implements Comparator<Camera.Size> {
		public SizeComparator() {
		}

		public int compare(Camera.Size paramSize1, Camera.Size paramSize2) {
			return paramSize2.width * paramSize2.height - paramSize1.width
					* paramSize1.height;
		}
	}
}