package com.liantuo.tourism.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liantuo.tourism.po.OdsNginxTeamdbsPo;

public class OdsNginxTourismDBProcessor implements DBProcessor {
	private final Logger logger = LoggerFactory.getLogger(OdsNginxTourismDBProcessor.class);
	private final String TABLE_FIELDS = "app_name,server_IP,req_time,http_status,req_url_path,counts,resp_time,size,req_date,req_url_type";
	private final String TABLE_NAME = "ods_nginx_tourism";

	public void insert(List<OdsNginxTeamdbsPo> pos, Connection conn, PreparedStatement ps) throws SQLException {
		if (pos.size() == 0)
			return;
		String values = "";
		int counts = 0;
		for (OdsNginxTeamdbsPo po : pos) {
			values += "('" + po.getAppName() + "',";
			values += "'" + po.getServerIP() + "',";
			values += "'" + po.getReqTime() + "',";
			values += "'" + po.getHttpStatus() + "',";
			values += "'" + po.getReqUrlPath() + "',";
			values += po.getCounts() + ",";
			values += po.getRespTime() + ",";
			values += po.getSize() + ",";
			values += "'" + po.getReqDate() + "',";
			values += "'" + po.getReqUrlType() + "'),";
			counts += po.getCounts();
		}
		logger.debug("insertCounts " + counts + " ");
		String sql = "insert into  " + TABLE_NAME + " (" + TABLE_FIELDS + ") values " + values.substring(0, values.length() - 1) + " ON DUPLICATE KEY UPDATE counts=VALUES(counts) + counts,resp_time=VALUES(resp_time) + resp_time,size=VALUES(size) + size";
		ps = conn.prepareStatement(sql);
		ps.execute();
	}

}
