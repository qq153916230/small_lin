package com.app.entity;

import java.util.Date;

public class WXRedpack {
    private String openid;

    private String fromOpenid;

    private Short ptype;

    private Integer fee;

    private String result;

    private String billno;

    private Date pdate;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getFromOpenid() {
        return fromOpenid;
    }

    public void setFromOpenid(String fromOpenid) {
        this.fromOpenid = fromOpenid == null ? null : fromOpenid.trim();
    }

    public Short getPtype() {
        return ptype;
    }

    public void setPtype(Short ptype) {
        this.ptype = ptype;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }
}