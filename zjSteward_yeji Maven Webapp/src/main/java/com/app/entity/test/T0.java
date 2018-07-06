package com.app.entity.test;

import java.math.BigDecimal;
import java.util.Date;

public class T0 {
    private Integer sid;

    private Long merchantcode;

    private String merchantname;

    private String skcard;

    private String skbank;

    private BigDecimal paymoney;

    private Date paydate;

    private Date paytimes;

    private String result;

    private BigDecimal shrate;

    private BigDecimal txrate;

    private BigDecimal handcharge;

    private BigDecimal jsprice;

    private String agent;

    private String posno;

    private String possn;

    private String cardno;

    private Boolean isreward;

    private Date gdate;

    private Short bluetype;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
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

    public String getSkcard() {
        return skcard;
    }

    public void setSkcard(String skcard) {
        this.skcard = skcard == null ? null : skcard.trim();
    }

    public String getSkbank() {
        return skbank;
    }

    public void setSkbank(String skbank) {
        this.skbank = skbank == null ? null : skbank.trim();
    }

    public BigDecimal getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(BigDecimal paymoney) {
        this.paymoney = paymoney;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public Date getPaytimes() {
        return paytimes;
    }

    public void setPaytimes(Date paytimes) {
        this.paytimes = paytimes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public BigDecimal getShrate() {
        return shrate;
    }

    public void setShrate(BigDecimal shrate) {
        this.shrate = shrate;
    }

    public BigDecimal getTxrate() {
        return txrate;
    }

    public void setTxrate(BigDecimal txrate) {
        this.txrate = txrate;
    }

    public BigDecimal getHandcharge() {
        return handcharge;
    }

    public void setHandcharge(BigDecimal handcharge) {
        this.handcharge = handcharge;
    }

    public BigDecimal getJsprice() {
        return jsprice;
    }

    public void setJsprice(BigDecimal jsprice) {
        this.jsprice = jsprice;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno == null ? null : posno.trim();
    }

    public String getPossn() {
        return possn;
    }

    public void setPossn(String possn) {
        this.possn = possn == null ? null : possn.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public Boolean getIsreward() {
        return isreward;
    }

    public void setIsreward(Boolean isreward) {
        this.isreward = isreward;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

    public Short getBluetype() {
        return bluetype;
    }

    public void setBluetype(Short bluetype) {
        this.bluetype = bluetype;
    }
}