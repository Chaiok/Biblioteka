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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LogbookActivity extends AppCompatActivity {
    LogAdapter logAdapter;
    ArrayList<Log> log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);
        RefreshLog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(1, 0, 0, "Выход");
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void EditLog(Integer id) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(LogbookActivity.this);
        subjectDialog.setTitle("Обновить запись");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.log_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.log_add_log);
        final Spinner et2 = (Spinner) vv.findViewById(R.id.log_add_client_spinner);
        final Spinner et3 = (Spinner) vv.findViewById(R.id.log_add_book_spinner);
        final Spinner et4 = (Spinner) vv.findViewById(R.id.log_add_librarian_spinner);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM client", null);
        List<Client> client_data = new ArrayList<Client>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id1;
            id1 = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            client_data.add(new Client(id1, first_name, last_name, middle_name, telephone));
        }
        ClientSpinner client_spinner = new ClientSpinner(LogbookActivity.this,client_data);
        et2.setAdapter(client_spinner);

        query = db.rawQuery("SELECT * FROM book", null);
        List<Book> book_data = new ArrayList<Book>();
        while (query.moveToNext()) {
            String book_name, genre, year_of_publishing;
            Integer id1, pages, id_author, id_publishing_house;
            id1 = query.getInt(0);
            book_name = query.getString(1);
            genre = query.getString(2);
            year_of_publishing = query.getString(3);
            pages = query.getInt(4);
            id_author = query.getInt(5);
            id_publishing_house = query.getInt(6);
            book_data.add(new Book(id1, book_name, genre, year_of_publishing, pages, id_author, id_publishing_house));
        }
        BookSpinner book_spinner = new BookSpinner(LogbookActivity.this,book_data);
        et3.setAdapter(book_spinner);

        query = db.rawQuery("SELECT * FROM librarian", null);
        List<Librarian> librarian_data = new ArrayList<Librarian>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id1;
            id1 = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            librarian_data.add(new Librarian(id1, first_name, last_name, middle_name, telephone));
        }
        LibrarianSpinner librarian_spinner = new LibrarianSpinner(LogbookActivity.this,librarian_data);
        et4.setAdapter(librarian_spinner);

        query.close();
        db.close();
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("log", et1.getText().toString());
                Client id_client = (Client) et2.getSelectedItem();
                values.put("id_client", id_client.getId());
                Book id_book = (Book) et3.getSelectedItem();
                values.put("id_book", id_book.getId());
                Librarian id_librarian = (Librarian) et4.getSelectedItem();
                values.put("id_librarian", id_librarian.getId());
                long newRowId = -1;
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try {
                        //db.execSQL("UPDATE book SET book_name = ? WHERE id = ?", new String[] {"kkk", String.valueOf(id)});
                        newRowId = db.update("logbook", values, "id = ?", new String[]{String.valueOf(id)});
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
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void AddLog(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(LogbookActivity.this);
        subjectDialog.setTitle("Обновить запись");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.log_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.log_add_log);
        final Spinner et2 = (Spinner) vv.findViewById(R.id.log_add_client_spinner);
        final Spinner et3 = (Spinner) vv.findViewById(R.id.log_add_book_spinner);
        final Spinner et4 = (Spinner) vv.findViewById(R.id.log_add_librarian_spinner);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM client", null);
        List<Client> client_data = new ArrayList<Client>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id1;
            id1 = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            client_data.add(new Client(id1, first_name, last_name, middle_name, telephone));
        }
        ClientSpinner client_spinner = new ClientSpinner(LogbookActivity.this, client_data);
        et2.setAdapter(client_spinner);

        query = db.rawQuery("SELECT * FROM book", null);
        List<Book> book_data = new ArrayList<Book>();
        while (query.moveToNext()) {
            String book_name, genre, year_of_publishing;
            Integer id1, pages, id_author, id_publishing_house;
            id1 = query.getInt(0);
            book_name = query.getString(1);
            genre = query.getString(2);
            year_of_publishing = query.getString(3);
            pages = query.getInt(4);
            id_author = query.getInt(5);
            id_publishing_house = query.getInt(6);
            book_data.add(new Book(id1, book_name, genre, year_of_publishing, pages, id_author, id_publishing_house));
        }
        BookSpinner book_spinner = new BookSpinner(LogbookActivity.this, book_data);
        et3.setAdapter(book_spinner);

        query = db.rawQuery("SELECT * FROM librarian", null);
        List<Librarian> librarian_data = new ArrayList<Librarian>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id1;
            id1 = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            librarian_data.add(new Librarian(id1, first_name, last_name, middle_name, telephone));
        }
        LibrarianSpinner librarian_spinner = new LibrarianSpinner(LogbookActivity.this, librarian_data);
        et4.setAdapter(librarian_spinner);

        query.close();
        db.close();
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("log", et1.getText().toString());
                Client id_client = (Client) et2.getSelectedItem();
                values.put("id_client", id_client.getId());
                Book id_book = (Book) et3.getSelectedItem();
                values.put("id_book", id_book.getId());
                Librarian id_librarian = (Librarian) et4.getSelectedItem();
                values.put("id_librarian", id_librarian.getId());
                db.execSQL("PRAGMA foreign_keys=ON");
                long newRowId = db.insert("logbook", null, values);
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

    public void RefreshLog(View view) {
        RefreshLog();
    }

    public void RefreshLog(){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM logbook", null);
        ListView lv = (ListView) findViewById(R.id.log_listview);
        log = new ArrayList<>();
        while (query.moveToNext()) {
            String logbook;
            Integer id, id_client, id_book, id_librarian;
            id = query.getInt(0);
            logbook = query.getString(1);
            id_client = query.getInt(2);
            id_book = query.getInt(3);
            id_librarian = query.getInt(4);
            log.add(new Log(id, logbook, id_client, id_book, id_librarian));
        }
        logAdapter = new LogAdapter(LogbookActivity.this, log);
        lv.setAdapter(logAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditLog(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.log_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }
}
