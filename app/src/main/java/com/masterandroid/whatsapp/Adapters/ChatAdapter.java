package com.masterandroid.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.masterandroid.whatsapp.R;
import com.masterandroid.whatsapp.UserModel.Messages;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Messages> messagesModel;

    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<Messages> messagesModel, Context context) {
        this.messagesModel = messagesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesModel.get(position).getUID().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Messages messages = messagesModel.get(position);

    if(holder.getClass() == SenderViewHolder.class)
    {
        ((SenderViewHolder)holder).senderMessage.setText(messages.getMessage());
    }
    else
    {
        ((RecieverViewHolder)holder).recieverMessage.setText(messages.getMessage());
    }

    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView recieverMessage,recieverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMessage=itemView.findViewById(R.id.recieverText);
            recieverTime=itemView.findViewById(R.id.recieverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }
}
