package com.example.yogaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class sleepsetting extends AppCompatActivity {

    private TimePicker timePicker;
    private Button btnSetSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepsetting);

        timePicker = findViewById(R.id.timePicker);
        btnSetSleep = findViewById(R.id.btnSaveSleepTime);

        btnSetSleep.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            SleepReminderHelper.setSleepReminder(this, hour, minute);
            Toast.makeText(this, "Sleep reminder has been set!", Toast.LENGTH_SHORT).show();
        });
    }

    public void back(View view) {
        Intent intent = new Intent(sleepsetting.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
