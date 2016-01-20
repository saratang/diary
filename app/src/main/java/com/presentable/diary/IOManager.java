package com.presentable.diary;

import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SaraTang on 15-12-30.
 */
public class IOManager {
    public static String pin;
    public static List<Entry> diaryEntries = new ArrayList<>();
    public static File filesDir;
    public static String diaryEntriesFiles = "diaryEntries.ser";
    public static File diaryEntriesDir;
    public static SharedPreferences prefs;

    public static void readDiaryEntries() {
        for (Entry entry : diaryEntries) {
            System.out.println(entry);
        }
    }

    public static void addEntry(Entry entry, Entry existingEntry) {
        List<Entry> diaryEntriesCopy = new ArrayList<>(diaryEntries);

        if (existingEntry != null) {
            diaryEntriesCopy.remove(existingEntry);
        }
        diaryEntriesCopy.add(entry);

        diaryEntries = diaryEntriesCopy;
        saveEntriesToFile();
    }

    private static void saveEntriesToFile() {
        try {
            OutputStream diaryEntryFile = new FileOutputStream(IOManager.diaryEntriesDir);
            OutputStream diaryEntryBuffer = new BufferedOutputStream(diaryEntryFile);
            ObjectOutput diaryEntryOutput = new ObjectOutputStream(diaryEntryBuffer);

            diaryEntryOutput.writeObject(diaryEntries);

            diaryEntryOutput.close();
        } catch (IOException e) {
            System.out.println("Unable to find file.");
        }
    }

    public static void loadEntriesFromFile() {
        try {
            InputStream diaryEntryFile = new FileInputStream(IOManager.diaryEntriesDir);
            InputStream diaryEntryBuffer = new BufferedInputStream(diaryEntryFile);
            ObjectInput diaryEntryInput = new ObjectInputStream(diaryEntryBuffer);

            diaryEntries = (List<Entry>) diaryEntryInput.readObject();

            diaryEntryInput.close();
        } catch (IOException e) {
            new File(filesDir, diaryEntriesFiles);
            System.out.println("Unable to find file.");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find class.");
        }
    }

    public static Entry getEntryByDate(Date date) {
        //only need to compare year, day, and month
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_YEAR);

        for (Entry existingEntry : IOManager.diaryEntries) {
            Date existingDate = existingEntry.getDate();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(existingDate);
            int existingYear = cal2.get(Calendar.YEAR);
            int existingDay = cal2.get(Calendar.DAY_OF_YEAR);
            if (existingYear == year && existingDay == day) {
                return existingEntry;
            }
        }
        return null;
    }

    public static void removeEntry(Entry entry) {
        IOManager.diaryEntries.remove(entry);
        saveEntriesToFile();
    }
}