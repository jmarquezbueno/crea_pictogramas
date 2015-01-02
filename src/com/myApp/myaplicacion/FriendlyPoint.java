package com.myApp.myaplicacion;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FriendlyPoint extends Point {

	private final Point neighbour;

	public FriendlyPoint(final float x, final float y, final int color, final Point neighbour, final int width) {
		super(x, y, color, width);
		this.neighbour = neighbour;
	}

	public void draw(final Canvas canvas, final Paint paint) {
		paint.setColor(color);
		paint.setStrokeWidth(width);
		canvas.drawLine(x, y, neighbour.x, neighbour.y, paint);
		canvas.drawCircle(x, y, width/2, paint);
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + color + "; N[" + neighbour.toString() + "]";
	}
}