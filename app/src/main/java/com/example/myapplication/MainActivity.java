package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button calculate;
    private EditText name;
    private EditText email;
    private Button save;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName=name.getText().toString();
                String studentEmail=email.getText().toString();
                saveResult(studentName,studentEmail);
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        });

        calculate=findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                        startActivity(intent);
            }
        });
    }

    private void saveResult(String name,String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.putString("Email", email);
        editor.apply();
    }

}