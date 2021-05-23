package com.example.bibliy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    BookAdapter bookAdapter;
    ArrayList<Book> book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        RefreshBooks();
        View.OnClickListener delete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(BookActivity.this);
                subjectDialog.setTitle("Удалить книгу");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_layout, null);
                final Spinner et1 = (Spinner) vv.findViewById(R.id.delete_spinner);

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query = db.rawQuery("SELECT * FROM book", null);
                List<Book> book_data = new ArrayList<Book>();
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
                    book_data.add(new Book(id, book_name, genre, year_of_publishing, pages, id_author, id_publishing_house));
                }
                BookSpinner book_spinner = new BookSpinner(BookActivity.this, book_data);
                et1.setAdapter(book_spinner);

                subjectDialog.setView(vv);

                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Book cl = (Book) et1.getSelectedItem();
                        db.execSQL("PRAGMA foreign_keys=ON");
                        try {
                            db.delete("book", "id = ?", new String[]{String.valueOf(cl.getId())});
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } finally {
                        }
                        db.close();
                        RefreshBooks();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();
            }
        };
        ((Button) findViewById(R.id.book_delete)).setOnClickListener(delete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(1, 0, 0, "Авторы");
        menu.add(1, 1, 1, "Издательства");
        menu.add(1, 2, 2, "Выход");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case 0:
                intent = new Intent(this, AuthorActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                return true;
            case 1:
                intent = new Intent(this, Publishing_houseActivity.class);
                startActivity(intent);
                return true;
            case 2:
                finish();
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
        final Spinner et5 = (Spinner) vv.findViewById(R.id.book_add_author_spinner);
        final Spinner et6 = (Spinner) vv.findViewById(R.id.book_add_pub_house_spinner);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM author", null);
        List<Author> author_data = new ArrayList<Author>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name;
            Integer id1;
            id1 = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            author_data.add(new Author(id1, first_name, last_name, middle_name));
        }
        AuthorSpinner author_spinner = new AuthorSpinner(BookActivity.this,author_data);
        et5.setAdapter(author_spinner);

        query = db.rawQuery("SELECT * FROM publishing_house", null);
        List<Publishing_house> publishing_house_data = new ArrayList<Publishing_house>();
        while (query.moveToNext()) {
            String publisher_name, city_full, city_less;
            Integer id1;
            id1 = query.getInt(0);
            publisher_name = query.getString(1);
            city_full = query.getString(2);
            city_less = query.getString(3);
            publishing_house_data.add(new Publishing_house(id1, publisher_name, city_full, city_less));
        }
        Publishing_houseSpinner publishing_house_spinner = new Publishing_houseSpinner(BookActivity.this,publishing_house_data);
        et6.setAdapter(publishing_house_spinner);
        query.close();
        db.close();

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
                Author author = (Author) et5.getSelectedItem();
                values.put("id_author", author.getId());
                Publishing_house pub_house = (Publishing_house) et6.getSelectedItem();
                values.put("id_publishing_house", pub_house.getId());
                //values.put("id_author", Integer.valueOf(et5.getText().toString()));
                //values.put("id_publishing_house", Integer.valueOf(et6.getText().toString()));
                long newRowId = -1;
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try {
                        //db.execSQL("UPDATE book SET book_name = ? WHERE id = ?", new String[] {"kkk", String.valueOf(id)});
                        newRowId = db.update("book", values, "id = ?", new String[]{String.valueOf(id)});
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (newRowId != -1)
                            Toast.makeText(getApplicationContext(), "Данные успешно изменены", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                }
                db.close();
                RefreshBooks();
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
        final Spinner et5 = (Spinner) vv.findViewById(R.id.book_add_author_spinner);
        final Spinner et6 = (Spinner) vv.findViewById(R.id.book_add_pub_house_spinner);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM author", null);
        List<Author> author_data = new ArrayList<Author>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name;
            Integer id;
            id = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            author_data.add(new Author(id, first_name, last_name, middle_name));
        }
        AuthorSpinner author_spinner = new AuthorSpinner(BookActivity.this,author_data);
        et5.setAdapter(author_spinner);

        query = db.rawQuery("SELECT * FROM publishing_house", null);
        List<Publishing_house> publishing_house_data = new ArrayList<Publishing_house>();
        while (query.moveToNext()) {
            String publisher_name, city_full, city_less;
            Integer id;
            id = query.getInt(0);
            publisher_name = query.getString(1);
            city_full = query.getString(2);
            city_less = query.getString(3);
            publishing_house_data.add(new Publishing_house(id, publisher_name, city_full, city_less));
        }
        Publishing_houseSpinner publishing_house_spinner = new Publishing_houseSpinner(BookActivity.this,publishing_house_data);
        et6.setAdapter(publishing_house_spinner);
        query.close();
        db.close();

        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                    ContentValues values = new ContentValues();
                    values.put("book_name", et1.getText().toString());
                    values.put("genre", et2.getText().toString());
                    values.put("year_of_publishing", et3.getText().toString());
                    values.put("pages", Integer.valueOf(et4.getText().toString()));
                    Author author = (Author) et5.getSelectedItem();
                    values.put("id_author", author.getId());
                    Publishing_house pub_house = (Publishing_house) et6.getSelectedItem();
                    values.put("id_publishing_house", pub_house.getId());
                    //values.put("id_author", Integer.valueOf(et5.getText().toString()));
                    //values.put("id_publishing_house", Integer.valueOf(et6.getText().toString()));
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long newRowId = db.insert("book", null, values);
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Данные успешно изменены", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                finally {
                    db.close();
                    RefreshBooks();
                }
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void RefreshBooks(View view) {
        RefreshBooks();
    }

    public void RefreshBooks(){
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
