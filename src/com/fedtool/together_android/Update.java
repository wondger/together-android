package com.fedtool.together_android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;

public class Update {
	private static ProgressDialog pBar = null;
	private static int verCode = R.string.versionCode;
	private static String verName = "" + R.string.versionName;
	private static String appName = "" + R.string.app_name;
	private static int newVerCode = -1;
	private static String newVerName = "";
	private static String apk = "";
	private static String saveName = "";

	
	private static Activity act = null;
	private static Handler handler = new Handler();
	
	public static void checkUpdate(Activity a) {
		act = a;
		Client.get("check-update?v=" + verCode, null, new JsonHttpResponseHandler(){
			@Override
            public void onSuccess(JSONObject d) {
				try {
					newVerCode = d.getInt("verCode");
					newVerName = d.getString("verName");
					apk = d.getString("apk");

					if (newVerCode > verCode) {
						doNewVersionUpdate();
						saveName = appName + "." + newVerName + ".apk";
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

	private static void doNewVersionUpdate() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ǰ�汾:");
		sb.append(verName);
		sb.append(",�����°汾:");
		sb.append(newVerName);
		sb.append("Code:");
		sb.append(newVerCode);
		sb.append(", �Ƿ����?");
		Dialog dialog = new AlertDialog.Builder(act)
				.setTitle("�������")
				.setMessage(sb.toString())
				// ��������
				.setPositiveButton("����",// ����ȷ����ť
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(act);
								pBar.setTitle("��������");
								pBar.setMessage("���Ժ�...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(apk);
							}
						})
				.setNegativeButton("�ݲ�����",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// ���&quot;ȡ��&quot;��ť֮���˳�����
								//finish();
							}
						}).create();// ����
		// ��ʾ�Ի���
		dialog.show();
	}

	static void downFile(final String url) {  
	    pBar.show();  
	    new Thread() {  
	        public void run() {  
	            HttpClient client = new DefaultHttpClient();  
	            HttpGet get = new HttpGet(url);  
	            HttpResponse response;  
	            try {  
	                response = client.execute(get);  
	                HttpEntity entity = response.getEntity();  
	                long length = entity.getContentLength();  
	                InputStream is = entity.getContent();  
	                FileOutputStream fileOutputStream = null;  
	                if (is != null) {  
	                    File file = new File(  
	                            Environment.getExternalStorageDirectory(),  
	                            saveName);  
	                    fileOutputStream = new FileOutputStream(file);  
	                    byte[] buf = new byte[1024];  
	                    int ch = -1;  
	                    int count = 0;  
	                    while ((ch = is.read(buf)) != -1) {  
	                        fileOutputStream.write(buf, 0, ch);  
	                        count += ch;  
	                        if (length > 0) {  
	                        }  
	                    }  
	                }  
	                fileOutputStream.flush();  
	                if (fileOutputStream != null) {  
	                    fileOutputStream.close();  
	                }  
	                down();  
	            } catch (ClientProtocolException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }.start();  
	}

	private static void down() {  
        handler.post(new Runnable() {  
            public void run() {  
                pBar.cancel();  
                update();  
            }  
        });
	}

	private static void update() {  
	    Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.setDataAndType(Uri.fromFile(new File(Environment  
	            .getExternalStorageDirectory(), saveName)),  
	            "application/vnd.android.package-archive");  
	    act.startActivity(intent);  
	}
}