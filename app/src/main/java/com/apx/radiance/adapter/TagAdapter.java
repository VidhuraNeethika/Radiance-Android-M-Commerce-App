package com.apx.radiance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.apx.radiance.R;
import com.apx.radiance.model.Tags;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.Viewholder> {

    Context context;
    ArrayList<Tags> tagsArrayList;

    public TagAdapter(Context context,ArrayList<Tags> tagsArrayList){
        this.context = context;
        this.tagsArrayList = tagsArrayList;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        Button tagButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tagButton = itemView.findViewById(R.id.tagButton);
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tag_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Tags tags = tagsArrayList.get(position);
        holder.tagButton.setText(tags.getTagName());

    }

    @Override
    public int getItemCount() {
        return tagsArrayList.size();
    }


}


