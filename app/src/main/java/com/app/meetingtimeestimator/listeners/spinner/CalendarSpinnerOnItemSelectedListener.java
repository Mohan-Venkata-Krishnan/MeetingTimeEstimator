package com.app.meetingtimeestimator.listeners.spinner;

import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.HashMap;

public class CalendarSpinnerOnItemSelectedListener implements MaterialSpinner.OnItemSelectedListener {

    Object[] allCalendars;
    String selectedCalendarName;
    String selectedCalendarId;
    HashMap<String, String> calendarNameIdMap;
    ProcessCalendarEventResource processCalendarEventResource;

    public CalendarSpinnerOnItemSelectedListener(Object[] allCalendars, String selectedCalendarName, String selectedCalendarId, HashMap<String, String> calendarNameIdMap, ProcessCalendarEventResource processCalendarEventResource) {
        this.allCalendars = allCalendars;
        this.selectedCalendarName = selectedCalendarName;
        this.selectedCalendarId = selectedCalendarId;
        this.calendarNameIdMap = calendarNameIdMap;
        this.processCalendarEventResource = processCalendarEventResource;
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        selectedCalendarName = allCalendars[view.getSelectedIndex()].toString();
        selectedCalendarId = calendarNameIdMap.get(selectedCalendarName);
        processCalendarEventResource.setSelectedCalendarId(selectedCalendarId);
    }
}
