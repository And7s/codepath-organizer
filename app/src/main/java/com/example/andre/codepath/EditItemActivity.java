package com.example.andre.codepath;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView tvDueDate;
    private EditText etTaskNotes, etTaskName;
    private Spinner prioritySpinner;

    private Task task = new Task();

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }
    public void save(MenuItem item) {
        task.name = etTaskName.getText().toString();
        task.notes = etTaskNotes.getText().toString();
        task.priority = prioritySpinner.getSelectedItemPosition();
        task.status = 1;
        TaskStorage.tasks.add(task);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();

        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        prioritySpinner.setAdapter(adapter);

        String s = intent.getStringExtra("todo");
        Log.d("asd", s);
        inflateWithTask();
    }

    private void inflateWithTask() {
        tvDueDate.setText(task.dueYear + "-" + task.dueMonth + "-" + task.dueDay);
        etTaskName.setText(task.name);
        etTaskNotes.setText(task.notes);
    }

    public void selectDate(View v) {
        DatePickerDialog dialog = new DatePickerDialog(this, this, task.dueYear, task.dueMonth - 1, task.dueDay);
        dialog.show();

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        tvDueDate.setText(year + "-" + (monthOfYear + 1)+ "-" + dayOfMonth);
        task.dueDay = dayOfMonth;
        task.dueMonth = monthOfYear + 1;
        task.dueYear = year;
    }
}
