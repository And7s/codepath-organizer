package com.example.andre.codepath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_item, parent, false);

        TextView tvTaskName = (TextView) view.findViewById(R.id.tvItemTaskName);
        TextView tvItemTaskDate = (TextView) view.findViewById(R.id.tvItemTaskDate);

        Task task = tasks.get(position);
        tvTaskName.setText(task.name);
        tvItemTaskDate.setText(task.dueYear + "-" + task.dueMonth + "-" + task.dueDay);


        return view;
    }
}