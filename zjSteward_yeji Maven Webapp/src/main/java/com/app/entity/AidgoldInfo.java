package com.app.entity;

public class AidgoldInfo {
    private Integer tid;

    private Integer mid;

    private String sname;

    private String mobile;

    private String weixin;

    private String email;

    private String contactName1;
    
    private String contactRela1;

    private String contactTele1;
    
    private String contactIdno1;

    private String contactName2;
    
    private String contactRela2;

    private String contactTele2;
    
    private String contactIdno2;

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

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1 == null ? null : contactName1.trim();
    }

    public String getContactTele1() {
        return contactTele1;
    }

    public void setContactTele1(String contactTele1) {
        this.contactTele1 = contactTele1 == null ? null : contactTele1.trim();
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2 == null ? null : contactName2.trim();
    }

    public String getContactTele2() {
        return contactTele2;
    }

    public void setContactTele2(String contactTele2) {
        this.contactTele2 = contactTele2 == null ? null : contactTele2.trim();
    }

	public String getContactIdno1() {
		return contactIdno1;
	}

	public void setContactIdno1(String contactIdno1) {
		this.contactIdno1 = contactIdno1;
	}

	public String getContactIdno2() {
		return contactIdno2;
	}

	public void setContactIdno2(String contactIdno2) {
		this.contactIdno2 = contactIdno2;
	}

	public String getContactRela1() {
		return contactRela1;
	}

	public void setContactRela1(String contactRela1) {
		this.contactRela1 = contactRela1;
	}

	public String getContactRela2() {
		return contactRela2;
	}

	public void setContactRela2(String contactRela2) {
		this.contactRela2 = contactRela2;
	}
}