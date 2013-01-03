package com.fedtool.together_android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class UI {
	public static TableRow addTableRow(Context context) {
    	// create a new TableRow  
        TableRow row = new TableRow(context);
        TableLayout.LayoutParams params = new LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        
    	return row;
    }
    
    public static TextView addTextView(String text, Context context) {
    	TextView tv = new TextView(context);
    	tv.setText(text);
    	
    	return tv;
    }
    
    public static Button addButton(String text, Context context) {
    	Button btn = new Button(context);
   	 	//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	//btn.setGravity(Gravity.CLIP_VERTICAL);
   	 	//btn.setLayoutParams(params);
    	
   	 	btn.setText(text);
    	
    	return btn;
    }
    
     public static SmartImageView addImageView(String src, Context context) {
    	 SmartImageView iv = new SmartImageView(context);
    	 
    	 /*
    	 // Get singletone instance of ImageLoader
    	 ImageLoader imageLoader = ImageLoader.getInstance();
    	 // Initialize ImageLoader with configuration. Do it once.
    	 imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    	 // Load and display image asynchronously
    	 imageLoader.displayImage(src, iv);
    	 */
    	 
    	 //iv.setLayoutParams(new LayoutParams(50, 50, Gravity.CENTER_VERTICAL));
    	 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
    	 params.setMargins(5, 0, 5, 0);
    	 
    	 iv.setLayoutParams(params);
    	 
    	 iv.setImageUrl(src);
    	 
    	 return iv;
     }
     
     public static LinearLayout addLinearLayout(Context context) {
    	 LinearLayout ll = new LinearLayout(context);
    	 ll.setOrientation(LinearLayout.HORIZONTAL);
    	 
    	 return ll;
     }
     
}
