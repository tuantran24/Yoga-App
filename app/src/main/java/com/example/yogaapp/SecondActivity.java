package com.example.yogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SecondActivity extends AppCompatActivity {

    int[] newArray;
    private AdView mAdview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{

              R.id.bow_pose, R.id.bridge_pose, R.id.chair_pose, R.id.child_pose, R.id.cobbler_pose, R.id.cow_pose, R.id.playji_pose, R.id.pauseji_pose, R.id.crunches_pose,
                R.id.situp_pose, R.id.rotation_pose, R.id.twist_pose, R.id.windmill_pose, R.id.legup_pose,

        };

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
            Intent intent = new Intent(SecondActivity.this, PlanActivity.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

    public void Imagebuttonclicked(View view) {

        for(int i = 0; i < newArray.length; i++) {

            if (view.getId() == newArray[i]) {

                int value = i + 1;
                Log.i("FIRST", String.valueOf(value));
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);


            }

        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
}