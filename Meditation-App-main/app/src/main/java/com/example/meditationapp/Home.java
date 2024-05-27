package com.example.meditationapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphImageView;

import static android.content.ContentValues.TAG;


public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }


    NeumorphCardView moriningmeditation, infintechanting , nightmeditation;

    MediaPlayer mediaPlayer;

    boolean mediaplayerRunning = true;

    ProgressBar progressBar;
    TextView progresstext;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int progress;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
         moriningmeditation = view.findViewById(R.id.morningMeditation);
        infintechanting = view.findViewById(R.id.infinitechanting);
        nightmeditation = view.findViewById(R.id.nightmeditation);
        progressBar = view. findViewById(R.id.progressBar);
        progresstext = view.findViewById(R.id.prog);
        Log.i(TAG, "onCreateView: Called");



        preferences = getActivity().getSharedPreferences("Progress", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
        getParentFragmentManager().setFragmentResultListener("ProgressOfUser", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {

                editor.putInt("progress", result.getInt("progress"));
                editor.apply();
                progress =result.getInt("progress");
            }
        });



        progress = preferences.getInt("progress",0);
        progressBar.setProgress(progress);

        progressBar.invalidate();




        progresstext.setText("Here's Your Progress..: "+progress);

        progresstext.invalidate();







        moriningmeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("backgroundColor", Color.parseColor("#03DAC5"));
                bundle.putInt("lightShadow", Color.parseColor("#1EF8E2"));
                bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                bundle.putInt("visualizer", Color.parseColor("#03DAC5"));
                bundle.putInt("music",R.raw.light_m);
                bundle.putBoolean("check",true);

                musicPlayerFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout ,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                fragmentTransaction.commit();


            }
        });


        infintechanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("backgroundColor", Color.parseColor("#FF8A00"));
                bundle.putInt("lightShadow", Color.parseColor("#FFA63D"));
                bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                bundle.putInt("visualizer", Color.parseColor("#FF8A00"));
                bundle.putInt("music",R.raw.new_om);
                bundle.putBoolean("check",false);
                musicPlayerFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                fragmentTransaction.commit();
            }
        });


        nightmeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("backgroundColor", Color.parseColor("#0F0D76"));
                bundle.putInt("lightShadow", Color.parseColor("#22208B"));
                bundle.putInt("darkShadow", Color.parseColor("#33000000"));
                bundle.putInt("visualizer", Color.parseColor("#0F0D76"));
                bundle.putInt("music",R.raw.aff_mm);
                bundle.putBoolean("check",true);
                musicPlayerFragment.setArguments(bundle);


                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout,R.anim.fadein,R.anim.fadeout).addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, musicPlayerFragment);
                fragmentTransaction.commit();

            }
        });







        return view;
    }


}