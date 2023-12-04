package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.model.Orders;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    ArrayList<Orders> orderArrayList = new ArrayList<>();

    public OrdersAdapter(ArrayList<Orders> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView productName,qty,price,date,brand;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productNameText);
            qty=itemView.findViewById(R.id.qtyText);
            price=itemView.findViewById(R.id.priceText);
            date=itemView.findViewById(R.id.dateText);
        }
    }
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        Orders order = orderArrayList.get(position);
        System.out.println(position);
        holder.productName.setText(order.getProduct().getName());
        holder.qty.setText(String.valueOf("Quantity : "+ order.getQty()));
        holder.price.setText(String.valueOf("LKR "+ order.getProduct().getPrice()+"0"));
        holder.date.setText(order.getDate());
        holder.imageView.setImageResource(order.getProduct().getImageSource());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}
