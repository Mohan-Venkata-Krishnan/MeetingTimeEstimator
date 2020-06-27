package com.app.meetingtimeestimator.visualizer;

import android.widget.TextView;

import com.github.anastr.speedviewlib.TubeSpeedometer;

public class TotalWorkingHoursVisualizer {

    public static int SPEED_TO_MOVE = 0;
    public static int MOVE_DURATION = 1000;
    public static float RESET_MAX_SPEED = 0.1f;

    public static void renderChartData(TubeSpeedometer tubeSpeedometer, float totalWorkingHours, TextView totalTimeSpentText, boolean defaultAcceptanceCriteria, float confirmedMeetingTimeInHours) {
        if (defaultAcceptanceCriteria) {
            totalTimeSpentText.setText("Total Hours Spent - Accepted Events Only");
        } else {
            totalTimeSpentText.setText("Total Hours Spent - All Events");
        }
        tubeSpeedometer.speedTo(SPEED_TO_MOVE, MOVE_DURATION);
        tubeSpeedometer.setMaxSpeed(totalWorkingHours);
        tubeSpeedometer.speedTo(confirmedMeetingTimeInHours, MOVE_DURATION);
    }

    public static void clearChartData(TubeSpeedometer tubeSpeedometer) {
        tubeSpeedometer.speedTo(SPEED_TO_MOVE, MOVE_DURATION);
        tubeSpeedometer.setMaxSpeed(RESET_MAX_SPEED);
    }
}
