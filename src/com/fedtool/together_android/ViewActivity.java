package com.fedtool.together_android;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class ViewActivity extends Activity {
	private GoogleMap gmap;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        
        //gmap = ((MapFragment) findFragmentById(R.id.map)).getMap();
	}
}
