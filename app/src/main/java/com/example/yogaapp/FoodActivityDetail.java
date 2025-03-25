package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class FoodActivityDetail extends AppCompatActivity {

    TextView textView;
    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mAdview = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        textView = findViewById(R.id.txt);
        String dstory = getIntent().getStringExtra("story");
        textView.setText(dstory);
    }

    public void goback(View view) {
        Intent intent = new Intent(FoodActivityDetail.this, FoodActivity.class);
        startActivity(intent);
        finish();
    }
}