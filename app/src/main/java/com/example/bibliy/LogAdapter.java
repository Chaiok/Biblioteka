package com.example.bibliy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class LogAdapter extends BaseAdapter {
    ArrayList<Log> log = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    LogAdapter(Context context, ArrayList<Log> log) {
        this.ctx = context;
        //this.subjects = subjects;
        this.log.addAll(log);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return log.size();
    }

    @Override
    public Object getItem(int position) {
        return log.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.log_list_layout, parent, false);
        if (log.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.client_list_lay_id)).setText(log.get(position).getId().toString());
        //((TextView) view.findViewById(R.id.client_list_lay_fn)).setText(log.get(position).getFirst_name());
        //((TextView) view.findViewById(R.id.client_list_lay_ln)).setText(log.get(position).getLast_name());
        //((TextView) view.findViewById(R.id.client_list_lay_mn)).setText(log.get(position).getMiddle_name());
        //((TextView) view.findViewById(R.id.client_list_lay_t)).setText(log.get(position).getTelephone());
        return view;
    }
}