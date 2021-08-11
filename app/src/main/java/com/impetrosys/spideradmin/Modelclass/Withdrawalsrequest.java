package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Withdrawalsrequest {
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
    @SerializedName("withdrawdetail")
    @Expose
    private List<Withdrawdetail> withdrawdetail = null;
    @SerializedName("paymentmethodid")
    @Expose
    private String paymentmethodid;
    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("withdrawstatus")
    @Expose
    private String withdrawstatus;

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

    public List<Withdrawdetail> getWithdrawdetail() {
        return withdrawdetail;
    }

    public void setWithdrawdetail(List<Withdrawdetail> withdrawdetail) {
        this.withdrawdetail = withdrawdetail;
    }

    public String getPaymentmethodid() {
        return paymentmethodid;
    }

    public void setPaymentmethodid(String paymentmethodid) {
        this.paymentmethodid = paymentmethodid;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getWithdrawstatus() {
        return withdrawstatus;
    }

    public void setWithdrawstatus(String withdrawstatus) {
        this.withdrawstatus = withdrawstatus;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public class Withdrawdetail {

        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("accountno")
        @Expose
        private String accountno;
        @SerializedName("bankname")
        @Expose
        private String bankname;
        @SerializedName("branch")
        @Expose
        private String branch;
        @SerializedName("ifsc")
        @Expose
        private String ifsc;

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getIfsc() {
            return ifsc;
        }

        public void setIfsc(String ifsc) {
            this.ifsc = ifsc;
        }

    }
}
