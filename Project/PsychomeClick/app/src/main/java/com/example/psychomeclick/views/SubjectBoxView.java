package com.example.psychomeclick.views;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.example.psychomeclick.R;

public class SubjectBoxView extends androidx.appcompat.widget.AppCompatTextView implements View.OnDragListener {

    public SubjectBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Set background drawable (shape drawable for box appearance)
        //setBackgroundResource(R.drawable.subject_box_background);
        setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Optional: Visual drag feedback
                setAlpha(0.5f);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                setAlpha(1.0f);
                return true;
        }
        return false;
    }


}
