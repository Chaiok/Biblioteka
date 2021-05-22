package com.example.bibliy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Publishing_houseAdapter extends BaseAdapter {
    ArrayList<Publishing_house> publishing_house = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Publishing_houseAdapter(Context context, ArrayList<Publishing_house> publishing_house) {
        this.ctx = context;
        //this.subjects = subjects;
        this.publishing_house.addAll(publishing_house);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return publishing_house.size();
    }

    @Override
    public Object getItem(int position) {
        return publishing_house.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.publishing_house_list_layout, parent, false);
        if (publishing_house.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.pub_house_list_lay_id)).setText(publishing_house.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.pub_house_list_lay_pub_name)).setText(publishing_house.get(position).getPublisher_name());
        ((TextView) view.findViewById(R.id.pub_house_list_lay_city_full)).setText(publishing_house.get(position).getCity_full());
        ((TextView) view.findViewById(R.id.pub_house_list_lay_city_less)).setText(publishing_house.get(position).getCity_less());
        return view;
    }
}