package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

public class ResultDeleteEditDao {
    @SerializedName("err_msg")
    private String errMsg;

    public ResultDeleteEditDao() {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
