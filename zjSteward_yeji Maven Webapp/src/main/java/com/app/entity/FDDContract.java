package com.app.entity;

import java.util.Date;

public class FDDContract {
    private Integer pid;

    private Integer aidgoldId;

    private Integer userId;

    private String contractId;

    private String docTitle;

    private String docFile;

    private String docUrl;

    private String docType;

    private String transactionId;

    private String downloadUrl;

    private String viewpdfUrl;

    private Date cdate;

    private Date gdate;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAidgoldId() {
        return aidgoldId;
    }

    public void setAidgoldId(Integer aidgoldId) {
        this.aidgoldId = aidgoldId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle == null ? null : docTitle.trim();
    }

    public String getDocFile() {
        return docFile;
    }

    public void setDocFile(String docFile) {
        this.docFile = docFile == null ? null : docFile.trim();
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl == null ? null : docUrl.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getViewpdfUrl() {
        return viewpdfUrl;
    }

    public void setViewpdfUrl(String viewpdfUrl) {
        this.viewpdfUrl = viewpdfUrl == null ? null : viewpdfUrl.trim();
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