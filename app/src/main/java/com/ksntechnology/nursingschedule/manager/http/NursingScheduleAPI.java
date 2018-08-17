package com.ksntechnology.nursingschedule.manager.http;

import com.ksntechnology.nursingschedule.dao.AddNursingItemDao;
import com.ksntechnology.nursingschedule.dao.NursingItemCollectionDao;
import com.ksntechnology.nursingschedule.dao.SignInRegisterResultDao;
import com.ksntechnology.nursingschedule.dao.WorkLocationCollectionDao;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NursingScheduleAPI {
    @FormUrlEncoded
    @POST("sign-in.php")
    Call<SignInRegisterResultDao> requestSignIn(@Field("IS_REQUEST") String is_request,
                             @Field("user_name") String userName,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<SignInRegisterResultDao> requestRegister(@Field("IS_REQUEST") String is_request,
                                                  @Field("user_name") String userName,
                                                  @Field("password") String password,
                                                  @Field("email") String email,
                                                  @Field("gender") String gender,
                                                  @Field("password_hint") String pswdHint);

    @FormUrlEncoded
    @POST("insertitem.php")
    Call<AddNursingItemDao> feedToServer(@Field("user_working") String user_working,
                                         @Field("date") String date,
                                         @Field("from_time") String from_time,
                                         @Field("to_time") String to_time,
                                         @Field("shift") String shift,
                                         @Field("job_type") String job_type,
                                         @Field("location") String location,
                                         @Field("remark") String remark);

    @FormUrlEncoded
    @POST("getnursingschedule.php")
    Observable<NursingItemCollectionDao> postObservableNursing(@Field("user_working") String userWorking,
                                                       @Field("month") int month,
                                                       @Field("year") int year);

    @FormUrlEncoded
    @POST("getnursing-bycondition.php")
    Observable<NursingItemCollectionDao> postObservableNursingByCondition(@Field("id") int id,
                                                                          @Field("select_mode") String select_mode,
                                                                          @Field("date") String date,
                                                                          @Field("location") String location,
                                                                          @Field("shift") String shift);

    /*@FormUrlEncoded
    @POST("getnursingschedule.php")
    Call<NursingItemCollectionDao> postNursingSchedule(@Field("user_working") String userWorking,
                                                       @Field("month") int month,
                                                       @Field("year") int year);

    @POST("test.php")
    Call<NursingItemCollectionDao> postTest();*/

    @GET("getworklocation.php")
    Observable<WorkLocationCollectionDao> getWorkLocation();
}
