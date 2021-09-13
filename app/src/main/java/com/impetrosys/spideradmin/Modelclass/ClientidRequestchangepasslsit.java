package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientidRequestchangepasslsit {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("requestid")
    @Expose
    private String requestid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("websitename")
    @Expose
    private String websitename;
    @SerializedName("websiteurl")
    @Expose
    private String websiteurl;
    @SerializedName("websitephoto")
    @Expose
    private String websitephoto;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("loginusername")
    @Expose
    private String loginusername;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("requestdate")
    @Expose
    private String requestdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
    }
}
