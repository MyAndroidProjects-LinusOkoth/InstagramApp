package com.example.linusgram;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapater extends RecyclerView.Adapter<PostAdapater.ViewHolder> {

    private Context context;
    private List list;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
