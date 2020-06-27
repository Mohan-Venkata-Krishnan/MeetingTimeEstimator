package com.app.meetingtimeestimator.dto;

import java.util.HashMap;

public class CalendarEventEstimatorDTO {

    public long totalMeetingTimeInMillis;
    public long confirmedMeetingTimeInMillis;
    HashMap<String, Integer> totalCalendarNameCountMap;
    HashMap<String, Integer> confirmedCalendarNameCountMap;


    public CalendarEventEstimatorDTO() {
    }

    public long getTotalMeetingTimeInMillis() {
        return totalMeetingTimeInMillis;
    }

    public void setTotalMeetingTimeInMillis(long totalMeetingTimeInMillis) {
        this.totalMeetingTimeInMillis = totalMeetingTimeInMillis;
    }

    public long getConfirmedMeetingTimeInMillis() {
        return confirmedMeetingTimeInMillis;
    }

    public void setConfirmedMeetingTimeInMillis(long confirmedMeetingTimeInMillis) {
        this.confirmedMeetingTimeInMillis = confirmedMeetingTimeInMillis;
    }

    public HashMap<String, Integer> getTotalCalendarNameCountMap() {
        return totalCalendarNameCountMap;
    }

    public void setTotalCalendarNameCountMap(HashMap<String, Integer> totalCalendarNameCountMap) {
        this.totalCalendarNameCountMap = totalCalendarNameCountMap;
    }

    public HashMap<String, Integer> getConfirmedCalendarNameCountMap() {
        return confirmedCalendarNameCountMap;
    }

    public void setConfirmedCalendarNameCountMap(HashMap<String, Integer> confirmedCalendarNameCountMap) {
        this.confirmedCalendarNameCountMap = confirmedCalendarNameCountMap;
    }
}
