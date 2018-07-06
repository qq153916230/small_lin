package com.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AidgoldAgent {
    private Integer mid;

    private BigDecimal interest;

    private BigDecimal poundage;

    private BigDecimal totalgd;

    private BigDecimal usedgd;

    private Date gdate;
    
    private Integer agentrate;
    
    private BigDecimal bridgegd;
    
    
    private BigDecimal balance;
    private BigDecimal txreward;
    private BigDecimal txservice;
    
    

	private String nick;
    
    private String email;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getTotalgd() {
        return totalgd;
    }

    public void setTotalgd(BigDecimal totalgd) {
        this.totalgd = totalgd;
    }

    public BigDecimal getUsedgd() {
        return usedgd;
    }

    public void setUsedgd(BigDecimal usedgd) {
        this.usedgd = usedgd;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAgentrate() {
		return agentrate;
	}

	public void setAgentrate(Integer agentrate) {
		this.agentrate = agentrate;
	}

	public BigDecimal getBridgegd() {
		return bridgegd;
	}

	public void setBridgegd(BigDecimal bridgegd) {
		this.bridgegd = bridgegd;
	}

    public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTxreward() {
		return txreward;
	}

	public void setTxreward(BigDecimal txreward) {
		this.txreward = txreward;
	}

	public BigDecimal getTxservice() {
		return txservice;
	}

	public void setTxservice(BigDecimal txservice) {
		this.txservice = txservice;
	}
}