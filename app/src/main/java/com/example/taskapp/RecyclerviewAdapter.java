package com.example.taskapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private final RecyclerViewClickListener listener;
    Context context;
    ArrayList<String> nameList;
    ArrayList<String> idList;
    int[] img;

    public RecyclerviewAdapter(Context context, ArrayList<String> nameList, ArrayList<String> idList, int[] img, RecyclerViewClickListener listener) {
        this.context = context;
        this.nameList = nameList;
        this.idList = idList;
        this.img = img;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            textView = itemView.findViewById(R.id.textview1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(nameList.get(position));
        holder.imageView.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}
