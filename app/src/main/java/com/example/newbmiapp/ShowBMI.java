package com.example.newbmiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowBMI extends AppCompatActivity {
    private TextView heightTextView;
    private TextView weightTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_bmi);

        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);

        // Create an instance of DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Fetch height and weight from your DatabaseHelper
        double heightInCentiMeters = dbHelper.getHeight(username); // Get height in meters
        double weightInKg = Double.parseDouble(dbHelper.getWeight(username)); // Get weight in kilograms

        double heightInMeters = heightInCentiMeters/100;

        // Calculate BMI
        double bmi = weightInKg / (heightInMeters * heightInMeters);

//        displayUserData(username);

        //display the height and weight
        heightTextView.setText(String.valueOf(heightInCentiMeters)); // Display BMI with two decimal places
        weightTextView.setText(String.valueOf(weightInKg)); // Display BMI with two decimal places

        // Display the calculated BMI
        TextView bmiTextView = findViewById(R.id.bmiTextView);
        bmiTextView.setText(String.format("%.2f", bmi)); // Display BMI with two decimal places

        Button logoutButton;
        Button homeButton;

        logoutButton = findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(ShowBMI.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        homeButton = findViewById(R.id.home_btn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(ShowBMI.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}