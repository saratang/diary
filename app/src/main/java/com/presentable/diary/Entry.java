package com.presentable.diary;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SaraTang on 15-12-30.
 */
public class Entry implements Serializable {
    static int diaryEntryId;

    private int entryId;
    private Date date;
    private Mood mood;
    private Weather weather;
    private String contents;

    public static class Builder {
        private String contents;
        private int entryId;

        private Date date = Calendar.getInstance().getTime();
        private Mood mood = new Mood();
        private Weather weather = new Weather();

        public Builder(String contents) {
            this.contents = contents;
            this.entryId = Entry.diaryEntryId;
            Entry.diaryEntryId++;
        }

        public Builder setDate (Date date) {
            this.date = date;
            return this;
        }

        public Builder setMood (Mood mood) throws InvalidMoodException {
            this.mood = mood;
            return this;
        }

        public Builder setWeather (Weather weather) throws InvalidWeatherException {
            this.weather = weather;
            return this;
        }

        public Entry build() throws InvalidWeatherException, InvalidMoodException {
            return new Entry(this);
        }
    }

    private Entry(Builder builder)
            throws InvalidWeatherException, InvalidMoodException {
        entryId = builder.entryId;
        date = builder.date;
        mood = builder.mood;
        weather = builder.weather;
        contents = builder.contents;
    }

    public String getContents() {
        return contents;
    }

    public int getEntryId() {
        return entryId;
    }

    public Date getDate() {
        return date;
    }

    public Mood getMood() {
        return mood;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getPreview() {
        return contents.substring(0, 80);
    }

    public String toString() {
        return String.format("Entry Number: %s\nToday's weather: %s\nToday's mood: %s\n%s", entryId, weather.getWeather(), mood.getMood(), contents);
    }
}