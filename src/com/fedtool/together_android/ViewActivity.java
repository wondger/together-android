package com.fedtool.together_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ViewActivity extends Activity {
	private String name = null;
	private String uuid = null;
	private String nick = null;
	private WebView webview = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uuid = intent.getStringExtra("uuid");
        
        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setAppCacheEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);

        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath(); 
        ws.setDatabasePath(databasePath);

    	webview.loadUrl("http://106.187.92.33:7777/web/map.html?uuid=" + uuid + "&activityName=" + name);
        
        webview.setWebChromeClient(new WebChromeClient() {
        	public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        	    callback.invoke(origin, true, false);
        	 }
        });
	}
}
