package com.ksntechnology.nursingschedule.adapter.model;

import com.ksntechnology.nursingschedule.dao.NoDataItem;

public class ViewNursingConverter {

    public static ViewNursingSectionItem createSectionItem(String dateString) {
        ViewNursingSectionItem sectionItem = new ViewNursingSectionItem();
        sectionItem.setSectionDateMonth(dateString);
        return sectionItem;
    }

    //id,iconID,location,shiftType
    public static ViewNursingChildItem createChildItem(
            int id, int iconId, String location, String shiftType
    ) {
        ViewNursingChildItem childItem = new ViewNursingChildItem();
        childItem.setId(id);
        childItem.setIconId(iconId);
        childItem.setTextLocation(location);
        childItem.setTextShiftType(shiftType);
        return childItem;
    }

    public static NoDataItem createNoDataItem() {
        return new NoDataItem();
    }

}
