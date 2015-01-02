package com.myApp.myaplicacion;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DragObserverLayout extends RelativeLayout {
	float startX, startY, stopX, stopY;
    private Paint mPaint = new Paint();
    private List<Rect> lines = new ArrayList<Rect>();

    public DragObserverLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(2.0f);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        final int count = lines.size();
        for (int i = 0; i < count; i++) {
            final Rect r = lines.get(i);
            canvas.drawLine(r.left, r.top, r.right, r.bottom, mPaint);
        }
    }

    public void addLine(Rect r) {
        lines.add(r);
        invalidate();
    }

}
