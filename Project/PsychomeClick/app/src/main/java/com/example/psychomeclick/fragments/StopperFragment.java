package com.example.psychomeclick.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.psychomeclick.R;


import java.util.Locale;


public class StopperFragment extends Fragment {

    private TextView stopperTv, infoTv, timerTv;
    private Button startBtn, finishTestBtn, pauseBtn, nextPerekBtn;
    private Spinner spinner;
    private CheckBox checkBox;
    private CountDownTimer timer;
    private boolean isEssayIncluded = false;
    private int numOfSections = 0;
    private int currentSection = 0;

    private LinearLayout colorTv;
    private TextToSpeech textToSpeech;
    boolean isEssay=false;
    private static final long essayDuration=30 * 60 * 1000;
    private static final long breakDuration=5 * 60 * 1000;
    private static final long sectionDuration=20 * 60 * 1000;


    public StopperFragment() {
        // Required empty public constructor
    }
    boolean paused=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stopper, container, false);
        initViews(v);
        setListeners();
        return v;
    }

    private void initViews(View view) {
        stopperTv = view.findViewById(R.id.stopperTv);
        infoTv = view.findViewById(R.id.infoTv);
        timerTv = view.findViewById(R.id.timerTv);
        startBtn = view.findViewById(R.id.startBtn);
        finishTestBtn = view.findViewById(R.id.finishTestBtn);
        pauseBtn = view.findViewById(R.id.pauseBtn);
        nextPerekBtn = view.findViewById(R.id.nextPerekBtn);
        spinner = view.findViewById(R.id.spinner);
        checkBox = view.findViewById(R.id.checkBox);
        colorTv=view.findViewById(R.id.colorTv);
        colorTv.setBackgroundColor(Color.GRAY);
        String[] spinnerArray={"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(adapter);

        textToSpeech = new TextToSpeech(view.getContext(), i -> {
            if (i != TextToSpeech.ERROR) textToSpeech.setLanguage(Locale.US);
        });
    }

    private void setListeners() {
        startBtn.setOnClickListener(v -> startTest());
        finishTestBtn.setOnClickListener(v -> finishTest());
        pauseBtn.setOnClickListener(v -> pauseTest());
        nextPerekBtn.setOnClickListener(v -> nextSection());
    }

    private void startTest() {
        isEssayIncluded = checkBox.isChecked();
        numOfSections = spinner.getSelectedItemPosition();

        if (isEssayIncluded) {
            startEssayTimer();
        } else {
            if(numOfSections==0){
                return;
            }
            nextSection();
        }

        startBtn.setVisibility(View.GONE);
        finishTestBtn.setVisibility(View.VISIBLE);
        pauseBtn.setVisibility(View.VISIBLE);
        nextPerekBtn.setVisibility(View.VISIBLE);
    }

    private void startEssayTimer() {
        infoTv.setText("Essay");
        playSound("Essay");
        colorTv.setBackgroundColor(Color.GREEN);
        isEssay=true;
        timer = new CountDownTimer(essayDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText(formatTime(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                startBreakTimer();
            }
        }.start();
    }

    private void startBreakTimer() {
        if(numOfSections==0){
            finishTest();
            return;
        }
        playSound("five minute break!");
        infoTv.setText("Break");
        timerTv.setText("05:00");
        colorTv.setBackgroundColor(Color.BLUE);
        isEssay=false;
        timer = new CountDownTimer(breakDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText(formatTime(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                nextSection();
            }
        }.start();
    }

    private void startNextSection() {
        currentSection++;
        if (currentSection > numOfSections){
            finishTest();
            return;
        }
        // Start timer for the next section
        colorTv.setBackgroundColor(Color.GREEN);
        playSound("next section!");
        isEssay=false;

        infoTv.setText("Section " +currentSection + " out of " + numOfSections);
            timerTv.setText("00:20");
            timer = new CountDownTimer(sectionDuration, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTv.setText(formatTime(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    nextSection();
                }
            }.start();

    }

    private void pauseTest() {
        Long time = parseTime(timerTv.getText().toString())*1000; // Store remaining time before pausing
        timer.cancel();
        if (paused) {
            if(isEssay){
                timer= new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerTv.setText(formatTime(millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        startBreakTimer();
                    }
                }.start();
            }
            else{
                    timer= new CountDownTimer(time, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timerTv.setText(formatTime(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            nextSection();
                        }
                    }.start();
            }

            timer.start();
            pauseBtn.setText("Pause");
            colorTv.setBackgroundColor(Color.GREEN);
        } else {
            timer.cancel();
            pauseBtn.setText("Resume");
            colorTv.setBackgroundColor(Color.RED);

        }

        paused = !paused;
    }

    private void nextSection() {
        if(timer!=null){
            timer.cancel();
            startNextSection();
        }

    }

    private void finishTest() {
        timer.cancel();
        stopperTv.setText("Stopper");
        infoTv.setText("Info");
        timerTv.setText("00:00");
        startBtn.setVisibility(View.VISIBLE);
        finishTestBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.GONE);
        nextPerekBtn.setVisibility(View.GONE);
        currentSection = 0;
        playSound("the test has finished!");
        colorTv.setBackgroundColor(Color.GRAY);
    }

    private void playSound(String soundFileName) {

        textToSpeech.speak(soundFileName,TextToSpeech.QUEUE_FLUSH,null,null);

    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    private long parseTime(String time) {
        String[] parts = time.split(":");
        long minutes = Long.parseLong(parts[0]);
        long seconds = Long.parseLong(parts[1]);
        return minutes * 60 + seconds;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}