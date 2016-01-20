package com.presentable.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends ActionBarActivity {
    private Context context;
    private CaldroidFragment caldroidFragment;
    private static final int REQUEST_CODE = 168;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        initializeCalendar();
    }

    private void initializeCalendar() {
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent = new Intent(context, EditEntryActivity.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        initializeCells();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.activity_home_calendar, caldroidFragment);
        t.commit();
    }

    private void initializeCells() {
        for (Entry entry : IOManager.diaryEntries) {
            paintCell(entry);
        }
    }

    private void paintCell(Entry entry) {
        Date date = entry.getDate();
        Integer color = null;
        switch (entry.getWeather().getWeather()) {
            case Weather.UNKNOWN:
                color = R.color.LightSalmon;
                break;
            case Weather.SUNNY:
                color = R.color.Gold;
                break;
            case Weather.CLOUDY:
                color = R.color.LightGrey;
                break;
            case Weather.RAINY:
                color = R.color.LightSkyBlue;
                break;
            case Weather.SNOWY:
                color = R.color.WhiteSmoke;
                break;
            case Weather.THUNDERSTORM:
                color = R.color.Thistle;
                break;
            case Weather.WINDY:
                color = R.color.DarkSeaGreen;
                break;
            default:
                color = R.color.white;
                break;
        }
        caldroidFragment.setBackgroundResourceForDate(color, date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        caldroidFragment.refreshView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Date date = (Date) intent.getSerializableExtra("date");
                Entry editedEntry = IOManager.getEntryByDate(date);

                caldroidFragment.setBackgroundResourceForDate(R.color.white, date);

                if (editedEntry != null) {
                    paintCell(editedEntry);
                }

                caldroidFragment.refreshView();
            }
        }
    }
}
