package com.example.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView tvResults2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResults2=findViewById(R.id.tvResults2);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String name=sharedPreferences.getString("Name","Own");
        String email=sharedPreferences.getString("Email","own");

        String resultsText = "Name: " + name + "\n" +
                "Email: " + email;

        tvResults2.setText(resultsText);
        }
}