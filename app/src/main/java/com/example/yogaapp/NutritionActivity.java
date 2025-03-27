package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class NutritionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvTotalCalories;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        recyclerView = findViewById(R.id.recyclerView);
        tvTotalCalories = findViewById(R.id.calories);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodList = FoodData.getFoodList();

        foodAdapter = new FoodAdapter(foodList, totalCalories ->
                tvTotalCalories.setText(String.valueOf(totalCalories))
        );

        recyclerView.setAdapter(foodAdapter);

        // Cài đặt chức năng tìm kiếm
        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foodAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void nutriback(View view) {
        Intent intent = new Intent(NutritionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
