package com.example.yogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.startyoga);
        button2 = findViewById(R.id.startyoga2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "✅ Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "⚠️ Unable to send notifications without permission!", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iotexpert1.blogspot.com/2020/10/weight-loss-privacy-ploicy-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_more){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Monsanto+Bogays&h1=en_US&g1=US"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_rate){
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

            } catch (Exception ex){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/apps/details?id=" + getPackageName())));
            }
            return true;
        }
        if (id == R.id.id_share){
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String sharebody = "This is the best for yoga\n By this app you streach your body\n This is the free download Now\n" + "https://play.google.com/apps/details?id=com.example.yogaapp&hl=en";
            String sharehub = "Yoga App";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sharehub);
            myIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(myIntent.createChooser(myIntent, "share using"));

            return true;
        }
        if (id == R.id.id_plan){
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.id_nutrition) {
            Intent intent = new Intent(MainActivity.this, NutritionActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.id_sleep) {
            Intent intent = new Intent(MainActivity.this, sleepsetting.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.id_smart_plan){
            Intent intent = new Intent(MainActivity.this, SmartPlanActivity.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

    public void beforeage18(View view) {
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);

    }

    public void afterage18(View view) {
        Intent intent = new Intent(MainActivity.this,SecondActivity2.class);
        startActivity(intent);

    }

    public void food(View view) {
        Intent intent = new Intent(MainActivity.this,FoodActivity.class);
        startActivity(intent);

    }
}