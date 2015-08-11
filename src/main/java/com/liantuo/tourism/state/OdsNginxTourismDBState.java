package com.liantuo.tourism.state;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liantuo.tourism.db.DBManager;
import storm.trident.operation.TridentCollector;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;

import com.liantuo.tourism.db.DBProcessor;
import com.liantuo.tourism.po.OdsNginxTeamdbsPo;

public class OdsNginxTourismDBState implements State {
	private DBProcessor dbProcessor;
	private Long txid;
	private PreparedStatement ps;

	public OdsNginxTourismDBState(DBProcessor dbProcessor) {
		this.dbProcessor = dbProcessor;
	}

	public void beginCommit(Long txid) {
		this.txid = txid;
	}

	public void commit(Long txid) {
	}

	public void update(List<TridentTuple> tuples, TridentCollector collector) {
		Connection conn = DBManager.getConnection();
		try {
			conn.setAutoCommit(false);
			List<OdsNginxTeamdbsPo> addList = new ArrayList<OdsNginxTeamdbsPo>();
			for (TridentTuple tuple : tuples) {
				addList.add(new OdsNginxTeamdbsPo(tuple, txid));
			}
			dbProcessor.insert(addList, conn, ps);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}

		}
	}
}
