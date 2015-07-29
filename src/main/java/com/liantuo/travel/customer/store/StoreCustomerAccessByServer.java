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

public class StoreCustomerAccessByServer extends BaseFunction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -134360533028883430L;
	private static final String FIELDS = "app_name,server_ip,req_time,num,req_date";
	private static final String TABLE_NAME = "customer_server_access";
	private Queue<TridentTuple> tupleQueue = new ConcurrentLinkedQueue<TridentTuple>();
	private int count = 1000;
	private long lastTime = System.currentTimeMillis(); // 上次批量处理的时间戳;
	String sql = "insert into  " + TABLE_NAME + " (" + FIELDS + ") values (?,?,?,?,?)";

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
			ps.setString(1, tuple.getStringByField("appName"));
			ps.setString(2, tuple.getStringByField("serverIP"));
			ps.setString(3, tuple.getStringByField("reqTime"));
			ps.setLong(4, tuple.getLongByField("num"));
			ps.setString(5, tuple.getStringByField("reqTime").substring(0, 10));
			ps.addBatch();
		}
		ps.executeBatch(); // 批量提交sql conn.commit();
	}
}
