package com.liantuo.tourism.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * 日志bean
 *
 * @author chenzhilei
 */
public class OdsNginxTeamdbsVo {
	private String appName;
	private String clientIP;
	private String reqTime;
	private String method;
	private String reqUrl;
	private String reqUrlPath;
	private String httpStatus;
	private Long size;
	private String serverIP;
	private Long txid;
	private Long counts;
	private Long preCounts;
	private Double respTime;
	private Double preRespTime;
	private boolean isNew;
	private String reqUrlType;

	public OdsNginxTeamdbsVo() {
	}

	public OdsNginxTeamdbsVo(String[] arrs) {
		appName = arrs[0];
		clientIP = arrs[1];
		reqTime = this.format(arrs[2].replaceAll("T|\\+08:00", " "));
		method = arrs[3];
		reqUrl = arrs[4];
		reqUrlPath = arrs[4].split("\\?")[0];
		this.setReqUrlType(this.getType(this.getReqUrlPath()));
		httpStatus = arrs[5];

		if (StringUtils.isBlank(arrs[6])) {
			System.out.println(arrs[6]);
		}
		size = StringUtils.isBlank(arrs[6]) ? 0 : Long.valueOf(arrs[6]);
		serverIP = arrs[8].split(":")[0];
		try {
			respTime = Double.valueOf("-".equals(arrs[9]) ? "0" : arrs[9]);
		} catch (Exception e) {
			respTime = Double.valueOf("-".equals(arrs[9]) ? "0" : arrs[9]);
		}

	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getReqUrlPath() {
		return reqUrlPath;
	}

	public void setReqUrlPath(String reqUrlPath) {
		this.reqUrlPath = reqUrlPath;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Long getTxid() {
		return txid;
	}

	public void setTxid(Long txid) {
		this.txid = txid;
	}

	public Long getCounts() {
		return counts;
	}

	public void setCounts(Long counts) {
		this.counts = counts;
	}

	public Long getPreCounts() {
		return preCounts;
	}

	public void setPreCounts(Long preCounts) {
		this.preCounts = preCounts;
	}

	public Double getRespTime() {
		return respTime;
	}

	public void setRespTime(Double respTime) {
		this.respTime = respTime;
	}

	public Double getPreRespTime() {
		return preRespTime;
	}

	public void setPreRespTime(Double preRespTime) {
		this.preRespTime = preRespTime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getReqUrlType() {
		return reqUrlType;
	}

	public void setReqUrlType(String reqUrlType) {
		this.reqUrlType = reqUrlType;
	}

	/**
	 * 格式化时间
	 *
	 * @return
	 */
	private String format(String reqTime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(reqTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year = c.get(Calendar.YEAR);
		String month = (c.get(Calendar.MONTH) + 1) > 9 ? String.valueOf(c.get(Calendar.MONTH) + 1) : "0" + (c.get(Calendar.MONTH) + 1);
		String day = c.get(Calendar.DAY_OF_MONTH) > 9 ? String.valueOf(c.get(Calendar.DAY_OF_MONTH)) : "0" + c.get(Calendar.DAY_OF_MONTH);
		String hour = (c.get(Calendar.HOUR_OF_DAY) > 9 ? String.valueOf(c.get(Calendar.HOUR_OF_DAY)) : "0" + c.get(Calendar.HOUR_OF_DAY));
		String minute = (c.get(Calendar.MINUTE) / 10 * 10 > 9 ? String.valueOf(c.get(Calendar.MINUTE) / 10 * 10) : "0" + c.get(Calendar.MINUTE) / 10 * 10);
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
	}

	public String getType(String reqUrlPath) {
		if (-1 != reqUrlPath.indexOf(";jsessionid")) {
			reqUrlPath = reqUrlPath.substring(0, reqUrlPath.indexOf(";jsessionid"));
		}
		if (-1 == reqUrlPath.lastIndexOf(".")) {
			return "/";
		} else {
			return reqUrlPath.substring(reqUrlPath.lastIndexOf(".") + 1);
		}
	}

	public static void main(String[] args) {
		OdsNginxTeamdbsVo o = new OdsNginxTeamdbsVo();
		System.out.println(o.getType("/index.htm;jsessionid=F473090FE903D6E906063BD2ACE3924D"));
	}
}
