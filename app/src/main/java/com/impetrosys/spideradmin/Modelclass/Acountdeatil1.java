package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Acountdeatil1 {
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
    @SerializedName("paymentmethodid")
    @Expose
    private String paymentmethodid;
    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("number")
    @Expose
    private String number;
    @Expose
    private String id;
    @SerializedName("uid")


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
