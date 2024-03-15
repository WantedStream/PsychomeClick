package com.example.psychomeclick.views;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;


public class BorderTogglingButton extends androidx.appcompat.widget.AppCompatImageButton {
    private boolean isBorderEnabled = false;

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

    private void init() {
        // Set initial appearance (without border)
        setBorderEnabled(false);
        setOnClickListener(v -> toggleBorder());
    }

    public void toggleBorder() {
        isBorderEnabled = !isBorderEnabled;
        setBorderEnabled(isBorderEnabled);
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
            // Set border color and width
            getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            getBackground().setAlpha(255); // Set alpha to opaque
        } else {
            // Remove border
            getBackground().clearColorFilter();
        }
    }
}
