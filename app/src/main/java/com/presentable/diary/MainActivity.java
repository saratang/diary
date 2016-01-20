package com.presentable.diary;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this.getApplicationContext();
        IOManager.filesDir = context.getFilesDir();
        IOManager.prefs = PreferenceManager.getDefaultSharedPreferences(context);

        IOManager.diaryEntriesDir = new File(IOManager.filesDir, IOManager.diaryEntriesFiles);

        if (!IOManager.diaryEntriesDir.exists()) {
            IOManager.diaryEntriesDir.mkdir();
        }

        IOManager.loadEntriesFromFile();

        final String pin = IOManager.prefs.getString("pin", null);
        if (pin == null) {
            if (IOManager.diaryEntries.isEmpty()) {
                createEntries();
            }
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            final EditText pinField = (EditText) findViewById(R.id.activity_main_et_pin);
            Button pinButton = (Button) findViewById(R.id.activity_main_btn_pin);
            pinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredPin = pinField.getText().toString();
                    if (enteredPin.equals(pin)) {
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void createEntries() {
        try {
            Date date = Calendar.getInstance().getTime();
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DAY_OF_MONTH, 1);
            Date date2 = cal2.getTime();

            String contents = "Hello";
            Weather weather = new Weather(Weather.SUNNY);
            Weather weather2 = new Weather(Weather.CLOUDY);
            Mood mood = new Mood(Mood.HAPPY);
            Entry entry1 = new Entry.Builder(contents)
                    .setDate(date)
                    .setMood(mood)
                    .setWeather(weather)
                    .build();
            Entry entry2 = new Entry.Builder(contents)
                    .setDate(date2)
                    .setMood(mood)
                    .setWeather(weather2)
                    .build();
            IOManager.addEntry(entry1, null);
            IOManager.addEntry(entry2, null);
        } catch (InvalidWeatherException | InvalidMoodException e) {
            e.printStackTrace();
        }
    }
}