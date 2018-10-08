package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionItemDao {
    @SerializedName("id")           private int id;
    @SerializedName("section")      private String section;
    @SerializedName("section_detail")  private String section_detail;

    public SectionItemDao() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection_detail() {
        return section_detail;
    }

    public void setSection_detail(String section_detail) {
        this.section_detail = section_detail;
    }
}
