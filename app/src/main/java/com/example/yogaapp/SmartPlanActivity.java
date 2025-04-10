package com.example.yogaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SmartPlanActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btnSaveDate;
    private CheckBox checkAll;
    private LinearLayout planResultLayout;
    private SharedPreferences sharedPreferences;
    private List<String> selectedDates;
    private Set<String> checkedDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_plan);



        // Khởi tạo UI
        datePicker = findViewById(R.id.datePicker);
        btnSaveDate = findViewById(R.id.btnSaveDate);
        checkAll = findViewById(R.id.checkAll);
        planResultLayout = findViewById(R.id.planResultLayout);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        selectedDates = new ArrayList<>(sharedPreferences.getStringSet("selected_dates", new HashSet<>())) ;
        checkedDates = new HashSet<>(sharedPreferences.getStringSet("checked_dates", new HashSet<>())) ;

        // Load dữ liệu khi mở app
        displaySelectedDates();

        // Nút Save Date
        btnSaveDate.setOnClickListener(v -> saveSelectedDate());

        // Checkbox Check All
        checkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedDates.addAll(selectedDates);
            } else {
                checkedDates.clear();
            }
            saveDatesToPreferences();
            displaySelectedDates();
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void saveSelectedDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

        if (!selectedDates.contains(selectedDate)) {
            selectedDates.add(selectedDate);
            saveDatesToPreferences();
            displaySelectedDates();
        }
    }

    private void saveDatesToPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("selected_dates", new HashSet<>(selectedDates));
        editor.putStringSet("checked_dates", checkedDates);
        editor.apply();
    }

    private void displaySelectedDates() {
        planResultLayout.removeAllViews();

        for (String date : selectedDates) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(16, 10, 16, 10);

            // CheckBox để tick
            CheckBox checkBox = new CheckBox(this);
            checkBox.setChecked(checkedDates.contains(date));
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    checkedDates.add(date);
                } else {
                    checkedDates.remove(date);
                }
                saveDatesToPreferences();
            });

            // Text hiển thị ngày
            TextView tv = new TextView(this);
            tv.setText("📅 " + date);
            tv.setTextSize(16f);
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            // Nút X để xóa
            Button btnDelete = new Button(this);
            btnDelete.setText("❌");
            btnDelete.setOnClickListener(v -> {
                selectedDates.remove(date);
                checkedDates.remove(date); // xóa luôn trong set đã tick nếu có
                saveDatesToPreferences();
                displaySelectedDates();
            });

            row.addView(checkBox); // thêm CheckBox trước
            row.addView(tv);       // sau đó là ngày
            row.addView(btnDelete); // cuối cùng là nút xóa
            planResultLayout.addView(row);
        }
    }
}
