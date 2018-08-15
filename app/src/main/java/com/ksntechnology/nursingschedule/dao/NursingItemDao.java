package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

public class NursingItemDao {

    @SerializedName("id")
    private int id;

    @SerializedName("user_working")
    private String userWorking;

    @SerializedName("date")
    private String date;

    @SerializedName("from_time")
    private String fromTime;

    @SerializedName("to_time")
    private String toTime;

    @SerializedName("shift")
    private String shift;

    @SerializedName("job_type")
    private String jobType;

    @SerializedName("location")
    private String location;

    @SerializedName("remark")
    private String remark;

    public NursingItemDao() {
        //
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserWorking() {
        return userWorking;
    }

    public void setUserWorking(String userWorking) {
        this.userWorking = userWorking;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
