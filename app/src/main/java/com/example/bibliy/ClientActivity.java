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

public class ClientActivity extends AppCompatActivity {
    ClientAdapter clientAdapter;
    ArrayList<Client> client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        RefreshClients();
        View.OnClickListener delete = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(ClientActivity.this);
                subjectDialog.setTitle("Удалить клиента");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.delete_layout, null);
                final Spinner et1 = (Spinner) vv.findViewById(R.id.delete_spinner);

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query = db.rawQuery("SELECT * FROM client", null);
                List<Client> client_data = new ArrayList<Client>();
                while (query.moveToNext()) {
                    String first_name, last_name, middle_name, telephone;
                    Integer id;
                    id = query.getInt(0);
                    first_name = query.getString(1);
                    last_name = query.getString(2);
                    middle_name = query.getString(3);
                    telephone = query.getString(4);
                    client_data.add(new Client(id, first_name, last_name, middle_name, telephone));
                }
                ClientSpinner author_spinner = new ClientSpinner(ClientActivity.this, client_data);
                et1.setAdapter(author_spinner);

                subjectDialog.setView(vv);

                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Client cl = (Client) et1.getSelectedItem();
                        db.execSQL("PRAGMA foreign_keys=ON");
                        try {
                            db.delete("client", "id = ?", new String[]{String.valueOf(cl.getId())});
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } finally {
                        }
                        db.close();
                        RefreshClients();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();
            }
        };
        ((Button) findViewById(R.id.client_delete)).setOnClickListener(delete);
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

    public void EditClient(Integer id) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(ClientActivity.this);
        subjectDialog.setTitle("Укажите новые данные клиента");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.client_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.client_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.client_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.client_add_mn);
        final EditText et4 = (EditText) vv.findViewById(R.id.client_add_t);
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
                    newRowId = db.update("client", values, "id = ?", new String[]{String.valueOf(id)});
                    if (newRowId != -1)
                        Toast.makeText(getApplicationContext(), "Клиент успешно изменен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                }
                db.close();
                RefreshClients();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void AddClient(View view) {
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(ClientActivity.this);
        subjectDialog.setTitle("Укажите данные нового клиента");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.client_layout, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.client_add_fn);
        final EditText et2 = (EditText) vv.findViewById(R.id.client_add_ln);
        final EditText et3 = (EditText) vv.findViewById(R.id.client_add_mn);
        final EditText et4 = (EditText) vv.findViewById(R.id.client_add_t);
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
                long newRowId = db.insert("client", null, values);
                if (newRowId != -1)
                    Toast.makeText(getApplicationContext(), "Клиент успешно добавлен", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                RefreshClients();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }

    public void RefreshClients(View view) {
        RefreshClients();
    }

    public void RefreshClients() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM client", null);
        ListView lv = (ListView) findViewById(R.id.client_listview);
        client = new ArrayList<>();
        while (query.moveToNext()) {
            String first_name, last_name, middle_name, telephone;
            Integer id;
            id = query.getInt(0);
            first_name = query.getString(1);
            last_name = query.getString(2);
            middle_name = query.getString(3);
            telephone = query.getString(4);
            client.add(new Client(id, first_name, last_name, middle_name, telephone));
        }
        clientAdapter = new ClientAdapter(ClientActivity.this, client);
        lv.setAdapter(clientAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                EditClient(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.client_list_lay_id)).getText().toString()));
            }
        });
        query.close();
        db.close();
    }
}
