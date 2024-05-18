package com.example.psychomeclick.views;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;


public class BorderTogglingButton extends androidx.appcompat.widget.AppCompatImageButton {
    protected boolean isBorderEnabled = false;
    protected OnBorderToggleListener onBorderToggleListener;

    protected int number=-1;
    public BorderTogglingButton(Context context) {
        super(context);
        init();
    }

    public BorderTogglingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BorderTogglingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

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

    public void toggleBorder() {
        isBorderEnabled = !isBorderEnabled;
        setBorderEnabled(isBorderEnabled);

        if (onBorderToggleListener != null) {
            onBorderToggleListener.onBorderToggled(isBorderEnabled);
        }
    }
    public void enable(){
        isBorderEnabled=true;
        setBorderEnabled(true);
    }
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
   public void setBorderColor(int color){
        this.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
   }
    public boolean isOn(){
        return this.isBorderEnabled;
    }
    public interface OnBorderToggleListener {
        void onBorderToggled(boolean isBorderEnabled);
    }

    public void setOnBorderToggledListener(OnBorderToggleListener listener) {
        this.onBorderToggleListener = listener;
    }
}
