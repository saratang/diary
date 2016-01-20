package com.presentable.diary;

import android.content.SharedPreferences;

/**
 * Created by SaraTang on 15-12-30.
 */
public class User {
    public void setPin(String pin) {
        SharedPreferences prefs = IOManager.prefs;
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("pin", pin);
        editor.commit();
    }
}
