package com.example.bibliy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BookAdapter extends BaseAdapter {
    ArrayList<Book> book = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    BookAdapter(Context context, ArrayList<Book> book) {
        this.ctx = context;
        //this.subjects = subjects;
        this.book.addAll(book);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return book.size();
    }

    @Override
    public Object getItem(int position) {
        return book.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.book_list_layout, parent, false);

        if (book.isEmpty()) return view;

        SQLiteDatabase db = ctx.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query1 = db.rawQuery("SELECT * FROM author where id=?", new String[]{String.valueOf(book.get(position).getId_author())});
        Cursor query2 = db.rawQuery("SELECT * FROM publishing_house where id=?", new String[]{String.valueOf(book.get(position).getId_publishing_house())});
        query1.moveToFirst();
        query2.moveToFirst();

        ((TextView) view.findViewById(R.id.book_list_lay_id)).setText(book.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.book_list_lay_name)).setText(book.get(position).getBook_name());
        ((TextView) view.findViewById(R.id.book_list_lay_genre)).setText(book.get(position).getGenre());
        ((TextView) view.findViewById(R.id.book_list_lay_year_of_pub)).setText(book.get(position).getYear_of_publishing());
        ((TextView) view.findViewById(R.id.book_list_lay_pages)).setText(book.get(position).getPages().toString());
        //((TextView) view.findViewById(R.id.book_list_lay_id_author)).setText(book.get(position).getId_author().toString());
        //((TextView) view.findViewById(R.id.book_list_lay_id_pub_house)).setText(book.get(position).getId_publishing_house().toString());
        ((TextView) view.findViewById(R.id.book_list_lay_id_author)).setText(query1.getString(1)+"\n"+query1.getString(2)+"\n"+query1.getString(3));
        ((TextView) view.findViewById(R.id.book_list_lay_id_pub_house)).setText(query2.getString(1)+"\n"+query2.getString(2));
        query1.close();
        query2.close();
        db.close();
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}