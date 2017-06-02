package com.zimincom.mafiaonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zimincom.mafiaonline.layout.ChatLayout;
import com.zimincom.mafiaonline.item.MessageItem;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 5. 29..
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    ArrayList<MessageItem> messages;
    Context context;
    int chatLayout;
    String userName;

    public MessageAdapter(Context context, ArrayList<MessageItem> messages, int chat_item, String userName) {
        this.context = context;
        this.messages = messages;
        this.chatLayout = chat_item;
        this.userName = userName;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(chatLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (messages.get(position).userName.equals(userName)) {
            holder.chatLayout.setToMyMessage();
            holder.chatLayout.setGravity(Gravity.END);
        }
        holder.chatLayout.setName(messages.get(position).userName);
        holder.chatLayout.setMessage(messages.get(position).content);
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(MessageItem messageItem) {
        messages.add(messageItem);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ChatLayout chatLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            chatLayout = (ChatLayout) itemView;

        }
    }
}
