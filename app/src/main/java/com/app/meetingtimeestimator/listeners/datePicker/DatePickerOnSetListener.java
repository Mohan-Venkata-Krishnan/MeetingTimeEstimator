package com.app.meetingtimeestimator.listeners.datePicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerOnSetListener implements DatePickerDialog.OnDateSetListener {

    String format = "yyyy-MM-dd HH:mm";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

    boolean isFromDate;
    Context context;
    Calendar calendar;
    EditText datePicker;
    ProcessCalendarEventResource processCalendarEventResource;

    public DatePickerOnSetListener(boolean isFromDate, Context context, Calendar calendar, EditText datePicker, ProcessCalendarEventResource processCalendarEventResource) {
        this.isFromDate = isFromDate;
        this.context = context;
        this.calendar = calendar;
        this.datePicker = datePicker;
        this.processCalendarEventResource = processCalendarEventResource;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog fromTimePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        datePicker.setText(sdf.format(calendar.getTime()));
                        if (isFromDate) {
                            processCalendarEventResource.setFromDateInMillis(calendar.getTimeInMillis());
                        } else {
                            processCalendarEventResource.setToDateInMillis(calendar.getTimeInMillis());
                        }
                    }
                }, currentHour, currentMinute, false);
        fromTimePickerDialog.show();
    }
}
