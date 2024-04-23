package com.example.psychomeclick.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.model.Message;
import com.example.psychomeclick.recyclers.ChatAdapter;

public class ChatView extends RecyclerView {

    private ChatAdapter adapter;

    public ChatView(Context context) {
        super(context);
        init(context);
    }

    public ChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(false);
        setLayoutManager(layoutManager);
        adapter = new ChatAdapter(context);
        setAdapter(adapter);
    }

    public void addMessage(String text, boolean isLeft, int color, boolean graduallyWrite, int icon) {
        Message message = new Message(text, isLeft, color, graduallyWrite, icon);
        adapter.addMessage(message);
        scrollToPosition(adapter.getItemCount() - 1);
    }
}
