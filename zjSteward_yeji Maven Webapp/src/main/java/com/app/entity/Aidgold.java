package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Aidgold {
    private Integer tid;

    private Integer mid;
    
    private Integer agentid;

    private Integer jkmoney;

    private Date jkdate;

    private Date enddate;

    private Integer jkdays;

    private BigDecimal jkrate;
    
    private Integer hkmoney;

    private BigDecimal cgrate;

    private String skbankno;

    private String skname;

    private String skbank;

    private Short jkcheck;

    private Integer cstimes;

    private String remark;
    
    private String declare;
    
    private Integer retype;

    private Date gdate;
    
    private String userName;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getJkmoney() {
        return jkmoney;
    }

    public void setJkmoney(Integer jkmoney) {
        this.jkmoney = jkmoney;
    }

    public Date getJkdate() {
        return jkdate;
    }

    public void setJkdate(Date jkdate) {
        this.jkdate = jkdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getJkdays() {
        return jkdays;
    }

    public void setJkdays(Integer jkdays) {
        this.jkdays = jkdays;
    }

    public BigDecimal getJkrate() {
        return jkrate;
    }

    public void setJkrate(BigDecimal jkrate) {
        this.jkrate = jkrate;
    }

    public BigDecimal getCgrate() {
        return cgrate;
    }
    
    public Integer getHkmoney() {
        return hkmoney;
    }

    public void setHkmoney(Integer hkmoney) {
        this.hkmoney = hkmoney;
    }

    public void setCgrate(BigDecimal cgrate) {
        this.cgrate = cgrate;
    }

    public String getSkbankno() {
        return skbankno;
    }

    public void setSkbankno(String skbankno) {
        this.skbankno = skbankno == null ? null : skbankno.trim();
    }

    public String getSkname() {
        return skname;
    }

    public void setSkname(String skname) {
        this.skname = skname == null ? null : skname.trim();
    }

    public String getSkbank() {
        return skbank;
    }

    public void setSkbank(String skbank) {
        this.skbank = skbank == null ? null : skbank.trim();
    }

    public Short getJkcheck() {
        return jkcheck;
    }

    public void setJkcheck(Short jkcheck) {
        this.jkcheck = jkcheck;
    }

    public Integer getCstimes() {
        return cstimes;
    }

    public void setCstimes(Integer cstimes) {
        this.cstimes = cstimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

	public Integer getAgentid() {
		return agentid;
	}

	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeclare() {
		return declare;
	}

	public void setDeclare(String declare) {
		this.declare = declare;
	}

	public Integer getRetype() {
		return retype;
	}
	//1234
	public void setRetype(Integer retype) { 
		this.retype = retype;
	}


}