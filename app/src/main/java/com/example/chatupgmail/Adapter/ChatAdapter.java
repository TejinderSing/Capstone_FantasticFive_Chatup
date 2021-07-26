package com.example.chatupgmail.Adapter;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatupgmail.Models.Messages;
import com.example.chatupgmail.R;
import com.example.chatupgmail.databinding.RecevierBinding;
import com.example.chatupgmail.databinding.SenderBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messagesArrayList;

    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    public ChatAdapter(Context context, ArrayList<Messages> messagesArrayList) {

        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recevier,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.binding.sendermessage.setText(messages.getMessage());
            System.out.println(messages.getMessage());
            viewHolder.binding.timeofmessage.setText(messages.getCurrenttime());
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.binding.reciever.setText(messages.getMessage());
            viewHolder.binding.timeofmessage.setText(messages.getCurrenttime());
        }

    }


    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))

        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }








    class SenderViewHolder extends RecyclerView.ViewHolder
    {
        SenderBinding binding;
//        TextView textViewmessaage;
//        TextView timeofmessage;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewmessaage=itemView.findViewById(R.id.sendermessage);
//            timeofmessage=itemView.findViewById(R.id.timeofmessage);
            binding = SenderBinding.bind(itemView);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder
    {
        RecevierBinding binding;
//        TextView textViewmessaage;
//        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewmessaage=itemView.findViewById(R.id.sendermessage);
//            timeofmessage=itemView.findViewById(R.id.timeofmessage);
            binding = RecevierBinding.bind(itemView);
        }
    }




}
