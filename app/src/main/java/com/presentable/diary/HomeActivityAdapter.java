package com.presentable.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaraTang on 15-12-31.
 */
public class HomeActivityAdapter extends ArrayAdapter<Entry> {
    private Context context;

    public HomeActivityAdapter(Context context,
                               List<Entry> diaryEntries) {
        super(context, 0, diaryEntries);
        this.context = context;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        final Entry entry = getItem(pos);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.list_item_entry, parent, false
            );
        }

        TextView dateView = (TextView) view.findViewById(R.id.list_item_entry_tv_date);
        TextView previewView = (TextView) view.findViewById(R.id.list_item_entry_tv_preview);

        dateView.setText(entry.getDate().toString());
        previewView.setText(entry.getPreview());

        return view;
    }
}
