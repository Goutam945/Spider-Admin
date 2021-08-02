package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseIdlist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("loginusername")
    @Expose
    private String loginusername;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("rid")
    @Expose
    private String rid;
    @SerializedName("closeiddate")
    @Expose
    private String closeiddate;
    @SerializedName("totalbalanceless")
    @Expose
    private String totalbalanceless;
    @SerializedName("noactivebets")
    @Expose
    private String noactivebets;
    @SerializedName("withdraw")
    @Expose
    private String withdraw;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("otherissue")
    @Expose
    private String otherissue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginusername() {
        return loginusername;
    }

    public void setLoginusername(String loginusername) {
        this.loginusername = loginusername;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCloseiddate() {
        return closeiddate;
    }

    public void setCloseiddate(String closeiddate) {
        this.closeiddate = closeiddate;
    }

    public String getTotalbalanceless() {
        return totalbalanceless;
    }

    public void setTotalbalanceless(String totalbalanceless) {
        this.totalbalanceless = totalbalanceless;
    }

    public String getNoactivebets() {
        return noactivebets;
    }

    public void setNoactivebets(String noactivebets) {
        this.noactivebets = noactivebets;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOtherissue() {
        return otherissue;
    }

    public void setOtherissue(String otherissue) {
        this.otherissue = otherissue;
    }

}
