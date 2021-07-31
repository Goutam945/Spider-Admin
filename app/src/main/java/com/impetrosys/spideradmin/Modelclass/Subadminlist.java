package com.impetrosys.spideradmin.Modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subadminlist {
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
//    @SerializedName("menu_access_id")
//    @Expose
//    private Object menuAccessId;
//    @SerializedName("menu_access")
//    @Expose
//    private List<Object> menuAccess = null;
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

//    public Object getMenuAccessId() {
//        return menuAccessId;
//    }
//
//    public void setMenuAccessId(Object menuAccessId) {
//        this.menuAccessId = menuAccessId;
//    }

//    public List<Object> getMenuAccess() {
//        return menuAccess;
//    }
//
//    public void setMenuAccess(List<Object> menuAccess) {
//        this.menuAccess = menuAccess;
//    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
