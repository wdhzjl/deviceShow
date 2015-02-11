package com.example.deviceshow;

import java.io.InputStream;
import java.util.List;

import com.bosstun.adapter.ViewHolder;



import com.bosstun.localdata.Book;
import com.bosstun.localdata.BookParser;
import com.bosstun.localdata.PullBookParser;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class sim extends Activity {
     
    private static final String[] str_num={"13996991234","13808301234","13709491234"};
    private static final String[] str_tc={"动感地带(套餐费12元，包含50条短信，10M本地流量，本地（主叫0.1元/分钟，被叫免费），异地（主叫0.4元/分钟，被叫0.2元/分钟）)","全球通(套餐费58元，包含50条短信，100M全国流量，本地（主叫0.1元/分钟，被叫免费），异地（主叫0.4元/分钟，被叫免费）)","4G(套餐费20元，包含100条短信，500M本地流量，本地（主叫0.2元/分钟，被叫免费），异地（主叫0.6元/分钟，被叫0.4元/分钟）)"};
    private TextView view02,view03 ;
	private final String TAG = "sim";
    private Spinner spinner02,spinner03;
    private Button bt_sim_success,bt_sm,bt_pz;
    private ArrayAdapter<String> adapter02,adapter03;
    private Camera camera = null;
	private Boolean bCameropen = false;
	private Parameters cameraParameter;
	private static final String LOG_TAG = "XML";
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim);
      
        view02 = (TextView) findViewById(R.id.spinnerText02);
        spinner02 = (Spinner) findViewById(R.id.Spinner02);
        bt_sim_success=(Button)findViewById(R.id.sim_success);
        bt_sm=(Button)findViewById(R.id.bt_sm);
        bt_pz=(Button)findViewById(R.id.bt_pz);

        view03 = (TextView) findViewById(R.id.spinnerText03);
        spinner03 = (Spinner) findViewById(R.id.Spinner03);
        //将可选内容与ArrayAdapter连接起来 
        adapter02 = new ArrayAdapter<String>(this,R.layout.myspinner,str_num);
        adapter03 = new ArrayAdapter<String>(this,R.layout.myspinner,str_tc);
        //设置下拉列表的风格
        adapter02.setDropDownViewResource(R.layout.myspinner);
        adapter03.setDropDownViewResource(R.layout.myspinner);
        //将adapter 添加到spinner中
        spinner02.setAdapter(adapter02);
        spinner03.setAdapter(adapter03);
        //添加事件Spinner事件监听  
        spinner02.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner03.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner02.setVisibility(View.VISIBLE);
        spinner03.setVisibility(View.VISIBLE);
        
        bt_sim_success.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
             {
            	
            	//获取LayoutInflater对象，该对象能把XML文件转换为与之一直的View对象  
                LayoutInflater inflater = getLayoutInflater();  
                //根据指定的布局文件创建一个具有层级关系的View对象  
                //第二个参数为View对象的根节点，即LinearLayout的ID  
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast));                   
                //查找ImageView控件  
                //注意是在layout中查找  
                TextView text = (TextView) layout.findViewById(R.id.tx_toast);  
                text.setText("开卡成功");  
  
                Toast toast = new Toast(getApplicationContext());  
                //设置Toast的位置  
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
                toast.setDuration(Toast.LENGTH_LONG);  
                //让Toast显示为我们自定义的样子  
                toast.setView(layout);  
                toast.show();
//            	
//            	Toast.makeText(getApplicationContext(), "开卡成功"
//            		,Toast.LENGTH_SHORT).show();  
           	    	
            }
            
        });
        bt_sm.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
             {
		        Intent localIntent4 = new Intent(sim.this, IDCardInfoReaderActivity.class);
		        localIntent4.putExtra("isReadAll", true);
		        localIntent4.putExtra("isWeb", false);
		        localIntent4.putExtra("getImg", "1");
		       sim.this.startActivity(localIntent4);
            	
//            	Intent intent = new Intent(); 
//            	intent.setClassName("com.newland.activity", "com.newland.activity.MenuActivity");
//            	startActivity(intent); 	   
		       
		       
            }
            
        });   
        bt_pz.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
             {
//        		View paramView = null ;
//				ViewHolder localViewHolder = (ViewHolder) paramView.getTag();
//				if (!bCameropen) {
//					localViewHolder.setText(ConfigConstant.STR_CLOSE_FLASH);
//					localViewHolder.setImageResource(R.drawable.light_off);
//					initCamera();
//					Log.v(TAG, "supported Flash Modes:"+cameraParameter.getSupportedFlashModes());
//					cameraParameter.setFlashMode(Parameters.FLASH_MODE_ON);
//					cameraParameter.set("flash-mode", "on");
//					camera.setParameters(cameraParameter);
//					camera.startPreview();
//					camera.takePicture(null, null, null);
//					bCameropen = true;
//				} else {
//					camera.takePicture(null, null, null);
//					localViewHolder.setText(ConfigConstant.STR_OPEN_FLASH);
//					localViewHolder.setImageResource(R.drawable.light_on);
//					cameraParameter = camera.getParameters();
//					cameraParameter.setFlashMode(Parameters.FLASH_MODE_OFF);
//					camera.setParameters(cameraParameter);
//					camera.stopPreview();
//					camera.release();
//					bCameropen = false;
//				}

			
            	Intent intent = new Intent(); 
            	intent.setClassName("com.newland.activity", "com.newland.activity.MenuActivity");
            	startActivity(intent); 	    	
//            }
//                Intent localIntent3 = new Intent(sim.this, IdentifyCardReader.class);
//                Intent localIntent4 = new Intent(sim.this, IdentifyCardReader.class);
//		        localIntent3.putExtra("isReadAll", true);
//		        localIntent4.putExtra("isWeb", false);
//		        localIntent4.putExtra("getImg", "1");
//		       sim.this.startActivity(localIntent3);        	    	
            	
       } 
        });
        
        
        
   
    }  
    public void initCamera() {
		this.camera = Camera.open();
		this.cameraParameter = camera.getParameters();
	} 
    //使用数组形式操作
    class SpinnerSelectedListener implements OnItemSelectedListener{
 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            view02.setText("选择号码："+str_num[arg2]);
            view03.setText("选择套餐："+str_tc[arg2]);
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        } 
      
    }   

	protected void onDestroy() {
		super.onDestroy();
		Log.v("menu", "onDestroy");
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
			finish();
		return true;
	}

	protected void onPause() {
		super.onPause();
		Log.v("menu", "onPause");
		if (bCameropen) {
			camera.release();
			bCameropen = false;
		}
	}

	public void onResume() {
		super.onResume();
		Log.v("menu", "onResume");
		// setRequestedOrientation(0);
	}

	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(1024, 1024);
	}

	public void setNoTitle() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void getBook() {
		try {
			InputStream is = getAssets().open("books.xml");
			BookParser parser = new PullBookParser();
			List<Book> books = parser.parse(is);
			for (Book book : books) {
				Log.i(LOG_TAG, book.toString());
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}
    
} 
