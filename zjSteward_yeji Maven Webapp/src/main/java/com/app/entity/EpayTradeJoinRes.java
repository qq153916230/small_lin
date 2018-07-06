package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EpayTradeJoinRes {
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
    
    /*join part*/
    
    private int r_mid;
    private String r_mobile;
    private String r_orderid;
    private String r_bussflowno;
    private BigDecimal r_money;
    private String r_acctno;
    private String r_acctname;
    private String r_accbankname;
    private int r_status;
    private Date r_statusDate;
    private Short r_ptimes;
    private String r_zdmonth;

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

	public int getR_mid() {
		return r_mid;
	}

	public void setR_mid(int r_mid) {
		this.r_mid = r_mid;
	}

	public String getR_mobile() {
		return r_mobile;
	}

	public void setR_mobile(String r_mobile) {
		this.r_mobile = r_mobile;
	}

	public String getR_orderid() {
		return r_orderid;
	}

	public void setR_orderid(String r_orderid) {
		this.r_orderid = r_orderid;
	}

	public String getR_bussflowno() {
		return r_bussflowno;
	}

	public void setR_bussflowno(String r_bussflowno) {
		this.r_bussflowno = r_bussflowno;
	}

	public BigDecimal getR_money() {
		return r_money;
	}

	public void setR_money(BigDecimal r_money) {
		this.r_money = r_money;
	}

	public String getR_acctno() {
		return r_acctno;
	}

	public void setR_acctno(String r_acctno) {
		this.r_acctno = r_acctno;
	}

	public String getR_acctname() {
		return r_acctname;
	}

	public void setR_acctname(String r_acctname) {
		this.r_acctname = r_acctname;
	}

	public String getR_accbankname() {
		return r_accbankname;
	}

	public void setR_accbankname(String r_accbankname) {
		this.r_accbankname = r_accbankname;
	}

	public int getR_status() {
		return r_status;
	}

	public void setR_status(int r_status) {
		this.r_status = r_status;
	}

	public Date getR_statusDate() {
		return r_statusDate;
	}

	public void setR_statusDate(Date r_statusDate) {
		this.r_statusDate = r_statusDate;
	}

	public Short getR_ptimes() {
		return r_ptimes;
	}

	public void setR_ptimes(Short r_ptimes) {
		this.r_ptimes = r_ptimes;
	}

	public String getR_zdmonth() {
		return r_zdmonth;
	}

	public void setR_zdmonth(String r_zdmonth) {
		this.r_zdmonth = r_zdmonth;
	}
    
}