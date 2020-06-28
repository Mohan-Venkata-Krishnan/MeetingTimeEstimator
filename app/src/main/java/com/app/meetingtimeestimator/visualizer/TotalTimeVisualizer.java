package com.app.meetingtimeestimator.visualizer;

import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TotalTimeVisualizer {

    public static void renderChartData(LineChart lineChart,
                                       TextView totalHoursText,
                                       boolean defaultAcceptanceCriteria,
                                       LinkedHashMap<String, Float> calendarTimeMap) {
        if (defaultAcceptanceCriteria) {
            totalHoursText.setText("Total Hours Consumed - Accepted Events");
        } else {
            totalHoursText.setText("Total Hours Consumed - Overall Events");
        }
        totalHoursText.setVisibility(View.VISIBLE);
        ArrayList<Entry> lineEntries = new ArrayList<>();
        ArrayList<String> xAxisLabels = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Float> entrySet : calendarTimeMap.entrySet()) {
            if (entrySet.getKey().length() > 20) {
                String subStringKey = entrySet.getKey().substring(0, 19);
                xAxisLabels.add(subStringKey);
            } else {
                xAxisLabels.add(entrySet.getKey());
            }
            lineEntries.add(new Entry(count++, entrySet.getValue()));
        }
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelCount(lineEntries.size());
        if (lineEntries.size() > 15) {
            xAxis.setLabelCount(15);
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, null);
        lineDataSet.setValueFormatter(new DefaultValueFormatter(1));
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setDescription(null);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraTopOffset(10f);
        lineChart.animateXY(1000, 1000);
    }


}
