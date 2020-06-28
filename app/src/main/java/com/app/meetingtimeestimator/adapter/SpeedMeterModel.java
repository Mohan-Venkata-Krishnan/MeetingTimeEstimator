package com.app.meetingtimeestimator.adapter;

public class SpeedMeterModel {

    public float totalWorkingHours;
    public Boolean defaultAcceptanceCriteria;
    public float confirmedMeetingTimeInHours;

    public SpeedMeterModel() {
    }

    public float getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(float totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public Boolean getDefaultAcceptanceCriteria() {
        return defaultAcceptanceCriteria;
    }

    public void setDefaultAcceptanceCriteria(Boolean defaultAcceptanceCriteria) {
        this.defaultAcceptanceCriteria = defaultAcceptanceCriteria;
    }

    public float getConfirmedMeetingTimeInHours() {
        return confirmedMeetingTimeInHours;
    }

    public void setConfirmedMeetingTimeInHours(float confirmedMeetingTimeInHours) {
        this.confirmedMeetingTimeInHours = confirmedMeetingTimeInHours;
    }
}
