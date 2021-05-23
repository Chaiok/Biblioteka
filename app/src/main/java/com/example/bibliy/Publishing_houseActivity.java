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

public class Publishing_houseActivity extends AppCompatActivity {
    Publishing_houseAdapter publishing_houseAdapter;
    ArrayList<Publishing_house> publishing_house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishing_house);

        RefreshPublishing_house();
        View.OnClickListener delete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Publishing_houseActivity.this);
                subjectDialog.setTitle("Удалить издательство");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_layout, null);
                final Spinner et1 = (Spinner) vv.findViewById(R.id.delete_spinner);

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query = db.rawQuery("SELECT * FROM publishing_house", null);
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
                Publishing_houseSpinner publishing_house_spinner = new Publishing_houseSpinner(Publishing_houseActivity.this, publishing_house_data);
                et1.setAdapter(publishing_house_spinner);

                subjectDialog.setView(vv);

                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Publishing_house cl = (Publishing_house) et1.getSelectedItem();
                        db.execSQL("PRAGMA foreign_keys=ON");
                        try {
                            db.delete("publishing_house", "id = ?", new String[]{String.valueOf(cl.getId())});
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } finally {
                        }
                        db.close();
                        RefreshPublishing_house();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();
            }
        };
        ((Button) findViewById(R.id.publishing_house_delete)).setOnClickListener(delete);
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

    public void EditPublishing_house(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Publishing_houseActivity.this);
        subjectDialog.setTitle("Укажите данные издательства");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.publishing_house_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.pub_house_add_pub_name);
        final EditText et2 = (EditText) vv.findViewById(R.id.pub_house_add_city_full);
        final EditText et3 = (EditText) vv.findViewById(R.id.pub_house_add_city_less);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("publisher_name", et1.getText().toString());
                values.put("city_full", et2.getText().toString());
                values.put("city_less", et3.getText().toString());
                long newRowId;
                if (id > 0) {
                    newRowId = db.update("publishing_house", values, "id = ?", new String[]{String.valueOf(id)});
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Издательство успешно изменено", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                }
                db.close();
                RefreshPublishing_house();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void AddPublishing_house(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Publishing_houseActivity.this);
        subjectDialog.setTitle("Укажите данные издательства");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.publishing_house_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.pub_house_add_pub_name);
        final EditText et2 = (EditText) vv.findViewById(R.id.pub_house_add_city_full);
        final EditText et3 = (EditText) vv.findViewById(R.id.pub_house_add_city_less);
        subjectDialog.setView(vv);

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("publisher_name", et1.getText().toString());
                values.put("city_full", et2.getText().toString());
                values.put("city_less", et3.getText().toString());
                long newRowId = db.insert("publishing_house", null, values);
                if(newRowId!=-1) Toast.makeText(getApplicationContext(),"Издательство успешно добавлено", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                RefreshPublishing_house();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void RefreshPublishing_house(View view){
        RefreshPublishing_house();
    }

    public void RefreshPublishing_house(){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM publishing_house", null);
        ListView lv = (ListView) findViewById(R.id.publishing_house_listview);
        publishing_house = new ArrayList<>();
        while (query.moveToNext()) {
            String publisher_name, city_full, city_less;
            Integer id;
            id = query.getInt(0);
            publisher_name = query.getString(1);
            city_full = query.getString(2);
            city_less = query.getString(3);
            publishing_house.add(new Publishing_house(id, publisher_name, city_full, city_less));
        }
        publishing_houseAdapter = new Publishing_houseAdapter(Publishing_houseActivity.this, publishing_house);
        lv.setAdapter(publishing_houseAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditPublishing_house(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.pub_house_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }

}
