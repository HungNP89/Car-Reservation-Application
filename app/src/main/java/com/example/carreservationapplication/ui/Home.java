package com.example.carreservationapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carreservationapplication.ListAllCar;
import com.example.carreservationapplication.R;
import com.example.carreservationapplication.adapters.CategoryCarAdapter;
import com.example.carreservationapplication.adapters.ListCarAdapter;
import com.example.carreservationapplication.models.CategoryCarModel;
import com.example.carreservationapplication.models.ListCarModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home extends Fragment {
    SearchView searchView;
    TextView listCar;

    //CategoryCarView
    RecyclerView categoryRec;
    ArrayList<CategoryCarModel> categoryCarModels;
    CategoryCarAdapter categoryCarAdapter;

    //ListCarView
    RecyclerView listRec;
    ArrayList<ListCarModel> listCarModels;
    ListCarAdapter listCarAdapter;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_home, container, false);
       searchView = view.findViewById(R.id.searchView);
       categoryRec = view.findViewById(R.id.categoryCar);
       listRec = view.findViewById(R.id.listCar);
       listCar = view.findViewById(R.id.textHome1);
       //database connect
        FirebaseFirestore fs = FirebaseFirestore.getInstance();

        //category car recycle view
        categoryCarModels = new ArrayList<>();
        fs.collection("Category Car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        CategoryCarModel CCM = documentSnapshot.toObject(CategoryCarModel.class);
                        categoryCarModels.add(CCM);
                        categoryCarAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting document.", task.getException());
                }
            }
        });
        categoryCarAdapter = new CategoryCarAdapter( categoryCarModels, getContext());
        categoryRec.setAdapter(categoryCarAdapter);
        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryRec.setHasFixedSize(true);
        categoryRec.setNestedScrollingEnabled(false);

        //list car recycle view
        listCarModels = new ArrayList<>();
        fs.collection("List Car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                        ListCarModel LCM = documentSnapshot.toObject(ListCarModel.class);
                        listCarModels.add(LCM);
                        listCarAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting documents",task.getException());
                }
            }
        });
        listCarAdapter = new ListCarAdapter(getActivity(),listCarModels);
        listRec.setAdapter(listCarAdapter);
        listRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        listRec.setHasFixedSize(true);
        listRec.setNestedScrollingEnabled(false);

        listCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListAllCar.class);
                startActivity(intent);
            }
        });

        //Search Engine
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
       return view;
    }

    private void filter(String text) {
        ArrayList<ListCarModel> filteredList = new ArrayList<ListCarModel>();
        for ( ListCarModel item: listCarModels) {
            if(item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()) {
            Log.w("", "Errors");
        } else {
            listCarAdapter.filterList(filteredList);
        }
    }
}