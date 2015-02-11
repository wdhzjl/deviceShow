package com.example.deviceshow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.bosstun.mbop.common.BitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class EIdPicNew extends Activity
{
  public static Bitmap mImagebitmap = null;
  private ImageView mImageView;

  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(1024, 1024);
    setContentView(R.layout.eidpic);
    this.mImageView = ((ImageView)findViewById(R.id.IDPIC));
    mImagebitmap = null;
    Intent startingIntent = getIntent();
    Toast.makeText(this, "<<<µã»÷ÆÁÄ»·µ»Ø>>>", Toast.LENGTH_SHORT).show();
    if(startingIntent != null){
    	Bundle b = startingIntent.getExtras();
    	if(bundle != null){
//    		if(bundle.getString("SIDE").equals("A")){
//    			mImageView.setImageBitmap(BitmapUtils.bytes2Bimap(IdCardPhotoCollectActivity.frontPic));
//    		}else{
//    			mImageView.setImageBitmap(BitmapUtils.bytes2Bimap(IdCardPhotoCollectActivity.oppositePic));
//    		}
    	}else{
    		InputStream inputStream = new ByteArrayInputStream(IDCardInfoReaderActivity.photoPic);
    		Options opts = new BitmapFactory.Options();
    		opts.inTempStorage = new byte[102400];
    		opts.inPreferredConfig = Bitmap.Config.RGB_565;
    		opts.inPurgeable = true;
    		opts.inInputShareable = true;
    		mImagebitmap = BitmapFactory.decodeStream(inputStream, null, opts);
    		mImageView.setImageBitmap(mImagebitmap);
    	}
    }
    mImageView.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EIdPicNew.this.finish();
		}
	});
    
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
      finish();
    return true;
  }
}