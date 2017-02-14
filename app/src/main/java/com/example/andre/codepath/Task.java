package com.example.andre.codepath;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Andre on 13/02/2017.
 */

public class Task {
    int id;
    String name, notes;
    int dueDay, dueMonth, dueYear;
    int priority, status;

    public Task() {
        id = -1;
        Calendar c = new GregorianCalendar();
        dueDay = c.get(Calendar.DAY_OF_MONTH);
        dueMonth = c.get(Calendar.MONTH) + 1;
        dueYear = c.get(Calendar.YEAR);
        priority = 0;
        status = 0;
    }
}
