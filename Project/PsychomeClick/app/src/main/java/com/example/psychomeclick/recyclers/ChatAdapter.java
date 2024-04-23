package com.example.psychomeclick.recyclers;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<Message> messages;
    private Context context;

    public ChatAdapter(Context context) {
        this.context = context;
        this.messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ImageView iconImage;
        RelativeLayout parent;
        boolean grad=true;
        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            iconImage = itemView.findViewById(R.id.icon_image);
            parent= itemView.findViewById(R.id.parent);
        }

        void bind(Message message) {

            messageText.setText("");
            if (message.isGraduallyWrite()&&grad) {
                graduallyWriteText(messageText, message.getText());
                grad=false;
            } else {
                messageText.setText(message.getText());
            }
            if (message.getIcon() != 0) {
                iconImage.setImageResource(message.getIcon());
                iconImage.setVisibility(View.VISIBLE);
            } else {
                iconImage.setVisibility(View.GONE);
            }
            if(message.isLeft()){
                ((RelativeLayout) parent).setGravity(Gravity.LEFT);
            }
            else{
                ((RelativeLayout) parent).setGravity(Gravity.RIGHT);
            }



        }

        private void graduallyWriteText(final TextView textView, final String text) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                int index = 0;

                @Override
                public void run() {
                    textView.setText(text.substring(0, index++));
                    if (index <= text.length()) {
                        handler.postDelayed(this, 50); // Adjust the delay time as needed
                    }
                }
            }, 50); // Adjust the initial delay time as needed
        }
    }
}
