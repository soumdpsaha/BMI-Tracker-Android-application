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

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText usernameInput;
    private Button loginButton;

    @Override
    public void onBackPressed() {
        // Exit the app when back button is pressed
        finishAffinity();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        usernameInput = findViewById(R.id.username_input);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                if (!dbHelper.userExists(username)) {
                    dbHelper.addUser(username); // User added, proceed to next activity or show message
                    Intent intent = new Intent(MainActivity.this, InsertData.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                } else {
                    // User exists, proceed to next activity or show message
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                }
            }
        });


    }
}
