package com.example.yogaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartPlanActivity extends AppCompatActivity {

    private Spinner sessionSpinner;
    private Button btnCreatePlan;
    private LinearLayout planResultLayout;

    private final String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_plan);

        sessionSpinner = findViewById(R.id.sessionSpinner);
        btnCreatePlan = findViewById(R.id.btnCreatePlan);
        planResultLayout = findViewById(R.id.planResultLayout);

        List<String> options = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            options.add(i + " sessions/week");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        sessionSpinner.setAdapter(adapter);

        btnCreatePlan.setOnClickListener(v -> generatePlan());
    }

    private void generatePlan() {
        int selectedPosition = sessionSpinner.getSelectedItemPosition();
        int numSessions = selectedPosition + 1;

        planResultLayout.removeAllViews();

        List<Integer> selectedDays = getDistributedDays(numSessions);

        for (int i = 0; i < numSessions; i++) {
            int dayIndex = selectedDays.get(i);
            int workoutIndex = i + 1;

            TextView tv = new TextView(this);
            tv.setText("💪 " + weekdays[dayIndex] + ": Workout " + workoutIndex);
            tv.setTextSize(16f);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(16, 12, 16, 12);

            int finalWorkoutIndex = workoutIndex;
            tv.setOnClickListener(v -> {
                Intent intent = new Intent(SmartPlanActivity.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(finalWorkoutIndex));
                startActivity(intent);
            });

            planResultLayout.addView(tv);
        }
    }

    private List<Integer> getDistributedDays(int count) {
        List<Integer> days = new ArrayList<>();
        List<Integer> weekDays = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            weekDays.add(i);
        }

        double step = 7.0 / count;
        double currentIndex = 0;

        for (int i = 0; i < count; i++) {
            days.add(weekDays.get((int) Math.round(currentIndex) % 7));
            currentIndex += step;
        }

        Collections.sort(days);
        return days;
    }

    public void smartplan(View view) {
        Intent intent = new Intent(SmartPlanActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}