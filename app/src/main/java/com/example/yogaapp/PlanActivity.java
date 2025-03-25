package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private EditText etHeight, etWeight;
    private Button btnCalculate;
    private TextView tvResult;
    private LineChart lineChart;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "BMI_HISTORY";
    private static final int MAX_HISTORY = 10;
    private Button btnClearHistory;
    private TextView tvAdvice;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        tvAdvice = findViewById(R.id.tv_advice);
        tvNoData = findViewById(R.id.tv_no_data);

        btnClearHistory = findViewById(R.id.btn_clear_history);
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                if (lineChart.getData() != null) {
                    lineChart.getData().clearValues();
                    lineChart.clear();
                    lineChart.invalidate();
                }
                tvAdvice.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.GONE);
            }
        });



        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        btnCalculate = findViewById(R.id.btn_calculate);
        tvResult = findViewById(R.id.tv_result);
        lineChart = findViewById(R.id.line_chart);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        loadChartData();
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float height = Float.parseFloat(heightStr) / 100;
            float weight = Float.parseFloat(weightStr);
            float bmi = weight / (height * height);

            String category;
            if (bmi < 18.5) category = "Underweight";
            else if (bmi < 24.9) category = "Normal weight";
            else if (bmi < 29.9) category = "Overweight";
            else category = "Obese";

            tvResult.setText(String.format("BMI: %.1f\nCategory: %s", bmi, category));

            saveBMI(bmi);
            loadChartData();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input! Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBMI(float bmi) {
        List<String> bmiList = new ArrayList<>(Arrays.asList(getBMIHistory()));
        if (bmiList.size() >= MAX_HISTORY) bmiList.remove(0);
        bmiList.add(String.valueOf(bmi));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bmi_data", String.join(",", bmiList));
        editor.apply();
    }

    private String[] getBMIHistory() {
        String data = sharedPreferences.getString("bmi_data", "");
        return data.isEmpty() ? new String[0] : data.split(",");
    }

    private void loadChartData() {
        String[] bmiHistory = getBMIHistory();
        List<Entry> entries = new ArrayList<>();

        if (bmiHistory.length == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);
            return;
        } else {
            tvNoData.setVisibility(View.GONE);
            lineChart.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < bmiHistory.length; i++) {
            entries.add(new Entry(i + 1, Float.parseFloat(bmiHistory[i])));
        }

        if (entries.size() > 1) {
            // Lấy 2 giá trị BMI gần nhất
            float lastBMI = entries.get(entries.size() - 1).getY();
            float prevBMI = entries.get(entries.size() - 2).getY();

            float change = lastBMI - prevBMI;

            if (change > 1.5) {
                tvAdvice.setText("⚠️ You are gaining weight rapidly! Keep exercising diligently. 🏃‍♂️");
                tvAdvice.setTextColor(Color.RED);
                tvAdvice.setVisibility(View.VISIBLE);
            } else if (change < -1.5) {
                tvAdvice.setText("🍽️ You are losing weight rapidly! Make sure to eat well and maintain your health. 💪");
                tvAdvice.setTextColor(Color.BLUE);
                tvAdvice.setVisibility(View.VISIBLE);
            } else {
                tvAdvice.setVisibility(View.GONE);
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "BMI History");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextSize(12f);
        dataSet.setCircleColor(Color.RED);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();
    }


    public void advice(View view) {
        Intent intent;
        String adviceText = tvAdvice.getText().toString();
        if (adviceText.contains("⚠️ You are gaining weight rapidly! Keep exercising diligently. 🏃‍♂️")) {
            intent = new Intent(PlanActivity.this, MainActivity.class);
        } else if (adviceText.contains("🍽️ You are losing weight rapidly! Make sure to eat well and maintain your health. 💪")) {
            intent = new Intent(PlanActivity.this, FoodActivity.class);
        } else {
            return;
        }
        intent.putExtra("advice", adviceText);
        startActivity(intent);
    }
}
