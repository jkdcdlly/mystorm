package com.liantuo.travel.customer.state;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

import com.liantuo.travel.customer.db.DBFactory;
import com.liantuo.travel.customer.db.DBProcessor;
import com.liantuo.travel.customer.po.OdsNginxTeamdbsPo;

public class OdsNginxTourismDBState implements State {
	private DBProcessor dbProcessor;
	private Long txid;
	private PreparedStatement ps;
	private ResultSet rs;

	public OdsNginxTourismDBState(DBProcessor dbProcessor) {
		this.dbProcessor = dbProcessor;
	}

	public void beginCommit(Long txid) {
		this.txid = txid;
	}

	public void commit(Long txid) {
	}

	public void update(List<TridentTuple> tuples, TridentCollector collector) {
		Connection conn = DBFactory.getConnection();
		
		try {
			conn.setAutoCommit(false);
			List<OdsNginxTeamdbsPo> updateList = new ArrayList<OdsNginxTeamdbsPo>();
			List<OdsNginxTeamdbsPo> addList = new ArrayList<OdsNginxTeamdbsPo>();
			for (TridentTuple tuple : tuples) {
				OdsNginxTeamdbsPo curPo = new OdsNginxTeamdbsPo(tuple, txid);
//				OdsNginxTeamdbsPo prePo = dbProcessor.find(curPo, conn, ps, rs);
//				OdsNginxTeamdbsPo result = dbProcessor.merge(curPo, prePo);
//				if (result.isNew()) {
					addList.add(curPo);
//				} else {
//					updateList.add(result);
//				}
			}
			dbProcessor.insert(addList, conn, ps);
//			dbProcessor.update(updateList, conn, ps);
//			conn.setAutoCommit(true);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				conn = null;
			}

		}
	}
}
