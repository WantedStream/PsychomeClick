package com.example.psychomeclick.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * The type Percentage ring view.
 */
public class PercentageRingView extends View {

    private int percentage;
    private Paint innerRingPaint;
    private Paint outerRingPaint;
    private Paint textPaint;
    private RectF innerRingBounds;
    private RectF outerRingBounds;

    /**
     * Instantiates a new Percentage ring view.
     *
     * @param context the context
     */
    public PercentageRingView(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Percentage ring view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PercentageRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Percentage ring view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
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

        // draw inner ring
        innerRingBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(innerRingBounds, -90, 360, false, innerRingPaint);

        // draw outer ring based on percentage
        outerRingBounds.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(outerRingBounds, -90, (float) (3.6 * percentage), false, outerRingPaint);

        String text = percentage + "%";
        canvas.drawText(text, centerX, centerY + (textPaint.getTextSize() / 3), textPaint);
    }

    /**
     * Sets percentage.
     *
     * @param percentage the percentage
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
        invalidate();
    }
    /*
    super.onDraw(canvas): This line calls the onDraw method of the superclass, ensuring that any parent views are drawn first.
    int centerX = getWidth() / 2; and int centerY = getHeight() / 2;: These lines calculate the center coordinates of the view.
    int radius = Math.min(centerX, centerY) - 50;: This line calculates the radius of the circle to be drawn. It takes the minimum of centerX and centerY and subtracts 50 to leave space around the edges.
    Draw inner ring:
            innerRingBounds.set(...): This line sets the bounds of the inner ring using the calculated center coordinates and radius.
            canvas.drawArc(innerRingBounds, -90, 360, false, innerRingPaint): This line draws a complete circle (360 degrees) representing the inner ring using the drawArc method. The arc starts at -90 degrees (12 o'clock position) and sweeps 360 degrees clockwise. The innerRingPaint is used to define the style, color, and stroke width of the inner ring.
            Draw outer ring based on percentage:
            outerRingBounds.set(...): This line sets the bounds of the outer ring similar to the inner ring.
            canvas.drawArc(outerRingBounds, -90, (float) (3.6 * percentage), false, outerRingPaint): This line draws an arc representing the outer ring based on the percentage value. The arc starts at -90 degrees (12 o'clock position) and sweeps a portion of the circle corresponding to the percentage value. The calculation (3.6 * percentage) converts the percentage to degrees (since 100% corresponds to a full circle of 360 degrees). The outerRingPaint is used to define the style, color, and stroke width of the outer ring.
            Draw text inside the circle:
            String text = percentage + "%";: This line creates a string representation of the percentage value to be displayed.
            canvas.drawText(text, centerX, centerY + (textPaint.getTextSize() / 3), textPaint): This line draws the percentage text at the center of the view. The text is drawn using the drawText method. The (centerX, centerY + (textPaint.getTextSize() / 3)) specifies the coordinates where the text will be drawn, with adjustments made to vertically center it. The textPaint is used to define the style, color, and size of the text.
*/
}
