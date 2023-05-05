package com.example.carreservationapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carreservationapplication.Detail;
import com.example.carreservationapplication.R;
import com.example.carreservationapplication.models.ListCarModel;

import java.util.ArrayList;

public class ListCarAdapter extends RecyclerView.Adapter<ListCarAdapter.ViewHolder> {
    Context context;
    ArrayList<ListCarModel> list;
    LayoutInflater layoutInflater;

    public ListCarAdapter(Context context, ArrayList<ListCarModel> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void filterList(ArrayList<ListCarModel> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListCarAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListCarAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView2);
        holder.name2.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("detailed", list.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int limit = 5;
        return Math.min(list.size(), limit);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView2;
        TextView name2, description, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.car_list);
            name2 = itemView.findViewById(R.id.carName);
            description = itemView.findViewById(R.id.carDescription);
            price = itemView.findViewById(R.id.carPrice);
        }
    }
}
