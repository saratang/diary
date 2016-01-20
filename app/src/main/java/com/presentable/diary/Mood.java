package com.presentable.diary;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by SaraTang on 15-12-30.
 */
public class Mood implements Serializable {
    public static final String UNKNOWN = "(none)";
    public static final String HAPPY = "happy";
    public static final String SAD = "sad";
    public static final String ANGRY = "angry";
    public static final String EXCITED = "excited";
    public static final String NERVOUS = "nervous";

    public static final String[] moods = {UNKNOWN, HAPPY, SAD, ANGRY, EXCITED, NERVOUS};
    private String mood;

    public Mood(String mood) throws InvalidMoodException {
        if (Arrays.asList(moods).contains(mood)) {
            this.mood = mood;
        } else {
            new InvalidMoodException();
        }
    }

    public Mood() {
        this.mood = Mood.UNKNOWN;
    }

    public String getMood() {
        return mood;
    }
}
