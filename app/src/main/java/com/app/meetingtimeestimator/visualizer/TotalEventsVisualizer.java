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

public class TotalEventsVisualizer {

    public static void renderChartData(BarChart linearChart, TextView topTimeEventsText, Integer maxXAxisEvents, HashMap<String, Integer> totalCalendarNameCountMap) {
        topTimeEventsText.setText("Top " + maxXAxisEvents + " Time Consuming Events - Overall");
        topTimeEventsText.setVisibility(View.VISIBLE);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        totalCalendarNameCountMap = StringUtils.sortByValue(totalCalendarNameCountMap);
        totalCalendarNameCountMap = StringUtils.filterBySize(totalCalendarNameCountMap, maxXAxisEvents);
        int count = 0;
        for (Map.Entry<String, Integer> entrySet : totalCalendarNameCountMap.entrySet()) {
            if (entrySet.getKey().length() > 20) {
                String subStringKey = entrySet.getKey().substring(0, 19);
                xAxisLabels.add(subStringKey);
            } else {
                xAxisLabels.add(entrySet.getKey());
            }
            barEntries.add(new BarEntry(count++, Float.valueOf(entrySet.getValue())));
        }
        XAxis xAxis = linearChart.getXAxis();
        xAxis.setLabelCount(barEntries.size());
        xAxis.setLabelRotationAngle(90f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Count of Events");
        BarData barData = new BarData(barDataSet);
        linearChart.setExtraTopOffset(10f);
        linearChart.setData(barData);
        linearChart.setDescription(null);
        linearChart.animateXY(500, 500);
    }

    public static void clearChartData(BarChart linearChart, TextView topTimeEventsText) {
        topTimeEventsText.setVisibility(View.GONE);
        linearChart.setData(null);
        linearChart.invalidate();

    }
}
