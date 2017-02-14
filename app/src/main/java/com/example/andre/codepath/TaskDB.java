package com.example.andre.codepath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Andre on 14/02/2017.
 */


public class TaskDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "codepath.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TASKS_TABLE_NAME = "tasks";
    private static ArrayList<Task> tasks = new ArrayList<>();

    private static TaskDB instance;


    public TaskDB(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME +
                " (id integer primary key autoincrement, " +
                "name text, " +
                "notes text, " +
                "dueDay int," +
                "dueMonth int, " +
                "dueYear int, " +
                "priority int, " +
                "status int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    public static long insertOrUpdate(Task t) {
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", t.name);
        contentValues.put("notes", t.notes);
        contentValues.put("dueDay", t.dueDay);
        contentValues.put("dueMonth", t.dueMonth);
        contentValues.put("dueYear", t.dueYear);
        contentValues.put("priority", t.priority);
        contentValues.put("status", t.status);

        if (t.id != -1) {   // if the task does have an id its already in the db
            return db.update(TASKS_TABLE_NAME, contentValues, "id=" + t.id, null);
        } else {    // new task => insert
            int ret = (int) db.insert(TASKS_TABLE_NAME, null, contentValues);
            t.id = ret;
            tasks.add(t);
            return ret;
        }
    }

    public static ArrayList<Task> readAll() {
        SQLiteDatabase db = instance.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " ORDER BY id ASC", null);

        int numRes = res.getCount();
        tasks = new ArrayList<>(numRes);
        if (numRes > 0) {
            res.moveToFirst();
            int col_id = res.getColumnIndex("id"),
                col_name = res.getColumnIndex("name"),
                col_notes = res.getColumnIndex("notes"),
                col_dueDay = res.getColumnIndex("dueDay"),
                col_dueMonth = res.getColumnIndex("dueMonth"),
                col_dueYear = res.getColumnIndex("dueYear"),
                col_priority = res.getColumnIndex("priority"),
                col_status = res.getColumnIndex("status");


            for (int i = 0; i < numRes; i++) {
                Task t = new Task();
                t.id = res.getInt(col_id);
                t.name = res.getString(col_name);
                t.notes = res.getString(col_notes);
                t.dueDay = res.getInt(col_dueDay);
                t.dueMonth = res.getInt(col_dueMonth);
                t.dueYear = res.getInt(col_dueYear);
                t.priority = res.getInt(col_priority);
                t.status = res.getInt(col_status);
                tasks.add(i, t);
                res.moveToNext();
            }
        }
        return tasks;
    }

    public static int remove(int position) {
        int id = tasks.get(position).id;
        tasks.remove(position);
        SQLiteDatabase db = instance.getReadableDatabase();
        return db.delete(TASKS_TABLE_NAME, "id=" + id, null);
    }

    public static Task getTask(int position) {
        return tasks.get(position);
    }
}