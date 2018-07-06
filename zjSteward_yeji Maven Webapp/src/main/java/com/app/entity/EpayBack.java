package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EpayBack {
    private Integer id;

    private Integer mid;

    private String mobile;

    private String orderid;

    private String bussflowno;

    private BigDecimal money;

    private String acctno;

    private String acctname;

    private String accbankname;

    private String remark;

    private Integer status;

    private Date statusDate;

    private Short ptimes;

    private String zdmonth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getBussflowno() {
        return bussflowno;
    }

    public void setBussflowno(String bussflowno) {
        this.bussflowno = bussflowno == null ? null : bussflowno.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getAccbankname() {
        return accbankname;
    }

    public void setAccbankname(String accbankname) {
        this.accbankname = accbankname == null ? null : accbankname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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
}