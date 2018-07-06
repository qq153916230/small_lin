package com.app.entity;

import java.util.Date;

public class User {
    private String username;

    private Integer tid;

    private String userpass;

    private String nick;

    private Integer usergroup;

    private Short userrole;

    private String userright;

    private String remark;

    private Date gdate;

    private String ucode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass == null ? null : userpass.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public Integer getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(Integer usergroup) {
        this.usergroup = usergroup;
    }

    public Short getUserrole() {
        return userrole;
    }

    public void setUserrole(Short userrole) {
        this.userrole = userrole;
    }

    public String getUserright() {
        return userright;
    }

    public void setUserright(String userright) {
        this.userright = userright == null ? null : userright.trim();
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

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode == null ? null : ucode.trim();
    }
}