package com.example.andre.codepath;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Task> taskAdapter;
    ListView lvItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        new TaskDB(this);
        ArrayList<Task> tasks = TaskDB.readAll();
        taskAdapter = new TaskAdapter(this, tasks);
        lvItems.setAdapter(taskAdapter);


        setUpListViewListener();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.actionbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setTitle("");

    }
    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }

/*
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        taskAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }*/

    private void setUpListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            TaskDB.remove(position);
            taskAdapter.notifyDataSetChanged();
            return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
            i.putExtra("taskPosition", position);
            startActivity(i);
            }
        });
    }

    public void addNewTask(View v) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("todo", "asd");
        startActivity(i);
    }

/*
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}
