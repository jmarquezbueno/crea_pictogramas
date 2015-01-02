package com.myApp.myaplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class DrawPicture extends Activity implements View.OnTouchListener{
	/* Variables */
	TabHost th;
	TextView showResults;
	long start,stop;
	private Resources mResources;
	int bandera=0;
	Button btn;
	int accion = 0; 
	ImageView imageview;
	Bitmap bitmap;

	//Definimos el marco por el cual podemos arrastrar la imagen
	 private ViewGroup mHappy,mTriste,mEnfadado,mAsustado,mConfundido,mLLorando,mSonriendo,mVergonzoso, mIndiferente,mArrogante,mLengua,
	 /*Pestaña 2*/ mCasa, mColegio, mPlaya, mFeria, mParque, mCirco, mServicio, mJardin, mSierra, mCacharritos, mCComercial, mIglesia,
	 /*Pestaña 3*/ mCepilloDientes, mVaso, mPasta, mCoche, mTobogan, mBicicleta, mPan, mTenedor, mCuchillo, mCuchara, mPlato, mBanera, mPuerta,
	 /*Pestaña 4*/ mDormir, mComer, mLavar, mCepillar, mCorrer, mSentarse, mTumbarse, mVer, mBeber, mPasear, mNadar, mSubirEscaleras,mBajarEscaleras,
	 /*Pestaña 5*/ mCabeza, mCuerpo;
	 
	 //Definimos la imagen que vasmo arrastrar
	 private ImageView imHappy,imTriste,imEnfadado,imAsustado, imConfundido, imLLorando,imSonriendo,imVergonzoso,imIndiferente,imArrogante,imLengua,
	 /*Pestaña 2*/	imCasa, imColegio, imPlaya, imFeria, imParque, imCirco, imServicio, imJardin, imSierra, imCacharritos, imCComercial, imIglesia,
	 /*Pestaña 3*/	imCepilloDientes, imVaso, imPasta, imCoche, imTobogan, imBicicleta, imPan, imTenedor, imCuchillo, imCuchara, imPlato, imBanera,
	 			imPuerta,
	 /*Pestaña 4*/ imDormir, imComer, imLavar, imCepillar, imCorrer, imSentarse, imTumbarse, imVer, imBeber, imPasear, imNadar, imSubirEscaleras, 
	 			imBajarEscaleras,
	 /*Pestaña 5*/ imCabeza, imCuerpo;
	 
	 //Variables para centrar la imagen bajo el dedo
	 private int xDelta;
	 private int yDelta;
	
	
	//GridView1
	public static final String[] filesnames = { "Boton 0", "Boton 1", "Boton 2", "Boton 3" };
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// Oculta la barra de estado.
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				 WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.draw_activity);
		
		// Cambiamos la orientacion a horizontal.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		
		//**** CODIGO PARA DIBUJAR ****//
		
		imageview = (ImageView) this.findViewById(R.id.imageView1);
	
	   
	    imageview.setOnTouchListener(this);
	    
	    mResources=getResources();
	    
	    //******************************

	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
    	return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
		case R.id.save_id:
			View content = findViewById(R.id.DrawActivity_id);
	        content.setDrawingCacheEnabled(true);
	        saveScreen(content);
	        
	        Toast.makeText(getApplicationContext(), "Imagen guardada", Toast.LENGTH_SHORT).show();
	        
	        //Refrescamos la galeria
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
	        
	        return true;
		case R.id.item1:
			mHappy = (ViewGroup)findViewById(R.id.marco); 		//Relacionamos
			imHappy = new ImageView(this); 		//Creamos la imagen
			imHappy.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_feliz, 400, 400)); 	//Señalamos la imagen a mostrar
			imHappy.setOnTouchListener(this);		//Añadimos el Listener de la clase
			mHappy.addView(imHappy);		//Añadimos la imagen al marco
			  return true;
		case R.id.item2:
			mTriste = (ViewGroup)findViewById(R.id.marco);
			imTriste = new ImageView(this);
			imTriste.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_triste, 400, 400));
			imTriste.setOnTouchListener(this);
			mTriste.addView(imTriste);
			return true;
			
		case R.id.item3:
			mEnfadado = (ViewGroup)findViewById(R.id.marco);
			imEnfadado = new ImageView(this);
			imEnfadado.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_enfadada, 400, 400));
			imEnfadado.setOnTouchListener(this);
			mEnfadado.addView(imEnfadado);
			
		case R.id.item4:

			mAsustado = (ViewGroup)findViewById(R.id.marco);
			imAsustado = new ImageView(this);
			imAsustado.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_asustada, 400, 400));
			imAsustado.setOnTouchListener(this);
			mAsustado.addView(imAsustado);
			return true;
			
		case R.id.item5:
			mConfundido = (ViewGroup)findViewById(R.id.marco);
			imConfundido = new ImageView(this);
			imConfundido.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_confundida, 400, 400));
			imConfundido.setOnTouchListener(this);
			mConfundido.addView(imConfundido);
			return true;
			
		case R.id.item6:
			mLLorando = (ViewGroup)findViewById(R.id.marco);
			imLLorando = new ImageView(this);
			imLLorando.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_llorando, 400, 400));
			imLLorando.setOnTouchListener(this);
			mLLorando.addView(imLLorando);
			return true;
			
		case R.id.item7:
			mSonriendo = (ViewGroup)findViewById(R.id.marco);
			imSonriendo = new ImageView(this);
			imSonriendo.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_sonriendo, 400, 400));
			imSonriendo.setOnTouchListener(this);
			mSonriendo.addView(imSonriendo);
			return true;
			
		case R.id.item8:
			mVergonzoso = (ViewGroup)findViewById(R.id.marco);
			imVergonzoso = new ImageView(this);
			imVergonzoso.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_vergonzoso, 400, 400));
			imVergonzoso.setOnTouchListener(this);
			mVergonzoso.addView(imVergonzoso);
			return true;
			
		case R.id.item9:
			mIndiferente = (ViewGroup)findViewById(R.id.marco);
			imIndiferente = new ImageView(this);
			imIndiferente.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_indiferente, 400, 400));
			imIndiferente.setOnTouchListener(this);
			mIndiferente.addView(imIndiferente);
			return true;
			
		case R.id.item10:
			mArrogante = (ViewGroup)findViewById(R.id.marco);
			imArrogante = new ImageView(this);
			imArrogante.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_arrogante, 400, 400));
			imArrogante.setOnTouchListener(this);
			mArrogante.addView(imArrogante);
			return true;
			
		case R.id.item11:
			mLengua = (ViewGroup)findViewById(R.id.marco);
			imLengua = new ImageView(this);
			imLengua.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cara_lengua, 400, 400));
			imLengua.setOnTouchListener(this);
			mLengua.addView(imLengua);
			return true;
			
		case R.id.item12:
			mCasa = (ViewGroup)findViewById(R.id.marco);
			imCasa = new ImageView(this);
			imCasa.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.casa, 300, 300));
			imCasa.setOnTouchListener(this);
			mCasa.addView(imCasa);
			return true;
			
		case R.id.item13:
			mColegio = (ViewGroup)findViewById(R.id.marco);
			imColegio = new ImageView(this);
			imColegio.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.colegio, 400, 400));
			imColegio.setOnTouchListener(this);
			mColegio.addView(imColegio);
			return true;
			
		case R.id.item14:
			mPlaya = (ViewGroup)findViewById(R.id.marco);
			imPlaya = new ImageView(this);
			imPlaya.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.playa, 400, 400));
			imPlaya.setOnTouchListener(this);
			mPlaya.addView(imPlaya);
			return true;
			
		case R.id.item15: 
			mFeria = (ViewGroup)findViewById(R.id.marco);
			imFeria = new ImageView(this);
			imFeria.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.feria, 400, 400));
			imFeria.setOnTouchListener(this);
			mFeria.addView(imFeria);
			return true;
			
		case R.id.item16: 
			mParque = (ViewGroup)findViewById(R.id.marco);
			imParque = new ImageView(this);
			imParque.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.parque, 400, 400));
			imParque.setOnTouchListener(this);
			mParque.addView(imParque);
			return true;
			
		case R.id.item17: 
			mCirco = (ViewGroup)findViewById(R.id.marco);
			imCirco = new ImageView(this);
			imCirco.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.circo, 400, 400));
			imCirco.setOnTouchListener(this);
			mCirco.addView(imCirco);
			return true;
			
		case R.id.item18: 
			mServicio = (ViewGroup)findViewById(R.id.marco);
			imServicio = new ImageView(this);
			imServicio.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.servicio, 400, 400));
			imServicio.setOnTouchListener(this);
			mServicio.addView(imServicio);
			return true;
			
		case R.id.item19: 
			mJardin = (ViewGroup)findViewById(R.id.marco);
			imJardin = new ImageView(this);
			imJardin.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.jardin, 400, 400));
			imJardin.setOnTouchListener(this);
			mJardin.addView(imJardin);
			return true;
			
		case R.id.item20: 
			mSierra = (ViewGroup)findViewById(R.id.marco);
			imSierra = new ImageView(this);
			imSierra.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.sierra, 400, 400));
			imSierra.setOnTouchListener(this);
			mSierra.addView(imSierra);
			return true;
			
		case R.id.item21: 
			mCacharritos = (ViewGroup)findViewById(R.id.marco);
			imCacharritos = new ImageView(this);
			imCacharritos.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cacharritos, 400, 400));
			imCacharritos.setOnTouchListener(this);
			mCacharritos.addView(imCacharritos);
			return true;
			
		case R.id.item22: 
			mCComercial = (ViewGroup)findViewById(R.id.marco);
			imCComercial = new ImageView(this);
			imCComercial.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.centro_comercial, 400, 400));
			imCComercial.setOnTouchListener(this);
			mCComercial.addView(imCComercial);
			return true;
			
		case R.id.item23: 
			mIglesia = (ViewGroup)findViewById(R.id.marco);
			imIglesia = new ImageView(this);
			imIglesia.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.iglesia, 400, 400));
			imIglesia.setOnTouchListener(this);
			mIglesia.addView(imIglesia);
			return true;
			
		case R.id.item24:
			mCepilloDientes = (ViewGroup)findViewById(R.id.marco);
			imCepilloDientes = new ImageView(this);
			imCepilloDientes.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cepillo, 400, 400));
			imCepilloDientes.setOnTouchListener(this);
			mCepilloDientes.addView(imCepilloDientes);
			return true;
			
		case R.id.item25:
			mVaso = (ViewGroup)findViewById(R.id.marco);
			imVaso = new ImageView(this);
			imVaso.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.vaso, 400, 400));
			imVaso.setOnTouchListener(this);
			mVaso.addView(imVaso);
			return true;
			
		case R.id.item26:
			mPasta = (ViewGroup)findViewById(R.id.marco);
			imPasta = new ImageView(this);
			imPasta.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.pasta, 400, 400));
			imPasta.setOnTouchListener(this);
			mPasta.addView(imPasta);
			return true;
			
		case R.id.item27:
			mCoche = (ViewGroup)findViewById(R.id.marco);
			imCoche = new ImageView(this);
			imCoche.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.coche, 400, 400));
			imCoche.setOnTouchListener(this);
			mCoche.addView(imCoche);
			return true;
			
		case R.id.item28:
			mTobogan = (ViewGroup)findViewById(R.id.marco);
			imTobogan = new ImageView(this);
			imTobogan.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tobogan, 400, 400));
			imTobogan.setOnTouchListener(this);
			mTobogan.addView(imTobogan);
			return true;
			
		case R.id.item29: 
			mBicicleta = (ViewGroup)findViewById(R.id.marco);
			imBicicleta = new ImageView(this);
			imBicicleta.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.bicicleta, 400, 400));
			imBicicleta.setOnTouchListener(this);
			mBicicleta.addView(imBicicleta);
			return true;
			
		case R.id.item30: 
			mPan = (ViewGroup)findViewById(R.id.marco);
			imPan = new ImageView(this);
			imPan.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.pan, 400, 400));
			imPan.setOnTouchListener(this);
			mPan.addView(imPan);
			return true;
			
		case R.id.item31: 
			mTenedor = (ViewGroup)findViewById(R.id.marco);
			imTenedor = new ImageView(this);
			imTenedor.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tenedor, 400, 400));
			imTenedor.setOnTouchListener(this);
			mTenedor.addView(imTenedor);
			return true;
			
		case R.id.item32: 
			mCuchillo = (ViewGroup)findViewById(R.id.marco);
			imCuchillo = new ImageView(this);
			imCuchillo.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cuchillo, 400, 400));
			imCuchillo.setOnTouchListener(this);
			mCuchillo.addView(imCuchillo);
			return true;
			
		case R.id.item33: 
			mCuchara = (ViewGroup)findViewById(R.id.marco);
			imCuchara = new ImageView(this);
			imCuchara.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cuchara, 400, 400));
			imCuchara.setOnTouchListener(this);
			mCuchara.addView(imCuchara);
			return true;			
		
		case R.id.item34: 
			mPlato = (ViewGroup)findViewById(R.id.marco);
			imPlato = new ImageView(this);
			imPlato.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.plato, 400, 400));
			imPlato.setOnTouchListener(this);
			mPlato.addView(imPlato);
			return true;
			
		case R.id.item35: 
			mBanera = (ViewGroup)findViewById(R.id.marco);
			imBanera = new ImageView(this);
			imBanera.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.banera, 400, 400));
			imBanera.setOnTouchListener(this);
			mBanera.addView(imBanera);
			return true;
			
		case R.id.item36: 
			mPuerta = (ViewGroup)findViewById(R.id.marco);
			imPuerta = new ImageView(this);
			imPuerta.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.puerta, 400, 400));
			imPuerta.setOnTouchListener(this);
			mPuerta.addView(imPuerta);
			return true;
			
		case R.id.item37:
			mDormir = (ViewGroup)findViewById(R.id.marco);
			imDormir = new ImageView(this);
			imDormir.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.dormir, 400, 400));
			imDormir.setOnTouchListener(this);
			mDormir.addView(imDormir);
			return true;
			
		case R.id.item38:
			mComer = (ViewGroup)findViewById(R.id.marco);
			imComer = new ImageView(this);
			imComer.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.comer, 400, 400));
			imComer.setOnTouchListener(this);
			mComer.addView(imComer);
			return true;
			
		case R.id.item39:
			mLavar = (ViewGroup)findViewById(R.id.marco);
			imLavar = new ImageView(this);
			imLavar.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.lavar, 400, 400));
			imLavar.setOnTouchListener(this);
			mLavar.addView(imLavar);
			return true;
			
		case R.id.item40:
			mCepillar = (ViewGroup)findViewById(R.id.marco);
			imCepillar = new ImageView(this);
			imCepillar.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cepillar, 400, 400));
			imCepillar.setOnTouchListener(this);
			mCepillar.addView(imCepillar);
			return true;
			
		case R.id.item41:
			mCorrer = (ViewGroup)findViewById(R.id.marco);
			imCorrer = new ImageView(this);
			imCorrer.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.correr, 400, 400));
			imCorrer.setOnTouchListener(this);
			mCorrer.addView(imCorrer);
			return true;
			
		case R.id.item42:
			mSentarse = (ViewGroup)findViewById(R.id.marco);
			imSentarse = new ImageView(this);
			imSentarse.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.sentarse, 400, 400));
			imSentarse.setOnTouchListener(this);
			mSentarse.addView(imSentarse);
			return true;
			
		case R.id.item43:
			mTumbarse = (ViewGroup)findViewById(R.id.marco);
			imTumbarse = new ImageView(this);
			imTumbarse.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.acostarse, 400, 400));
			imTumbarse.setOnTouchListener(this);
			mTumbarse.addView(imTumbarse);
			return true;
			
		case R.id.item44:
			mVer = (ViewGroup)findViewById(R.id.marco);
			imVer = new ImageView(this);
			imVer.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.ver, 400, 400));
			imVer.setOnTouchListener(this);
			mVer.addView(imVer);
			return true;
			
		case R.id.item45:
			mBeber = (ViewGroup)findViewById(R.id.marco);
			imBeber = new ImageView(this);
			imBeber.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.beber, 400, 400));
			imBeber.setOnTouchListener(this);
			mBeber.addView(imBeber);
			return true;
			
		case R.id.item46:
			mPasear = (ViewGroup)findViewById(R.id.marco);
			imPasear = new ImageView(this);
			imPasear.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.pasear, 400, 400));
			imPasear.setOnTouchListener(this);
			mPasear.addView(imPasear);
			return true;
			
		case R.id.item47:
			mNadar = (ViewGroup)findViewById(R.id.marco);
			imNadar = new ImageView(this);
			imNadar.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.nadar, 400, 400));
			imNadar.setOnTouchListener(this);
			mNadar.addView(imNadar);
			return true;
			
		case R.id.item48:
			mSubirEscaleras = (ViewGroup)findViewById(R.id.marco);
			imSubirEscaleras = new ImageView(this);
			imSubirEscaleras.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.subir_escaleras, 400, 400));
			imSubirEscaleras.setOnTouchListener(this);
			mSubirEscaleras.addView(imSubirEscaleras);
			return true;
			
		case R.id.item49:
			mBajarEscaleras = (ViewGroup)findViewById(R.id.marco);
			imBajarEscaleras = new ImageView(this);
			imBajarEscaleras.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.bajar_escaleras, 400, 400));
			imBajarEscaleras.setOnTouchListener(this);
			mBajarEscaleras.addView(imBajarEscaleras);
			return true;
			
		case R.id.Caras:
			return true;
		case R.id.Lugares:
			return true;	
		
		
		case R.id.Objetos:
			return true;
			
		case R.id.Acciones:
			return true;
			
		}	
		return false;
	}
	
	
	
	//Al tocar la pantalla...
	 public boolean onTouch(View view, MotionEvent event) {
	  //Recogemos las coordenadas del dedo
	  final int X = (int) event.getRawX();
	  final int Y = (int) event.getRawY();
	  //Dependiendo de la accion recogida..
	  switch (event.getAction() & MotionEvent.ACTION_MASK) {
	      //Al tocar la pantalla
	     case MotionEvent.ACTION_DOWN:
	         //Recogemos los parametros de la imagen que hemo tocado
	         RelativeLayout.LayoutParams Params = 
	            (RelativeLayout.LayoutParams) view.getLayoutParams();
	         xDelta = X - Params.leftMargin;
	         yDelta = Y - Params.topMargin;
	         break;
	      case MotionEvent.ACTION_MOVE:
	       //Al mover el dedo vamos actualizando los margenes de la imagen para
	       //crear efecto de arrastrado
	          RelativeLayout.LayoutParams layoutParams = 
	             (RelativeLayout.LayoutParams) view.getLayoutParams();
	          layoutParams.leftMargin = X - xDelta;
	          layoutParams.topMargin = Y - yDelta;
	          //Qutamos un poco de margen para que la imagen no se deforme
	          //al llegar al final de la pantalla y pueda ir más allá
	          //probar también el codigo omitiendo estas dos líneas
	          layoutParams.rightMargin = -2000;
	          layoutParams.bottomMargin = -2000;
	          //le añadimos los nuevos parametros para mover la imagen
	          view.setLayoutParams(layoutParams);
	          break;
	      default:
	    	  break;
	      }	  
	       
	  return true;
	 }
	 
	 public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
		        int reqWidth, int reqHeight) {

		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeResource(res, resId, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeResource(res, resId, options);
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
	 
	 public void saveScreen(View drawView) {
		 
		 File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Pictogramas");

	     if(!folder.exists())
	     {
	    	 folder.mkdirs();
	     }      
	          
			
			// Store image in dcim
			Random generator = new Random();
			int n = 10000;
			n = generator.nextInt(n);
			String fname = "Pictograma-"+ n +".jpg";
			
			
			Bitmap bitmap = drawView.getDrawingCache();
	        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictogramas", fname);
	        
	        try 
	        {
	            file.createNewFile();
	            FileOutputStream ostream = new FileOutputStream(file);
	            bitmap.compress(CompressFormat.PNG, 100, ostream);
	            ostream.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
			
		}
	 
	 public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	 
	 @Override

		public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		}
		
}
