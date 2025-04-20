package com.example.newbmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateData extends AppCompatActivity {

    private EditText heightInput;
    private EditText weightInput;
    private Button submitButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_data);
        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        heightInput = findViewById(R.id.height_input);
        weightInput = findViewById(R.id.weight_input);
        submitButton = findViewById(R.id.submit_btn);

        dbHelper = new DatabaseHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double height = Double.parseDouble(heightInput.getText().toString());
                String weight = weightInput.getText().toString();

                dbHelper.updateUser(username, height, weight);

                // Redirect to HomePage
                Intent intent = new Intent(UpdateData.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        Button logoutButton;
        Button homeButton;

        logoutButton = findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(UpdateData.this, MainActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        homeButton = findViewById(R.id.home_btn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(UpdateData.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}