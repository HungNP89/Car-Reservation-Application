package com.example.carreservationapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.carreservationapplication.models.CategoryCarListModel;
import com.example.carreservationapplication.models.ListAllCarModel;
import com.example.carreservationapplication.models.ListCarModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Detail extends AppCompatActivity {
    ImageView imageView;
    TextView name, description, price, condition, tvStarDate, tvEndDate;
    Button bookCar;
    ListCarModel listCarModel = null;
    ListAllCarModel listAllCarModel = null;
    CategoryCarListModel categoryCarListModel = null;
    Toolbar toolbar;
    String fromDate;
    String toDate;

    int totalPrice = 0;
    int CarPrice = 0;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    private int diffInDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detailed_car_image);
        name = findViewById(R.id.detailed_car_name);
        description = findViewById(R.id.detailed_car_description);
        price = findViewById(R.id.detailed_car_price);
        condition = findViewById(R.id.detailed_car_condition);
        tvStarDate = findViewById(R.id.start_date);
        tvEndDate = findViewById(R.id.end_date);
        bookCar = findViewById(R.id.book_car);
        toolbar = findViewById(R.id.detailed_toolbar);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof ListCarModel) {
            listCarModel = (ListCarModel) obj;
        } else if (obj instanceof ListAllCarModel) {
            listAllCarModel = (ListAllCarModel) obj;
        } else if (obj instanceof CategoryCarListModel) {
            categoryCarListModel = (CategoryCarListModel) obj;
        }

        if (listCarModel != null) {
            Glide.with(getApplicationContext()).load(listCarModel.getImage()).into(imageView);
            name.setText(listCarModel.getName());
            description.setText(listCarModel.getDescription());
            price.setText(String.valueOf(listCarModel.getPrice()));
            condition.setText(listCarModel.getCondition());

            CarPrice = listCarModel.getPrice();
        }

        if (listAllCarModel != null) {
            Glide.with(getApplicationContext()).load(listAllCarModel.getImage()).into(imageView);
            name.setText(listAllCarModel.getName());
            description.setText(listAllCarModel.getDescription());
            price.setText(String.valueOf(listAllCarModel.getPrice()));
            condition.setText(listAllCarModel.getCondition());

            CarPrice = listAllCarModel.getPrice();
        }

        if (categoryCarListModel != null) {
            Glide.with(getApplicationContext()).load(categoryCarListModel.getImage()).into(imageView);
            name.setText(categoryCarListModel.getName());
            description.setText(categoryCarListModel.getDescription());
            price.setText(String.valueOf(categoryCarListModel.getPrice()));
            condition.setText(categoryCarListModel.getCondition());

            CarPrice = categoryCarListModel.getPrice();
        }

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvStarDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Detail.this, (datePicker, year1, month1, day1) -> {
                month1 = month1 + 1;
                fromDate = day1 + "/" + month1 + "/" + year1;
                tvStarDate.setText(fromDate);
            }, year, day, month);
            datePickerDialog.show();
        });

        tvEndDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Detail.this, (datePicker, year12, month12, day12) -> {
                month12 = month12 + 1;
                toDate = day12 + "/" + month12 + "/" + year12;
                tvEndDate.setText(toDate);

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date fromDateObj = sdf.parse(fromDate);
                    Date toDateObj = sdf.parse(toDate);

                    long diffInMilliseconds = toDateObj.getTime() - fromDateObj.getTime();
                    diffInDays = (int) TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

                    // Output the difference in days
                    System.out.println("Total days between " + fromDate + " and " + toDate + " is " + diffInDays);

                    if (listCarModel != null) {
                        totalPrice = listCarModel.getPrice() * diffInDays;
                    } else if (listAllCarModel != null) {
                        totalPrice = listAllCarModel.getPrice() * diffInDays;
                    } else if (categoryCarListModel != null) {
                        totalPrice = categoryCarListModel.getPrice() * diffInDays;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }, year, day, month);
            datePickerDialog.show();
        });

        bookCar.setOnClickListener(view -> addBookedCar());
    }

    public void onBackPressed() {
        Intent intent = new Intent(Detail.this, MainScreen.class);
        startActivity(intent);
        finish();
    }

    private void addBookedCar() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String, Object> bookMap = new HashMap<>();
        bookMap.put("currentTime", saveCurrentTime);
        bookMap.put("currentDate", saveCurrentDate);
        bookMap.put("carName", name.getText().toString());
        bookMap.put("carPrice", CarPrice);
        bookMap.put("startDate", tvStarDate.getText().toString());
        bookMap.put("endDate", tvEndDate.getText().toString());
        bookMap.put("totalDays", diffInDays);
        bookMap.put("totalPrice", totalPrice);

        db.collection("User Booking").document(fAuth.getCurrentUser().getUid()).collection("Car Reserved").add(bookMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            finish();
                        }
                    }
                });
                thread.start();
                Toast.makeText(Detail.this, "Car Reserved", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
    }
}