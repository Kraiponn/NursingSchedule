package com.ksntechnology.nursingschedule.manager;

import android.content.Context;

import com.ksntechnology.nursingschedule.manager.http.NursingScheduleAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpNursingRequest {
    private static HttpNursingRequest sInstance;

    public static HttpNursingRequest getInstance() {
        if (sInstance == null) {
            sInstance = new HttpNursingRequest();
        }
        return sInstance;
    }

    private Context mContext;
    private NursingScheduleAPI api;

    private OkHttpClient getRequestHeader() {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            return client;
        } catch (Exception ex) {
            // Alert TimeOut Dialog Here
            return null;
        }
    }

    private HttpNursingRequest() {
        mContext = Contextor.getInstance().getContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/www.ksnajaroon.com/nurse_note/")
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(NursingScheduleAPI.class);
    }

    public NursingScheduleAPI getApi() {
        return api;
    }

    public void setApi(NursingScheduleAPI api) {
        this.api = api;
    }
}
