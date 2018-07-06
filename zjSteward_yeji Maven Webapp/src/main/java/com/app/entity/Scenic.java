package com.app.entity;

import java.util.Date;

public class Scenic {
    private Integer sid;

    private String sname;

    private Integer tprice;

    private String surl;

    private String spicture;

    private String sintroduce;

    private String sarea;

    private Date cdate;

    private Date gdate;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public Integer getTprice() {
        return tprice;
    }

    public void setTprice(Integer tprice) {
        this.tprice = tprice;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl == null ? null : surl.trim();
    }

    public String getSpicture() {
        return spicture;
    }

    public void setSpicture(String spicture) {
        this.spicture = spicture == null ? null : spicture.trim();
    }

    public String getSintroduce() {
        return sintroduce;
    }

    public void setSintroduce(String sintroduce) {
        this.sintroduce = sintroduce == null ? null : sintroduce.trim();
    }

    public String getSarea() {
        return sarea;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea == null ? null : sarea.trim();
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
}