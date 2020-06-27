package com.app.meetingtimeestimator.listeners.spinner;

import android.view.View;
import android.widget.LinearLayout;

import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;
import com.app.meetingtimeestimator.utils.FromAndToUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class TimeSlotSpinnerOnItemSelectedListener implements MaterialSpinner.OnItemSelectedListener {

    Object[] allTimeSlots;
    String selectedTimeSlot;
    LinearLayout datePickerLinearLayout;
    ProcessCalendarEventResource processCalendarEventResource;

    public TimeSlotSpinnerOnItemSelectedListener(Object[] allTimeSlots, String selectedTimeSlot, LinearLayout datePickerLinearLayout, ProcessCalendarEventResource processCalendarEventResource) {
        this.allTimeSlots = allTimeSlots;
        this.selectedTimeSlot = selectedTimeSlot;
        this.datePickerLinearLayout = datePickerLinearLayout;
        this.processCalendarEventResource = processCalendarEventResource;
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        selectedTimeSlot = allTimeSlots[view.getSelectedIndex()].toString();
        if (selectedTimeSlot.equals("Set Custom Date")) {
            datePickerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            datePickerLinearLayout.setVisibility(View.GONE);
            FromAndToUtils.setFromAndToDateInMillis(selectedTimeSlot, processCalendarEventResource);
        }
    }

}
