package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashbordcountlist {
    @SerializedName("newuser")
    @Expose
    private String newuser;
    @SerializedName("newwithdrawal")
    @Expose
    private String newwithdrawal;
    @SerializedName("newdeposit")
    @Expose
    private String newdeposit;
    @SerializedName("approvewithdraw")
    @Expose
    private String approvewithdraw;
    @SerializedName("approvedeposit")
    @Expose
    private String approvedeposit;
    @SerializedName("userrequest")
    @Expose
    private Integer userrequest;

    public String getNewuser() {
        return newuser;
    }

    public void setNewuser(String newuser) {
        this.newuser = newuser;
    }

    public String getNewwithdrawal() {
        return newwithdrawal;
    }

    public void setNewwithdrawal(String newwithdrawal) {
        this.newwithdrawal = newwithdrawal;
    }

    public String getNewdeposit() {
        return newdeposit;
    }

    public void setNewdeposit(String newdeposit) {
        this.newdeposit = newdeposit;
    }

    public String getApprovewithdraw() {
        return approvewithdraw;
    }

    public void setApprovewithdraw(String approvewithdraw) {
        this.approvewithdraw = approvewithdraw;
    }

    public String getApprovedeposit() {
        return approvedeposit;
    }

    public void setApprovedeposit(String approvedeposit) {
        this.approvedeposit = approvedeposit;
    }

    public Integer getUserrequest() {
        return userrequest;
    }

    public void setUserrequest(Integer userrequest) {
        this.userrequest = userrequest;
    }


}
