package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AidgoldBack {
    private Integer tid;

    private Integer aid;

    private Integer mid;

    private Integer period;

    private BigDecimal hkmoney;

    private Short modo;

    private Short status;

    private BigDecimal principal;

    private BigDecimal poundage;

    private BigDecimal interest;

    private BigDecimal overmoney;

    private Date enddate;

    private Date hkdate;

    private Integer overdate;

    private Date gdate;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getHkmoney() {
        return hkmoney;
    }

    public void setHkmoney(BigDecimal hkmoney) {
        this.hkmoney = hkmoney;
    }

    public Short getModo() {
        return modo;
    }

    public void setModo(Short modo) {
        this.modo = modo;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getOvermoney() {
        return overmoney;
    }

    public void setOvermoney(BigDecimal overmoney) {
        this.overmoney = overmoney;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getHkdate() {
        return hkdate;
    }

    public void setHkdate(Date hkdate) {
        this.hkdate = hkdate;
    }

    public Integer getOverdate() {
        return overdate;
    }

    public void setOverdate(Integer overdate) {
        this.overdate = overdate;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}