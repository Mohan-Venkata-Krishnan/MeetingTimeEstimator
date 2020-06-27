package com.app.meetingtimeestimator.listeners.datePicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;

import java.util.Calendar;

public class DatePickerOnClickListener implements View.OnClickListener {

    Context context;
    Calendar toCalendar;
    DatePickerDialog.OnDateSetListener toDateSetListener;

    public DatePickerOnClickListener(Context context, Calendar toCalendar, DatePickerDialog.OnDateSetListener toDateSetListener) {
        this.context = context;
        this.toCalendar = toCalendar;
        this.toDateSetListener = toDateSetListener;
    }

    @Override
    public void onClick(View v) {
        new DatePickerDialog(context, toDateSetListener, toCalendar.get(Calendar.YEAR), toCalendar.get(Calendar.MONTH), toCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
