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
import com.example.carreservationapplication.models.CategoryCarListModel;

import java.util.List;

public class CategoryCarListAdapter extends RecyclerView.Adapter<CategoryCarListAdapter.ViewHolder> {

    Context context;
    List<CategoryCarListModel> list;

    public CategoryCarListAdapter(Context context, List<CategoryCarListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryCarListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCarListAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView3);
        holder.name3.setText(list.get(position).getName());
        holder.description2.setText(list.get(position).getDescription());
        holder.price2.setText(String.valueOf(list.get(position).getPrice()));

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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView3;
        TextView name3, description2, price2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView3 = itemView.findViewById(R.id.car_list);
            name3 = itemView.findViewById(R.id.carName);
            description2 = itemView.findViewById(R.id.carDescription);
            price2 = itemView.findViewById(R.id.carPrice);
        }
    }
}
