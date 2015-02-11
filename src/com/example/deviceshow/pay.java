package com.example.deviceshow;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.newland.nativepackage.PrintContent;
import com.newland.nativepackage.PrintBlock;
import com.newland.nativepackage.PrintSet;
import com.newland.nativepackage.Printer;










import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
 
public class pay extends Activity {
     
    private static final String[] m={"10元","20元","30元","50元","100元","其他"};
	private static  String DeviceId ;
	private static  int printerType =4;
	private static  String password ="1234";
    private TextView view ;
    private Spinner spinner;
    private Button bt_pay_success;
    private String contentGlb,str,str1,str2;
    private int   paramInt;
    private ArrayAdapter<String> adapter;
	private byte[] version;
    private  TelephonyManager deviceId; 
    private EditText et_num;
 
    private PrintHandler mhandler = new PrintHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
         
        view = (TextView) findViewById(R.id.spinnerText);
        spinner = (Spinner) findViewById(R.id.Spinner01);
        bt_pay_success=(Button)findViewById(R.id.pay_success);
        et_num=(EditText)findViewById(R.id.num);
        
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,R.layout.myspinner,m);        
        //设置下拉列表的风格
        adapter.setDropDownViewResource(R.layout.myspinner);       
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);        
        //添加事件Spinner事件监听  
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());        
        //设置默认值
        spinner.setVisibility(View.VISIBLE);    
        bt_pay_success.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
             {
//            	Intent intent = new Intent(); 
//            	intent.setClassName("com.newland.activity", "com.newland.activity.MenuActivity");
//            	startActivity(intent); 
//            	getPrintContent(null);           	
            	printTest();           	
             }    
       });  
 
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss     ");     
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
        str   =   formatter.format(curDate);     
        
    }       
	//使用数组形式操作
    class SpinnerSelectedListener implements OnItemSelectedListener{
 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            view.setText("充值金额："+m[arg2]);
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    
    private int getIntValue(Element paramElement, String paramString, int paramInt)
    {
      if ("".equals(paramElement.getAttribute(paramString)));
      while (true)
      {
        //return paramInt;
        paramInt = Integer.parseInt(paramElement.getAttribute(paramString));
      }
    }
     
    private void printTest()
    {
      if (!loadLibs());  
      str1=et_num.getText().toString(); 
      str2= view.getText().toString();
       // return;
//        try
//        { 
//          if (Printer.openPrinter(this.printerType, this.deviceId, this.password) == 0)
//            break label75;
//          this.mHandler.sendEmptyMessage(2);
//        }
//        catch (Exception localException)
//        {
//          this.mHandler.obtainMessage(4, localException.getMessage()).sendToTarget();
//        }
//        continue;
//        if (Printer.initialPrinter() != 0)
//        {
//          label57: Printer.closePrinter();
//          this.mHandler.sendEmptyMessage(1);
    	  DeviceId  ="/dev/ttyS2";
    	Printer.openPrinter( printerType, DeviceId,password);
    	//Printer.getPrinterVersion(this.version);
       // Printer.print("获取组件版本信息是：" + new String(this.version) + "\r\n");
    	Printer.print("重庆博士顿科技有限公司");
    	  Printer.print("\r\n\r\n\r\n");
       Printer.initialPrinter();	
//        Printer.setZoonIn(1, 2);
//        Printer.print("设置字体宽高缩放比例1:2\r\n");
//        Printer.setZoonIn(2,1) ;
//        Printer.print("设置字体宽高缩放比例2:1\r\n");
//        Printer.setZoonIn(1, 1);
//        Printer.print("手机号码");
//        Printer.initialPrinter();
//        Printer.setAlignType(0);
//        Printer.print(str1+"\r\n");
//        Printer.setAlignType(1);
//        Printer.print("客户名称");
//        Printer.setAlignType(2);
//        Printer.print("王明");
       Printer.initialPrinter();
       Printer.setLeftMargin(24);
       Printer.print("营业点：\r\n");
       Printer.initialPrinter();
       Printer.print("\r\n\r\n");
       Printer.setLeftMargin(24);
       Printer.print("受理工号：0275634\r\n");
       Printer.print("\r\n\r\n");
        Printer.initialPrinter();
        Printer.setLeftMargin(24);
        Printer.print("手机号码："+str1+"\r\n");
        Printer.print("\r\n\r\n");
        Printer.initialPrinter();
        Printer.setLeftMargin(24);
        Printer.print(str2+"\r\n");
        Printer.print("\r\n\r\n");
        Printer.initialPrinter();
        Printer.setLeftMargin(24);
        Printer.print(str+"\r\n");
//        Printer.initialPrinter();
//        Printer.setAlignType(2);
//        Printer.setRightMargin(24);
//        Printer.print(str2 +"\r\n");
//        Printer.initialPrinter();
//        Printer.setLineSpacingByDotPitch(15);
//        Printer.print("设置行间距：15个点距\r\n");
//        Printer.initialPrinter();
//        Printer.setWordSpacingByDotPitch(5);
//        Printer.print("设置字符间距:5个点距\r\n");
//        Printer.initialPrinter();
//        Printer.setPrintOrientation(0);
//        Printer.print("设置打印方向:竖打\r\n");
//         Printer.initialPrinter();
//         Printer.setBold(0);
//         Printer.print(str);
//        Printer.setBold(1);
//        Printer.print("设置/取消粗体打印:设置\r\n");
//        Printer.initialPrinter();
//        Printer.setUnderLine(0);
//        Printer.print("设置/取消下划线打印:取消\r\n");
//        Printer.setUnderLine(1);
//        Printer.print("设置/取消下划线打印:设置\r\n");
//        Printer.initialPrinter();
//        Printer.setInverse(0);
//        Printer.print("设置/取消反白打印:取消\r\n");
//        Printer.setInverse(1);
//        Printer.print("设置/取消反白打印:设置\r\n");        
        Printer.print("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
        Printer.closePrinter();
        //this.mhandler.sendEmptyMessage(29);
       this.mhandler.obtainMessage(29).sendToTarget();      
    }    
 

   public boolean loadLibs()
    {
    	System.loadLibrary("CMCC_PRINT_BOSSTUN_UA100");   
    	return true;
    }         

}
