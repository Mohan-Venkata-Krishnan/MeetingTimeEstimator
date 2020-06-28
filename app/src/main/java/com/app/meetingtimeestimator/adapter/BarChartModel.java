package com.app.meetingtimeestimator.adapter;

import java.util.HashMap;

public class BarChartModel {

    public Integer maxXAxisEvents;
    public boolean defaultAcceptanceCriteria;
    public HashMap<String, Integer> calendarNameCountMap;

    public BarChartModel() {
    }

    public Integer getMaxXAxisEvents() {
        return maxXAxisEvents;
    }

    public void setMaxXAxisEvents(Integer maxXAxisEvents) {
        this.maxXAxisEvents = maxXAxisEvents;
    }

    public boolean isDefaultAcceptanceCriteria() {
        return defaultAcceptanceCriteria;
    }

    public void setDefaultAcceptanceCriteria(boolean defaultAcceptanceCriteria) {
        this.defaultAcceptanceCriteria = defaultAcceptanceCriteria;
    }

    public HashMap<String, Integer> getCalendarNameCountMap() {
        return calendarNameCountMap;
    }

    public void setCalendarNameCountMap(HashMap<String, Integer> calendarNameCountMap) {
        this.calendarNameCountMap = calendarNameCountMap;
    }
}
