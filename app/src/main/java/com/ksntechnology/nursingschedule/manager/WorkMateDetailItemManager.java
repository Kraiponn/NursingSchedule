package com.ksntechnology.nursingschedule.manager;

import android.content.Context;

import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;

public class WorkMateDetailItemManager {
    private NursingItemCollectionDao dao;
    private Context mContext;

    public void WorkMateDetailItemManager() {
        //
    }

    public NursingItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(NursingItemCollectionDao dao) {
        this.dao = dao;
    }
}
