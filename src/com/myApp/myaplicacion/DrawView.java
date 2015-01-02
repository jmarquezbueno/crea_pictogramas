package com.myApp.myaplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";

	List<Point> points = new ArrayList<Point>();
	List<Point> undonePoints = new ArrayList<Point>(); 
	Paint paint = new Paint();
	Random gen;
	int col_mode;
	int wid_mode;

	public DrawView(Context context) {
		super(context);

		// set default colour to white
		col_mode = 7;

		// set default width to 7px
		wid_mode = 10;

		setFocusable(true);
		setFocusableInTouchMode(true);

		this.setOnTouchListener(this);

		paint.setAntiAlias(true);
	}

	// used to clear the screen
	public void clearPoints () {
		points.clear();
		forceRedraw();
	}
	
	public void Undo () { 
	    if (points.size()>0) { 
	       undonePoints.add(points.remove(points.size()-1));
	       forceRedraw();
	     }
	}

	public void forceRedraw() {
		invalidate();
	}

	// Seleccionar color para dibujar.
	public void changeColour (int col_in) {
		col_mode = col_in;
	}

	// Seleccionar grosor para dibujar.
	public void changeWidth (int wid_in) {
		wid_mode = wid_in;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// for each point, draw on canvas
		for (Point point : points) {
			point.draw(canvas, paint);
			Log.d(TAG, "pointcount: " + points.size());
		}
	}

	public boolean onTouch(View view, MotionEvent event) {
		int new_col = 0;
		if (col_mode < 0) {
			gen = new Random();
			col_mode = gen.nextInt( 8 );
		}
		// This if statement may be redundant now
		if (col_mode >= 0) {
			switch (col_mode) {
				case 0 : {
					new_col = Color.WHITE;
					break;
				}
				case 1 : {
					new_col = Color.BLUE;
					break;
				}
				case 2 : {
					new_col = Color.CYAN;
					break;
				}
				case 3 : {
					new_col = Color.GREEN;
					break;
				}
				case 4 : {
					new_col = Color.MAGENTA;
					break;
				}
				case 5 : {
					new_col = Color.RED;
					break;
				}
				case 6 : {
					new_col = Color.YELLOW;
					break;
				}
				case 7 : {
					new_col = Color.BLACK;
					break;
				}
			}
		}
		
		Point point;
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			point = new FriendlyPoint(event.getX(), event.getY(), new_col, points.get(points.size() - 1), wid_mode);	
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {	
			point = new Point(event.getX(), event.getY(), new_col, wid_mode);
		} else {
			return false;
		}
		points.add(point);
		forceRedraw();
		Log.d(TAG, "point: " + point);
		return true;
	}

	public void saveView(View drawView) {
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
}