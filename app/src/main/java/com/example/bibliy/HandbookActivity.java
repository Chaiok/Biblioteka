package com.example.bibliy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HandbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handbook);
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

    public void startActivityClient(View view){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void startActivityLibrarian(View view){
        Intent intent = new Intent(this, LibrarianActivity.class);
        startActivity(intent);
    }

    public void startActivityBook(View view){
        Intent intent = new Intent(this, BookActivity.class);
        startActivity(intent);
    }
}
