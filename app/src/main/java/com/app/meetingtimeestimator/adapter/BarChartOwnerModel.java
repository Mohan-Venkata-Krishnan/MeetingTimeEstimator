package com.app.meetingtimeestimator.adapter;

import java.util.HashMap;

public class BarChartOwnerModel {

    public boolean defaultAcceptanceCriteria;
    public HashMap<String, Integer> calendarOwnerCountMap;

    public BarChartOwnerModel() {
    }

    public boolean isDefaultAcceptanceCriteria() {
        return defaultAcceptanceCriteria;
    }

    public void setDefaultAcceptanceCriteria(boolean defaultAcceptanceCriteria) {
        this.defaultAcceptanceCriteria = defaultAcceptanceCriteria;
    }

    public HashMap<String, Integer> getCalendarOwnerCountMap() {
        return calendarOwnerCountMap;
    }

    public void setCalendarOwnerCountMap(HashMap<String, Integer> calendarOwnerCountMap) {
        this.calendarOwnerCountMap = calendarOwnerCountMap;
    }
}
