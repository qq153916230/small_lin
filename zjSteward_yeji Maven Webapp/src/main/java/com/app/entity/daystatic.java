package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class daystatic {
    private Date tjdate;

    private BigDecimal t0Paymoney;

    private Integer t0Paynum;

    private BigDecimal t1Paymoney;

    private Integer t1Paynum;

    private BigDecimal payTotle;

    private BigDecimal moneyBt;

    private BigDecimal moneyTx;

    private Integer countTx;

    public Date getGdate() {
        return tjdate;
    }

    public void setGdate(Date gdate) {
        this.tjdate = gdate;
    }

    public BigDecimal getT0Paymoney() {
        return t0Paymoney;
    }

    public void setT0Paymoney(BigDecimal t0Paymoney) {
        this.t0Paymoney = t0Paymoney;
    }

    public Integer getT0Paynum() {
        return t0Paynum;
    }

    public void setT0Paynum(Integer t0Paynum) {
        this.t0Paynum = t0Paynum;
    }

    public BigDecimal getT1Paymoney() {
        return t1Paymoney;
    }

    public void setT1Paymoney(BigDecimal t1Paymoney) {
        this.t1Paymoney = t1Paymoney;
    }

    public Integer getT1Paynum() {
        return t1Paynum;
    }

    public void setT1Paynum(Integer t1Paynum) {
        this.t1Paynum = t1Paynum;
    }

    public BigDecimal getPayTotle() {
        return payTotle;
    }

    public void setPayTotle(BigDecimal payTotle) {
        this.payTotle = payTotle;
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

    public Integer getCountTx() {
        return countTx;
    }

    public void setCountTx(Integer countTx) {
        this.countTx = countTx;
    }

	@Override
	public String toString() {
		return "daystatic [tjdate=" + tjdate + ", t0Paymoney=" + t0Paymoney
				+ ", t0Paynum=" + t0Paynum + ", t1Paymoney=" + t1Paymoney
				+ ", t1Paynum=" + t1Paynum + ", payTotle=" + payTotle
				+ ", moneyBt=" + moneyBt + ", moneyTx=" + moneyTx
				+ ", countTx=" + countTx + "]";
	}
    
}