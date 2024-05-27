package com.example.meditationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class Achievements extends AppCompatActivity {


    LottieAnimationView animationView;
    int award;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        animationView = findViewById(R.id.animationView);

        Intent intent = getIntent();

        award = intent.getIntExtra("award",R.raw.award1);

        animationView.setAnimation(award);







    }
}