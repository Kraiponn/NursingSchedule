package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

public class WorkLocationItemDao {
    @SerializedName("id")               private int id;
    @SerializedName("location_name")    private String locationName;

    public WorkLocationItemDao() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
