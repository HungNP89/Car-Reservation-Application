package com.example.carreservationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carreservationapplication.adapters.CategoryCarListAdapter;
import com.example.carreservationapplication.models.CategoryCarListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CarListByCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryCarListAdapter carListAdapter;
    List<CategoryCarListModel> carListModel;

    FirebaseFirestore fs;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list_by_category);

        recyclerView = findViewById(R.id.show_cat_item);
        toolbar = findViewById(R.id.show_cat_toolbar);
        carListModel = new ArrayList<>();
        carListAdapter = new CategoryCarListAdapter(this, carListModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(carListAdapter);

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fs = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        if (type == null || type.isEmpty()) {
            fs.collection("List Car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            CategoryCarListModel CCLM = document.toObject(CategoryCarListModel.class);
                            carListModel.add(CCLM);
                            carListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("hybrid")) {
            fs.collection("List Car").whereEqualTo("type", "hybrid").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds : dsList) {
                        CategoryCarListModel CCLM = ds.toObject(CategoryCarListModel.class);
                        carListModel.add(CCLM);
                        carListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("electric")) {
            fs.collection("List Car").whereEqualTo("type", "electric").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds : dsList) {
                        CategoryCarListModel CCLM = ds.toObject(CategoryCarListModel.class);
                        carListModel.add(CCLM);
                        carListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if (type != null && type.equalsIgnoreCase("normal")) {
            fs.collection("List Car").whereEqualTo("type", "normal").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds : dsList) {
                        CategoryCarListModel CCLM = ds.toObject(CategoryCarListModel.class);
                        carListModel.add(CCLM);
                        carListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    public void onBackPressed() {
        Intent intent = new Intent(CarListByCategory.this, MainScreen.class);
        startActivity(intent);
        finish();
    }
}