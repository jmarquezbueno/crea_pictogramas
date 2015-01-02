package com.myApp.myaplicacion;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Point {
	public final float x, y;
	public final int color;
	public final int width;

	public Point(final float x, final float y, final int color, final int width) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.width = width;
	}

	public void draw(final Canvas canvas, final Paint paint) {
		paint.setColor(color);
		canvas.drawCircle(x, y, width/2, paint);
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + color;
	}
}