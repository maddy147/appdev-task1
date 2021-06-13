package com.example.guesstheday;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radiogrp = findViewById(R.id.radioGroup);
        Button chk = findViewById(R.id.button2);
        final int[] score = {0};
        final String[] dayName = {generateRanDate()};
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radiogrp.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                String ans = String.valueOf(radioButton.getText());
                ConstraintLayout rl = findViewById(R.id.activity_main);
                if (ans.equals(dayName[0])) {
                    rl.setBackgroundColor(Color.GREEN);
                    vib.vibrate(500);
                    dayName[0] =generateRanDate();
                    score[0]++;
                    radiogrp.clearCheck();
                } else {
                    rl.setBackgroundColor(Color.RED);
                    vib.vibrate(400);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup, null);
                    final PopupWindow popup= new PopupWindow(popupView,ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    popup.showAtLocation(popupView, Gravity.CENTER,0,0);
                    ((TextView)popup.getContentView().findViewById(R.id.textView2)).setText("Your Score:"+(score[0]));
                    popup.getContentView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAndRemoveTask();
                        }
                    })
                ;}
            }
        });
    }
        public static int randBetween(int start, int end) {
            return start + (int)Math.round(Math.random() * (end - start));
        }

    @SuppressLint("SetTextI18n")
        public String  generateRanDate(){
            Button[] btn = new Button[4];
            btn[0] = findViewById(R.id.radioButton);
            btn[1] = findViewById(R.id.radioButton2);
            btn[2] = findViewById(R.id.radioButton3);
            btn[3] = findViewById(R.id.radioButton4);

            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            GregorianCalendar gc = new GregorianCalendar();

            int year = randBetween(1900, 2010);

            gc.set(Calendar.YEAR, year);

            int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

            gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

            TextView theTextView = findViewById(R.id.textView);
            theTextView.setText(gc.get(Calendar.DAY_OF_MONTH) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.YEAR));

            String dayName = gc.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

            int index = randBetween(0, 3);
            int i,j=0;
            btn[index].setText(dayName);    
            for (i = 0; i < 4; i++) {
                if (i == index) {
                    continue;
                }
                if(!(days[j].equals(dayName))){
                    btn[i].setText(days[j]);
                }
                else{
                    btn[i].setText(days[++j]);
                }
                j++;
            }
            return dayName;
        }
}