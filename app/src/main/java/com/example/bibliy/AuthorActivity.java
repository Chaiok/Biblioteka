package com.example.bibliy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
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

public class AuthorActivity extends AppCompatActivity {
    AuthorAdapter authorAdapter;
    ArrayList<Author> author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        RefreshAuthors();
        View.OnClickListener delete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(AuthorActivity.this);
                subjectDialog.setTitle("Удалить автора");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_layout, null);
                final Spinner et1 = (Spinner) vv.findViewById(R.id.delete_spinner);

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
                AuthorSpinner author_spinner = new AuthorSpinner(AuthorActivity.this, author_data);
                et1.setAdapter(author_spinner);

                subjectDialog.setView(vv);

                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Author cl = (Author) et1.getSelectedItem();
                        db.execSQL("PRAGMA foreign_keys=ON");
                        try {
                            db.delete("author", "id = ?", new String[]{String.valueOf(cl.getId())});
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } finally {
                        }
                        db.close();
                        RefreshAuthors();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();
            }
        };
        ((Button) findViewById(R.id.author_delete)).setOnClickListener(delete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("Выход");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 0:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void EditAuthor(Integer id) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(AuthorActivity.this);
        subjectDialog.setTitle("Укажите данные автора");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.author_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.author_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.author_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.author_add_mn);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("first_name", et1.getText().toString());
                values.put("last_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                long newRowId;
                if (id > 0) {
                    newRowId = db.update("author", values, "id = ?", new String[]{String.valueOf(id)});
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Автор успешно изменен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                }
                db.close();
                RefreshAuthors();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void AddAuthor(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(AuthorActivity.this);
        subjectDialog.setTitle("Укажите данные нового автора");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.author_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.author_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.author_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.author_add_mn);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("first_name", et1.getText().toString());
                values.put("last_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                long newRowId = db.insert("author", null, values);
                if (newRowId != -1)
                    Toast.makeText(getApplicationContext(), "Автор успешно добавлен", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                RefreshAuthors();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void RefreshAuthors(View view) {
        RefreshAuthors();
    }

    public void RefreshAuthors() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM author", null);
        ListView lv = (ListView) findViewById(R.id.author_listview);
        author = new ArrayList<>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name;
            Integer id;
            id = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            author.add(new Author(id, first_name, last_name, middle_name));
        }
        authorAdapter = new AuthorAdapter(AuthorActivity.this, author);
        lv.setAdapter(authorAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditAuthor(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.author_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }
}
