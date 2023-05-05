package com.example.carreservationapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carreservationapplication.R;
import com.example.carreservationapplication.adapters.CarReservedAdapter;
import com.example.carreservationapplication.models.CarReservedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Reserved extends Fragment {

    RecyclerView recyclerView;
    List<CarReservedModel> carReservedModel;
    CarReservedAdapter carReservedAdapter;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    public Reserved() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserved, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.reserved_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        carReservedModel = new ArrayList<>();
        carReservedAdapter = new CarReservedAdapter(getActivity(),carReservedModel);
        recyclerView.setAdapter(carReservedAdapter);

        fStore.collection("User Booking").document(fAuth.getCurrentUser().getUid()).collection("Car Reserved")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (DocumentSnapshot ds: task.getResult().getDocuments()) {
                                String documentID = ds.getId();
                                CarReservedModel CRM = ds.toObject(CarReservedModel.class);
                                CRM.setDocumentId(documentID);
                                carReservedModel.add(CRM);
                                carReservedAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        return view;
    }
}