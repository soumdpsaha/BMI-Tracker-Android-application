package com.example.newbmiapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class InsertData extends AppCompatActivity {

    private EditText nameInput;
    private EditText dobInput;
    private EditText heightInput;
    private EditText weightInput;
    private Button submitButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_insert_data);
        String username = getIntent().getStringExtra("USERNAME"); //primary key value

        // Initialize views
        nameInput = findViewById(R.id.name_input);
        dobInput = findViewById(R.id.dob_input);
        heightInput = findViewById(R.id.height_input);
        weightInput = findViewById(R.id.weight_input);
        submitButton = findViewById(R.id.submit_btn);

        // Make dobInput not editable
        dobInput.setKeyListener(null);

        // Initialize dbHelper
        dbHelper = new DatabaseHelper(this);




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String dob = dobInput.getText().toString();
                double height = Double.parseDouble(heightInput.getText().toString());
                String weight = weightInput.getText().toString();

                dbHelper.updateUserDetails(username, name, dob, height, weight);

                // Redirect to HomePage
                Intent intent = new Intent(InsertData.this, HomePage.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        dobInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

    }

    private void showDatePicker() {
        // Get current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Set date in dd/mm/yyyy format
                        dobInput.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}



//ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });