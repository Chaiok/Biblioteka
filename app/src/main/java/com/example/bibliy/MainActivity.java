package com.example.bibliy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS client " +
                "(id integer primary key autoincrement, first_name TEXT, last_name TEXT, middle_name TEXT, telephone TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS librarian " +
                "(id integer primary key autoincrement, first_name TEXT, last_name TEXT, middle_name TEXT, telephone TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS author " +
                "(id integer primary key autoincrement, first_name TEXT, last_name TEXT, middle_name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS publishing_house " +
                "(id integer primary key autoincrement, publisher_name TEXT, city_full TEXT, city_less TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS book " +
                "(id integer primary key autoincrement, book_name TEXT, genre TEXT, year_of_publishing TEXT, " +
                "pages INTEGER, id_author INTEGER, id_publishing_house INTEGER, " +
                "FOREIGN KEY (id_author) REFERENCES author (id), " +
                "FOREIGN KEY (id_publishing_house) REFERENCES publishing_house (id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS logbook " +
                "(id integer primary key autoincrement, log TEXT, id_client INTEGER, id_book INTEGER, id_librarian INTEGER, " +
                "FOREIGN KEY (id_client) REFERENCES client (id), " +
                "FOREIGN KEY (id_book) REFERENCES book (id), " +
                "FOREIGN KEY (id_librarian) REFERENCES librarian (id))");
        //select
        /*
        Cursor query = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", "logbook"});
        TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        if (!query.moveToFirst())
        {
            //headerView.setText("false");
        }
        else{
            int count = query.getInt(0);
            //headerView.setText(String.valueOf(count));
        }
        */
        //insert client+id
        /*
        ContentValues values = new ContentValues();
        values.put("first_name", "Vasy");
        values.put("last_name", "Pupkin");
        values.put("middle_name", "Pupkevich");
        values.put("telephone", "+6(863)27-45");
        long newRowId = db.insert("client", null, values);
        headerView.setText(String.valueOf(newRowId));
        */
        //insert logbook+id+FK
        /*
        ContentValues values = new ContentValues();
        values.put("log", "Vasy");
        values.put("id_client", 1);
        values.put("id_book", 2);
        values.put("id_librarian", 3);
        //db.execSQL("PRAGMA foreign_keys=ON"); //при каждой сесии надо включать
        long newRowId = db.insert("logbook", null, values);
        headerView.setText(String.valueOf(newRowId));
        */
        //context delete db
        /*
        Context context =  this;
        context.deleteDatabase("app.db");
        */
        //query.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        Intent intent;
        switch (id) {
            case R.id.logbook_settings:
                //headerView.setText("Журнал учета");
                intent = new Intent(this, LogbookActivity.class);
                startActivity(intent);
                return true;
            case R.id.handbook_settings:
                //headerView.setText("Справочники");
                intent = new Intent(this, HandbookActivity.class);
                startActivity(intent);
                return true;
            case R.id.reports_settings:
                //headerView.setText("Отчеты");
                return true;
            case R.id.about_the_program_settings:
                //headerView.setText("О программе");
                return true;
            case R.id.exit_settings:
                //headerView.setText("Выход");
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }
}