package com.app.entity;

import java.util.Date;

public class AidgoldCS {
    private Integer tid;

    private Integer mid;

    private Integer aid;

    private Short csmodo;

    private Date csdate;

    private String cscont;

    private String remark;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Short getCsmodo() {
        return csmodo;
    }

    public void setCsmodo(Short csmodo) {
        this.csmodo = csmodo;
    }

    public Date getCsdate() {
        return csdate;
    }

    public void setCsdate(Date csdate) {
        this.csdate = csdate;
    }

    public String getCscont() {
        return cscont;
    }

    public void setCscont(String cscont) {
        this.cscont = cscont == null ? null : cscont.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}