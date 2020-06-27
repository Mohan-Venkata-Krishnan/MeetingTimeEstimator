package com.app.meetingtimeestimator.visualizer;

import android.view.View;
import android.widget.TextView;

import com.app.meetingtimeestimator.utils.StringUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TotalConfirmedWorkingHoursVisualizer {

    public static void renderChartData(BarChart linearConfirmedChart, TextView topTimeConfirmedEventsText, Integer maxXAxisEvents, View horizontalLineTwo, HashMap<String, Integer> confirmedCalendarNameCountMap) {
        topTimeConfirmedEventsText.setText("Top " + maxXAxisEvents + " Time Consuming Events - Accepted");
        topTimeConfirmedEventsText.setVisibility(View.VISIBLE);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        confirmedCalendarNameCountMap = StringUtils.sortByValue(confirmedCalendarNameCountMap);
        confirmedCalendarNameCountMap = StringUtils.filterBySize(confirmedCalendarNameCountMap, maxXAxisEvents);
        int count = 0;
        for (Map.Entry<String, Integer> entrySet : confirmedCalendarNameCountMap.entrySet()) {
            if (entrySet.getKey().length() > 20) {
                String subStringKey = entrySet.getKey().substring(0, 19);
                xAxisLabels.add(subStringKey);
            } else {
                xAxisLabels.add(entrySet.getKey());
            }
            barEntries.add(new BarEntry(count++, Float.valueOf(entrySet.getValue())));
        }
        XAxis xAxis = linearConfirmedChart.getXAxis();
        xAxis.setLabelCount(barEntries.size());
        xAxis.setLabelRotationAngle(90f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Count of Accepted Events");
        BarData barData = new BarData(barDataSet);
        linearConfirmedChart.setExtraTopOffset(10f);
        linearConfirmedChart.setData(barData);
        linearConfirmedChart.setDescription(null);
        horizontalLineTwo.setVisibility(View.VISIBLE);
        linearConfirmedChart.animateXY(500, 500);
    }

    public static void clearChartData(BarChart linearConfirmedChart, TextView topTimeConfirmedEventsText, View horizontalLineTwo) {
        horizontalLineTwo.setVisibility(View.GONE);
        topTimeConfirmedEventsText.setVisibility(View.GONE);
        linearConfirmedChart.setData(null);
        linearConfirmedChart.invalidate();
    }

}
