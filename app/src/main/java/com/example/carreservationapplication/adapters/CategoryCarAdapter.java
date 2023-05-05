package com.example.carreservationapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carreservationapplication.CarListByCategory;
import com.example.carreservationapplication.R;
import com.example.carreservationapplication.models.CategoryCarModel;

import java.util.ArrayList;

public class CategoryCarAdapter extends RecyclerView.Adapter<CategoryCarAdapter.ViewHolder> {
    Activity activity;
    ArrayList<CategoryCarModel> list;
    Context context;

    public CategoryCarAdapter(ArrayList<CategoryCarModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryCarAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCarAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarListByCategory.class);
                intent.putExtra("type", list.get(holder.getAdapterPosition()).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.category_image);
            textView = itemView.findViewById(R.id.category_name);
            cardView = itemView.findViewById(R.id.categoryView);
        }
    }
}
