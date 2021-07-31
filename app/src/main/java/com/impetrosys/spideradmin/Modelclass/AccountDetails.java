package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountDetails {
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("accountdetail")
    @Expose
    private Accountdetail accountdetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Accountdetail getAccountdetail() {
        return accountdetail;
    }

    public void setAccountdetail(Accountdetail accountdetail) {
        this.accountdetail = accountdetail;
    }
    public class Accountdetail {

        @SerializedName("Bank Transfer")
        @Expose
        private List<Acountdeatil1> bankTransfer = null;
        @SerializedName("Phone Pay")
        @Expose
        private List<Acountdeatil1> phonePay = null;
        @SerializedName("Google Pay")
        @Expose
        private List<Acountdeatil1> googlePay = null;
        @SerializedName("Paytm Wallet")
        @Expose
        private List<Acountdeatil1> paytmWallet = null;
        @SerializedName("Paytm UPI")
        @Expose
        private List<Acountdeatil1> paytmUPI = null;

        public List<Acountdeatil1> getBankTransfer() {
            return bankTransfer;
        }

        public void setBankTransfer(List<Acountdeatil1> bankTransfer) {
            this.bankTransfer = bankTransfer;
        }

        public List<Acountdeatil1> getPhonePay() {
            return phonePay;
        }

        public void setPhonePay(List<Acountdeatil1> phonePay) {
            this.phonePay = phonePay;
        }

        public List<Acountdeatil1> getGooglePay() {
            return googlePay;
        }

        public void setGooglePay(List<Acountdeatil1> googlePay) {
            this.googlePay = googlePay;
        }

        public List<Acountdeatil1> getPaytmWallet() {
            return paytmWallet;
        }

        public void setPaytmWallet(List<Acountdeatil1> paytmWallet) {
            this.paytmWallet = paytmWallet;
        }

        public List<Acountdeatil1> getPaytmUPI() {
            return paytmUPI;
        }

        public void setPaytmUPI(List<Acountdeatil1> paytmUPI) {
            this.paytmUPI = paytmUPI;
        }

    }
    public class BankTransfer {

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

    }
    public class GooglePay {

        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("paymentmethodid")
        @Expose
        private String paymentmethodid;
        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;

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

    }
    public class PaytmUPus {

        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("paymentmethodid")
        @Expose
        private String paymentmethodid;
        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;

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

    }
    public class PaytmWallet {

        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("paymentmethodid")
        @Expose
        private String paymentmethodid;
        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;

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

    }
    public class PhonePay {

        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("paymentmethodid")
        @Expose
        private String paymentmethodid;
        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;

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

    }

    }

