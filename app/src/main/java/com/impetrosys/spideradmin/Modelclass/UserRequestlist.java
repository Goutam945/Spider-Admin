package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequestlist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("websitename")
    @Expose
    private Object websitename;
    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;
    @SerializedName("loginusername")
    @Expose
    private String loginusername;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("paymentscreenshot")
    @Expose
    private String paymentscreenshot;
    @SerializedName("rewardstatus")
    @Expose
    private String rewardstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getWebsitename() {
        return websitename;
    }

    public void setWebsitename(Object websitename) {
        this.websitename = websitename;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentscreenshot() {
        return paymentscreenshot;
    }

    public void setPaymentscreenshot(String paymentscreenshot) {
        this.paymentscreenshot = paymentscreenshot;
    }

    public String getRewardstatus() {
        return rewardstatus;
    }

    public void setRewardstatus(String rewardstatus) {
        this.rewardstatus = rewardstatus;
    }
}
