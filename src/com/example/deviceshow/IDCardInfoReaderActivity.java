package com.example.deviceshow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bosstun.mbop.common.BizFileHelper;
import com.bosstun.mbop.view.custom.CustomDialog;
import com.bosstun.mbop.view.custom.CustomProgressDialog;
import com.bosstun.utils.helper.CommonHelper;
import  com.yunmai.android.engine.OcrEngine;
import com.yunmai.android.vo.IDCard;

public class IDCardInfoReaderActivity extends Activity implements
		View.OnClickListener {
	public static final String EXTRA_ALL_IDCARD_INFO = "ALL_IDCARD_INFO";
	public static final String EXTRA_ERROR_DESC = "errorDesc";
	public static final String elePicFolderName = "elePic";
	public static IDCardImg idCardImg = null;
	public static boolean isShowMsg = true;
	public static byte[] photoPic;
	private final String TAG = "IDCardInfoReaderActivity";
	private String base64BitmapString = "";
	private Button btn_back;
	private Button btn_getphoto;
	private int carmela_tpye;
	private EditText edit_address;
	private EditText edit_create;
	private EditText edit_date;
	private EditText edit_id;
	private EditText edit_limit_date_end;
	private EditText edit_limit_date_start;
	private EditText edit_name;
	private EditText edit_sex;
	private EditText edit_type;
	private String getImg = "0";
	private MyHandler handler;
	private String[] iDCardInfo = { "", "", "", "", "", "", "", "", "" };
	private IDCard idCard;
	private boolean isA = true;
	private boolean isReadAll = false;
	public boolean isWeb = false;
	private String pic_path;
	private Dialog proressDialig;
	private SharedPreferences sp;
	private String sp_key_carmela_tpye = "sp_key_carmela_tpye";
	private String temp_path = Environment.getExternalStorageDirectory()
			+ "/IDCard.jpg";

	public static String bitmapToString(String paramString) {
		Bitmap localBitmap = getSmallBitmap(paramString);
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		localBitmap.compress(Bitmap.CompressFormat.JPEG, 40,
				localByteArrayOutputStream);
		return Base64.encodeToString(localByteArrayOutputStream.toByteArray(),
				0);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight && width >= reqWidth) {
			inSampleSize = 1;
		} else {
			int heightRatio = Math.round(height / reqHeight);
			int widthRatio = Math.round(width / reqHeight);
			inSampleSize = Math.min(heightRatio, widthRatio);
		}
		return inSampleSize;
	}

	private String getFileName(String before, String after) {
		Date localDate = new Date(System.currentTimeMillis());
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US)
				.format(localDate);
		if (before == null) {
			before = "";
		}
		if (after == null) {
			after = "";
		}

		StringBuilder str = new StringBuilder(String.valueOf(before)).append(
				fileName).append(after);

		return str.toString();
	}

	private String getPhotoFullFileName(String before, String after) {

		return CommonHelper.Path.getElePicFull() + getFileName(before, after);
	}

	public static Bitmap getSmallBitmap(String filePath) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, localOptions);
		localOptions.inSampleSize = calculateInSampleSize(localOptions, 480,
				800);
		localOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, localOptions);
	}

	private void init() {
		this.carmela_tpye = ConfigConstant.CUSTOM_CAMERA;
		idCardImg = new IDCardImg();
		this.handler = new MyHandler();
		this.edit_name = ((EditText) findViewById(R.id.edit_name));
		this.edit_id = ((EditText) findViewById(R.id.edit_id));
		this.edit_sex = ((EditText) findViewById(R.id.edit_sex));
		this.edit_type = ((EditText) findViewById(R.id.edit_type));
		this.edit_date = ((EditText) findViewById(R.id.edit_date));
		this.edit_address = ((EditText) findViewById(R.id.edit_address));
		this.edit_create = ((EditText) findViewById(R.id.edit_create));
		this.edit_limit_date_start = ((EditText) findViewById(R.id.edit_limit_date_start));
		this.edit_limit_date_end = ((EditText) findViewById(R.id.edit_limit_date_end));
		this.btn_getphoto = ((Button) findViewById(R.id.btn_get_idcard_photo));
		this.btn_getphoto.setOnClickListener(this);
		this.btn_back = ((Button) findViewById(R.id.back_btn));
		this.btn_back.setOnClickListener(this);
		returnrResult();
		if (this.isWeb) {
			findViewById(R.id.top_layout).setVisibility(View.GONE);
			findViewById(R.id.scllowview_read_idcard).setVisibility(View.GONE);
			takePhoto();
		}
		if (this.isReadAll) {
			this.btn_getphoto.setText("开始身份证识别(正面/反面))");
			findViewById(R.id.layout_create).setVisibility(View.VISIBLE);
			findViewById(R.id.layout_limit_date_end)
					.setVisibility(View.VISIBLE);
			findViewById(R.id.layout_limit_date_start).setVisibility(
					View.VISIBLE);
			return;
		}
		this.btn_getphoto.setText("开始身份证识别");
		findViewById(R.id.layout_create).setVisibility(View.GONE);
		findViewById(R.id.layout_limit_date_end).setVisibility(View.GONE);
		findViewById(R.id.layout_limit_date_start).setVisibility(View.GONE);
	}

	private void returnrResult() {
		Intent localIntent = new Intent();
		localIntent.putExtra("ALL_IDCARD_INFO", this.iDCardInfo);
		setResult(-1, localIntent);
	}

	private void showMessageDialog() {
		final CustomDialog cd = new CustomDialog(this);
		cd.setTitle("注意事项");
		cd.setMessage("1、拍摄时应尽量让身份证充满整个镜头，拍摄距离不宜过远。\r\n2、请将镜头处于身份证的正上方，减小倾角，忌倾斜，忌抖动。\r\n3、在光线良好的情况下，最好不要打开闪光灯。\r\n4、拍照前请先对焦，最好以身份证上的文字作为对焦的目标。");
		cd.setPositiveButton("不再提示", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramDialogInterface,
					int paramInt) {
				IDCardInfoReaderActivity.isShowMsg = false;
				cd.dismiss();
				IDCardInfoReaderActivity.this.takePhoto();
			}
		});
		cd.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramDialogInterface,
					int paramInt) {
				cd.dismiss();
				IDCardInfoReaderActivity.this.takePhoto();
			}
		});
		cd.show();
	}

	private void takePhoto() {
		if (this.carmela_tpye == ConfigConstant.CUSTOM_CAMERA) {
			takePhotoByMbop();
		} else if (this.carmela_tpye == ConfigConstant.SYS_CAMERA) {
			takePhotoBySys();
		}
	}

	private void takePhotoByMbop() {
		this.pic_path = getPhotoFullFileName(null, "IDCard.jpg");
		File localFile = new File(this.pic_path);
		if (localFile.exists())
			localFile.delete();
		photoPic = null;
		this.base64BitmapString = "";
		IdCardCameraNew.isColse = false;
		this.isA = true;
		startActivityForResult(new Intent(this, IdCardCameraNew.class), 1);
	}

	private void takePhotoBySys() {
		Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
		this.pic_path = getPhotoFullFileName(null, "IDCard.jpg");
		File localFile = new File(this.pic_path);
		if (localFile.exists())
			localFile.delete();
		localIntent.putExtra("output", Uri.fromFile(localFile));
		this.isA = true;
		this.base64BitmapString = "";
		startActivityForResult(localIntent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			readIdCardInfo();
		}
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.btn_get_idcard_photo:
			if (isShowMsg) {
				showMessageDialog();
				return;
			}
			takePhoto();
			return;
	case R.id.back_btn:
		default:
			finish();
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.read_idcard_info_new);
		((TextView) findViewById(R.id.navigation_title)).setText("身份证识别");
		if (getIntent().getExtras() != null) {
			this.isReadAll = getIntent().getExtras().getBoolean("isReadAll");
			this.getImg = getIntent().getExtras().getString("getImg");
			this.isWeb = getIntent().getExtras().getBoolean("isWeb");
		}
		init();
	}

	protected void onResume() {
		super.onResume();
		// if ((this.carmela_tpye == 0) && (photoPic != null))
		// readIdCardInfo();
		// if ((((this.carmela_tpye != 0) || (!IdCardCameraNew.isColse) ||
		// (!this.isWeb)))
		// && (((this.carmela_tpye != 1) || (!this.isWeb) || (new File(
		// this.temp_path).exists()))))
		// return;
		// finish();
	}

	public void readIdCardInfo() {
		if (photoPic == null)
			return;
		this.proressDialig = CustomProgressDialog.show(this, "", "正在识别，请稍等...");
		new Thread(new Runnable() {
			private final String TAG = "ORC Process Thread";

			public void run() {
				String idcard_path = IDCardInfoReaderActivity.this.pic_path;
				if (IDCardInfoReaderActivity.this.carmela_tpye == ConfigConstant.CUSTOM_CAMERA) {
					BizFileHelper.saveBitmapForJPG(EIdPicNew.mImagebitmap,
							idcard_path);
				}
				Log.v(TAG, "身份证识别路径pic_path：" + idcard_path);
				if (!new File(idcard_path).exists())
					return;
				OcrEngine orc = new OcrEngine();
				try {
					IDCardInfoReaderActivity.this.idCard = orc.recognize(
							IDCardInfoReaderActivity.this, idcard_path);
					int regCode = IDCardInfoReaderActivity.this.idCard
							.getRecogStatus();
					Log.v(TAG, "regCode=" + regCode);
					switch (regCode) {
					case OcrEngine.RECOG_OK:
					case OcrEngine.RECOG_SMALL:
					case OcrEngine.RECOG_BLUR:
					case OcrEngine.RECOG_LANGUAGE:
						IDCardInfoReaderActivity.this.handler
								.sendEmptyMessage(regCode);
						break;
					default:
						IDCardInfoReaderActivity.this.handler
								.sendEmptyMessage(OcrEngine.RECOG_FAIL);
						break;
					}
				} catch (Exception localException) {
					localException.printStackTrace();
					IDCardInfoReaderActivity.this.handler
							.sendEmptyMessage(OcrEngine.RECOG_FAIL);
					return;
				}
			}
		}).start();
	}

	public class IDCardImg {
		private String base64Bitmap_a = "";
		private String base64Bitmap_b = "";

		public IDCardImg() {
		}

		public String getBase64Bitmap_a() {
			return this.base64Bitmap_a;
		}

		public String getBase64Bitmap_b() {
			return this.base64Bitmap_b;
		}

		public void setBase64Bitmap_a(String paramString) {
			this.base64Bitmap_a = paramString;
		}

		public void setBase64Bitmap_b(String paramString) {
			this.base64Bitmap_b = paramString;
		}
	}

	private class MyHandler extends Handler {

		public void handleMessage(Message paramMessage) {
			System.out.println("handle message.what = " + paramMessage.what);
			Log.v(TAG,
					"IDCard:" + IDCardInfoReaderActivity.this.idCard.toString());
			if (IDCardInfoReaderActivity.this.proressDialig.isShowing())
				IDCardInfoReaderActivity.this.proressDialig.dismiss();
			String toastStr = "";
			switch (paramMessage.what) {
			case OcrEngine.RECOG_CANCEL:
				toastStr = "识别取消~";
				break;
			case OcrEngine.RECOG_OK:
				toastStr = "识别成功~";
				IDCard idCard = IDCardInfoReaderActivity.this.idCard;
				if (idCard != null) {
					Toast.makeText(IDCardInfoReaderActivity.this, "识别成功~", 0)
							.show();
					if (idCard.getName() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[0] = idCard
								.getName();
						IDCardInfoReaderActivity.this.edit_name
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[0]);
						System.out
								.println(IDCardInfoReaderActivity.this.edit_name
										.getText().toString());
					}
					if (IDCardInfoReaderActivity.this.idCard.getCardNo() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[1] = idCard
								.getCardNo();
						IDCardInfoReaderActivity.this.edit_id
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[1]);
					}
					if (IDCardInfoReaderActivity.this.idCard.getSex() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[2] = idCard
								.getSex();
						IDCardInfoReaderActivity.this.edit_sex
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[2]);
					}
					if (IDCardInfoReaderActivity.this.idCard.getBirth() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[3] = idCard
								.getBirth();
						IDCardInfoReaderActivity.this.edit_date
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[3]);
					}
					if (IDCardInfoReaderActivity.this.idCard.getAddress() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[4] = idCard
								.getAddress();
						IDCardInfoReaderActivity.this.edit_address
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[4]);
					}
					if (IDCardInfoReaderActivity.this.idCard.getEthnicity() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[5] = idCard
								.getEthnicity();
						IDCardInfoReaderActivity.this.edit_type
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[5]);
					}
					if (IDCardInfoReaderActivity.this.idCard.getAuthority() != null) {
						IDCardInfoReaderActivity.this.iDCardInfo[6] = idCard
								.getAuthority();
						IDCardInfoReaderActivity.this.edit_create
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[6]);
					}
					if ((idCard.getPeriod() != null)
							&& (!idCard.getPeriod().trim().equals(""))
							&& (IDCardInfoReaderActivity.this.idCard
									.getPeriod().contains("-"))) {
						IDCardInfoReaderActivity.this.iDCardInfo[7] = idCard
								.getPeriod();
						IDCardInfoReaderActivity.this.iDCardInfo[7] = idCard
								.getPeriod().substring(0,
										idCard.getPeriod().indexOf("-"));
						IDCardInfoReaderActivity.this.edit_limit_date_start
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[7]);
						IDCardInfoReaderActivity.this.iDCardInfo[8] = idCard
								.getPeriod().substring(
										1 + idCard.getPeriod().indexOf("-"),
										idCard.getPeriod().length());
						IDCardInfoReaderActivity.this.edit_limit_date_end
								.setText(IDCardInfoReaderActivity.this.iDCardInfo[8]);
					}
				}
				break;
			case OcrEngine.RECOG_FAIL:
				toastStr = "识别失败~";
				break;
			case OcrEngine.RECOG_SMALL:
				toastStr = "图像太小~";
				break;
			case OcrEngine.RECOG_BLUR:
				toastStr = "图形模糊~";
				break;
			case OcrEngine.RECOG_LANGUAGE:
				toastStr = "识别语言错误~";
				break;
			default:
				break;
			}
			Toast.makeText(IDCardInfoReaderActivity.this, toastStr, 0).show();

		}
	}

}