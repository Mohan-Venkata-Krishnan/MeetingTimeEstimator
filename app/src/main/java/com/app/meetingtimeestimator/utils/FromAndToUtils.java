package com.app.meetingtimeestimator.utils;

import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;

import java.util.Calendar;
import java.util.Date;

public class FromAndToUtils {

    public static void setFromAndToDateInMillis(String timeSlot, ProcessCalendarEventResource processCalendarEventResource) {
        if (null != timeSlot) {
            switch (timeSlot) {
                default:
                case "Today": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }

                case "Tomorrow": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    calendar.add(Calendar.DATE, 1);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }

                case "Yesterday": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    calendar.add(Calendar.DATE, -1);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }

                case "Last Week": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
                    calendar.add(Calendar.DATE, -i - 7);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    calendar.add(Calendar.DATE, 6);
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }

                case "Current Week": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
                    calendar.add(Calendar.DATE, -i + 1);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    calendar.add(Calendar.DATE, 6);
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }

                case "Last 30 Days": {
                    Long currentTimeInMillis = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(currentTimeInMillis);
                    int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
                    calendar.add(Calendar.DATE, -i + 1);
                    processCalendarEventResource.setFromDateInMillis(atStartOfDay(calendar.getTime()).getTime());
                    calendar.add(Calendar.DATE, 29);
                    processCalendarEventResource.setToDateInMillis(atEndOfDay(calendar.getTime()).getTime());
                    break;
                }
            }
        }
    }

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
