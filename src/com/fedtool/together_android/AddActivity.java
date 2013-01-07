package com.fedtool.together_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddActivity extends Activity {
	protected TextView name  = null;
	protected TextView start  = null;
	protected TextView end  = null;
	protected TextView password  = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        
        name = (TextView) findViewById(R.id.name);
        start = (TextView) findViewById(R.id.start);
        end = (TextView) findViewById(R.id.end);
        password = (TextView) findViewById(R.id.password);
	}
	
	public void create(View v) {

		String sName = name.getText().toString();
		String sStart = start.getText().toString();
		String sEnd = end.getText().toString();
		String sPassword = password.getText().toString();
		if(Utils.isEmpty(sName)) {
			Utils.dialog("请输入活动名！", this);
			return;
		}
		
		if(Utils.isEmpty(sStart)) {
			Utils.dialog("请输入活动起点！", this);
			return;
		}
		
		if(Utils.isEmpty(sEnd)) {
			Utils.dialog("请输入活动终点！", this);
			return;
		}
		
		if(Utils.isEmpty(sPassword)) {
			Utils.dialog("请输入活动密码！", this);
			return;
		}
		
		RequestParams params = new RequestParams();
		params.put("name", sName);
		params.put("startSite", sName);
		params.put("endSite", sName);
		params.put("password", sName);
		params.put("ownerId", Utils.uuid);
		
		final Context context = this;

		
		Client.post("add-activity", params, new JsonHttpResponseHandler(){
			@Override
            public void onSuccess(JSONObject activity) {
				String activityName = null;
				try {
					activityName = activity.getString("name");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				if (!Utils.isEmpty(activityName)) {
					createSuc();
					return;
				}
				else {
					createFail();
				}
            }
    		
    		public void onFailure(Throwable e, String response) {
                Utils.dialog(e.getMessage(), context);
    		
    		}
		});
	}
	
	public void createSuc () {
		Utils.dialog("创建成功！", this);
		backMain(null);
	}
	
	public void createFail () {
		Utils.dialog("创建失败！", this);
	}
	
	public void backMain(View v) {
     	Utils.go(MainActivity.class, this, null);
     	finish();
    }
}
