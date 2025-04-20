package com.example.newbmiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;

public class WeightRecords extends AppCompatActivity {

    private ListView weightListView;
    private List<String> weightRecords; // Replace with actual data
    private WeightHistoryDatabaseHelper weightHistoryDbHelper;
//    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_records);

        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        weightListView = findViewById(R.id.weightListView);
        weightRecords = new ArrayList<>(); // Initialize with actual weight records

        weightHistoryDbHelper = new WeightHistoryDatabaseHelper(this);

        List<String> retrievedWeightRecords = getWeightRecordsFromDatabase(username);

        if (retrievedWeightRecords != null) {
            weightRecords.addAll(retrievedWeightRecords);
        } else {
            // Handle case when no weight records are found
            weightRecords.add("No weight records found for " + username);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weightRecords);
        weightListView.setAdapter(adapter);

//        barChart = findViewById(R.id.barChart);
//        setupBarChart();

        Button logoutButton;
        Button homeButton;

        logoutButton = findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(WeightRecords.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        homeButton = findViewById(R.id.home_btn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(WeightRecords.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    // Replace with actual method to retrieve weight history from the database
    private List<String> getWeightRecordsFromDatabase(String username) {
        List<String> weightRecords = new ArrayList<>();

        // Assuming weightHistoryDbHelper is an instance of WeightHistoryDatabaseHelper
        Cursor cursor = weightHistoryDbHelper.getWeightHistory(username);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
                @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
                String weightRecord = timestamp + ": " + weight + " kg";
                weightRecords.add(weightRecord);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            // Handle case when no records are found
            weightRecords.add("No weight records found for " + username);
        }

        return weightRecords;
    }



//    private void setupBarChart() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        // Replace with your actual weight records data
//        entries.add(new BarEntry(0, 65.5f)); // Example: Date 1, Weight 65.5 kg
//        entries.add(new BarEntry(1, 66.0f)); // Example: Date 2, Weight 66.0 kg
//        // Add more entries as needed
//
//        BarDataSet dataSet = new BarDataSet(entries, "Weight Records");
//        dataSet.setColor(getResources().getColor(R.color.colorPrimary)); // Customize color
//
//        BarData barData = new BarData(dataSet);
//        barChart.setData(barData);
//
//        // Customize chart appearance (e.g., axis labels, legend, etc.)
//        // ...
//    }
}
