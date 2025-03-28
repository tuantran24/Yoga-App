package com.example.yogaapp;

<<<<<<< HEAD
import android.annotation.SuppressLint;
=======
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.CheckBox;
=======
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
=======
import java.util.ArrayList;
import java.util.Collections;
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
import java.util.List;

public class SmartPlanActivity extends AppCompatActivity {

    private Spinner sessionSpinner;
    private Button btnCreatePlan;
    private LinearLayout planResultLayout;

<<<<<<< HEAD
=======
    private final String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_plan);

        sessionSpinner = findViewById(R.id.sessionSpinner);
        btnCreatePlan = findViewById(R.id.btnCreatePlan);
        planResultLayout = findViewById(R.id.planResultLayout);

<<<<<<< HEAD
        // Tạo spinner lựa chọn số buổi
=======
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
        List<String> options = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            options.add(i + " sessions/week");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        sessionSpinner.setAdapter(adapter);

        btnCreatePlan.setOnClickListener(v -> generatePlan());
    }

<<<<<<< HEAD
    @SuppressLint("SimpleDateFormat")
=======
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
    private void generatePlan() {
        int selectedPosition = sessionSpinner.getSelectedItemPosition();
        int numSessions = selectedPosition + 1;

<<<<<<< HEAD
        planResultLayout.removeAllViews(); // Clear cũ

        List<Calendar> sessionDates = getSmartDistributedDates(numSessions);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM");

        for (int i = 0; i < numSessions; i++) {
            Calendar sessionDate = sessionDates.get(i);
            String formattedDate = sdf.format(sessionDate.getTime());
            int workoutIndex = i + 1;

            // Layout hàng ngang chứa Text + CheckBox
            LinearLayout sessionRow = new LinearLayout(this);
            sessionRow.setOrientation(LinearLayout.HORIZONTAL);
            sessionRow.setPadding(16, 10, 16, 10);

            // TextView cho ngày tập
            TextView tv = new TextView(this);
            tv.setText("💪 " + formattedDate + ": Session " + workoutIndex);
            tv.setTextSize(16f);
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            // CheckBox đánh dấu đã tập
            CheckBox checkBox = new CheckBox(this);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Sự kiện click để mở bài tập
=======
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

>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
            int finalWorkoutIndex = workoutIndex;
            tv.setOnClickListener(v -> {
                Intent intent = new Intent(SmartPlanActivity.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(finalWorkoutIndex));
                startActivity(intent);
            });

<<<<<<< HEAD
            // Add cả 2 vào dòng
            sessionRow.addView(tv);
            sessionRow.addView(checkBox);
            planResultLayout.addView(sessionRow);
        }
    }

    // Phân bố ngày tập theo preset xen kẽ (bắt đầu từ Thứ 2)
    private List<Calendar> getSmartDistributedDates(int count) {
        List<Calendar> result = new ArrayList<>();
        Calendar start = Calendar.getInstance();

        // Bắt đầu từ Thứ 2 tuần hiện tại
        while (start.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        int[][] presets = {
                {0},                   // 1 buổi -> Thứ 2
                {0, 2},                // 2 buổi -> Thứ 2, Thứ 4
                {0, 2, 4},             // 3 buổi -> 2 4 6
                {0, 1, 3, 5},          // 4 buổi
                {0, 1, 3, 4, 6},       // 5 buổi
                {0, 1, 2, 3, 5, 6},    // 6 buổi
                {0, 1, 2, 3, 4, 5, 6}  // 7 buổi
        };

        int[] offsets = presets[Math.min(count - 1, 6)];
        for (int offset : offsets) {
            Calendar c = (Calendar) start.clone();
            c.add(Calendar.DAY_OF_MONTH, offset);
            result.add(c);
        }

        return result;
=======
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
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
    }

    public void smartplan(View view) {
        Intent intent = new Intent(SmartPlanActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> bc2dd54fdcfb4d1024f2a86119632187f79c2638
