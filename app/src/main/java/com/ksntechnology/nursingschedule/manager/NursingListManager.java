package com.ksntechnology.nursingschedule.manager;

import android.content.Context;

import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;

public class NursingListManager {
    private NursingItemCollectionDao dao;
    private Context mContext;

    public NursingListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public NursingItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(NursingItemCollectionDao dao) {
        this.dao = dao;
    }
}
