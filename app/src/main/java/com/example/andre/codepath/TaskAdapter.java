package com.example.andre.codepath;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Andre on 14/02/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    private final Context context;
    private final ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, -1, tasks);
        this.context = context;
        this.tasks = tasks;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_item, parent, false);

        TextView tvTaskName = (TextView) view.findViewById(R.id.tvItemTaskName);
        TextView tvItemTaskDate = (TextView) view.findViewById(R.id.tvItemTaskDate);
        TextView tvNotes = (TextView) view.findViewById(R.id.tvNotes);

        View vPriority = view.findViewById(R.id.vPriority);

        final Task task = tasks.get(position);
        tvTaskName.setText(task.name);
        tvNotes.setText(task.notes);
        tvItemTaskDate.setText(task.dueYear + "-" + task.dueMonth + "-" + task.dueDay);
        if (task.priority == 0) {
            vPriority.setBackgroundColor(Color.GREEN);
        } else if (task.priority == 1) {
            vPriority.setBackgroundColor(Color.YELLOW);
        } else if (task.priority == 2) {
            vPriority.setBackgroundColor(Color.RED);
        }

        Button btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", position+"");
                Intent i = new Intent(context, EditItemActivity.class);
                i.putExtra("taskPosition", position);
                context.startActivity(i);
            }
        });

        Button btnDone = (Button) view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context appContext = context.getApplicationContext();
                CharSequence text = "Task: " + task.name + " marked as done";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(appContext, text, duration);
                toast.show();

                Log.d("delete", position+"");
                TaskDB.remove(position);
                ((MainActivity) context).taskAdapter.notifyDataSetChanged();
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(context)
                    .setTitle("Title")
                    .setMessage("Do you really want to delete task \"" + task.name + "\"?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Context appContext = context.getApplicationContext();
                            CharSequence text = "Task: " + task.name + " has been deleted";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(appContext, text, duration);
                            toast.show();

                            Log.d("delete", position+"");
                            TaskDB.remove(position);
                            ((MainActivity) context).taskAdapter.notifyDataSetChanged();
                        }})
                    .setNegativeButton("No", null).show();
            }
        });

        return view;
    }
}