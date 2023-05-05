package com.example.carreservationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.carreservationapplication.adapters.ListAllCarAdapter;
import com.example.carreservationapplication.models.ListAllCarModel;
import com.example.carreservationapplication.models.ListCarModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListAllCar extends AppCompatActivity {
    RecyclerView recyclerView;
    ListAllCarAdapter listAllCarAdapter;
    List<ListAllCarModel> listAllCarModelList;

    FirebaseFirestore fs;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_car);

        recyclerView = findViewById(R.id.show_all_item);
        toolbar = findViewById(R.id.show_all_toolbar);

        fs = FirebaseFirestore.getInstance();
        listAllCarModelList = new ArrayList<>();
        fs.collection("List Car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        ListAllCarModel LACM = documentSnapshot.toObject(ListAllCarModel.class);
                        listAllCarModelList.add(LACM);
                        listAllCarAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting documents",task.getException());
                }
            }
        });
        listAllCarAdapter = new ListAllCarAdapter(this, listAllCarModelList);
        recyclerView.setAdapter(listAllCarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(ListAllCar.this, MainScreen.class);
        startActivity(intent);
        finish();
    }
}