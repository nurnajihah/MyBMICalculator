package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCal;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOw;

    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
    final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.CalculateButton);
        btnReset = findViewById(R.id.ResetButton);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOw = findViewById(R.id.textViewOverweight);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText("");
                etHeight.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();

                tvDate.setText("Last Calculated date: ");
                tvBMI.setText("Last Calculated BMI: ");
                tvOw.setText("");
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float floatweight = Float.parseFloat(etWeight.getText().toString());
                float floatheight = Float.parseFloat(etHeight.getText().toString());
                float bmiValue = floatweight / (floatheight * floatheight);

                tvDate.setText("Last calculated date: " + datetime);
                tvBMI.setText("Last calculated BMI: " + bmiValue);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putString("date", datetime);

                prefEdit.putFloat("bmi", bmiValue);

                if (bmiValue < 18.5) {
                    tvOw.setText("You are overweight");
                } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
                    tvOw.setText("Your BMI is normal");
                }
                else if (bmiValue >= 25 && bmiValue <= 29.9)    {
                    tvOw.setText("You are overweight");
                }
                else {
                    tvOw.setText("You are obese");
                }

                etWeight.setText("");
                etHeight.setText("");

            }



        });


    }
    @Override
    protected void onPause() {
        super.onPause();

        float floatweight = Float.parseFloat(etWeight.getText().toString());
        float floatheight = Float.parseFloat(etHeight.getText().toString());
        float bmi = Float.parseFloat(tvBMI.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putFloat("weight", floatweight);
        prefEdit.putFloat("height", floatheight);
        prefEdit.putString("Date", "");
        prefEdit.putFloat("BMI", bmi);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String date = prefs.getString("date", "");
        Float bmi = prefs.getFloat("bmi", 0.0f);
        Float txtweight = prefs.getFloat("weight", 0.0f);
        Float txtheight = prefs.getFloat("height", 0.0f);

        etWeight.setText("");
        etHeight.setText("");

        tvDate.setText("Last Calculated Date: " + date);
        tvBMI.setText("Last Calculated BMI: " + bmi.toString());
    }
}


