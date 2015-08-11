package com.liantuo.tourism.po;

import storm.trident.tuple.TridentTuple;

public class OdsNginxTeamdbsPo {
    private Long txid;
    private String appName;
    private String serverIP;
    private String reqTime;
    private String httpStatus;
    private String reqUrlPath;
    private Long counts;
    private Long preCounts;
    private Double respTime;
    private Double preRespTime;
    private Long size;
    private Long preSize;
    private String reqDate;
    private boolean isNew;
    private String reqUrlType;



    public OdsNginxTeamdbsPo(TridentTuple tuple, Long txid) {
        this.txid = txid;
        this.appName = tuple.getStringByField("appName");
        this.serverIP = tuple.getStringByField("serverIP");
        this.reqTime = tuple.getStringByField("reqTime");
        this.httpStatus = tuple.getStringByField("httpStatus");
        this.reqUrlPath = tuple.getStringByField("reqUrlPath");
        this.counts = tuple.getLongByField("counts");
        this.respTime = tuple.getDoubleByField("respTimeSum");
        this.size = tuple.getLongByField("sizeSum");
        this.reqDate = tuple.getStringByField("reqDate");
        this.setReqUrlType(tuple.getStringByField("reqUrlType"));
    }
    public String getReqUrlType() {
        return reqUrlType;
    }

    public void setReqUrlType(String reqUrlType) {
        this.reqUrlType = reqUrlType;
    }

    public String getTableName() {
        return "ods_nginx_" + appName;
    }

    public Long getTxid() {
        return txid;
    }

    public void setTxid(Long txid) {
        this.txid = txid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReqUrlPath() {
        return reqUrlPath;
    }

    public void setReqUrlPath(String reqUrlPath) {
        this.reqUrlPath = reqUrlPath;
    }

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    public Long getPreCounts() {
        if (preCounts == null) {
            preCounts = 0L;
        }
        return preCounts;
    }

    public void setPreCounts(Long preCounts) {
        this.preCounts = preCounts;
    }

    public Double getRespTime() {
        if (respTime == null) {
            respTime = 0.0;
        }
        return respTime;
    }

    public void setRespTime(Double respTime) {
        this.respTime = respTime;
    }

    public Double getPreRespTime() {
        if (preRespTime == null) {
            preRespTime = 0.0;
        }
        return preRespTime;
    }

    public void setPreRespTime(Double preRespTime) {
        this.preRespTime = preRespTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getPreSize() {
        if (preSize == null) {
            preSize = 0L;
        }
        return preSize;
    }

    public void setPreSize(Long preSize) {
        this.preSize = preSize;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

}
