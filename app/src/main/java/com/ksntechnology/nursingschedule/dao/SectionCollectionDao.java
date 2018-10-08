package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionCollectionDao {
    @SerializedName("err_msg")  private String errMsg;
    @SerializedName("data")     private List<SectionItemDao> data;

    public SectionCollectionDao() {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<SectionItemDao> getData() {
        return data;
    }

    public void setData(List<SectionItemDao> data) {
        this.data = data;
    }
}
