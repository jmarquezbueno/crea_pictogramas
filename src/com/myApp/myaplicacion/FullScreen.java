package com.myApp.myaplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FullScreen extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// Quita el título de la activity en la parte superior
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Pantalla completa (oculta incluso la barra de estado)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 //WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.fullscreen);
		
		//Codigo para bordes 
		//Create the filter
		CannyEdgeDetector detector = new CannyEdgeDetector();
		
		//adjust its parameters and desired
		detector.setLowThreshold(2f);
		detector.setHighThreshold(3f);
		
		// Recibimos el intent
		String url = getIntent().getStringExtra("url");
		String nombrePic = getIntent().getExtras().getString("namePic");
		
		// Cargo en una variable tipo TextView el campo de la pantalla
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pipomayu.ttf");
		TextView mostrar_nombre = (TextView)findViewById(R.id.textView1);
		mostrar_nombre.setText(nombrePic);
		mostrar_nombre.setTypeface(myFont);
		Log.d(nombrePic, "ok");
		
		ImageView img = (ImageView)findViewById(R.id.imageView1);
		
		// Pasamos el string a bitmap
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(url, options);
        
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 500, 500);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        
        Bitmap bmp = BitmapFactory.decodeFile(url,options);
        
        
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        //bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        Bitmap orig = bmp;

				
		//Apply the filter
		detector.setSourceImage(bmp);
		detector.process();
		bmp=detector.getEdgesImage();
		
		bmp=invert(bmp);
		bmp = adjustOpacity(bmp,70);
		orig = adjustOpacity(orig, 70);
		
		//ImageView img2 = (ImageView)findViewById(R.id.imageView2);
		//img2.setImageBitmap(orig);
		
		bmp = overlay(bmp, orig);
		img.setImageBitmap(bmp);
		saveBitmap(bmp); //Guardamos el bitmap
		
		//Refrescamos la galeria
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		
	}
	
	public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
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

	
	/**
	 * @param bitmap The source bitmap.
	 * @param opacity a value between 0 (completely transparent) and 255 (completely
	 * opaque).
	 * @return The opacity-adjusted bitmap.  If the source bitmap is mutable it will be
	 * adjusted and returned, otherwise a new bitmap is created.
	 */
	@SuppressWarnings("unused")
	private Bitmap adjustOpacity(Bitmap bitmap, int opacity)
	{
	    Bitmap mutableBitmap = bitmap.isMutable()
	                           ? bitmap
	                           : bitmap.copy(Bitmap.Config.ARGB_8888, true);
	    Canvas canvas = new Canvas(mutableBitmap);
	    int colour = (opacity & 0xFF) << 24;
	    canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
	    return mutableBitmap;
	}
	
	private void saveBitmap(Bitmap bitmap)
	{
		// Store image in dcim
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				String fname = "Pictograma-"+ n +".jpg";
				
		File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictogramas", fname);
		try{
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, ostream);
			ostream.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Bitmap invert(Bitmap src) {
        Bitmap output = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixelColor;
        int height = src.getHeight();
        int width = src.getWidth();

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            pixelColor = src.getPixel(x, y);
            A = Color.alpha(pixelColor);
            
            R = 255 - Color.red(pixelColor);
            G = 255 - Color.green(pixelColor);
            B = 255 - Color.blue(pixelColor);
            
            output.setPixel(x, y, Color.argb(A, R, G, B));
        }
    }

    return output;
}  
	
	@Override

	public void onConfigurationChanged(Configuration newConfig) {

	super.onConfigurationChanged(newConfig);

	}		
}
