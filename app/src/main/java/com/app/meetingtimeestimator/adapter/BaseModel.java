package com.app.meetingtimeestimator.adapter;

public class BaseModel {

    public static final int METER_TYPE = 0;
    public static final int BAR_CHART_TYPE = 1;
    public static final int LINE_CHART_TYPE = 2;

    public int type;
    public SpeedMeterModel speedMeterModel;
    public BarChartModel barChartModel;
    public LineChartModel lineChartModel;

    public BaseModel(int type, SpeedMeterModel speedMeterModel) {
        this.type = type;
        this.speedMeterModel = speedMeterModel;
    }

    public BaseModel(int type, BarChartModel barChartModel) {
        this.type = type;
        this.barChartModel = barChartModel;
    }

    public BaseModel(int type, LineChartModel lineChartModel) {
        this.type = type;
        this.lineChartModel = lineChartModel;
    }
}