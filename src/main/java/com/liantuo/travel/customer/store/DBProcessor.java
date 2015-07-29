package com.liantuo.travel.customer.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import com.liantuo.travel.customer.db.DBManager;

public class DBProcessor extends BaseFunction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -134360533028883430L;
	private static final String FIELDS = "txid,app_name,server_IP,req_time,http_status,counts,pre_counts,resp_time,pre_resp_time";
	private static final String TABLE_NAME = "customer_log";
	private Queue<TridentTuple> tupleQueue = new ConcurrentLinkedQueue<TridentTuple>();
	private int count = 1000;
	private long lastTime = System.currentTimeMillis(); // 上次批量处理的时间戳;
	String sql = "insert into  " + TABLE_NAME + " (" + FIELDS + ") values (?,?,?,?,?,?,?,?,?)";

	public void execute(TridentTuple tuple, TridentCollector collector) {
		tupleQueue.add(tuple);
		long currentTime = System.currentTimeMillis();
		// 每count条tuple批量提交一次，或者每个1秒钟提交一次
		if (tupleQueue.size() >= count || currentTime >= lastTime + 2000) {
			Connection conn = DBManager.getConnection(); // 通过DBManager获取数据库连接
			PreparedStatement ps = null;
			try {
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(sql);
				this.executeBatch(tupleQueue, ps);
				conn.setAutoCommit(true);
				lastTime = currentTime;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void executeBatch(Queue<TridentTuple> tupleQueue, PreparedStatement ps) throws SQLException {
		for (int i = 0; i < tupleQueue.size(); i++) {
			TridentTuple tuple = tupleQueue.poll();
			// LogBean logBean = logBeans.get(i);
			ps.setLong(1, 1);
			ps.setString(2, tuple.getStringByField("appName"));
			ps.setString(3, tuple.getStringByField("serverIP"));
			ps.setString(4, tuple.getStringByField("reqTime"));
			ps.setString(5, tuple.getStringByField("httpStatus"));
			ps.setLong(6, tuple.getLongByField("counts"));
			ps.setLong(7, tuple.getLongByField("counts"));
			ps.setDouble(8, tuple.getLongByField("sums"));
			ps.setDouble(9, tuple.getLongByField("sums"));

			ps.addBatch();
		}
		ps.executeBatch(); // 批量提交sql conn.commit();
	}
}
