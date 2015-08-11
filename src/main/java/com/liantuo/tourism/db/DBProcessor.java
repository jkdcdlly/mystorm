package com.liantuo.tourism.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.liantuo.tourism.po.OdsNginxTeamdbsPo;

public interface DBProcessor {

	/**
	 * 当前记录与前驱记录做合并
	 * 
	 * @param curPo
	 * @param prePo
	 * @return
	 */

	public void insert(List<OdsNginxTeamdbsPo> logBeans, Connection conn, PreparedStatement ps) throws SQLException;


}
