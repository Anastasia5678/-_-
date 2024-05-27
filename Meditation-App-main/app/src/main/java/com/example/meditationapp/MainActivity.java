package com.example.meditationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
MeowBottomNavigation bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bnv  = findViewById(R.id.bottomnav);
        replace(new Home());
        bnv.add(new MeowBottomNavigation.Model(1 ,R.drawable.home));
        bnv.add(new MeowBottomNavigation.Model(2 ,R.drawable.music_frequency));
        bnv.add(new MeowBottomNavigation.Model(3 ,R.drawable.quotes));
        bnv.add(new MeowBottomNavigation.Model(4 ,R.drawable.profile));

        bnv.show(1,true);
        bnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId())
                {
                    case 1:
                        replace(new Home());
                        bnv.setBackgroundBottomColor(Color.parseColor("#ffffff"));
                        break;

                    case 2:
                        replace(new Frequency());
                        bnv.setBackgroundBottomColor(Color.parseColor("#ffffff"));
                        break;
                    case 3:
                        replace(new Quotes());
                        bnv.setBackgroundBottomColor(Color.parseColor("#FF3700B3"));
                        break;
                    case 4:
                        replace(new Profile());
                        bnv.setBackgroundBottomColor(Color.parseColor("#ffffff"));
                        break;


                }




                return null;
            }
        });



    }

    private void replace(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

}