package com.example.deviceshow;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import  com.example.deviceshow.pay;

public class PrintHandler extends Handler{
	  public static final int PRINT_COMPLETE = 29;
	  private pay context;
	  
	  public PrintHandler(pay  parampay)
	  {
	    this.context = parampay;
	  }
public void handleMessage (Message paramMessage)
	  {
	
	   super.handleMessage(paramMessage);
	      switch (paramMessage.what){    
	      case 29:
	          //this.context.getWaitDialog().dismiss();
	          Toast.makeText(this.context, "½É·Ñ³É¹¦",Toast.LENGTH_LONG).show();
	         this.context.setResult(MainActivity.RESULT_OK);
	         // this.context.finish();
	          break;       	 
	    }  	  
}
}

