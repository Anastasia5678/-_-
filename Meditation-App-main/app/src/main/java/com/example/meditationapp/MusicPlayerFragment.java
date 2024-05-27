package com.example.meditationapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;

import java.util.Locale;

import soup.neumorphism.NeumorphButton;

import static android.content.ContentValues.TAG;


public class MusicPlayerFragment extends Fragment {

    public MusicPlayerFragment() {
        // Required empty public constructor
    }
//MEDIAPLAYER VARIABLES
    public MediaPlayer mediaPlayer;
    NeumorphButton meditateBtn;
    boolean btnclick = true;
    WaveVisualizer visualizer;

//COUNTDDOWN TIMER VARIABLES
    EditText inputForTimeTxt;
    Button setTimebtn;
    CountDownTimer countDownTimer;
    long timeleft;
    boolean istimerSet = false;
    String userTime;
    boolean timerRunning = false;

//PROGRESS VARIABLES
    int counter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int TimeInMillis;



// Changes in UI

    ConstraintLayout background;

    // Get music from home fragment

    int musicfromHomeFragment;
    boolean check;









    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timerRunning)
        {countDownTimer.cancel();}
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (timerRunning)
        {countDownTimer.cancel();}
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        meditateBtn.setShapeType(0);
        btnclick = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_music_player, container, false);

        meditateBtn = view.findViewById(R.id.MeditateButton);
        visualizer = view.findViewById(R.id.bar);
        background = view.findViewById(R.id.meditate_background);

        inputForTimeTxt = view.findViewById(R.id.minutes_input);
        setTimebtn = view.findViewById(R.id.set_button);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }


        //Change layout colors
        int backgroundColor = getArguments().getInt("backgroundColor");
        int lightColor = getArguments().getInt("lightShadow");
        int darkColor = getArguments().getInt("darkShadow");
        int visualizerColor = getArguments().getInt("visualizer");
        musicfromHomeFragment = getArguments().getInt("music");

        background.setBackgroundColor(backgroundColor);
        meditateBtn.setShadowColorLight(lightColor);
        meditateBtn.setShadowColorDark(darkColor);
        visualizer.setColor(visualizerColor);

        check = getArguments().getBoolean("check");


        if(check)
        {
            inputForTimeTxt.setVisibility(View.GONE);
            setTimebtn.setVisibility(View.GONE);
        }







        preferences = this.getActivity().getSharedPreferences("com.example.apicall.progressUser", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
        counter = preferences.getInt("progress", 0); ;


        setTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTime = inputForTimeTxt.getText().toString();
                if (userTime.length()== 0)
                {
                    Toast.makeText(getContext(),"Field cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                timeleft = Long.parseLong(userTime)*60000;

                if (timeleft== 0)
                {
                    Toast.makeText(getContext(),"Timer cannot be 0",Toast.LENGTH_SHORT).show();
                    return;
                }

                istimerSet = true;

            }
        });


        meditateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                if (btnclick) {
                    meditateBtn.setShapeType(2);
                    if (istimerSet) {
                        startTimer();

                    }
                    else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                Log.i(TAG, "Thread is Running : " + Thread.currentThread().getId());
                                if (mediaPlayer == null) {

                                    mediaPlayer = MediaPlayer.create(getContext(), musicfromHomeFragment);
                                    int audiosessionID = mediaPlayer.getAudioSessionId();

                                    if (audiosessionID != -1) {
                                        visualizer.setAudioSessionId(audiosessionID);
                                    }


                                }

                                if(check)
                                {
                                    mediaPlayer.setLooping(false);
                                    timeleft = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            startTimer();
                                        }
                                    });

                                }
                                else
                                {
                                mediaPlayer.setLooping(true);}

                                mediaPlayer.start();
                                Log.i(TAG, "Above increase counter function");
                                increasecounter();
                                Log.i(TAG, "Below increase counter function");
                                if(check)
                                {
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            meditateBtn.setShapeType(0);

                                        }
                                    });

                                }

                            }
                        }).start();

                    }

                }
                else {
                    meditateBtn.setShapeType(0);
                    if (istimerSet){
                        pauseTimer();
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                        }

                    }
                    else{
                        if (mediaPlayer != null) {
                           if(check)
                            {
                                pauseTimer();
                            }
                            mediaPlayer.pause();
                        }
                    }


                }
                btnclick = !btnclick;
            }
        });






        return view;
    }

    private void increasecounter() {

        if(mediaPlayer.getCurrentPosition() >= 1000)
            Log.i(TAG, "counter is working fine");
        counter = preferences.getInt("progress", 0);
        counter = 5+ counter ;
        editor.putInt("progress",counter);
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putInt("progress",counter);
        getParentFragmentManager().setFragmentResult("ProgressOfUser",bundle);

    }


    // Methods

    private void pauseTimer() {
        countDownTimer.cancel();

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeleft ,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                timerRunning = true;
                updateMeditateButtonText();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i(TAG, "Thread is Running : " + Thread.currentThread().getId());
                        if (mediaPlayer == null) {

                            mediaPlayer = MediaPlayer.create(getContext(), musicfromHomeFragment);
                            int audiosessionID = mediaPlayer.getAudioSessionId();

                            if (audiosessionID != -1) {
                                visualizer.setAudioSessionId(audiosessionID);
                            }


                        }
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();


                    }
                }).start();
            }

            @Override
            public void onFinish() {
                meditateBtn.setShapeType(0);
                mediaPlayer.release();
                meditateBtn.setText("Meditate");
                istimerSet=false;
                timerRunning= false;
                inputForTimeTxt.setText("0");


                counter = preferences.getInt("progress", 0);
                counter =  10+counter ;
                editor.putInt("progress",counter);
                editor.apply();
                Toast.makeText(getContext() , "The progress is :"+counter,Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("progress",counter);
                getParentFragmentManager().setFragmentResult("ProgressOfUser",bundle);







            }
        }.start();

    }



    private void updateMeditateButtonText() {
        int hours = (int)(timeleft /1000)/3600;
        int minutes = (int)((timeleft /1000)%3600)/60;
        int seconds = (int)(timeleft /1000)%60;

        String timeformatted;
        if (hours>0)
        {

            timeformatted = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
        }
        else {
            timeformatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        }
        meditateBtn.setText(timeformatted);




    }

}