package com.example.bibliy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private final List<MyDataSpinner> data;
    LayoutInflater lInflater;
    Context ctx;

    public MySpinnerAdapter(Context context, List<MyDataSpinner> data) {
        this.ctx = context;
        this.data = data;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View recycle, ViewGroup parent) {
        TextView text;
        if (recycle != null) {
            text = (TextView) recycle;
        } else {
            text = (TextView) lInflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
        text.setTextColor(Color.BLACK);
        text.setText(data.get(position).getName());
        return text;
    }
}
