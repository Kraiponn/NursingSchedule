package com.ksntechnology.nursingschedule.adapter.model;

public class ViewNursingSectionItem extends BaseViewNursingItem {
    private String sectionDateMonth;

    public ViewNursingSectionItem() {
        super(ViewNursingType.TYPE_SECTION_ITEM);
    }

    public String getSectionDateMonth() {
        return sectionDateMonth;
    }

    public void setSectionDateMonth(String sectionDateMonth) {
        this.sectionDateMonth = sectionDateMonth;
    }

}
