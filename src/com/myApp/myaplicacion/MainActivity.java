package com.myApp.myaplicacion;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Creamos una variable de tipo fuente a la que le asignamos una fuente importada al proyecto.
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pipomayu.ttf");
        // Identificamos el texto de cada botón.
        TextView btn1 = (TextView)findViewById(R.id.button1);
        TextView btn2 = (TextView)findViewById(R.id.button2);
        TextView btn3 = (TextView)findViewById(R.id.button3);
        TextView btn4 = (TextView)findViewById(R.id.button4);
        // Aplicamos el nuevo tiop de letra.
        btn1.setTypeface(myFont);
        btn2.setTypeface(myFont);
        btn3.setTypeface(myFont);
        btn4.setTypeface(myFont);
    }
    
    
    public void onClick(View v)
    {
    	
    	startActivity(new Intent(this, TakePhoto.class));
    }
    
    public void onClick2(View v)
    {
    	startActivity(new Intent(this, DrawPicture.class));
    }
    
    public void onClick3(View v){
		startActivity(new Intent(this, FingerPaint.class));
    }
    
    public void onClick4(View v){
    	
		startActivity(new Intent(this, ImportPicture.class));
    }
    
    @Override

	public void onConfigurationChanged(Configuration newConfig) {

	super.onConfigurationChanged(newConfig);

	}
   
    
    
}
