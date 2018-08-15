package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkLocationCollectionDao {
    @SerializedName("err_msg")  private String errMsg;
    @SerializedName("data")     private List<WorkLocationItemDao> data;

    public WorkLocationCollectionDao() {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<WorkLocationItemDao> getData() {
        return data;
    }

    public void setData(List<WorkLocationItemDao> data) {
        this.data = data;
    }

}
