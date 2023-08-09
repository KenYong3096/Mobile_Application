package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    private EditText billAmountInput1;
    private EditText numStudentsInput1;
    private LinearLayout layoutPercentages;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        billAmountInput1=findViewById(R.id.billAmountInput1);
        numStudentsInput1=findViewById(R.id.numStudentsInput1);
        layoutPercentages=findViewById(R.id.layoutPercentages);
        textViewResult=findViewById(R.id.tvResults);

        Button buttonPeople = findViewById(R.id.buttonPeople);
        buttonPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numPeople = Integer.parseInt(numStudentsInput1.getText().toString());
                numStudentsInput1.setText(String.valueOf(numPeople));
                addPercentageEditTextFields(numPeople);
            }
        });

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCustomBreakdown();
            }
        });
    }


    private void calculateCustomBreakdown(){
        double totalBill = Double.parseDouble(billAmountInput1.getText().toString());
        int numPeople = Integer.parseInt(numStudentsInput1.getText().toString());

        double[]percentages=new double[numPeople];

        for (int i = 0; i < numPeople; i++) {
            EditText editText = (EditText) layoutPercentages.getChildAt(i);
            String percentageStr = editText.getText().toString();
            if (!TextUtils.isEmpty(percentageStr)) {
                percentages[i] = Double.parseDouble(percentageStr);
            } else {
                percentages[i] = 0.0; // Default to 0 if no input
            }
        }

        double[] amounts = new double[numPeople];
        for(int i=0;i<numPeople;i++){
            amounts[i]=(percentages[i]/100)*totalBill;
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int i=0;i<numPeople;i++){
            resultBuilder.append("Person ")
                    .append(i + 1)
                    .append(": RM ")
                    .append(amounts[i])
                    .append("\n");
        }

        textViewResult.setText(resultBuilder.toString());
    }

    private void addPercentageEditTextFields(int numPeople) {
        layoutPercentages.removeAllViews();
        for (int i = 0; i < numPeople ; i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            editText.setHint("Percentage for Person " + (i + 1));
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layoutPercentages.addView(editText);
        }
    }
}