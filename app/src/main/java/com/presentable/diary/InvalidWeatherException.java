package com.presentable.diary;

/**
 * Created by SaraTang on 15-12-30.
 */
public class InvalidWeatherException extends Exception {
    public InvalidWeatherException() {
        super();
    }

    public InvalidWeatherException(String msg) {
        super(msg);
    }
}
