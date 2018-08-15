package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NursingItemCollectionDao {

    @SerializedName("err_msg")  private String err_msg;
    @SerializedName("data")     private List<NursingItemDao> data;

    public NursingItemCollectionDao() {
        //
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public List<NursingItemDao> getData() {
        return data;
    }

    public void setData(List<NursingItemDao> data) {
        this.data = data;
    }

}
