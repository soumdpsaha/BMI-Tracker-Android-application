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

public class HomePage extends AppCompatActivity {
    private TextView nameTextView;
    private DatabaseHelper databaseHelper;
    private Button viewButton;
    private Button updateButton;
    private Button checkBmiButton;
    private Button logoutButton;
    private Button deleteButton;
    private Button whButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home_page);
        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        nameTextView = findViewById(R.id.nameTextView);

        databaseHelper = new DatabaseHelper(this);

        displayName(username);

        viewButton = findViewById(R.id.button_view_data);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, ViewData.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        updateButton = findViewById(R.id.button_update_data);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, UpdateData.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        checkBmiButton = findViewById(R.id.button_calculate_bmi);

        checkBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, ShowBMI.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        logoutButton = findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        deleteButton = findViewById(R.id.delete_btn);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, DeleteAccount.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        whButton = findViewById(R.id.button_weight_history);

        whButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(HomePage.this, WeightRecords.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    private void displayName(String username) {
        Cursor cursor = databaseHelper.getUserDetails(username);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnName()));

            nameTextView.setText(name);
        }
        cursor.close();
    }
}