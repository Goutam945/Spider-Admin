
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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


