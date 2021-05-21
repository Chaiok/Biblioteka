package com.example.bibliy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class LibrarianAdapter extends BaseAdapter {
    ArrayList<Librarian> librarian = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    LibrarianAdapter(Context context, ArrayList<Librarian> librarian) {
        this.ctx = context;
        //this.subjects = subjects;
        this.librarian.addAll(librarian);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return librarian.size();
    }

    @Override
    public Object getItem(int position) {
        return librarian.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.librarian_list_layout, parent, false);
        if (librarian.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.librarian_list_lay_id)).setText(librarian.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.librarian_list_lay_fn)).setText(librarian.get(position).getFirst_name());
        ((TextView) view.findViewById(R.id.librarian_list_lay_ln)).setText(librarian.get(position).getLast_name());
        ((TextView) view.findViewById(R.id.librarian_list_lay_mn)).setText(librarian.get(position).getMiddle_name());
        ((TextView) view.findViewById(R.id.librarian_list_lay_t)).setText(librarian.get(position).getTelephone());
        return view;
    }
}