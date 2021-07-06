package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatModel> chatModelList;
    private Context context;

    public MessageAdapter(ArrayList<ChatModel> chatModelList, Context context) {
        this.chatModelList = chatModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.user_view, parent, false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.bot_view, parent, false);
                return new BotViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModelList.get(position);
        switch (chatModel.getSender()){
            case "USER":
                ((UserViewHolder)holder).userQuery.setText(chatModel.getMessage());
                break;
            case "CHAT_BOT":
                ((BotViewHolder)holder).botReply.setText(chatModel.getMessage());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch(chatModelList.get(position).getSender()){
            case "USER" :
                return 0;
            case "CHAT_BOT":
                return 1;
            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userQuery;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userQuery = itemView.findViewById(R.id.user_query);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView botReply;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botReply = itemView.findViewById(R.id.bot_reply);
        }
    }
}
