package com.ksntechnology.nursingschedule.adapter.model;

public class ViewNursingChildItem extends BaseViewNursingItem {
    private int id;
    private int iconId;
    private String textLocation;
    private String textShiftType;

    public ViewNursingChildItem() {
        super(ViewNursingType.TYPE_CHILD_ITEM);
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
