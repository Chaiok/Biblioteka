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

public class BookSpinner extends BaseAdapter implements SpinnerAdapter {
    private final List<Book> data;
    LayoutInflater lInflater;
    Context ctx;

    public BookSpinner(Context context, List<Book> data) {
        this.ctx = context;
        this.data = data;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the Size of the ArrayList
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Returns one Element of the ArrayList
     * at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Returns the View that is shown when a element was
     * selected.
     */
    @Override
    public View getView(int position, View recycle, ViewGroup parent) {
        TextView text;
        if (recycle != null) {
            // Re-use the recycled view here!
            text = (TextView) recycle;
        } else {
            // No recycled view, inflate the "original" from the platform:
            text = (TextView) lInflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );
        }
        text.setTextColor(Color.BLACK);
        text.setText(data.get(position).getBook_name()+" "+data.get(position).getYear_of_publishing());
        return text;
    }
}
