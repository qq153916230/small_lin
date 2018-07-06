package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EpayTrade {
    private Long tid;

    private String orderid;

    private BigDecimal amount;

    private String mobile;

    private String idno;

    private String acctno;

    private String acctname;

    private String acctbank;

    private String acctbankname;

    private String cardno;

    private String master;

    private String bank;

    private Date skdate;

    private Short status;

    private String remark;

    private String thirdvoucher;

    private String bussseqno;

    private Short ptimes;

    private String zdmonth;

    private Date gdate;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno == null ? null : acctno.trim();
    }

    public String getAcctname() {
        return acctname;
    }

    public void setAcctname(String acctname) {
        this.acctname = acctname == null ? null : acctname.trim();
    }

    public String getAcctbank() {
        return acctbank;
    }

    public void setAcctbank(String acctbank) {
        this.acctbank = acctbank == null ? null : acctbank.trim();
    }

    public String getAcctbankname() {
        return acctbankname;
    }

    public void setAcctbankname(String acctbankname) {
        this.acctbankname = acctbankname == null ? null : acctbankname.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master == null ? null : master.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public Date getSkdate() {
        return skdate;
    }

    public void setSkdate(Date skdate) {
        this.skdate = skdate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getThirdvoucher() {
        return thirdvoucher;
    }

    public void setThirdvoucher(String thirdvoucher) {
        this.thirdvoucher = thirdvoucher == null ? null : thirdvoucher.trim();
    }

    public String getBussseqno() {
        return bussseqno;
    }

    public void setBussseqno(String bussseqno) {
        this.bussseqno = bussseqno == null ? null : bussseqno.trim();
    }

    public Short getPtimes() {
        return ptimes;
    }

    public void setPtimes(Short ptimes) {
        this.ptimes = ptimes;
    }

    public String getZdmonth() {
        return zdmonth;
    }

    public void setZdmonth(String zdmonth) {
        this.zdmonth = zdmonth == null ? null : zdmonth.trim();
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}