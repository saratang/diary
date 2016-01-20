package com.presentable.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class EditEntryActivity extends ActionBarActivity {
    private Spinner mWeatherSpinner;
    private Spinner mMoodSpinner;
    private EditText mEntryEditText;
    private Button mSaveButton;
    private Button mDeleteButton;
    private Context context;
    private Date date;
    private Entry existingEntry;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        date = (Date) intent.getSerializableExtra("date");
        setTitle(String.format("%1$tD", date));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();
        mEntryEditText = (EditText) findViewById(R.id.activity_edit_entry_et_entry);
        mWeatherSpinner =
                (Spinner) findViewById(R.id.activity_edit_entry_spn_weather);
        mMoodSpinner =
                (Spinner) findViewById(R.id.activity_edit_entry_spn_mood);
        mSaveButton = (Button) findViewById(R.id.activity_edit_entry_btn_save);
        mDeleteButton = (Button) findViewById(R.id.activity_edit_entry_btn_delete);

        resultIntent = intent;

        initializeSpinners();
        initializeEntry();
        addListenerOnSaveButton();
        addListenerOnDeleteButton();
    }

    public void initializeSpinners() {
        ArrayAdapter<String> weatherSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        Weather.weathers);
        weatherSpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mWeatherSpinner.setAdapter(weatherSpinnerAdapter);

        ArrayAdapter<String> moodSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        Mood.moods);
        moodSpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mMoodSpinner.setAdapter(moodSpinnerAdapter);
    }

    public void addListenerOnSaveButton() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String contents = mEntryEditText.getText().toString();
                    String selectedWeather =
                            String.valueOf(mWeatherSpinner.getSelectedItem());
                    String selectedMood =
                            String.valueOf(mMoodSpinner.getSelectedItem());

                    Entry entry = new Entry.Builder(contents).setDate(date).setMood(new Mood(selectedMood)).setWeather(new Weather(selectedWeather)).build();

                    IOManager.addEntry(entry, existingEntry);

                    setResult(RESULT_OK, resultIntent);

                    Toast.makeText(context, "Added entry!", Toast.LENGTH_SHORT).show();
                } catch (InvalidWeatherException e) {
                    Toast.makeText(context, "Unfortunately, we were unable to set the weather.", Toast.LENGTH_SHORT).show();
                } catch (InvalidMoodException e) {
                    Toast.makeText(context, "Unfortunately, we were unable to set the mood.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addListenerOnDeleteButton() {
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(EditEntryActivity.this)
                    .setMessage("Are you sure you wish to delete this" +
                    "entry? This action cannot be undone.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IOManager.removeEntry(existingEntry);
                            setResult(RESULT_OK, resultIntent);
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
            }
        });
    }

    public void initializeEntry() {
        existingEntry = IOManager.getEntryByDate(date);
        if (existingEntry != null) {
            mEntryEditText.setText(existingEntry.getContents());
            mWeatherSpinner.setSelection(Arrays.asList(Weather.weathers).indexOf(existingEntry.getWeather().getWeather()));
            mMoodSpinner.setSelection(Arrays.asList(Mood.moods).indexOf(existingEntry.getMood().getMood()));
        } else {
            mDeleteButton.setVisibility(View.GONE);
        }
    }

}
