package com.example.deviceshow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String action_boot = "android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "BootBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)){
        	Log.v(TAG, "OnReceive BOOT_COMPLETED");
//            Intent ootStartIntent=new Intent(context,SelfStartActivity.class);
//            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(ootStartIntent);
        	Intent srvIntent = new Intent(context, SelfStartService.class);
//        	Bundle bundle = new Bundle();
//    		bundle.putInt("op", op);
//    		startService.putExtras(bundle);
    		context.startService(srvIntent);
    		
        }

    }
    

}