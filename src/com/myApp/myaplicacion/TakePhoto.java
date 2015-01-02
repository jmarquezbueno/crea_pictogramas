package com.myApp.myaplicacion;

import java.io.File;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TakePhoto extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 1;
	Bitmap imagen;
	int request_Code = 1;
	Bitmap userPhoto;	
	String fname;
	String url;
	String cadenaBuscar;
	boolean aux;
	MenuItem menu1;
	ImageView img;
	Button btnBuscar, btnTransformar;
	TextView nombre_pic;
	
	File folder;
	public static final String IMAGE_PATH = "imagevieweractivityfilepath";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takephoto_activity);

		// Creamos una variable de tipo fuente a la que asignamos una fuente importada.
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pipomayu.ttf");
        // Identificamos los TextView
        TextView btn_InputName = (TextView)findViewById(R.id.btn_InputName);
        TextView tv_mostrar_nombre = (TextView)findViewById(R.id.mostrar_nombre);
        // Le asignamos la fuente a los TextView
        btn_InputName.setTypeface(myFont);
        tv_mostrar_nombre.setTypeface(myFont);
		
		// Definimos el TextView y el dialgo.
		nombre_pic = (TextView) findViewById(R.id.mostrar_nombre);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		// Almacenamos la imagen en una nueva carpeta Pictogramas y le asignamos un nombre aleatorio.
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		fname = "Pictograma-"+ n +".jpg";
		
		folder = new File(Environment.getExternalStorageDirectory().toString() + "/Pictogramas");

        if(!folder.exists())
        {
            folder.mkdirs();
        }      
                
		// Lanzamos la cámara mediante un Intent.
		Intent TakePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); //Creamos un nuevo Intent
 
		TakePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, getImageUri());
		
		startActivityForResult(TakePictureIntent, CAMERA_PIC_REQUEST); //Lanza la actividad de la camara
		
		// Refrescamos la galeria
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));

		
		/* -------------------------*/
		/*--- Código del dialogo ---*/
		/* -------------------------*/
		alert.setTitle(R.string.InputName);
		
		final EditText input = new EditText(this);
		alert.setView(input);
		
		btnBuscar = (Button) findViewById(R.id.btn_InputName);
		btnTransformar = (Button) findViewById(R.id.btn_transformar);
		
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				cadenaBuscar = input.getText().toString();
				nombre_pic.setText(cadenaBuscar);
				nombre_pic.setVisibility(View.VISIBLE);
				btnBuscar.setVisibility(View.INVISIBLE);
				//menu1.setVisible(true);
			}
		});
		
		alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish(); //Volvemos a la Actividad principal
			}
		});
		
		btnBuscar.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0){
				alert.show();
				
			}
		});
	}
	// Fin onCreate
	
	protected void onStop(){
		super.onStop();
	}
	
	
	//El onActivityResult se lanzará al finalizar la cámara
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data); 
		if(requestCode == CAMERA_PIC_REQUEST)
		{
			//Si se realiza la foto
			if(resultCode == RESULT_OK){
				img = (ImageView) findViewById(R.id.imageView1);	//Busca un imageView
				
				BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.id.imageView1, options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                String imageType = options.outMimeType;

                url = folder.getAbsolutePath() + "/" + fname;
				
				img.setImageBitmap(decodeSampledBitmapFromResource(getResources(), url, 500, 500)); //Muestra la foto en el ImageView
				
				Toast.makeText(getApplicationContext(), "Pulse Transformar para realizar la operación, puede tardar varios minutos", Toast.LENGTH_SHORT).show();
				
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
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, String url,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(url, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(url, options);
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
    		Intent intent = new Intent(TakePhoto.this, FullScreen.class); // Creamos el intent
			intent.putExtra("url", url); // Pasamos la url de la foto al intent
			intent.putExtra("namePic", cadenaBuscar);
			startActivity(intent); // Lanzamos el intent
    		 		
       		return true;
    		}
    	default:
    		return true;
    	}
    }
	
	
	private Uri getImageUri() {
	    
	    File file = new File(Environment.getExternalStorageDirectory() + "/Pictogramas", fname);
	    Uri imgUri = Uri.fromFile(file);

	    return imgUri;
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

	super.onConfigurationChanged(newConfig);

	}
			
}


