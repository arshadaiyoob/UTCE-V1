package com.izar.dev.utce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;

public class SplashActivity extends AppCompatActivity {
    DatabaseReference myRef;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         intent = new Intent(this, Countdown.class);
        startActivity(intent);
        finish();
    }




}