package com.app.meetingtimeestimator.listeners.button;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.meetingtimeestimator.R;
import com.app.meetingtimeestimator.adapter.BarChartModel;
import com.app.meetingtimeestimator.adapter.BaseModel;
import com.app.meetingtimeestimator.adapter.LineChartModel;
import com.app.meetingtimeestimator.adapter.MultiViewTypeAdapter;
import com.app.meetingtimeestimator.adapter.SpeedMeterModel;
import com.app.meetingtimeestimator.dto.CalendarEventEstimatorDTO;
import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;

import java.util.ArrayList;


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

        CardView cardView1 = activity.findViewById(R.id.card_view_1);
        LinearLayout linearLayoutCard = activity.findViewById(R.id.card_view_layout);

        TextView totalMeetings = activity.findViewById(R.id.total_meetings_text);
        TextView totalAcceptedMeetings = activity.findViewById(R.id.total_accepted_meetings_text);
        TextView totalDeclinedMeetings = activity.findViewById(R.id.total_declined_meetings_text);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long defaultDailyHours = Long.parseLong(settings.getString("default_daily_hours", "8"));
        Integer maxXAxisEvents = Integer.parseInt(settings.getString("max_x_axis_events", "10"));
        Boolean defaultAcceptanceCriteria = settings.getBoolean("default_acceptance_criteria", true);

        Long totalMeetingTimeInHours = calendarEventEstimatorDTO.getTotalMeetingTimeInMillis() / (1000 * 60);
        float confirmedMeetingTimeInHours = calendarEventEstimatorDTO.getConfirmedMeetingTimeInMillis() / (1000f * 60f * 60f);
        float totalTimeInHours = (processCalendarEventResource.getToDateInMillis() - processCalendarEventResource.getFromDateInMillis()) / (1000f * 60f * 60f);

        cardView1.setVisibility(View.GONE);
        linearLayoutCard.setVisibility(View.GONE);

        ArrayList<BaseModel> dataList = new ArrayList();
        if (totalMeetingTimeInHours <= 0 || confirmedMeetingTimeInHours <= 0) {
            setRecycleViewAdapter(dataList);
            return;
        }

        float totalDays = totalTimeInHours / 24f;
        float totalWorkingHours = totalDays * defaultDailyHours;
        SpeedMeterModel speedMeterModel = new SpeedMeterModel();
        speedMeterModel.setTotalWorkingHours(totalWorkingHours);
        speedMeterModel.setDefaultAcceptanceCriteria(defaultAcceptanceCriteria);
        speedMeterModel.setConfirmedMeetingTimeInHours(confirmedMeetingTimeInHours);
        dataList.add(new BaseModel(BaseModel.METER_TYPE, speedMeterModel));

        if (!defaultAcceptanceCriteria && null != calendarEventEstimatorDTO.getTotalCalendarTimeMap() && calendarEventEstimatorDTO.getTotalCalendarTimeMap().size() > 0) {
            cardView1.setVisibility(View.VISIBLE);
            linearLayoutCard.setVisibility(View.VISIBLE);
            LineChartModel lineChartModel = new LineChartModel();
            lineChartModel.setDefaultAcceptanceCriteria(defaultAcceptanceCriteria);
            lineChartModel.setCalendarTimeMap(calendarEventEstimatorDTO.getTotalCalendarTimeMap());
            totalMeetings.setText("Total Meetings Scheduled : " + calendarEventEstimatorDTO.getTotalNoOfMeetings());
            totalAcceptedMeetings.setText("Accepted : " + calendarEventEstimatorDTO.getConfirmedNoOfMeetings());
            totalDeclinedMeetings.setText("Declined : " + calendarEventEstimatorDTO.getDeclinedNoOfMeetings());
            dataList.add(new BaseModel(BaseModel.LINE_CHART_TYPE, lineChartModel));
        }

        if (defaultAcceptanceCriteria && null != calendarEventEstimatorDTO.getConfirmedCalendarTimeMap() && calendarEventEstimatorDTO.getConfirmedCalendarTimeMap().size() > 0) {
            cardView1.setVisibility(View.VISIBLE);
            linearLayoutCard.setVisibility(View.VISIBLE);
            LineChartModel lineChartModel = new LineChartModel();
            lineChartModel.setDefaultAcceptanceCriteria(defaultAcceptanceCriteria);
            lineChartModel.setCalendarTimeMap(calendarEventEstimatorDTO.getConfirmedCalendarTimeMap());
            totalMeetings.setText("Total Meetings Scheduled : " + calendarEventEstimatorDTO.getTotalNoOfMeetings());
            totalAcceptedMeetings.setText("Accepted : " + calendarEventEstimatorDTO.getConfirmedNoOfMeetings());
            totalDeclinedMeetings.setText("Declined : " + calendarEventEstimatorDTO.getDeclinedNoOfMeetings());
            dataList.add(new BaseModel(BaseModel.LINE_CHART_TYPE, lineChartModel));
        }

        if (!defaultAcceptanceCriteria && null != calendarEventEstimatorDTO.getTotalCalendarNameCountMap() && calendarEventEstimatorDTO.getTotalCalendarNameCountMap().size() > 0) {
            cardView1.setVisibility(View.VISIBLE);
            linearLayoutCard.setVisibility(View.VISIBLE);
            BarChartModel barChartModel = new BarChartModel();
            barChartModel.setMaxXAxisEvents(maxXAxisEvents);
            barChartModel.setDefaultAcceptanceCriteria(defaultAcceptanceCriteria);
            barChartModel.setCalendarNameCountMap(calendarEventEstimatorDTO.getTotalCalendarNameCountMap());
            totalMeetings.setText("Total Meetings Scheduled : " + calendarEventEstimatorDTO.getTotalNoOfMeetings());
            totalAcceptedMeetings.setText("Accepted : " + calendarEventEstimatorDTO.getConfirmedNoOfMeetings());
            totalDeclinedMeetings.setText("Declined : " + calendarEventEstimatorDTO.getDeclinedNoOfMeetings());
            dataList.add(new BaseModel(BaseModel.BAR_CHART_TYPE, barChartModel));
        }

        if (defaultAcceptanceCriteria && null != calendarEventEstimatorDTO.getConfirmedCalendarNameCountMap() && calendarEventEstimatorDTO.getConfirmedCalendarNameCountMap().size() > 0) {
            cardView1.setVisibility(View.VISIBLE);
            linearLayoutCard.setVisibility(View.VISIBLE);
            BarChartModel barChartModel = new BarChartModel();
            barChartModel.setMaxXAxisEvents(maxXAxisEvents);
            barChartModel.setDefaultAcceptanceCriteria(defaultAcceptanceCriteria);
            barChartModel.setCalendarNameCountMap(calendarEventEstimatorDTO.getConfirmedCalendarNameCountMap());
            totalMeetings.setText("Total Meetings Scheduled : " + calendarEventEstimatorDTO.getTotalNoOfMeetings());
            totalAcceptedMeetings.setText("Accepted : " + calendarEventEstimatorDTO.getConfirmedNoOfMeetings());
            totalDeclinedMeetings.setText("Declined : " + calendarEventEstimatorDTO.getDeclinedNoOfMeetings());
            dataList.add(new BaseModel(BaseModel.BAR_CHART_TYPE, barChartModel));
        }

        setRecycleViewAdapter(dataList);

    }

    private void setRecycleViewAdapter(ArrayList<BaseModel> dataList) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity.getApplicationContext(), OrientationHelper.HORIZONTAL, false);
        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(dataList, activity.getApplicationContext());

        final RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
