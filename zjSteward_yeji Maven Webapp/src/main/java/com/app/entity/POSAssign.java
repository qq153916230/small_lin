package com.app.entity;

import java.util.Date;

public class POSAssign {
    private Integer mid;

    private String mobile;

    private String possn;

    private String merchantcode;

    private String merchantname;

    private Integer posid;

    private String postype;

    private Short posmode;

    private Short isreward;

    private Date gdate;

    private String remark;

    private Date dbdate;

    private String dbvip;

    private String dbremark;

    private String vipMobile;

    private String vipNick;

    private Short bluetype;

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

    public String getPossn() {
        return possn;
    }

    public void setPossn(String possn) {
        this.possn = possn == null ? null : possn.trim();
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode == null ? null : merchantcode.trim();
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname == null ? null : merchantname.trim();
    }

    public Integer getPosid() {
        return posid;
    }

    public void setPosid(Integer posid) {
        this.posid = posid;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype == null ? null : postype.trim();
    }

    public Short getPosmode() {
        return posmode;
    }

    public void setPosmode(Short posmode) {
        this.posmode = posmode;
    }

    public Short getIsreward() {
        return isreward;
    }

    public void setIsreward(Short isreward) {
        this.isreward = isreward;
    }

    public Date getGdate() {
        return gdate;
    }

    public void setGdate(Date gdate) {
        this.gdate = gdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getDbdate() {
        return dbdate;
    }

    public void setDbdate(Date dbdate) {
        this.dbdate = dbdate;
    }

    public String getDbvip() {
        return dbvip;
    }

    public void setDbvip(String dbvip) {
        this.dbvip = dbvip == null ? null : dbvip.trim();
    }

    public String getDbremark() {
        return dbremark;
    }

    public void setDbremark(String dbremark) {
        this.dbremark = dbremark == null ? null : dbremark.trim();
    }

    public String getVipMobile() {
        return vipMobile;
    }

    public void setVipMobile(String vipMobile) {
        this.vipMobile = vipMobile == null ? null : vipMobile.trim();
    }

    public String getVipNick() {
        return vipNick;
    }

    public void setVipNick(String vipNick) {
        this.vipNick = vipNick == null ? null : vipNick.trim();
    }

    public Short getBluetype() {
        return bluetype;
    }

    public void setBluetype(Short bluetype) {
        this.bluetype = bluetype;
    }
}