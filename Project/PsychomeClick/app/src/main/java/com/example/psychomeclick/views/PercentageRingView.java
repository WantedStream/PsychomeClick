package com.example.psychomeclick.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PercentageRingView extends View {

    private int percentage;
    private Paint innerRingPaint;
    private Paint outerRingPaint;
    private Paint textPaint;
    private RectF innerRingBounds;
    private RectF outerRingBounds;

    public PercentageRingView(Context context) {
        super(context);
        init();
    }

    public PercentageRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentageRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        innerRingPaint = new Paint();
        innerRingPaint.setColor(Color.GRAY);
        innerRingPaint.setStyle(Paint.Style.STROKE);
        innerRingPaint.setStrokeWidth(30);

        outerRingPaint = new Paint();
        outerRingPaint.setColor(Color.BLUE);
        outerRingPaint.setStyle(Paint.Style.STROKE);
        outerRingPaint.setStrokeWidth(30);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
        textPaint.setTextAlign(Paint.Align.CENTER);

        innerRingBounds = new RectF();
        outerRingBounds = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 50;

        // Draw inner ring
        innerRingBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(innerRingBounds, -90, 360, false, innerRingPaint);

        // Draw outer ring based on percentage
        outerRingBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(outerRingBounds, -90, (float) (3.6 * percentage), false, outerRingPaint);

        // Draw text inside the circle
        String text = percentage + "%";
        canvas.drawText(text, centerX, centerY + (textPaint.getTextSize() / 3), textPaint);
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        invalidate();
    }
}
