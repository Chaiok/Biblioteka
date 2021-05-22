package com.example.bibliy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;

public class AuthorAdapter extends BaseAdapter {
    ArrayList<Author> author = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    AuthorAdapter(Context context, ArrayList<Author> author) {
        this.ctx = context;
        //this.subjects = subjects;
        this.author.addAll(author);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return author.size();
    }

    @Override
    public Object getItem(int position) {
        return author.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.author_list_layout, parent, false);
        if (author.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.author_list_lay_id)).setText(author.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.author_list_lay_fn)).setText(author.get(position).getFirst_name());
        ((TextView) view.findViewById(R.id.author_list_lay_ln)).setText(author.get(position).getLast_name());
        ((TextView) view.findViewById(R.id.author_list_lay_mn)).setText(author.get(position).getMiddle_name());
        return view;
    }
}