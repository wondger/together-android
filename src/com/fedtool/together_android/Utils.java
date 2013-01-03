package com.fedtool.together_android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class Utils {
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
}
