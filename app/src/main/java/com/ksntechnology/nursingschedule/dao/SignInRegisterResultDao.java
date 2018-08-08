package com.ksntechnology.nursingschedule.dao;

import com.google.gson.annotations.SerializedName;

public class SignInRegisterResultDao {

    @SerializedName("err_flag")
    private int errFlag;

    @SerializedName("err_msg")
    private String errMsg;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("email")
    private String email;

    public SignInRegisterResultDao(){}


    public int getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(int errFlag) {
        this.errFlag = errFlag;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
