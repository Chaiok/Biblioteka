package com.example.bibliy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class About_the_programActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_program);

        TextView t1 = (TextView) findViewById(R.id.about_the_program_author);
        TextView t2 = (TextView) findViewById(R.id.about_the_program_program);
        t1.setText("Шакура Артур Станиславович");
        t2.setText("Приложение для учета книг в библиотеке");
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
}
