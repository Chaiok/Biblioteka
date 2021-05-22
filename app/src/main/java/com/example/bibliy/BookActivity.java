package com.example.bibliy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    BookAdapter bookAdapter;
    ArrayList<Book> book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM book", null);
        ListView lv = (ListView) findViewById(R.id.book_listview);
        book = new ArrayList<>();
        while (query.moveToNext()) {
            String book_name, genre, year_of_publishing;
            Integer id, pages, id_author, id_publishing_house;
            id = query.getInt(0);
            book_name = query.getString(1);
            genre = query.getString(2);
            year_of_publishing = query.getString(3);
            pages = query.getInt(4);
            id_author = query.getInt(5);
            id_publishing_house = query.getInt(6);
            book.add(new Book(id, book_name, genre, year_of_publishing, pages, id_author, id_publishing_house));
        }
        bookAdapter = new BookAdapter(BookActivity.this, book);
        lv.setAdapter(bookAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditBook(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.book_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("Выход");
        menu.add("Авторы");
        menu.add("Издательства");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case 0:
                finish();
                return true;
            case 1:
                //intent = new Intent(this, AuthorActivity.class);
                //startActivity(intent);
                return true;
            case 2:
                //intent = new Intent(this, Publishing_houseActivity.class);
                //startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void EditBook(Integer id) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(BookActivity.this);
        subjectDialog.setTitle("Обновите информацию о книге");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.book_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.book_add_name);
        final EditText et2 = (EditText) vv.findViewById(R.id.book_add_genre);
        final EditText et3 = (EditText) vv.findViewById(R.id.book_add_year_of_pub);
        final EditText et4 = (EditText) vv.findViewById(R.id.book_add_pages);
        final EditText et5 = (EditText) vv.findViewById(R.id.book_add_id_author);
        final EditText et6 = (EditText) vv.findViewById(R.id.book_add_id_pub_house);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("book_name", et1.getText().toString());
                values.put("genre", et2.getText().toString());
                values.put("year_of_publishing", et3.getText().toString());
                values.put("pages", Integer.valueOf(et4.getText().toString()));
                values.put("id_author", Integer.valueOf(et5.getText().toString()));
                values.put("id_publishing_house", Integer.valueOf(et6.getText().toString()));
                long newRowId;
                if (id > 0) {
                    newRowId = db.update("book", values, "id = ?", new String[]{String.valueOf(id)});
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Данные успешно изменены", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                }
                db.close();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void AddBook(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(BookActivity.this);
        subjectDialog.setTitle("Укажите информацию о новой книге");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.book_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.book_add_name);
        final EditText et2 = (EditText) vv.findViewById(R.id.book_add_genre);
        final EditText et3 = (EditText) vv.findViewById(R.id.book_add_year_of_pub);
        final EditText et4 = (EditText) vv.findViewById(R.id.book_add_pages);
        final EditText et5 = (EditText) vv.findViewById(R.id.book_add_id_author);
        final EditText et6 = (EditText) vv.findViewById(R.id.book_add_id_pub_house);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("book_name", et1.getText().toString());
                values.put("genre", et2.getText().toString());
                values.put("year_of_publishing", et3.getText().toString());
                values.put("pages", Integer.valueOf(et4.getText().toString()));
                values.put("id_author", Integer.valueOf(et5.getText().toString()));
                values.put("id_publishing_house", Integer.valueOf(et6.getText().toString()));
                long newRowId = db.insert("book", null, values);
                if (newRowId != -1)
                    Toast.makeText(getApplicationContext(), "Данные успешно изменены", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void RefreshBooks(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM book", null);
        ListView lv = (ListView) findViewById(R.id.book_listview);
        book = new ArrayList<>();
        while (query.moveToNext()) {
            String book_name, genre, year_of_publishing;
            Integer id, pages, id_author, id_publishing_house;
            id = query.getInt(0);
            book_name = query.getString(1);
            genre = query.getString(2);
            year_of_publishing = query.getString(3);
            pages = query.getInt(4);
            id_author = query.getInt(5);
            id_publishing_house = query.getInt(6);
            book.add(new Book(id, book_name, genre, year_of_publishing, pages, id_author, id_publishing_house));
        }
        bookAdapter = new BookAdapter(BookActivity.this, book);
        lv.setAdapter(bookAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditBook(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.book_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }
}
