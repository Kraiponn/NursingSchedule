package com.ksntechnology.nursingschedule.dao;

public class WorkMateDetailItem {
    private String workingUser;
    private String location;

    public WorkMateDetailItem(String workingUser, String location) {
        this.workingUser = workingUser;
        this.location = location;
    }

    public String getWorkingUser() {
        return workingUser;
    }

    public void setWorkingUser(String workingUser) {
        this.workingUser = workingUser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
