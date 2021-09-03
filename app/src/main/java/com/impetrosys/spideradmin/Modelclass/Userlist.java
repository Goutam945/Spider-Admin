
package com.impetrosys.spideradmin.Modelclass;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Userlist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("payment_detail")
    @Expose
    private List<PaymentDetail> paymentDetail = null;
    @SerializedName("isrefer")
    @Expose
    private Integer isrefer;
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("referaldetail")
    @Expose
    private List<Referaldetail> referaldetail = null;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PaymentDetail> getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(List<PaymentDetail> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public Integer getIsrefer() {
        return isrefer;
    }

    public void setIsrefer(Integer isrefer) {
        this.isrefer = isrefer;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Referaldetail> getReferaldetail() {
        return referaldetail;
    }

    public void setReferaldetail(List<Referaldetail> referaldetail) {
        this.referaldetail = referaldetail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public class Referaldetail {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("reward")
        @Expose
        private String reward;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("referdate")
        @Expose
        private String referdate;
        @SerializedName("uid")
        @Expose
        private String uid;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getReferdate() {
            return referdate;
        }

        public void setReferdate(String referdate) {
            this.referdate = referdate;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }
    public class Wallet {

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

    public class PaymentDetail {

        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;
        @SerializedName("displayname")
        @Expose
        private String displayname;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("bankname")
        @Expose
        private String bankname;
        @SerializedName("ifsc")
        @Expose
        private String ifsc;
        @SerializedName("branch")
        @Expose
        private String branch;
        @SerializedName("accountno")
        @Expose
        private String accountno;
        @SerializedName("accountholder")
        @Expose
        private String accountholder;
        @SerializedName("accounttype")
        @Expose
        private String accounttype;

        public String getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(String paymentmethod) {
            this.paymentmethod = paymentmethod;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getIfsc() {
            return ifsc;
        }

        public void setIfsc(String ifsc) {
            this.ifsc = ifsc;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
        }

        public String getAccountholder() {
            return accountholder;
        }

        public void setAccountholder(String accountholder) {
            this.accountholder = accountholder;
        }

        public String getAccounttype() {
            return accounttype;
        }

        public void setAccounttype(String accounttype) {
            this.accounttype = accounttype;
        }

    }


    }


