package com.app.meetingtimeestimator.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CalendarEventEstimatorDTO {

    public long totalMeetingTimeInMillis;
    public long confirmedMeetingTimeInMillis;
    public int totalNoOfMeetings;
    public int confirmedNoOfMeetings;
    public int declinedNoOfMeetings;
    HashMap<String, Integer> totalCalendarNameCountMap;
    HashMap<String, Integer> confirmedCalendarNameCountMap;
    LinkedHashMap<String, Float> confirmedCalendarTimeMap;
    LinkedHashMap<String, Float> totalCalendarTimeMap;


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

    public int getTotalNoOfMeetings() {
        return totalNoOfMeetings;
    }

    public void setTotalNoOfMeetings(int totalNoOfMeetings) {
        this.totalNoOfMeetings = totalNoOfMeetings;
    }

    public int getConfirmedNoOfMeetings() {
        return confirmedNoOfMeetings;
    }

    public void setConfirmedNoOfMeetings(int confirmedNoOfMeetings) {
        this.confirmedNoOfMeetings = confirmedNoOfMeetings;
    }

    public HashMap<String, Integer> getTotalCalendarNameCountMap() {
        return totalCalendarNameCountMap;
    }

    public int getDeclinedNoOfMeetings() {
        return declinedNoOfMeetings;
    }

    public void setDeclinedNoOfMeetings(int declinedNoOfMeetings) {
        this.declinedNoOfMeetings = declinedNoOfMeetings;
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

    public LinkedHashMap<String, Float> getConfirmedCalendarTimeMap() {
        return confirmedCalendarTimeMap;
    }

    public void setConfirmedCalendarTimeMap(LinkedHashMap<String, Float> confirmedCalendarTimeMap) {
        this.confirmedCalendarTimeMap = confirmedCalendarTimeMap;
    }

    public LinkedHashMap<String, Float> getTotalCalendarTimeMap() {
        return totalCalendarTimeMap;
    }

    public void setTotalCalendarTimeMap(LinkedHashMap<String, Float> totalCalendarTimeMap) {
        this.totalCalendarTimeMap = totalCalendarTimeMap;
    }
}
