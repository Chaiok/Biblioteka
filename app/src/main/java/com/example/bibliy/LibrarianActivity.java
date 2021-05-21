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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LibrarianActivity extends AppCompatActivity {
    LibrarianAdapter librarianAdapter;
    ArrayList<Librarian> librarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM librarian", null);
        ListView lv = (ListView) findViewById(R.id.librarian_listview);
        librarian = new ArrayList<>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id;
            id = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            librarian.add(new Librarian(id, first_name, last_name, middle_name, telephone));
        }
        librarianAdapter = new LibrarianAdapter(LibrarianActivity.this, librarian);
        lv.setAdapter(librarianAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditLibrarian(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.librarian_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
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

    public void EditLibrarian(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(LibrarianActivity.this);
        subjectDialog.setTitle("Укажите новые данные библиотекаря");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.librarian_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.librarian_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.librarian_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.librarian_add_mn);
        final EditText et4 = (EditText) vv.findViewById(R.id.librarian_add_t);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("first_name", et1.getText().toString());
                values.put("last_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                values.put("telephone", et4.getText().toString());
                long newRowId;
                if (id > 0) {
                    newRowId = db.update("librarian", values, "id = ?", new String[]{String.valueOf(id)});
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Библиотекарь успешно изменен", Toast.LENGTH_LONG).show();
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

    public void AddLibrarian(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(LibrarianActivity.this);
        subjectDialog.setTitle("Укажите данные нового библиотекаря");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.librarian_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.librarian_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.librarian_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.librarian_add_mn);
        final EditText et4 = (EditText) vv.findViewById(R.id.librarian_add_t);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("first_name", et1.getText().toString());
                values.put("last_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                values.put("telephone", et4.getText().toString());
                long newRowId = db.insert("librarian", null, values);
                if (newRowId != -1)
                    Toast.makeText(getApplicationContext(), "Библиотекарь успешно добавлен", Toast.LENGTH_LONG).show();
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

    public void RefreshLibrarians(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM librarian", null);
        ListView lv = (ListView) findViewById(R.id.librarian_listview);
        librarian = new ArrayList<>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id;
            id = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            librarian.add(new Librarian(id, first_name, last_name, middle_name, telephone));
        }
        librarianAdapter = new LibrarianAdapter(LibrarianActivity.this, librarian);
        lv.setAdapter(librarianAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditLibrarian(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.librarian_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }
}
