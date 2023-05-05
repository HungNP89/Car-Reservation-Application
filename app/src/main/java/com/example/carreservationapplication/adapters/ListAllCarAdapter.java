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
import com.example.carreservationapplication.models.ListAllCarModel;

import java.util.List;

public class ListAllCarAdapter extends RecyclerView.Adapter<ListAllCarAdapter.ViewHolder> {

    Context context;
    List<ListAllCarModel> list;

    public ListAllCarAdapter(Context context, List<ListAllCarModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ListAllCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListAllCarAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAllCarAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView4);
        holder.name4.setText(list.get(position).getName());
        holder.description3.setText(list.get(position).getDescription());
        holder.price3.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Detail.class);
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
        ImageView imageView4;
        TextView name4, description3, price3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView4 = itemView.findViewById(R.id.car_list);
            name4 = itemView.findViewById(R.id.carName);
            description3 = itemView.findViewById(R.id.carDescription);
            price3 = itemView.findViewById(R.id.carPrice);
        }
    }
}
