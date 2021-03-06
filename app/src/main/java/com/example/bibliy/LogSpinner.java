package com.example.bibliy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LogSpinner extends BaseAdapter implements SpinnerAdapter {
    private final List<Log> data;
    LayoutInflater lInflater;
    Context ctx;

    public LogSpinner(Context context, List<Log> data) {
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
        SQLiteDatabase db = ctx.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query1 = db.rawQuery("SELECT * FROM client where id=?", new String[]{String.valueOf(data.get(position).getId_client())});
        query1.moveToFirst();
        text.setTextColor(Color.BLACK);
        text.setText(data.get(position).getLog()+" "+query1.getString(1)+" "+query1.getString(2)+" "+query1.getString(3));
        query1.close();
        db.close();
        return text;
    }
}
