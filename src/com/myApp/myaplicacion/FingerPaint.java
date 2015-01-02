package com.myApp.myaplicacion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class FingerPaint extends Activity {
	private static final String TAG = "FingerPaint";
	DrawView drawView;
	private int color;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // lock screen orientation (stops screen clearing when rotating phone)
        //setRequestedOrientation(getResources().getConfiguration().orientation);
        
        // Cambiamos la orientacion a horizontal.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.setBackgroundColor(Color.WHITE);
        drawView.requestFocus();
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.paint_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection
    	switch (item.getItemId()) {
    	case R.id.undo_id : 
    		drawView.Undo();
    		return true;
    	case R.id.save_id : {
    		drawView.setDrawingCacheEnabled(true);
    		drawView.saveView(drawView);
    		Toast.makeText(getApplicationContext(), "Imagen guardada", Toast.LENGTH_SHORT).show();
    		//Refrescamos la galeria
    		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
    		return true;
    	}
    	case R.id.clear_id : {
    		//drawView.clearPoints();
    		//Log.d(String.format("Color =  %d", color), "ok");
    		if(color==0){
    			drawView.changeColour(0);
    		}
    		if(color==1){
    			drawView.changeColour(1);
    		}
    		if(color==2){
    			drawView.changeColour(2);
    		}
    		if(color==3){
    			drawView.changeColour(3);
    		}
    		if(color==4){
    			drawView.changeColour(4);
    		}
    		if(color==5){
    			drawView.changeColour(5);
    		}
    		if(color==6){
    			drawView.changeColour(6);
    		}
    		if(color==7){
    			drawView.changeColour(7);
    		}
    		if(color==-1){
    			drawView.changeColour(0);
    		}
    		return true;	
    	}
    	case R.id.p_white_id : {
    		drawView.changeColour(0);
    		return true;
    	}
    	case R.id.p_blue_id : {
    		drawView.changeColour(1);
    		return true;
    	}
    	case R.id.p_lblue_id : {
    		drawView.changeColour(2);
    		return true;
    	}
    	case R.id.p_green_id : {
    		drawView.changeColour(3);
    		return true;
    	}
    	case R.id.p_pink_id : {
    		drawView.changeColour(4);
    		return true;
    	}
    	case R.id.p_red_id : {
    		drawView.changeColour(5);
    		return true;
    	}
    	case R.id.p_yellow_id : {
    		drawView.changeColour(6);
    		return true;
    	}
    	case R.id.p_black_id : {
    		drawView.changeColour(7);
    		return true;
    	}
    	case R.id.p_random_id : {
    		drawView.changeColour(-1);
    		return true;
    	}
    	case R.id.b_white_id : {
    		color=0;
    		drawView.setBackgroundColor(Color.WHITE);
    		return true;
    	}
    	case R.id.b_blue_id : {
    		color=1;
    		drawView.setBackgroundColor(Color.BLUE);
    		return true;
    	}
    	case R.id.b_lblue_id : {
    		color=2;
    		drawView.setBackgroundColor(Color.CYAN);
    		return true;
    	}
    	case R.id.b_green_id : {
    		color=3;
    		drawView.setBackgroundColor(Color.GREEN);
    		return true;
    	}
    	case R.id.b_pink_id : {
    		color=4;
    		drawView.setBackgroundColor(Color.MAGENTA);
    		return true;
    	}
    	case R.id.b_red_id : {
    		color=5;
    		drawView.setBackgroundColor(Color.RED);
    		return true;
    	}
    	case R.id.b_yellow_id : {
    		color=6;
    		drawView.setBackgroundColor(Color.YELLOW);
    		return true;
    	}
    	case R.id.b_black_id : {
    		color=7;
    		drawView.setBackgroundColor(Color.BLACK);
    		return true;
    	}
    	case R.id.b_custom_id : {
    		color=-1;
    		setCustomBackground(drawView);
    		return true;
    	}
    	case R.id.w_xsmall : {
    		drawView.changeWidth(0);
    		return true;
    	}
    	case R.id.w_small : {
    		drawView.changeWidth(5);
    		return true;
    	}
    	case R.id.w_medium : {
    		drawView.changeWidth(10);
    		return true;
    	}
    	case R.id.w_large : {
    		drawView.changeWidth(15);
    		return true;
    	}
    	case R.id.w_xlarge : {
    		drawView.changeWidth(20);
    		return true;
    	}
    	default : {
    		return true;
    	}
    	}
    }
   


	void setCustomBackground(DrawView v) {
    	Intent fileChooserIntent = new Intent();
    	fileChooserIntent.addCategory(Intent.CATEGORY_OPENABLE);
    	fileChooserIntent.setType("image/*");
    	fileChooserIntent.setAction(Intent.ACTION_GET_CONTENT);
    	startActivityForResult(Intent.createChooser(fileChooserIntent, "Select Picture"), 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// if statement prevents force close error when picture isn't selected
    	if (resultCode == RESULT_OK)
    	{
	    	Uri resultUri = data.getData();
	    	//String resultString = data.getData().toString();

	    	String drawString = resultUri.getPath();
	    	String galleryString = getGalleryPath(resultUri);

	    	// if Gallery app was used
	    	if (galleryString != null)
	    	{
	    		Log.d(TAG, galleryString);
	    		drawString = galleryString;
	    	}
	    	// else another file manager was used
	    	else
	    	{
	    		Log.d(TAG, drawString);
		    	//File Manager: "content://org.openintents.cmfilemanager/mimetype//mnt/sdcard/DCIM/Camera/IMG_20110909_210412.jpg"
		    	//ASTRO:        "file:///mnt/sdcard/DCIM/Camera/IMG_20110924_133324.jpg"
		    	if (drawString.contains("//"))
		    	{
		    		drawString = drawString.substring(drawString.lastIndexOf("//"));
		    	}
	    	}

	    	// set the background to the selected picture
	    	if (drawString.length() > 0)
	    	{
	    		BitmapFactory.Options options = new BitmapFactory.Options();
	    		options.inJustDecodeBounds = true;

	            BitmapFactory.decodeFile(drawString, options);
	            // Calculate inSampleSize
	            options.inSampleSize = calculateInSampleSize(options, 500, 500);
	            
	            // Decode bitmap with inSampleSize set
	            options.inJustDecodeBounds = false;
	            
	            Bitmap bmp = BitmapFactory.decodeFile(drawString,options);
	            BitmapDrawable drawBackground = new BitmapDrawable(bmp);
	    		//Drawable drawBackground = Drawable.createFromPath(drawString);
	    		drawBackground.setAlpha(70);
	    		drawView.setBackgroundDrawable(drawBackground);
	    	}

    	}
    }
    
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    	}
    
    return inSampleSize;
	}
    
    // used when trying to get an image path from the URI returned by the Gallery app
    public String getGalleryPath(Uri uri) {
    	String[] projection = { MediaStore.Images.Media.DATA };
    	Cursor cursor = managedQuery(uri, projection, null, null, null);
    	
    	if (cursor != null)
    	{
    		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    		cursor.moveToFirst();
    		return cursor.getString(column_index);
    	}
    	
    	
    	return null;
    }
    
    @Override

	public void onConfigurationChanged(Configuration newConfig) {

	super.onConfigurationChanged(newConfig);

	}

}
