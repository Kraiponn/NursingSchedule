package com.ksntechnology.nursingschedule.dao;

public class WorkMateItem {
    private String textUser;
    private String textResponsible;

    public WorkMateItem(String textUser, String textResponsible) {
        this.textUser = textUser;
        this.textResponsible = textResponsible;
    }

    public String getTextUser() {
        return textUser;
    }

    public void setTextUser(String textUser) {
        this.textUser = textUser;
    }

    public String getTextResponsible() {
        return textResponsible;
    }

    public void setTextResponsible(String textResponsible) {
        this.textResponsible = textResponsible;
    }
}
