package com.example.bibliy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ClientAdapter extends BaseAdapter {
    ArrayList<Client> client = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    ClientAdapter(Context context, ArrayList<Client> client) {
        this.ctx = context;
        //this.subjects = subjects;
        this.client.addAll(client);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return client.size();
    }

    @Override
    public Object getItem(int position) {
        return client.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.client_list_layout, parent, false);
        if (client.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.client_list_lay_id)).setText(client.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.client_list_lay_fn)).setText(client.get(position).getFirst_name());
        ((TextView) view.findViewById(R.id.client_list_lay_ln)).setText(client.get(position).getLast_name());
        ((TextView) view.findViewById(R.id.client_list_lay_mn)).setText(client.get(position).getMiddle_name());
        ((TextView) view.findViewById(R.id.client_list_lay_t)).setText(client.get(position).getTelephone());
        return view;
    }
}