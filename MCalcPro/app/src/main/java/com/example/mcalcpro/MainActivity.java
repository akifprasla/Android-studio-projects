package com.example.mcalcpro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener {

    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tts = new TextToSpeech(this, this);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onInit(int status) {
        this.tts.setLanguage(Locale.US);
    }

    public void btnclicked(View v) {
        try {
            MPro mp = new MPro();

            String cashPrice = ((EditText) findViewById(R.id.cashprice)).getText().toString();
            mp.setPrinciple(cashPrice);

            String amor = ((EditText) findViewById(R.id.amor)).getText().toString();
            mp.setAmortization(amor);

            String interest = ((EditText) findViewById(R.id.interest)).getText().toString();
            mp.setInterest(interest);

            String s = "Monthly Payment = " + mp.computePayment("%,.2f");
            s += "\n \n";
            s += "By making the payments monthly for ";
            s += "\n \n";

            s += String.format("%8d", 0) + mp.outstandingAfter(0, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 1) + mp.outstandingAfter(1, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 2) + mp.outstandingAfter(2, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 3) + mp.outstandingAfter(3, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 4) + mp.outstandingAfter(4, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 5) + mp.outstandingAfter(5, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 10) + mp.outstandingAfter(10, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 15) + mp.outstandingAfter(15, "%,16.0f");
            s += "\n \n";

            s += String.format("%8d", 20) + mp.outstandingAfter(20, "%,16.0f");
            s += "\n \n";

            ((TextView) findViewById(R.id.output)).setText(s);
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();
        }
    }

    @Override
    protected void onPause()
    {
        if(tts!=null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax*ax+ay*ay+az*az);

        if(a>20)
        {
            ((EditText)findViewById(R.id.cashprice)).setText(" ");
            ((EditText)findViewById(R.id.amor)).setText(" ");
            ((EditText)findViewById(R.id.interest)).setText(" ");
            ((TextView)findViewById(R.id.output)).setText(" ");
            tts.speak("",TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
