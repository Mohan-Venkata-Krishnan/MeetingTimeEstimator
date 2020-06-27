package com.app.meetingtimeestimator.listeners.button;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.app.meetingtimeestimator.R;
import com.app.meetingtimeestimator.dto.CalendarEventEstimatorDTO;
import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;
import com.app.meetingtimeestimator.visualizer.TotalConfirmedWorkingHoursVisualizer;
import com.app.meetingtimeestimator.visualizer.TotalEventsVisualizer;
import com.app.meetingtimeestimator.visualizer.TotalWorkingHoursVisualizer;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.github.mikephil.charting.charts.BarChart;

public class CalculateButtonOnClickListener implements View.OnClickListener {

    Context context;
    Activity activity;
    CalendarEventEstimatorDTO calendarEventEstimatorDTO;
    ProcessCalendarEventResource processCalendarEventResource;

    public CalculateButtonOnClickListener(Context context, Activity activity, CalendarEventEstimatorDTO calendarEventEstimatorDTO, ProcessCalendarEventResource processCalendarEventResource) {
        this.context = context;
        this.activity = activity;
        this.calendarEventEstimatorDTO = calendarEventEstimatorDTO;
        this.processCalendarEventResource = processCalendarEventResource;
    }

    @Override
    public void onClick(View v) {
        if (null == processCalendarEventResource.getSelectedCalendarId()) {
            return;
        }
        calendarEventEstimatorDTO = processCalendarEventResource.getAllEventData(processCalendarEventResource.getSelectedCalendarId(), context);
        setDataIntoView(calendarEventEstimatorDTO, processCalendarEventResource);
    }

    // Set Calculated Data
    private void setDataIntoView(CalendarEventEstimatorDTO calendarEventEstimatorDTO, ProcessCalendarEventResource processCalendarEventResource) {

        BarChart linearChart = activity.findViewById(R.id.linear_chart);
        View horizontalLineTwo = activity.findViewById(R.id.horizontal_line_2);
        TextView topTimeEventsText = activity.findViewById(R.id.top_time_events_text);
        TextView totalTimeSpentText = activity.findViewById(R.id.total_time_spent_text);
        BarChart linearConfirmedChart = activity.findViewById(R.id.linear_confirmed_chart);
        TextView topTimeConfirmedEventsText = activity.findViewById(R.id.top_time_confirmed_events_text);
        TubeSpeedometer tubeSpeedometer = activity.findViewById(R.id.confirmed_meeting_time);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long defaultDailyHours = Long.parseLong(settings.getString("default_daily_hours", "8"));
        Integer maxXAxisEvents = Integer.parseInt(settings.getString("max_x_axis_events", "10"));
        Boolean defaultAcceptanceCriteria = settings.getBoolean("default_acceptance_criteria", true);

        tubeSpeedometer.setMinSpeed(0);
        tubeSpeedometer.setUnit("Hour(s)");

        Long totalMeetingTimeInHours = calendarEventEstimatorDTO.getTotalMeetingTimeInMillis() / (1000 * 60);
        float confirmedMeetingTimeInHours = calendarEventEstimatorDTO.getConfirmedMeetingTimeInMillis() / (1000f * 60f * 60f);
        float totalTimeInHours = (processCalendarEventResource.getToDateInMillis() - processCalendarEventResource.getFromDateInMillis()) / (1000f * 60f * 60f);

        if (totalMeetingTimeInHours <= 0 || confirmedMeetingTimeInHours <= 0) {
            TotalWorkingHoursVisualizer.clearChartData(tubeSpeedometer);
            TotalEventsVisualizer.clearChartData(linearChart, topTimeEventsText);
            TotalConfirmedWorkingHoursVisualizer.clearChartData(linearConfirmedChart, topTimeConfirmedEventsText, horizontalLineTwo);
            return;
        }

        float totalDays = totalTimeInHours / 24f;
        float totalWorkingHours = totalDays * defaultDailyHours;
        TotalWorkingHoursVisualizer.renderChartData(tubeSpeedometer, totalWorkingHours, totalTimeSpentText, defaultAcceptanceCriteria, confirmedMeetingTimeInHours);

        if (null != calendarEventEstimatorDTO.getTotalCalendarNameCountMap()) {
            TotalEventsVisualizer.renderChartData(linearChart, topTimeEventsText, maxXAxisEvents, calendarEventEstimatorDTO.getTotalCalendarNameCountMap());
        } else {
            TotalEventsVisualizer.clearChartData(linearChart, topTimeEventsText);
        }

        if (null != calendarEventEstimatorDTO.getConfirmedCalendarNameCountMap()) {
            TotalConfirmedWorkingHoursVisualizer.renderChartData(linearConfirmedChart, topTimeConfirmedEventsText, maxXAxisEvents, horizontalLineTwo, calendarEventEstimatorDTO.getConfirmedCalendarNameCountMap());
        } else {
            TotalConfirmedWorkingHoursVisualizer.clearChartData(linearConfirmedChart, topTimeConfirmedEventsText, horizontalLineTwo);
        }
    }


}
