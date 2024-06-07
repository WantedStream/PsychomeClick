package com.example.psychomeclick.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.model.Message;
import com.example.psychomeclick.recyclers.ChatAdapter;

/**
 * The type Chat view.
 */
public class ChatView extends RecyclerView {

    private ChatAdapter adapter;

    /**
     * Instantiates a new Chat view.
     *
     * @param context the context
     */
    public ChatView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Instantiates a new Chat view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public ChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Instantiates a new Chat view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
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

    /**
     * Add message.
     *
     * @param text           the text
     * @param isLeft         the is left
     * @param graduallyWrite the gradually write
     * @param icon           the icon
     */
    public void addMessage(String text, boolean isLeft, boolean graduallyWrite, int icon) {
        Message message = new Message(text, isLeft, graduallyWrite, icon);
        adapter.addMessage(message);
        scrollToPosition(adapter.getItemCount() - 1);
    }
}
