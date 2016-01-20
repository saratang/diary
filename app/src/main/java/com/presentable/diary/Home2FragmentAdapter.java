package com.presentable.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by SaraTang on 16-01-02.
 */
public class Home2FragmentAdapter extends ArrayAdapter<Entry> {
    private Context context;

    public Home2FragmentAdapter(Context context, ArrayList<Entry> diaryEntries) {
        super(context, 0, diaryEntries);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Entry entry = getItem(pos);
        return convertView;
    }
}
