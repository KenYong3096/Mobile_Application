package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.health.connect.datatypes.units.Percentage;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class MainActivity2 extends AppCompatActivity {

    private EditText billAmountInput;
    private EditText numStudentsInput;
    private TextView tvResults;
    private SharedPreferences sharedPreferences;
    private SQLiteAdapter sqliteAdapter = new SQLiteAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        billAmountInput=findViewById(R.id.billAmountInput);
        numStudentsInput=findViewById(R.id.numStudentsInput);
        sharedPreferences = getSharedPreferences("BillData", MODE_PRIVATE);

        RadioButton equalBreakdownButton = findViewById(R.id.equalBreakdownButton);
        equalBreakdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEqualBreakdown();
            }
        });

        RadioButton CustomBreakdownButton = findViewById(R.id.CustomBreakdownButton);
        CustomBreakdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,MainActivity4.class));
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeResult();
            }
        });

        Button buttonShareEmail = findViewById(R.id.buttonShareEmail);
        buttonShareEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareViaEmail();
            }
        });
    }

    private void calculateEqualBreakdown(){
        try{
            double billAmount = Double.parseDouble(billAmountInput.getText().toString());
            int numStudents = Integer.parseInt(numStudentsInput.getText().toString());
            tvResults = findViewById(R.id.tvResults);

            if (numStudents<=0){
                showToast("Number of students must be greater than 0.");
            }

            double amountPerStudent = billAmount/numStudents;
            String resultsText = "Total Bill: $" + billAmount + "\n"
                    + "Individual Share: $" + amountPerStudent;
            tvResults.setText(resultsText);
        }catch(NumberFormatException e){
            showToast("Invalid input.Please enter valid numbers");
        }
    }

    private void storeResult(){
        sqliteAdapter.openToWrite();
        double billAmount=Double.parseDouble(billAmountInput.getText().toString());
        int numStudents = Integer.parseInt(numStudentsInput.getText().toString());

        double amountPerStudent = billAmount/numStudents;
        sqliteAdapter.insert(billAmount,amountPerStudent);
        sqliteAdapter.close();
    }

    private void shareViaEmail(){

        String subject = "Bill Breakdown Results";
        String body = tvResults.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            // No email client found
            Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}