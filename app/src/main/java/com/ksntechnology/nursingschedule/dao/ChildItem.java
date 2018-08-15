package com.ksntechnology.nursingschedule.dao;

public class ChildItem {
    private int id;
    private int iconId;
    private String textLocation;
    private String textShiftType;

    public ChildItem(int id, int iconId, String textLocation, String textShiftType) {
        this.id = id;
        this.iconId = iconId;
        this.textLocation = textLocation;
        this.textShiftType = textShiftType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTextLocation() {
        return textLocation;
    }

    public void setTextLocation(String textLocation) {
        this.textLocation = textLocation;
    }

    public String getTextShiftType() {
        return textShiftType;
    }

    public void setTextShiftType(String textShiftType) {
        this.textShiftType = textShiftType;
    }
}
