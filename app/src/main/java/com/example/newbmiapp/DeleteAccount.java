package com.example.newbmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeleteAccount extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);

        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        Button deleteButton;
        Button goBack;

        deleteButton = findViewById(R.id.yes);

        databaseHelper = new DatabaseHelper(this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete function called
                databaseHelper.deleteUser(username);

                // Redirect to HomePage
                Intent intent = new Intent(DeleteAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        goBack = findViewById(R.id.no);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to HomePage
                Intent intent = new Intent(DeleteAccount.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }
}