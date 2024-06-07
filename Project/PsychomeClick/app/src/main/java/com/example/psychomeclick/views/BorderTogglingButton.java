package com.example.psychomeclick.views;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;


/**
 * The type Border toggling button.
 */
public class BorderTogglingButton extends androidx.appcompat.widget.AppCompatImageButton {
    /**
     * The Is border enabled.
     */
    protected boolean isBorderEnabled = false;
    /**
     * The On border toggle listener.
     */
    protected OnBorderToggleListener onBorderToggleListener;

    /**
     * The Number.
     */
    protected int number=-1;

    /**
     * Instantiates a new Border toggling button.
     *
     * @param context the context
     */
    public BorderTogglingButton(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Border toggling button.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BorderTogglingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Border toggling button.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public BorderTogglingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Get number int.
     *
     * @return the int
     */
    public int getNumber(){
        return this.number;
    }
    private void setNumber(int number){
        this.number=number;
    }
    private void init() {
        // set initial appearance (no border)
        setBorderEnabled(false);
        setOnClickListener(v -> toggleBorder());

    }

    /**
     * Toggle border.
     */
    public void toggleBorder() {
        isBorderEnabled = !isBorderEnabled;
        setBorderEnabled(isBorderEnabled);

        if (onBorderToggleListener != null) {
            onBorderToggleListener.onBorderToggled(isBorderEnabled);
        }
    }

    /**
     * Enable.
     */
    public void enable(){
        isBorderEnabled=true;
        setBorderEnabled(true);
    }

    /**
     * Disable.
     */
    public void disable(){
        isBorderEnabled=false;
        setBorderEnabled(false);
    }
    private void setBorderEnabled(boolean enabled) {
        if (enabled) {
            // set border color and width
            getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            getBackground().setAlpha(255); // Set alpha to opaque
        } else {
            // remove border
            getBackground().clearColorFilter();
        }
    }

    /**
     * Set border color.
     *
     * @param color the color
     */
    public void setBorderColor(int color){
        this.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
   }

    /**
     * Is on boolean.
     *
     * @return the boolean
     */
    public boolean isOn(){
        return this.isBorderEnabled;
    }

    /**
     * The interface On border toggle listener.
     */
    public interface OnBorderToggleListener {
        /**
         * On border toggled.
         *
         * @param isBorderEnabled the is border enabled
         */
        void onBorderToggled(boolean isBorderEnabled);
    }

    /**
     * Sets on border toggled listener.
     *
     * @param listener the listener
     */
    public void setOnBorderToggledListener(OnBorderToggleListener listener) {
        this.onBorderToggleListener = listener;
    }
}
