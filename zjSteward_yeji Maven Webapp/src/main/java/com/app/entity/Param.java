package com.app.entity;

public class Param {
    private Integer tid;

    private Integer pgroup;

    private Short ptype;

    private String pname;

    private String pvalue;

    private String remark;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getPgroup() {
        return pgroup;
    }

    public void setPgroup(Integer pgroup) {
        this.pgroup = pgroup;
    }

    public Short getPtype() {
        return ptype;
    }

    public void setPtype(Short ptype) {
        this.ptype = ptype;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue == null ? null : pvalue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}