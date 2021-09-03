package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Paymentdepositslist {
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
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("userrewarddetail")
    @Expose
    private List<Userrewarddetail> userrewarddetail = null;
    @SerializedName("rewardstatus")
    @Expose
    private String rewardstatus;
    @SerializedName("depositstatus")
    @Expose
    private String depositstatus;
    @Expose
    private String withdrawstatus;
    @SerializedName("websitename")
    @Expose
    private String websitename;
    @SerializedName("clientname")
    @Expose
    private String clientname;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Userrewarddetail> getUserrewarddetail() {
        return userrewarddetail;
    }

    public void setUserrewarddetail(List<Userrewarddetail> userrewarddetail) {
        this.userrewarddetail = userrewarddetail;
    }

    public String getRewardstatus() {
        return rewardstatus;
    }

    public void setRewardstatus(String rewardstatus) {
        this.rewardstatus = rewardstatus;
    }

    public String getDepositstatus() {
        return depositstatus;
    }

    public void setDepositstatus(String depositstatus) {
        this.depositstatus = depositstatus;
    }
    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public class Userrewarddetail {

        @SerializedName("wallet")
        @Expose
        private Integer wallet;
        @SerializedName("reward")
        @Expose
        private Integer reward;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getWallet() {
            return wallet;
        }

        public void setWallet(Integer wallet) {
            this.wallet = wallet;
        }

        public Integer getReward() {
            return reward;
        }

        public void setReward(Integer reward) {
            this.reward = reward;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }

}
