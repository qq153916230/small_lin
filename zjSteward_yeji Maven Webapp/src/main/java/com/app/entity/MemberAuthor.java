package com.app.entity;

import java.util.Date;

public class MemberAuthor {
    private Integer mid;

    private String sname;

    private String idno;

    private Short idtype;

    private String mobile;

    private String imgfront;

    private String imgback;

    private String imghand;
    
    private String imgagent;

    private String customerId;

    private Date cdate;

    private Date gdate;
    
    /*Join part*/
    private String address;

    private String zipCode;

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
    }

    public Short getIdtype() {
        return idtype;
    }

    public void setIdtype(Short idtype) {
        this.idtype = idtype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getImgfront() {
        return imgfront;
    }

    public void setImgfront(String imgfront) {
        this.imgfront = imgfront == null ? null : imgfront.trim();
    }

    public String getImgback() {
        return imgback;
    }

    public void setImgback(String imgback) {
        this.imgback = imgback == null ? null : imgback.trim();
    }

    public String getImghand() {
        return imghand;
    }

    public void setImghand(String imghand) {
        this.imghand = imghand == null ? null : imghand.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

	public String getImgagent() {
		return imgagent;
	}

	public void setImgagent(String imgagent) {
		this.imgagent = imgagent;
	}

	@Override
	public String toString() {
		return "MemberAuthor [mid=" + mid + ", sname=" + sname + ", idno="
				+ idno + ", idtype=" + idtype + ", mobile=" + mobile
				+ ", imgfront=" + imgfront + ", imgback=" + imgback
				+ ", imghand=" + imghand + ", imgagent=" + imgagent
				+ ", customerId=" + customerId + ", cdate=" + cdate
				+ ", gdate=" + gdate + ", address=" + address + ", zipCode="
				+ zipCode + "]";
	}
	
}