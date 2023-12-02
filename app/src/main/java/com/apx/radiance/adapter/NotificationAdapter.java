package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.model.NotificationItems;
import com.apx.radiance.model.ProductItem;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationItems> notificationItemsList;

    public NotificationAdapter(ArrayList<NotificationItems> notificationItemsList) {
        this.notificationItemsList = notificationItemsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, description,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.notificationIndicator);
            name = itemView.findViewById(R.id.notificationTitle);
            description = itemView.findViewById(R.id.notificationDesText);
            time = itemView.findViewById(R.id.notificationTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_horizontal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItems currentItem = notificationItemsList.get(position);

        holder.imageView.setImageResource(currentItem.getImageSource());
        holder.name.setText(currentItem.getTitle());
        holder.description.setText(currentItem.getDescription());
        holder.time.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return notificationItemsList.size();
    }
}
