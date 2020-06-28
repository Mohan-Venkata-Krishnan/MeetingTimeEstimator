package com.app.meetingtimeestimator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.meetingtimeestimator.R;
import com.app.meetingtimeestimator.visualizer.TotalConfirmedWorkingHoursVisualizer;
import com.app.meetingtimeestimator.visualizer.TotalTimeVisualizer;
import com.app.meetingtimeestimator.visualizer.TotalWorkingHoursVisualizer;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<BaseModel> dataSet;
    Context mContext;

    public MultiViewTypeAdapter(ArrayList<BaseModel> data, Context mContext) {
        this.dataSet = data;
        this.mContext = mContext;
    }

    public static class MeterTypeViewHolder extends RecyclerView.ViewHolder {
        TubeSpeedometer speedometer;

        public MeterTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.speedometer = itemView.findViewById(R.id.speedMeter);
        }
    }

    public static class BarChartTypeViewHolder extends RecyclerView.ViewHolder {
        BarChart barChart;

        public BarChartTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.barChart = itemView.findViewById(R.id.bar_chart_type);
        }
    }

    public static class LineChartTypeHolder extends RecyclerView.ViewHolder {
        LineChart lineChart;

        public LineChartTypeHolder(@NonNull View itemView) {
            super(itemView);
            this.lineChart = itemView.findViewById(R.id.line_chart_type);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case BaseModel.METER_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meter_type, parent, false);
                int width = parent.findViewById(R.id.recyclerView).getWidth();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = (int) (width * 0.9);
                view.setLayoutParams(params);
                return new MeterTypeViewHolder(view);
            }

            case BaseModel.BAR_CHART_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barchart_type, parent, false);
                return new BarChartTypeViewHolder(view);
            }

            case BaseModel.LINE_CHART_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linechart_type, parent, false);
                return new LineChartTypeHolder(view);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int listPosition) {
        BaseModel baseModel = dataSet.get(listPosition);
        if (baseModel != null) {
            switch (baseModel.type) {
                case BaseModel.METER_TYPE: {
                    TextView textView = holder.itemView.findViewById(R.id.total_time_spent_text);
                    TotalWorkingHoursVisualizer.renderChartData
                            (((MeterTypeViewHolder) holder).speedometer,
                                    baseModel.speedMeterModel.getTotalWorkingHours(),
                                    textView,
                                    baseModel.speedMeterModel.getDefaultAcceptanceCriteria(),
                                    baseModel.speedMeterModel.getConfirmedMeetingTimeInHours());
                    break;
                }
                case BaseModel.BAR_CHART_TYPE: {
                    TextView textView = holder.itemView.findViewById(R.id.top_time_events_text);
                    TotalConfirmedWorkingHoursVisualizer.renderChartData(
                            ((BarChartTypeViewHolder) holder).barChart,
                            textView,
                            baseModel.barChartModel.isDefaultAcceptanceCriteria(),
                            baseModel.barChartModel.getMaxXAxisEvents(),
                            baseModel.barChartModel.getCalendarNameCountMap());
                    break;
                }
                case BaseModel.LINE_CHART_TYPE: {
                    TextView textView = holder.itemView.findViewById(R.id.top_time_text);
                    TotalTimeVisualizer.renderChartData(
                            ((LineChartTypeHolder) holder).lineChart,
                            textView,
                            baseModel.lineChartModel.isDefaultAcceptanceCriteria(),
                            baseModel.lineChartModel.getCalendarTimeMap());
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type) {
            case 0:
                return BaseModel.METER_TYPE;
            case 1:
                return BaseModel.BAR_CHART_TYPE;
            case 2:
                return BaseModel.LINE_CHART_TYPE;
            default:
                return -1;
        }
    }
}
