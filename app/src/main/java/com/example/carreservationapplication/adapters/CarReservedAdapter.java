package com.example.carreservationapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carreservationapplication.R;
import com.example.carreservationapplication.models.CarReservedModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CarReservedAdapter extends RecyclerView.Adapter<CarReservedAdapter.ViewHolder> {

    Context context;
    List<CarReservedModel> list;
    FirebaseFirestore fs;
    FirebaseAuth fa;

    public CarReservedAdapter(Context context, List<CarReservedModel> list) {
        this.context = context;
        this.list = list;
        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CarReservedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_reserved_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarReservedAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getCarName());
        holder.price.setText(String.valueOf(list.get(position).getCarPrice()));
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.dateFrom.setText(list.get(position).getStartDate());
        holder.dateTo.setText(list.get(position).getEndDate());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalDays.setText(String.valueOf(list.get(position).getTotalDays()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,date,time,dateFrom,dateTo,totalPrice,totalDays;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.car_reserved_name);
            price = itemView.findViewById(R.id.car_reserved_price);
            date = itemView.findViewById(R.id.car_reserved_date);
            time = itemView.findViewById(R.id.car_reserved_time);
            dateFrom = itemView.findViewById(R.id.car_reserved_from);
            dateTo = itemView.findViewById(R.id.car_reserved_to);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalDays = itemView.findViewById(R.id.total_days);
        }
    }
}
