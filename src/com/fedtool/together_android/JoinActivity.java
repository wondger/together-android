package com.fedtool.together_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JoinActivity extends Activity{
	protected String name = null;
	protected String nick = null;
	protected String avatar = "images/icon.png";
	protected String oldNick = null;
	protected String oldAvatar = null;
	protected String password = null;
	protected TextView tvName = null;
	protected TextView tvNick = null;
	protected TextView tvPw = null;
	protected Button btnJoin = null;
	
	protected Boolean new_user = true;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        
        tvName = (TextView) findViewById(R.id.activity_name);
    	tvNick = (TextView) findViewById(R.id.nick);
    	tvPw = (TextView) findViewById(R.id.password);
    	btnJoin = (Button) findViewById(R.id.btn_join);
        
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        tvName.setText(name);
        formInit(Utils.uuid);
        
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
            }
    		
    		public void onFailure(Throwable e, String response) {
                /*Utils.dialog(e.getMessage(), context);*/
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
					return;
				}
				
				_join();
            }
    		
    		public void onFailure(Throwable e, String response) {
                Utils.dialog(e.getMessage(), context);
    		}
		});
	}
	
	public void join() {
		getForm();
				
		if (Utils.isEmpty(nick)) {
			Utils.dialog("«Î ‰»ÎÍ«≥∆£°", this);
			return;
		}
		
		if (Utils.isEmpty(avatar)) {
			Utils.dialog("«Î…Ë÷√Õ∑œÒ£°", this);
			return;
		}
		
		if (Utils.isEmpty(password)) {
			Utils.dialog("«Î ‰»Î√‹¬Î£°", this);
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
					//Utils.dialog(activityName, context);

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
		Utils.dialog("join success", this);
	}
}
