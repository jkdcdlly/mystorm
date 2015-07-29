package com.liantuo.travel.customer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.liantuo.travel.customer.po.OdsNginxTeamdbsPo;

public interface DBProcessor {
	/**
	 * 查询出之前的记录
	 * 
	 * @param currPo
	 * @param conn
	 * @return
	 */
	public OdsNginxTeamdbsPo find(OdsNginxTeamdbsPo currPo, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;

	/**
	 * 当前记录与前驱记录做合并
	 * 
	 * @param curPo
	 * @param prePo
	 * @return
	 */
	public OdsNginxTeamdbsPo merge(OdsNginxTeamdbsPo curPo, OdsNginxTeamdbsPo prePo) throws SQLException;

	public void insert(List<OdsNginxTeamdbsPo> logBeans, Connection conn, PreparedStatement ps) throws SQLException;

	public void update(List<OdsNginxTeamdbsPo> logBeans, Connection conn, PreparedStatement ps) throws SQLException;

}
