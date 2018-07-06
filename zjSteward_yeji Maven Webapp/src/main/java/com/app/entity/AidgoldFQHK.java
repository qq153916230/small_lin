package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AidgoldFQHK extends AidgoldFQHKKey {
    private Integer balance;

    private BigDecimal rate;

    private BigDecimal fee;

    private Integer everymoney;

    private BigDecimal hkmoney;

    private Date hkdate;

    private Short status;

    private Date gdate;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getEverymoney() {
        return everymoney;
    }

    public void setEverymoney(Integer everymoney) {
        this.everymoney = everymoney;
    }

    public BigDecimal getHkmoney() {
        return hkmoney;
    }

    public void setHkmoney(BigDecimal hkmoney) {
        this.hkmoney = hkmoney;
    }

    public Date getHkdate() {
        return hkdate;
    }

    public void setHkdate(Date hkdate) {
        this.hkdate = hkdate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}