package com.thadocizn.lambdamessageboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    ArrayList<Message> messages;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMessage;

        public MyViewHolder(View view) {
            super(view);
            txtMessage = view.findViewById(R.id.tvMessages);

        }
    }

    public RecyclerViewAdapter(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        Message room = messages.get(i);
        myViewHolder.txtMessage.setText(new StringBuilder().append(room.sender).append("\n").append(room.text).append("\n").append(room.timestamp).toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
