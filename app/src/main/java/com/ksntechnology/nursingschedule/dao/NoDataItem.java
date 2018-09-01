package com.ksntechnology.nursingschedule.dao;

import com.ksntechnology.nursingschedule.adapter.model.BaseViewNursingItem;
import com.ksntechnology.nursingschedule.adapter.model.ViewNursingType;

public class NoDataItem extends BaseViewNursingItem {
    public NoDataItem() {
        super(ViewNursingType.TYPE_NO_DATA_ITEM);
    }
}
