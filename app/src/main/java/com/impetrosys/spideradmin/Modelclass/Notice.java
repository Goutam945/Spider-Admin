
package com.impetrosys.spideradmin.Modelclass;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("notice")
    @Expose
    private String notice;
    @SerializedName("supportno")
    @Expose
    private String supportno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getSupportno() {
        return supportno;
    }

    public void setSupportno(String supportno) {
        this.supportno = supportno;
    }

    }


