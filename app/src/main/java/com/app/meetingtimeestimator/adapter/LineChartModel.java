package com.app.meetingtimeestimator.adapter;

import java.util.LinkedHashMap;

public class LineChartModel {

    public boolean defaultAcceptanceCriteria;
    public LinkedHashMap<String, Float> calendarTimeMap;

    public LineChartModel() {
    }

    public boolean isDefaultAcceptanceCriteria() {
        return defaultAcceptanceCriteria;
    }

    public void setDefaultAcceptanceCriteria(boolean defaultAcceptanceCriteria) {
        this.defaultAcceptanceCriteria = defaultAcceptanceCriteria;
    }

    public LinkedHashMap<String, Float> getCalendarTimeMap() {
        return calendarTimeMap;
    }

    public void setCalendarTimeMap(LinkedHashMap<String, Float> calendarTimeMap) {
        this.calendarTimeMap = calendarTimeMap;
    }
}
