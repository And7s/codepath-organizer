package com.example.andre.codepath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Task> taskAdapter;
    ListView lvItems;

    private int filterId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        new TaskDB(this);
        getData();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.actionbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setTitle("");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filter0:
                filterId = 0;
                break;
            case R.id.filter1:
                filterId = 1;
               break;
            case R.id.filter2:
                filterId = 2;
                break;
            case R.id.filter3:
                filterId = 3;
                break;
        }
        Log.d("Filter", "is now "+ filterId);
        getData();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }

    public void getData() {
        ArrayList<Task> tasks = TaskDB.readAll(filterId);
        taskAdapter = new TaskAdapter(this, tasks);
        lvItems.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    public void addNewTask(View v) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("todo", "asd");
        startActivity(i);
    }
}
