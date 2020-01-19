package com.example.bmimodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View v){

        EditText weightView = (EditText) findViewById(R.id.weightBox);
        String weight = weightView.getText().toString();
        EditText heightview = (EditText) findViewById(R.id.heightBox);
        String height = heightview.getText().toString();

        BMIModel myModel = new BMIModel(weight, height);
        String myBMI = myModel.getBMI();

        ((TextView) findViewById(R.id.answer)).setText(myBMI);
    }
}
