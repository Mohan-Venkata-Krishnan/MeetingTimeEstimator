package com.app.meetingtimeestimator.resource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;

import com.app.meetingtimeestimator.dto.CalendarEventEstimatorDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.app.meetingtimeestimator.constants.CalendarURIConstants.WHEN_INSTANCES_URI;

public class ProcessCalendarEventResource {

    ContentResolver contentResolver;
    String selectedCalendarId;
    Long fromDateInMillis;
    Long toDateInMillis;

    public ProcessCalendarEventResource(ContentResolver contentResolver, String selectedCalendarId, Long fromDateInMillis, Long toDateInMillis) {
        this.contentResolver = contentResolver;
        this.selectedCalendarId = selectedCalendarId;
        this.fromDateInMillis = fromDateInMillis;
        this.toDateInMillis = toDateInMillis;
    }

    public CalendarEventEstimatorDTO getAllEventData(String id, Context context) {

        int totalNoOfMeetings = 0;
        int confirmedNoOfMeetings = 0;
        int declinedNoOfMeetings = 0;

        HashMap<String, Integer> totalCalendarNameCountMap = new HashMap<>();
        HashMap<String, Integer> confirmedCalendarNameCountMap = new HashMap<>();
        LinkedHashMap<String, Float> confirmedCalendarTimeMap = new LinkedHashMap<>();
        LinkedHashMap<String, Float> totalCalendarTimeMap = new LinkedHashMap<>();

        Uri.Builder builder = WHEN_INSTANCES_URI.buildUpon();

        ContentUris.appendId(builder, fromDateInMillis);
        ContentUris.appendId(builder, toDateInMillis);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean defaultAcceptanceCriteria = settings.getBoolean("default_acceptance_criteria", true);

        Cursor eventCursor = contentResolver.query(builder.build(),
                new String[]{"title", "begin", "end", "selfAttendeeStatus"}, CalendarContract.Instances.CALENDAR_ID + "=" + id,
                null, "startDay ASC, startMinute ASC");

        long totalMeetingTimeInMillis = -1L;
        long confirmedMeetingTimeInMillis = 1L;
        System.out.println("eventCursor count=" + eventCursor.getCount());
        if (eventCursor.getCount() > 0) {
            if (eventCursor.moveToFirst()) {
                do {

                    String title = eventCursor.getString(0);
                    String beginTimeStr = eventCursor.getString(1);
                    String endTimeStr = eventCursor.getString(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(beginTimeStr));
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDate = format.format(calendar.getTime());

                    long beginTimeInMillis = Long.parseLong(beginTimeStr);
                    long endTimeInMillis = Long.parseLong(endTimeStr);

                    if (totalCalendarNameCountMap.containsKey(title)) {
                        int currentValue = totalCalendarNameCountMap.get(title);
                        totalCalendarNameCountMap.put(title, ++currentValue);
                    } else {
                        totalCalendarNameCountMap.put(title, 1);
                    }

                    Float meetingTime = (endTimeInMillis - beginTimeInMillis) / (1000f * 60f * 60f);
                    if (totalCalendarTimeMap.containsKey(currentDate)) {
                        float currentValue = totalCalendarTimeMap.get(currentDate);
                        totalCalendarTimeMap.put(currentDate, currentValue + meetingTime);
                    } else {
                        totalCalendarTimeMap.put(currentDate, meetingTime);
                    }

                    int selfAttendeeStatus = Integer.parseInt(eventCursor.getString(3));
                    if (selfAttendeeStatus == CalendarContract.Instances.STATUS_CONFIRMED) {
                        confirmedMeetingTimeInMillis += (endTimeInMillis - beginTimeInMillis);
                        if (confirmedCalendarNameCountMap.containsKey(title)) {
                            int currentValue = confirmedCalendarNameCountMap.get(title);
                            confirmedCalendarNameCountMap.put(title, ++currentValue);
                        } else {
                            confirmedCalendarNameCountMap.put(title, 1);
                        }
                        if (confirmedCalendarTimeMap.containsKey(currentDate)) {
                            float currentValue = confirmedCalendarTimeMap.get(currentDate);
                            confirmedCalendarTimeMap.put(currentDate, currentValue + meetingTime);
                        } else {
                            confirmedCalendarTimeMap.put(currentDate, meetingTime);
                        }

                        confirmedNoOfMeetings++;
                    }
                    if(selfAttendeeStatus == CalendarContract.Instances.STATUS_CANCELED) {
                        declinedNoOfMeetings++;
                    }
                    totalNoOfMeetings++;
                    totalMeetingTimeInMillis += (endTimeInMillis - beginTimeInMillis);
                } while (eventCursor.moveToNext());
            }
        }
        if (!defaultAcceptanceCriteria) {
            confirmedMeetingTimeInMillis = totalMeetingTimeInMillis;
        }
        CalendarEventEstimatorDTO calendarEventEstimatorDTO = new CalendarEventEstimatorDTO();
        calendarEventEstimatorDTO.setTotalMeetingTimeInMillis(totalMeetingTimeInMillis);
        calendarEventEstimatorDTO.setTotalNoOfMeetings(totalNoOfMeetings);
        calendarEventEstimatorDTO.setConfirmedNoOfMeetings(confirmedNoOfMeetings);
        calendarEventEstimatorDTO.setDeclinedNoOfMeetings(declinedNoOfMeetings);
        calendarEventEstimatorDTO.setConfirmedMeetingTimeInMillis(confirmedMeetingTimeInMillis);
        calendarEventEstimatorDTO.setConfirmedCalendarNameCountMap(confirmedCalendarNameCountMap);
        calendarEventEstimatorDTO.setTotalCalendarNameCountMap(totalCalendarNameCountMap);
        calendarEventEstimatorDTO.setConfirmedCalendarTimeMap(confirmedCalendarTimeMap);
        calendarEventEstimatorDTO.setTotalCalendarTimeMap(totalCalendarTimeMap);
        return calendarEventEstimatorDTO;
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Long getFromDateInMillis() {
        return fromDateInMillis;
    }

    public void setFromDateInMillis(Long fromDateInMillis) {
        this.fromDateInMillis = fromDateInMillis;
    }

    public Long getToDateInMillis() {
        return toDateInMillis;
    }

    public String getSelectedCalendarId() {
        return selectedCalendarId;
    }

    public void setSelectedCalendarId(String selectedCalendarId) {
        this.selectedCalendarId = selectedCalendarId;
    }

    public void setToDateInMillis(Long toDateInMillis) {
        this.toDateInMillis = toDateInMillis;
    }
}
