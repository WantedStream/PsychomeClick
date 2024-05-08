package com.example.psychomeclick.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.psychomeclick.R;

public class StopView extends View {

    private String stopText;
    private int stopColor;
    private int textColor;
    private float textSize;
    private Paint paint;
    private int sxa;

    public StopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StopWatchStopperView);
        stopText = a.getString(R.styleable.StopWatchStopperView_stopText);
        stopColor = a.getColor(R.styleable.StopWatchStopperView_stopColor, Color.RED);
        textColor = a.getColor(R.styleable.StopWatchStopperView_textColor, Color.WHITE);
        textSize = a.getDimension(R.styleable.StopWatchStopperView_textSize, 20f);
        a.recycle();

        paint = new Paint();
        paint.setAntiAlias(true); // Smoother edges
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate dimensions based on view size
        float radius = Math.min(getWidth(), getHeight()) / 2f;
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float rectangleWidth = radius / 3f;
        float rectangleHeight = radius / 5f;

        // Draw circle
        paint.setColor(stopColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw rectangle (top center of the circle)
        paint.setColor(Color.BLACK); // Default black for the rectangle
        canvas.drawRect(centerX - rectangleWidth / 2f, centerY - radius - rectangleHeight,
                centerX + rectangleWidth / 2f, centerY - radius, paint);

        // Draw text (center it horizontally within the circle)
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(stopText);
        float textX = (centerX - textWidth / 2f);
        float textY = (centerY + paint.ascent()) / 2f; // Center vertically
        canvas.drawText(stopText, textX, textY, paint);
    }

    // Setters for programmatic customization
    public void setText(String stopText) {
        this.stopText = stopText;
        invalidate(); // Trigger redraw
    }

    public void setStopColor(int stopColor) {
        this.stopColor = stopColor;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }
}