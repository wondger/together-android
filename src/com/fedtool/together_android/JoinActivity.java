package com.fedtool.together_android;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JoinActivity extends Activity{
	protected String name = null;
	protected String nick = null;
	protected String avatar = "avatar/" + (int)(Math.random()*10) + ".png";
	protected String oldNick = null;
	protected String oldAvatar = null;
	protected String password = null;
	protected TextView tvName = null;
	protected TextView tvNick = null;
	protected TextView tvPw = null;
	protected ImageButton btnAvatar = null;
	protected ImageButton btnJoin = null;
	
	protected Boolean new_user = true;
	
	public final int CAPTURE_CODE = 3326;
	public final int IMAGE_CODE = 3327;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        
        tvName = (TextView) findViewById(R.id.activity_name);
    	tvNick = (TextView) findViewById(R.id.nick);
    	tvPw = (TextView) findViewById(R.id.password);
    	btnJoin = (ImageButton) findViewById(R.id.btn_submit);
    	btnAvatar = (ImageButton) findViewById(R.id.btn_avatar);
        
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        tvName.setText(name);
        formInit(Utils.uuid);
        
        final Activity self = this;
        
        btnAvatar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Utils.dialog(name, context);
				Photo.startCamera(self, CAPTURE_CODE);
			}
		});
        
	}

	public void bindJoin() {
		btnJoin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				join();
				
			}
		});
	}
	
	protected void formInit(String userId) {
		final Context context = this;
		Client.get("get-user?id=" + userId, null, new JsonHttpResponseHandler() {
			@Override
            public void onSuccess(JSONObject user) {
                try {

                    //System.out.println(endSite);
                    
                    oldNick = user.getString("nick");
                    oldAvatar = user.getString("avatar");
                    
                    if (Utils.isEmpty(oldNick) || Utils.isEmpty(oldAvatar)) {
                    	new_user = true;
                    }
                    else {
                    	new_user = false;

                    	_formInit(nick, avatar);
                    }
                    
                } catch(JSONException e) {
                    Utils.dialog(e.getMessage(), context);
                    //e.printStackTrace();
                }
                
                bindJoin();
            }
    		
    		public void onFailure(Throwable e, String response) {
                /*Utils.dialog(e.getMessage(), context);*/
    			bindJoin();
    		}
		});
	}
	
	protected void _formInit(String nick, String avatar) {
		tvNick.setText(oldNick);
	}
	
	protected void getForm() {
		nick = tvNick.getText().toString();
		password = tvPw.getText().toString();
	}
	
	protected void addUser() {
		final Context context = this;

		RequestParams params = new RequestParams();
		params.put("id", Utils.uuid);
		params.put("nick", nick);
		params.put("avatar", avatar);
		
		Client.post("add-user", params, new JsonHttpResponseHandler(){
			@Override
            public void onSuccess(JSONObject user) {
				//Utils.dialog("post", context);
				if (user == null) {
					Utils.dialog("数据更新失败！", null);
				}
				
				_join();
            }
    		
    		public void onFailure(Throwable e, String response) {
				Utils.dialog("e数据更新失败！", null);
				_join();

    		}
		});
	}
	
	public void join() {
		getForm();
				
		if (Utils.isEmpty(nick)) {
			Utils.dialog("请输入昵称！", this);
			return;
		}
		
		if (Utils.isEmpty(avatar)) {
			Utils.dialog("请设置头像！", this);
			return;
		}
		
		if (Utils.isEmpty(password)) {
			Utils.dialog("请输入密码！", this);
			return;
		}
		
		
		if (new_user) {
			//Utils.dialog("adduser", this);

			addUser();
		}
		else {
			//Utils.dialog("_join", this);

			_join();
		}
		
	}
	
	protected void _join() {
		final Context context = this;

		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("userId", Utils.uuid);
		params.put("password", password);
		
		Client.post("join-activity", params, new JsonHttpResponseHandler(){
			@Override
            public void onSuccess(JSONObject activity) {
				String activityName = null;
				try {
					activityName = activity.getString("name");
					Utils.dialog(activityName, context);
					Utils.dialog(name, context);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				if (!Utils.isEmpty(activityName)) {
					joinSuc();
					return;
				}
            }
    		
    		public void onFailure(Throwable e, String response) {
                Utils.dialog(e.getMessage(), context);
    		
    		}
		});
	}
	
	protected void joinSuc() {
		Utils.dialog("加入成功！", this);

		viewActivity(name);
	}
	
	public void backMain(View v) {
		Utils.go(MainActivity.class, this, null);
		finish();
	}
	
	public void viewActivity(String name) {
    	JSONObject data = new JSONObject();
        try {
			data.put("uuid", Utils.uuid);
			data.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     	Utils.go(ViewActivity.class, this, data);
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		
		Utils.dialog("" + resultCode, this);
		// 拍照
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case CAPTURE_CODE: {
			Log.d("CAPTURE", "1");
			break;
		}
			// 相册
		case IMAGE_CODE: {
			Log.d("CAPTURE", "1");

			break;
		}

		}
	}
}
