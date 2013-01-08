package com.fedtool.together_android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Utils {
	public static String uuid;
	public static double ver = R.string.versionCode;

	public static void setUUID(String uuid) {
		Utils.uuid = uuid;
	}
	
	public static void dialog(String message, Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				//this.finish();
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	
	public static String date(String time) {
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        Long miliSec = Long.parseLong(time);
        cal.setTimeInMillis(miliSec);
        Date date = cal.getTime();
        String dateStr = dateFm.format(date);
        
		return dateStr;
	}
	
	public static Boolean isEmpty(String str) {
		return str == null || str.equals("");
	}
	
	public static void go(Class act, Activity preAct, JSONObject data) {
     	Intent intent = new Intent(preAct.getBaseContext(), act);
     	
     	if (data != null) {
     		Iterator it = data.keys();  
            while (it.hasNext()) {  
                String key = (String) it.next();  
                try {
    				intent.putExtra(key, data.getString(key));
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
     	}
     	
     	preAct.startActivity(intent);
     	
     	//preAct.finish();
    }
	
	public static void checkUpdates() {
		Client.get("check-update?v=" + Utils.ver, null, new JsonHttpResponseHandler(){
			@Override
            public void onSuccess(JSONObject d) {
				String msg = "";
				try {
					msg = d.getString("msg");
					
					if (!Utils.isEmpty(msg)) {
						Utils.dialog(msg, null);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
            }
    		
    		public void onFailure(Throwable e, String response) {
    		
    		}
		});
	}
}
