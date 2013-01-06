package com.fedtool.together_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Button;
import android.widget.ImageButton;
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
    
    public static ImageButton addImageButton(String text, Context context) {
    	ImageButton btn = new ImageButton(context);
   	 	//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	//btn.setGravity(Gravity.CLIP_VERTICAL);
   	 	//btn.setLayoutParams(params);
    	
   	 	//btn.setText(text);
    	
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
     
     public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
         Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                 .getHeight(), Config.ARGB_8888);
         Canvas canvas = new Canvas(output);

         final int color = 0xff424242;
         final Paint paint = new Paint();
         final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
         final RectF rectF = new RectF(rect);
         final float roundPx = pixels;

         paint.setAntiAlias(true);
         canvas.drawARGB(0, 0, 0, 0);
         paint.setColor(color);
         canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

         paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
         canvas.drawBitmap(bitmap, rect, rect, paint);

         return output;
     }
     
     public static LinearLayout addLinearLayout(Context context) {
    	 LinearLayout ll = new LinearLayout(context);
    	 ll.setOrientation(LinearLayout.HORIZONTAL);
    	 
    	 return ll;
     }
     
}
