package com.example.yogaapp;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {
    private Button btnCheckIn;
    private TextView txtSelectedDate, txtWorkoutDays;
    private SharedPreferences sharedPreferences;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Ánh xạ view
        btnCheckIn = findViewById(R.id.btnCheckIn);
        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtWorkoutDays = findViewById(R.id.txtWorkoutDays);

        sharedPreferences = getSharedPreferences("GymAttendance", MODE_PRIVATE);
        calendar = Calendar.getInstance();

        // Cập nhật số ngày tập từ SharedPreferences
        updateWorkoutCount();

        // Sự kiện khi nhấn chọn ngày điểm danh
        btnCheckIn.setOnClickListener(view -> showDatePicker());
    }

    // Hiển thị DatePickerDialog để chọn ngày
    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());

                    txtSelectedDate.setText("Ngày đã chọn: " + selectedDate);
                    markAttendance(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show(); // Hiển thị lịch DatePicker
    }

    // Lưu ngày điểm danh vào SharedPreferences
    private void markAttendance(String date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean(date, false)) {
            editor.putBoolean(date, true);
            editor.apply();
            Toast.makeText(this, "Đã điểm danh cho ngày " + date, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bạn đã điểm danh ngày này rồi!", Toast.LENGTH_SHORT).show();
        }
        updateWorkoutCount();
    }

    // Cập nhật số ngày đã tập
    private void updateWorkoutCount() {
        int count = 0;
        for (String key : sharedPreferences.getAll().keySet()) {
            if (sharedPreferences.getBoolean(key, false)) {
                count++;
            }
        }
        txtWorkoutDays.setText("Bạn đã tập luyện: " + count + " ngày");
    }
}
