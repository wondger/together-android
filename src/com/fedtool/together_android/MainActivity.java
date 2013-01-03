package com.fedtool.together_android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
        
    	joinedTable = (TableLayout) findViewById(R.id.joined_table);
    	unjoinedTable = (TableLayout) findViewById(R.id.unjoined_table);

        getJoinedActivity("id_yanmu");
        getUnJoinedActivities("id_yanmu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
    	
    	joinedHeadRow.addView(joinedName);
    	joinedHeadRow.addView(joinedDate);
    	
    	// add joinedBodyRow
    	TableRow joinedBodyRow = UI.addTableRow(this);
    	LinearLayout joinedIconsLayout = UI.addLinearLayout(this);

    	Button joinBtn = UI.addButton("º”»Î", this);
    	joinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.dialog(name, context);
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
    	
    	joinedBodyRow.addView(joinedIconsLayout);
    	joinedBodyRow.addView(joinBtn);
    	
    	joinedTable.addView(joinedHeadRow);
    	joinedTable.addView(joinedBodyRow);
    }
    
    public void addUnJoinedActivity(JSONArray activities) {
    	if (activities != null) { 
    		int len = activities.length();
    		for (int i=0; i<len; i++){ 
    			try {
					JSONObject activity = activities.getJSONObject(i);
					TableRow row = UI.addTableRow(this);
					row.addView(UI.addTextView(activity.getString("name"), this));
					unjoinedTable.addView(row);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} 
    	}
    }
}
