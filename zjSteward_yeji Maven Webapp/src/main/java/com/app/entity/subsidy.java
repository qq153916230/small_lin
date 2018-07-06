package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class subsidy extends subsidyKey {
    private String mobile;

    private Integer period;

    private BigDecimal moneyBt;

    private BigDecimal moneyTx;

    private BigDecimal moneySy;

    private BigDecimal sumprice;

    private BigDecimal sumBt;

    private Date sdate;

    private Date gdate;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getMoneyBt() {
        return moneyBt;
    }

    public void setMoneyBt(BigDecimal moneyBt) {
        this.moneyBt = moneyBt;
    }

    public BigDecimal getMoneyTx() {
        return moneyTx;
    }

    public void setMoneyTx(BigDecimal moneyTx) {
        this.moneyTx = moneyTx;
    }

    public BigDecimal getMoneySy() {
        return moneySy;
    }

    public void setMoneySy(BigDecimal moneySy) {
        this.moneySy = moneySy;
    }

    public BigDecimal getSumprice() {
        return sumprice;
    }

    public void setSumprice(BigDecimal sumprice) {
        this.sumprice = sumprice;
    }

    public BigDecimal getSumBt() {
        return sumBt;
    }

    public void setSumBt(BigDecimal sumBt) {
        this.sumBt = sumBt;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}