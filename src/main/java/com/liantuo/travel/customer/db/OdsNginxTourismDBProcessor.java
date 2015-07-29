package com.liantuo.travel.customer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liantuo.travel.customer.po.OdsNginxTeamdbsPo;

public class OdsNginxTourismDBProcessor implements DBProcessor {
	private final Logger logger = LoggerFactory.getLogger(OdsNginxTourismDBProcessor.class);
	private final String TABLE_FIELDS = "txid,app_name,server_IP,req_time,http_status,req_url_path,counts,pre_counts,resp_time,pre_resp_time,size,pre_size,req_date";
	private final String WHERE_FIELDS = " where app_name = ? AND server_ip = ? AND req_url_path = ? AND http_status = ? AND req_time = ? AND req_date = ?";
	private final String[] fields = { "txid=", "counts =", "pre_counts=", "resp_time=", "pre_resp_time=", "size=", "pre_size=" };
	private final String TABLE_NAME = "ods_nginx_tourism";

	public OdsNginxTeamdbsPo find(OdsNginxTeamdbsPo po, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		OdsNginxTeamdbsPo preLog = null;
		String sql = "select " + TABLE_FIELDS + " FROM " + TABLE_NAME + WHERE_FIELDS;
		ps = conn.prepareStatement(sql);
		ps.setString(1, po.getAppName());
		ps.setString(2, po.getServerIP());
		ps.setString(3, po.getReqUrlPath());
		ps.setString(4, po.getHttpStatus());
		ps.setString(5, po.getReqTime());
		ps.setString(6, po.getReqDate());
		rs = ps.executeQuery();
		if (rs.next()) {
			preLog = new OdsNginxTeamdbsPo(rs);
		}
		return preLog;
	}

	public void update(List<OdsNginxTeamdbsPo> pos, Connection conn, PreparedStatement ps) throws SQLException {
		if (pos.size() == 0)
			return;
		for (OdsNginxTeamdbsPo po : pos) {
			logger.info("============================更新==================");
			String setFields = "";
			setFields += fields[0] + po.getTxid() + ",";
			setFields += fields[1] + po.getCounts() + ",";
			setFields += fields[2] + po.getPreCounts() + ",";
			setFields += fields[3] + po.getRespTime() + ",";
			setFields += fields[4] + po.getPreRespTime() + ",";
			setFields += fields[5] + po.getSize() + ",";
			setFields += fields[6] + po.getPreSize();
			String sql = "UPDATE " + TABLE_NAME + " set  " + setFields + WHERE_FIELDS;
			ps = conn.prepareStatement(sql);
			ps.setString(1, po.getAppName());
			ps.setString(2, po.getServerIP());
			ps.setString(3, po.getReqUrlPath());
			ps.setString(4, po.getHttpStatus());
			ps.setString(5, po.getReqTime());
			ps.setString(6, po.getReqDate());
			ps.execute();
//			ps.addBatch();
		}
//		ps.executeBatch();
	}

	public void insert(List<OdsNginxTeamdbsPo> pos, Connection conn, PreparedStatement ps) throws SQLException {
		if (pos.size() == 0)
			return;
		for (OdsNginxTeamdbsPo po : pos) {
			logger.info("============================插入==================");
			String sql = "insert into  " + TABLE_NAME + " (" + TABLE_FIELDS + ") values (" + getReqMark(TABLE_FIELDS) + ") ON DUPLICATE KEY UPDATE counts=VALUES(counts) + counts,resp_time=VALUES(resp_time) + resp_time,size=VALUES(size) + size";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, po.getTxid());
			ps.setString(2, po.getAppName());
			ps.setString(3, po.getServerIP());
			ps.setString(4, po.getReqTime());
			ps.setString(5, po.getHttpStatus());
			ps.setString(6, po.getReqUrlPath());

			ps.setLong(7, po.getCounts());
			ps.setLong(8, po.getCounts());
			ps.setDouble(9, po.getRespTime());
			ps.setDouble(10, po.getPreRespTime());
			ps.setLong(11, po.getSize());
			ps.setLong(12, po.getPreSize());
			ps.setString(13, po.getReqDate());
			ps.execute();
//			ps.addBatch();
		}
//		ps.executeBatch(); // 批量提交sql conn.commit();
	}

	public OdsNginxTeamdbsPo merge(OdsNginxTeamdbsPo curPo, OdsNginxTeamdbsPo prePo) {
		if (prePo == null || prePo.getTxid() == null || prePo.getTxid() == 0) {
			// 第一次保存直接返回
			curPo.setNew(true);
		} else if (!curPo.getTxid().equals(prePo.getTxid())) {
			// 非第一次保存，并且本次处理为新处理
			curPo.setCounts(curPo.getCounts() + prePo.getCounts());
			curPo.setRespTime(curPo.getRespTime() + prePo.getRespTime());
			curPo.setSize(curPo.getSize() + prePo.getSize());

			// 把上次记录的有效值记录为本次记录的前驱标记
			curPo.setPreCounts(prePo.getCounts());
			curPo.setPreRespTime(prePo.getRespTime());
			curPo.setPreSize(prePo.getSize());
			curPo.setNew(false);
		} else {
			// 非第一次保存，并且本次处理为重复处理
			curPo.setCounts(curPo.getCounts() + prePo.getPreCounts());
			curPo.setRespTime(curPo.getRespTime() + prePo.getPreRespTime());
			curPo.setSize(curPo.getSize() + prePo.getSize());

			// 把上次记录的有效值记录为本次记录的前驱标记
			curPo.setPreCounts(prePo.getCounts());
			curPo.setPreRespTime(prePo.getRespTime());
			curPo.setPreSize(prePo.getSize());
			curPo.setNew(false);
		}
		return curPo;
	}

	public String getReqMark(String str) {
		String req_mark = "";
		for (int i = 0; i < str.split(",").length; i++) {
			req_mark += "?,";
		}
		return req_mark.substring(0, req_mark.lastIndexOf(","));
	}

}
