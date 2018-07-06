package com.app.entity;

public class subsidyKey {
    private Integer mid;

    private String smonth;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getSmonth() {
        return smonth;
    }

    public void setSmonth(String smonth) {
        this.smonth = smonth == null ? null : smonth.trim();
    }
}