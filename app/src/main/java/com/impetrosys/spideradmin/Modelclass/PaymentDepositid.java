package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDepositid {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("pay_wallet")
    @Expose
    private String payWallet;
    @SerializedName("websiteid")
    @Expose
    private String websiteid;
    @SerializedName("websitename")
    @Expose
    private String websitename;
    @SerializedName("websiteurl")
    @Expose
    private String websiteurl;
    @SerializedName("websitephoto")
    @Expose
    private String websitephoto;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("requestusername")
    @Expose
    private String requestusername;
    @SerializedName("requestpassword")
    @Expose
    private Object requestpassword;
    @SerializedName("paymentmethodid")
    @Expose
    private String paymentmethodid;
    @SerializedName("paymentscreenshot")
    @Expose
    private String paymentscreenshot;
    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("depositstatus")
    @Expose
    private String depositstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getPayWallet() {
        return payWallet;
    }

    public void setPayWallet(String payWallet) {
        this.payWallet = payWallet;
    }

    public String getWebsiteid() {
        return websiteid;
    }

    public void setWebsiteid(String websiteid) {
        this.websiteid = websiteid;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    public String getWebsitephoto() {
        return websitephoto;
    }

    public void setWebsitephoto(String websitephoto) {
        this.websitephoto = websitephoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestusername() {
        return requestusername;
    }

    public void setRequestusername(String requestusername) {
        this.requestusername = requestusername;
    }

    public Object getRequestpassword() {
        return requestpassword;
    }

    public void setRequestpassword(Object requestpassword) {
        this.requestpassword = requestpassword;
    }

    public String getPaymentmethodid() {
        return paymentmethodid;
    }

    public void setPaymentmethodid(String paymentmethodid) {
        this.paymentmethodid = paymentmethodid;
    }

    public String getPaymentscreenshot() {
        return paymentscreenshot;
    }

    public void setPaymentscreenshot(String paymentscreenshot) {
        this.paymentscreenshot = paymentscreenshot;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDepositstatus() {
        return depositstatus;
    }

    public void setDepositstatus(String depositstatus) {
        this.depositstatus = depositstatus;
    }
}

