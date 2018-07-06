package com.app.entity;

import java.util.Date;

public class Card {
    private Integer cid;

    private Integer mid;

    private String mobile;

    private String master;

    private String cardno;

    private String bank;

    private String zdmonth;

    private String zdday;

    private String hkday;

    private Short period;

    private Integer hkprice;

    private String yxdate;

    private String cxpass;

    private String paypass;

    private String sncode;

    private Short status;

    private Short ischeck;

    private Date gdate;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master == null ? null : master.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getZdmonth() {
        return zdmonth;
    }

    public void setZdmonth(String zdmonth) {
        this.zdmonth = zdmonth == null ? null : zdmonth.trim();
    }

    public String getZdday() {
        return zdday;
    }

    public void setZdday(String zdday) {
        this.zdday = zdday == null ? null : zdday.trim();
    }

    public String getHkday() {
        return hkday;
    }

    public void setHkday(String hkday) {
        this.hkday = hkday == null ? null : hkday.trim();
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
    }

    public Integer getHkprice() {
        return hkprice;
    }

    public void setHkprice(Integer hkprice) {
        this.hkprice = hkprice;
    }

    public String getYxdate() {
        return yxdate;
    }

    public void setYxdate(String yxdate) {
        this.yxdate = yxdate == null ? null : yxdate.trim();
    }

    public String getCxpass() {
        return cxpass;
    }

    public void setCxpass(String cxpass) {
        this.cxpass = cxpass == null ? null : cxpass.trim();
    }

    public String getPaypass() {
        return paypass;
    }

    public void setPaypass(String paypass) {
        this.paypass = paypass == null ? null : paypass.trim();
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode == null ? null : sncode.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getIscheck() {
        return ischeck;
    }

    public void setIscheck(Short ischeck) {
        this.ischeck = ischeck;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}