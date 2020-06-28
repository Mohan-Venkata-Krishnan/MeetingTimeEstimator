package com.app.meetingtimeestimator.visualizer;

import android.widget.TextView;

import com.github.anastr.speedviewlib.TubeSpeedometer;

public class TotalWorkingHoursVisualizer {

    public static int SPEED_TO_MOVE = 0;
    public static int MOVE_DURATION = 1000;

    public static void renderChartData(TubeSpeedometer tubeSpeedometer,
                                       float totalWorkingHours, TextView totalTimeSpentText, boolean defaultAcceptanceCriteria, float confirmedMeetingTimeInHours) {
        if (defaultAcceptanceCriteria) {
            totalTimeSpentText.setText("Total Hours Spent - Accepted Events Only");
        } else {
            totalTimeSpentText.setText("Total Hours Spent - All Events");
        }
        tubeSpeedometer.setMinSpeed(0);
        tubeSpeedometer.setUnit("Hour(s)");
        tubeSpeedometer.speedTo(SPEED_TO_MOVE, MOVE_DURATION);
        tubeSpeedometer.setMaxSpeed(totalWorkingHours);
        tubeSpeedometer.speedTo(confirmedMeetingTimeInHours, MOVE_DURATION);
    }

}
