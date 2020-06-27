package com.app.meetingtimeestimator.constants;

import android.net.Uri;
import android.provider.CalendarContract;

public class CalendarURIConstants {

    public static final Uri GET_CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");
    public static final String[] GET_CALENDAR_URI_FIELDS = {CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,};
    public static final Uri WHEN_INSTANCES_URI = Uri.parse("content://com.android.calendar/instances/when");

}
