package com.app.meetingtimeestimator.resource;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import java.util.HashMap;

import static com.app.meetingtimeestimator.constants.CalendarURIConstants.GET_CALENDAR_URI;
import static com.app.meetingtimeestimator.constants.CalendarURIConstants.GET_CALENDAR_URI_FIELDS;


public class GetCalendarEventResource {

    ContentResolver contentResolver;
    HashMap<String, String> calendars = new HashMap<>();

    public GetCalendarEventResource(Context ctx) {
        contentResolver = ctx.getContentResolver();
    }

    public HashMap<String, String> getCalendars() {
        // Fetch a list of all calendars sync'd with the device and their display names
        Cursor cursor = contentResolver.query(GET_CALENDAR_URI, GET_CALENDAR_URI_FIELDS, CalendarContract.Calendars.IS_PRIMARY + "=1", null, null);
        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String displayName = cursor.getString(1);
                    calendars.put(displayName, id);
                }
            }
        } catch (AssertionError ex) { /*TODO: log exception and bail*/ }

        return calendars;
    }
}
