package com.example.newbmiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
    private TextView nameTextView;
    private TextView dobTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_data);
        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        nameTextView = findViewById(R.id.nameTextView);
        dobTextView = findViewById(R.id.dobTextView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);

        databaseHelper = new DatabaseHelper(this);

        displayUserData(username);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button logoutButton;
        Button homeButton;

        logoutButton = findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(ViewData.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        homeButton = findViewById(R.id.home_btn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(ViewData.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    private void displayUserData(String username) {
        Cursor cursor = databaseHelper.getUserDetails(username);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnName()));
            @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnDob()));
            @SuppressLint("Range") double height = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.getColumnHeight()));
            @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnWeight()));

            nameTextView.setText(name);
            dobTextView.setText(dob);
            heightTextView.setText(String.valueOf(height));
            weightTextView.setText(weight);
        }
        cursor.close();
    }
}