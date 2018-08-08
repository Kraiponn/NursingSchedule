package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

public class AddNursingItemDao {

    @SerializedName("err_msg")
    private String errMsg;

    @SerializedName("date")
    private String date;

    @SerializedName("shift")
    private String shift;

    public AddNursingItemDao(){
        //
    }


    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

}
