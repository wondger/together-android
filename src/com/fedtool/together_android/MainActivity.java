package com.fedtool.together_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {
	
	protected static TableLayout joinedTable;
	protected static TableLayout unjoinedTable;
	private static final String BASE_URL = "http://106.187.92.33:7777/web/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
        String IMEI = tm.getDeviceId();
        String SSN = tm.getSimSerialNumber();
        String IMSI = tm.getSubscriberId();
        
        String uuid = null;
        
        if (!Utils.isEmpty(IMEI)) {
        	uuid = IMEI;
        }
        else if (!Utils.isEmpty(SSN)) {
        	uuid = SSN;
        }
        else if (!Utils.isEmpty(IMSI)) {
        	uuid = IMSI;
        }
        
        Utils.setUUID(uuid);
        
        //Utils.dialog(uuid, null);
        //Utils.dialog(model, null);
        
        //Utils.dialog(Utils.uuid, this);
        
    	joinedTable = (TableLayout) findViewById(R.id.joined_table);
    	unjoinedTable = (TableLayout) findViewById(R.id.unjoined_table);

        getJoinedActivity(Utils.uuid);
        getUnJoinedActivities(Utils.uuid);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(this, MainActivity.class);  //your class
        startActivity(i);
        finish();

    }
    
    public void getJoinedActivity(String userId) {
    	final Context context = this;
    	Client.get("get-activity-by-userId?userId=" + userId, null, new JsonHttpResponseHandler(){
    		@Override
            public void onSuccess(JSONObject activity) {
                try {
                    //Utils.dialog(activity.toString(), context);

                    //Utils.dialog(firstEvent.toString(), context);
                    
                    // Do something with the response
                    //System.out.println(endSite);
                    
                    String dateStr = Utils.date(activity.getString("time"));
                    
                    addJoinedActivity(activity.getString("name"), dateStr, activity.getJSONArray("users"));
                    
                } catch(JSONException e) {
                    //Utils.dialog(e.getMessage(), context);
                    //e.printStackTrace();
                }
            }
    		
    		public void onFailure(Throwable e, String response) {
                Utils.dialog(e.getMessage(), context);
    		}
    	});
    }
    
    public void getUnJoinedActivities(String userId) {
    	final Context context = this;
    	Client.get("get-activities-exclude-userId?userId=" + userId, null, new JsonHttpResponseHandler(){
    		@Override
            public void onSuccess(JSONObject data) {
                try {
                    //Utils.dialog(activity.toString(), context);

                    JSONArray activities = data.getJSONArray("activities");
                    
                    //Utils.dialog(firstEvent.toString(), context);
                    
                    // Do something with the response
                    //System.out.println(endSite);
                    
                    
                    addUnJoinedActivity(activities);
                    
                } catch(JSONException e) {
                    //Utils.dialog(e.getMessage(), context);
                    //e.printStackTrace();
                }
            }
    		
    		public void onFailure(Throwable e, String response) {
                Utils.dialog(e.getMessage(), context);
    		}
    	});
    }
        
    public void addJoinedActivity(final String name, String date, JSONArray users) {
    	final Context context = this;
    	
    	// add joinedHeadRow
    	TableRow joinedHeadRow = UI.addTableRow(this);
    	TextView joinedName = UI.addTextView(name, this);
    	TextView joinedDate = UI.addTextView(date, this);
    	
    	joinedName.setTextSize(18);
		joinedDate.setTextColor(0xff999999);

    	joinedHeadRow.addView(joinedName);
    	joinedHeadRow.addView(joinedDate);
    	
    	// add joinedBodyRow
    	TableRow joinedBodyRow = UI.addTableRow(this);
    	LinearLayout joinedIconsLayout = UI.addLinearLayout(this);

    	ImageButton joinBtn = UI.addImageButton("查看", this);
    	joinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Utils.dialog(name, context);
				viewActivity(name);
			}
		});
    	
    	if (users != null) { 
    		int len = users.length();
    		for (int i=0; i<len; i++){ 
    			//list.add(jsonArray.get(i).toString());
    			try {
					JSONObject user = users.getJSONObject(i);
					joinedIconsLayout.addView(UI.addImageView(BASE_URL + user.getString("avatar"), this));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} 
    	}
    	
    	joinBtn.setBackgroundResource(R.drawable.btn_view);
    	joinedBodyRow.addView(joinedIconsLayout);
    	joinedBodyRow.addView(joinBtn);
    	
    	joinedHeadRow.setPadding(0, 10, 0, 10);
    	
    	joinedTable.addView(joinedHeadRow);
    	joinedTable.addView(joinedBodyRow);
    }
    
    public void addUnJoinedActivity(JSONArray activities) {
    	if (activities != null) {
			final Context context = this;

    		int len = activities.length();
    		for (int i=0; i<len; i++){ 
    			try {
					final JSONObject activity = activities.getJSONObject(i);
					TableRow row = UI.addTableRow(this);
					row.setPadding(0, 5, 0, 5);
					
					TextView name = UI.addTextView(activity.getString("name"), this);
					name.setTextSize(18);
					row.addView(name);
					
					TextView time = UI.addTextView(Utils.date(activity.getString("time")), this);
					time.setTextSize(14);
					time.setTextColor(0xff999999);
					time.setPadding(0, 0, 5, 0);
					row.addView(time);
					
					ImageButton joinBtn = UI.addImageButton("加入", this);
					joinBtn.setBackgroundResource(R.drawable.btn_join);
					row.setGravity(Gravity.CENTER_VERTICAL);
					
					joinBtn.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							try {
								joinActivity(activity.getString("name"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					
					row.addView(joinBtn);
					unjoinedTable.addView(row);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} 
    	}
    }
    
    public void joinActivity(String name) {
        JSONObject data = new JSONObject();
        try {
			data.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     	Utils.go(JoinActivity.class, this, data);
    }
    
    public void createActivity(View v) {
        Utils.go(AddActivity.class, this, null);
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
    
}
