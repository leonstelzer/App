package com.mind.simplelogin.events.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mind.simplelogin.R;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    public List<ChatMessage> chatList;
    public Context context;
    public LayoutInflater layoutInflater;


    public ChatListAdapter (Context context, List<ChatMessage> chatList){
        this.chatList = chatList;
        this.context= context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatList.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.message, null);

        ((TextView) convertView.findViewById(R.id.message_user)).setText(chatList.get(position).getMessageUser());
        ((TextView) convertView.findViewById(R.id.message_time)).setText(chatList.get(position).getMessageText());
        ((TextView) convertView.findViewById(R.id.message_text)).setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                chatList.get(position).getMessageTime()));
        return convertView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;
        public TextView messageUser;
        public TextView messageTime;



        public ViewHolder(@NonNull View v) {
            super(v);
            messageText = (TextView)v.findViewById(R.id.message_text);
            messageUser = (TextView)v.findViewById(R.id.message_user);
            messageTime = (TextView)v.findViewById(R.id.message_time);

        }
    }
}
