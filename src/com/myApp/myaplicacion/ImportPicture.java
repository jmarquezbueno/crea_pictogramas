package com.myApp.myaplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImportPicture extends Activity{
	private static final int SELECT_PICTURE = 1;
	String picturePath;
	Bitmap bitmap, orig;
	ImageView imageView2;
	MenuItem menu1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.importar_activity);
		
		// Cargo en una variable tipo TextView el campo de la pantalla
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pipomayu.ttf");
        // identificado con el id poker.
        TextView btn_InputName = (TextView)findViewById(R.id.btn_InputName);
        TextView tv_mostrar_nombre = (TextView)findViewById(R.id.mostrar_nombre);
        // Le aplico el nuevo tipo de letra
        btn_InputName.setTypeface(myFont);
        tv_mostrar_nombre.setTypeface(myFont);
		
		/*--- Definimos el TextView y el dialgo ---*/
		final TextView nombre_pic = (TextView) findViewById(R.id.mostrar_nombre);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		/* -------------------------*/
		/*--- Código del dialogo ---*/
		/* -------------------------*/
		alert.setTitle(R.string.InputName);
		//alert.setMessage("Por favor Introduzca una direccion");
		
		final EditText input = new EditText(this);
		alert.setView(input);
		
		final Button btnBuscar = (Button) findViewById(R.id.btn_InputName);
		
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				String cadenaBuscar = input.getText().toString();
				nombre_pic.setText(cadenaBuscar);
				nombre_pic.setVisibility(View.VISIBLE);
				btnBuscar.setVisibility(View.INVISIBLE);
				//menu1.setVisible(true);
			}
		});
		
		alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				//dialog.cancel();
				finish(); //Volvemos a la Actividad principal
			}
		});
		
		
		btnBuscar.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0){
				alert.show();
				
			}
		});
		
		/*--- Final Código del dialogo ---*/
		
		//Refrescamos la galeria
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		intent.setType("image/*"); //Seleccionamos el tipo para que solo muestre imagenes.
		startActivityForResult(intent, SELECT_PICTURE);

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		// We need to recyle unused bitmaps
        if (bitmap != null) {
          bitmap.recycle();
        }

		
		//***Recogemos los datos y los insertamos en un bitmap
		if(requestCode == SELECT_PICTURE)
			if(resultCode == Activity.RESULT_OK){
				Uri selectedImage = data.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA };
				
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				ImageView imageView = (ImageView) findViewById(R.id.imgPhoto);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
				//bitmap = BitmapFactory.decodeFile(picturePath);
                BitmapFactory.decodeResource(getResources(), R.id.imgPhoto, options);

				int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                String imageType = options.outMimeType;
							
                bitmap=decodeSampledBitmapFromResource(getResources(), picturePath, 500, 500);
                
                orig = bitmap;
				imageView.setImageBitmap(bitmap);
				Toast.makeText(getApplicationContext(), "Pulse Transformar para realizar la operación, puede tardar varios minutos", Toast.LENGTH_LONG).show();
				
			}
		}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.transformar_menu, menu);
    	menu1 = menu.findItem(R.id.btn_transformar);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection
    	switch (item.getItemId()) {
    	case R.id.btn_transformar : {    		
    		//Seleccionamos ImageView
    		imageView2 = (ImageView) findViewById(R.id.imgPhoto2);
    	    		
    		//Codigo para bordes 
    		//Create the filter
    		CannyEdgeDetector detector = new CannyEdgeDetector();
    		
    		//adjust its parameters and desired
    		detector.setLowThreshold(2f);
    		detector.setHighThreshold(3f);    		
    		
    		//Apply the filter
    		detector.setSourceImage(bitmap);
    		detector.process();
    		bitmap=detector.getEdgesImage();
    		
    		bitmap= invert(bitmap);
    		bitmap = adjustOpacity(bitmap,70); //No funciona correctamente, error clase bitmap
    		imageView2.setImageBitmap(bitmap);
    		
    		orig = adjustOpacity(orig, 70);
    		bitmap = overlay(bitmap, orig);
    		saveBitmap(bitmap);
    		 		
       		return true;
    		}
    	default:
    		return true;
    	}
    }
    
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }
    
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
		
		//Refrescamos la galeria
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
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
    
    public static Bitmap decodeSampledBitmapFromResource(Resources res, String pathName,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(pathName, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(pathName, options);
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
    
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
    	   //Redimensionamos
    	   int width = mBitmap.getWidth();
    	   int height = mBitmap.getHeight();

    	   // create a matrix for the manipulation
    	   Matrix matrix = new Matrix();
    	   
    	   if(width >= height)
    	   {
    		   float scaleWidth = ((float) newWidth) / width;
    		   // resize the bit map
        	   matrix.postScale(scaleWidth, scaleWidth);
    	   }
    	   else
    	   {
    		   float scaleHeight = ((float) newHeigth) / height;
    		// resize the bit map
        	   matrix.postScale(scaleHeight, scaleHeight);
    	   }
    	   
    	   // recreate the new Bitmap
    	   return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    	}
    

	@Override

	public void onConfigurationChanged(Configuration newConfig) {

	super.onConfigurationChanged(newConfig);

	}
	
}
 