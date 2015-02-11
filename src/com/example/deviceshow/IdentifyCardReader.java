package com.example.deviceshow;

import com.bosstun.mbop.common.CustomDialogHelper;
import com.bosstun.mbop.view.custom.CustomDialog;
import com.bosstun.utils.helper.DialogHelper;
import com.newland.nativepackage.IDCard;
import com.yunmai.android.vo.IdcInfo;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;

public class IdentifyCardReader extends Activity  {
	  private static final int IDCARD_INITERROR = 2;
	  private static final int IDCARD_OPENERROR = 1;
	  private static final int IDCARD_THROW = 6;
	  private static final int LOAD_PATH_ERROR = 5;
	  private static final int MESSAGE_FAILURE = 4;
	  private static final int MESSAGE_TOAST = 3;
	  private static final int REQUEST_DISPLAY = 1;
	  private static final int REQUEST_ENABLE_BT = 2;
	  public static final String EXTRA_ALL_IDCARD_INFO = "ALL_IDCARD_INFO";
	  private final String TAG = "IdentifyCardReaderActivity";
	  private Button backButton,btn_back,bt_get;
	  private boolean btEnabled;
	  private Dialog proressDialig; 
	  private Handler handler;
	  private String[] idCardInfo;
	  private int idCardType=3;
	  private byte[] img;
	  private byte[] arrayOfByte;;
	  private IdcInfo info;
	  boolean isReaderTest;
	  private boolean isReadAll = false;
	  public boolean isWeb = false;
	  private View.OnClickListener mDeviceClickListener;
	  private ArrayAdapter<String> mDevicesArrayAdapter;
	  private Handler mHandler;
	  private Runnable myRunnable;
	  private Thread mthread;
	  private final String DeviceId="/dev/ttyS1";
	  private final  String password="1234";
	  private int result;
	  private ImageButton searchBtn;
	  private View.OnClickListener searchClickListener;
	  private DialogHelper waitDialog = new CustomDialogHelper(this);
	  private String[] arrayOfString = { "", "", "", "", "", "", "", "", "" };
	  private EditText edit_address;
	 private EditText edit_create;
	private EditText edit_date;
	private EditText edit_id;
	private EditText edit_limit_date_end;
	private EditText edit_limit_date_start;
	private EditText edit_name;
	private EditText edit_sex;
	private EditText edit_type;
	private String getImg; 

		   
	  public void  IdentifyCardReaderActivity()
	  {	 
	    this.idCardInfo = arrayOfString;
	    this.img = new byte[62000];

	    this.btEnabled = true;
	    this.mHandler = new Handler();
//	    this.mDeviceClickListener = new IdentifyCardReaderActivity.3(this);
//	    this.result = 0;
//	    this.myRunnable = new IdentifyCardReaderActivity.4(this);
//	    this.handler = new IdentifyCardReaderActivity.5(this);
//	    this.mReceiver = new IdentifyCardReaderActivity.6(this);
	  }

	  private void idcRead()
	  {	
		  if (!loadLibs());  
		  final String TAG = "IDC Process Thread";
		   this.waitDialog.displayMessage("身份证识别", "正在获取身份证信息");
//		   new Thread(new Runnable(){
//	     public void run()
//	     {
		   IDCard idcard =new IDCard ();
	    	 IdcInfo idcInfo = new IdcInfo(); 
	    	 idcard.openIDCard(idCardType, DeviceId,password);
//		   if( idcard.openIDCard(idCardType, DeviceId,password)==0) {
//			   Toast.makeText(IdentifyCardReader.this, "未连接二代证模块", 0).show();	   	   
//		   }	  
	    	 idcard.getIdCardInfo(this.idCardInfo,this.img);
//	     this.returnrResult( idcard.getIdCardInfo(this.idCardInfo,this.img),this.getIntent());	 
//	     this. = getIntent().getExtras().getBoolean("isReadAll");
		  idcard.closeIDCard();
		   if (idcInfo.getName() != null) {
			   IdentifyCardReader.this.idCardInfo[0] = idcInfo.getName();
			   IdentifyCardReader.this.edit_name
						.setText(IdentifyCardReader.this.idCardInfo[0]);
				System.out
						.println(IdentifyCardReader.this.edit_name
								.getText().toString());
			}
			if ( idcInfo.getNo()!= null) {
				 IdentifyCardReader.this.idCardInfo[1] = idcInfo.getNo();
				 IdentifyCardReader.this.edit_id
						.setText( IdentifyCardReader.this.idCardInfo[1]);
			}
			if ( idcInfo.getSex() != null) {
				 IdentifyCardReader.this.idCardInfo[2] = idcInfo.getSex();
				 IdentifyCardReader.this.edit_sex
						.setText( IdentifyCardReader.this.idCardInfo[2]);
			}
			if (idcInfo.getBirthday() != null) {
				 IdentifyCardReader.this.idCardInfo[3] =idcInfo.getBirthday();
				 IdentifyCardReader.this.edit_date
						.setText( IdentifyCardReader.this.idCardInfo[3]);
			}
			if (idcInfo.getAddress() != null) {
				 IdentifyCardReader.this.idCardInfo[4] =idcInfo
						.getAddress();
				 IdentifyCardReader.this.edit_address
						.setText( IdentifyCardReader.this.idCardInfo[4]);
			}
			if (idcInfo.getDepartment()!= null) {
				 IdentifyCardReader.this.idCardInfo[5] = idcInfo.getDepartment();
				 IdentifyCardReader.this.edit_type
						.setText( IdentifyCardReader.this.idCardInfo[5]);
			}
			if (idcInfo.getNation()!= null) {
				 IdentifyCardReader.this.idCardInfo[6] = idcInfo
						 .getNation();
				 IdentifyCardReader.this.edit_create
						.setText( IdentifyCardReader.this.idCardInfo[6]);
			}
			if ((idcInfo.getBase64Bitmap()!= null)
					&& (!idcInfo.getBase64Bitmap().trim().equals(""))
					&& (idcInfo.getBase64Bitmap().contains("-"))) {
				 IdentifyCardReader.this.idCardInfo[7] = idcInfo.getBase64Bitmap();
				 IdentifyCardReader.this.idCardInfo[7] = idcInfo.getBase64Bitmap().substring(0,
						 idcInfo.getBase64Bitmap().indexOf("-"));
				 IdentifyCardReader.this.edit_limit_date_start
						.setText(	IdentifyCardReader.this.idCardInfo[7]);
				 IdentifyCardReader.this.idCardInfo[8] = idcInfo.getBase64Bitmap().substring(
								1 + idcInfo.getBase64Bitmap().indexOf("-"),
								idcInfo.getBase64Bitmap().length());
//				 IdentifyCardReader.this.edit_limit_date_end
//						.setText(IdenmtifyCardReader.this.idCardInfo[8]);				
			}			 
//	     }	    
//   }).start();   
		
	  }

//  private class myRunnable implements  Runnable {
//	    	
//	    public void run(){
//	    	
//	    	    		    		    	
//	    }
//	    
//  }		    		    		    	    	    	 
	  private void init()
	  {
			 this.edit_name = ((EditText) findViewById(R.id.edit_name));
				this.edit_id = ((EditText) findViewById(R.id.edit_id));
				this.edit_sex = ((EditText) findViewById(R.id.edit_sex));
				this.edit_type = ((EditText) findViewById(R.id.edit_type));
				this.edit_date = ((EditText) findViewById(R.id.edit_date));
				this.edit_address = ((EditText) findViewById(R.id.edit_address));
				this.edit_create = ((EditText) findViewById(R.id.edit_create));
				this.edit_limit_date_start = ((EditText) findViewById(R.id.edit_limit_date_start));
				this.edit_limit_date_end = ((EditText) findViewById(R.id.edit_limit_date_end));
				this.btn_back = ((Button) findViewById(R.id.back_btn));
				this.bt_get=((Button) findViewById(R.id.btn_get_idcard_photo));
	   // this.isReaderTest = getIntent().getBooleanExtra("readertest", false);
  
		
//	    this.backButton = ((Button)findViewById(2131361853));
//	    this.backButton.setOnClickListener(new IdentifyCardReaderActivity.7(this));	
	returnrResult(0, null);
//	bt_get.setOnClickListener(new OnClickListener(){
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			idcRead();	
//		}
//
//		
//	});
		
	  }
		private void returnrResult(int paramInt2, Intent paramIntent) {
			Intent localIntent = new Intent();
			localIntent.putExtra("ALL_IDCARD_INFO", this.arrayOfString);
			setResult(-1, localIntent);
			 finish();
			
		}	
//	   public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//		  {
//		    switch (paramInt1)
//		    {
//		    default:
//		    case 1:
//		    case 2:
//		    }
//		    while (true)
//		    {
//		      return;
//		      Intent localIntent = new Intent();
//		      localIntent.putExtra("idcInfo", this.info);
//		      setResult(-1, localIntent);
//		      finish();
//		      continue;
//		    
//		    }
//		  }
  		
protected void onCreate(Bundle paramBundle)
{
//	 setResult(0);
	super.onCreate(paramBundle);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.read_idcard_info_new);
//	((TextView) findViewById(R.id.navigation_title)).setText("二代证读取");
//	if (getIntent().getExtras() != null) {
	this.isReadAll = getIntent().getExtras().getBoolean("isReadAll");
	this.getImg = getIntent().getExtras().getString("getImg");
//		this.isWeb = getIntent().getExtras().getBoolean("isWeb");
	

}


//public void onClick(View paramView) {
//	
//	switch (paramView.getId()) {
//	case R.id.btn_get_idcard_photo:
//		idcRead();
//	}
//}	
private class MyHandler extends Handler {
public void  handleMessage(Message paramMessage)
{
  switch (paramMessage.what)
  {
  default:
  }
  while (true)
  {
    IdentifyCardReader.this.proressDialig.dismiss();
    Toast.makeText(IdentifyCardReader.this, "连接二代证设备失败", 0).show();
    continue;
  }
}
}
//class mThread extends Thread
//{
//  public void run()
//  {
//    if (!loadLibs());
//     
//        if (IDCard.openIDCard(idCardType, DeviceId,password ) == 0)
//        break;
//    
//	if (IDCard.initialIDCard() != 0)
//   //IdentifyCardReaderActivity.access$10(this.this$0).sendEmptyMessage(2);
//      byte[] arrayOfByte = new byte[3];
//      IDCard.getIDCardVersion(arrayOfByte);
//      System.out.println("version:" + new String(arrayOfByte));
//      IdentifyCardReader.onActivityResult(this., IDCard.getIdCardInfo(IdentifyCardReaderActivity.access$9(this.this$0), IdentifyCardReaderActivity.access$11(this.this$0)));
//      IDCard.closeIDCard();
//      IdentifyCardReaderActivity.access$10(this.this$0).post(IdentifyCardReaderActivity.access$19(this.this$0));
//    }
//  }


public boolean loadLibs()
 {
 	System.loadLibrary("CMCC_IDCARD_BOSSTUN_UA100");   
 	return true;
 }   

public void onStart()
{
  super.onStart();
}

protected void onStop()
{
  super.onStop();
}

public void setNoTitle()
{
  requestWindowFeature(1);
}

}
