package com.app.entity;

import java.util.Date;

public class WXMember {
    private Integer mId;

    private String mPass;

    private String usernick;

    private String tel;

    private String gsd;

    private String realName;

    private Date genTime;

    private String extendOne;

    private String extendTwo;

    private String extendThree;

    private String openid;

    private String fopenid;

    private String headimgurl;

    private String imgUrl;

    private String ewmUrl;

    private Integer erweimaid;

    private Date attentiondate;

    private Integer attentiontimes;

    private Byte vipLevel;

    private Byte state;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass == null ? null : mPass.trim();
    }

    public String getUsernick() {
        return usernick;
    }

    public void setUsernick(String usernick) {
        this.usernick = usernick == null ? null : usernick.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getGsd() {
        return gsd;
    }

    public void setGsd(String gsd) {
        this.gsd = gsd == null ? null : gsd.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne == null ? null : extendOne.trim();
    }

    public String getExtendTwo() {
        return extendTwo;
    }

    public void setExtendTwo(String extendTwo) {
        this.extendTwo = extendTwo == null ? null : extendTwo.trim();
    }

    public String getExtendThree() {
        return extendThree;
    }

    public void setExtendThree(String extendThree) {
        this.extendThree = extendThree == null ? null : extendThree.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getFopenid() {
        return fopenid;
    }

    public void setFopenid(String fopenid) {
        this.fopenid = fopenid == null ? null : fopenid.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getEwmUrl() {
        return ewmUrl;
    }

    public void setEwmUrl(String ewmUrl) {
        this.ewmUrl = ewmUrl == null ? null : ewmUrl.trim();
    }

    public Integer getErweimaid() {
        return erweimaid;
    }

    public void setErweimaid(Integer erweimaid) {
        this.erweimaid = erweimaid;
    }

    public Date getAttentiondate() {
        return attentiondate;
    }

    public void setAttentiondate(Date attentiondate) {
        this.attentiondate = attentiondate;
    }

    public Integer getAttentiontimes() {
        return attentiontimes;
    }

    public void setAttentiontimes(Integer attentiontimes) {
        this.attentiontimes = attentiontimes;
    }

    public Byte getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Byte vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}