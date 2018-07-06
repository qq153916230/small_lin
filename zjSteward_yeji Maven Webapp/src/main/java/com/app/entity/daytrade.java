package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class daytrade {
    private Long tid;

    private Long merchantcode;

    private String merchantname;

    private String ttype;

    private String posno;

    private String cardno;

    private Date paydate;

    private BigDecimal paymoney;

    private Integer paynum;

    private BigDecimal handcharge;

    private Date gdate;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(Long merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname == null ? null : merchantname.trim();
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype == null ? null : ttype.trim();
    }

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno == null ? null : posno.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public BigDecimal getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(BigDecimal paymoney) {
        this.paymoney = paymoney;
    }

    public Integer getPaynum() {
        return paynum;
    }

    public void setPaynum(Integer paynum) {
        this.paynum = paynum;
    }

    public BigDecimal getHandcharge() {
        return handcharge;
    }

    public void setHandcharge(BigDecimal handcharge) {
        this.handcharge = handcharge;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }
}