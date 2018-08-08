package com.ksntechnology.nursingschedule.manager;

import android.content.Context;

public class SingletonTemplate {
    private static  SingletonTemplate sInstance;

    public static SingletonTemplate getInstance() {
        if (sInstance == null) {
            sInstance = new SingletonTemplate();
        }
        return sInstance;
    }

    private Context mContext;

    private SingletonTemplate() {
        mContext = Contextor.getInstance().getContext();
    }
}
