package com.liantuo.travel.customer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBFactory {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String HOST_PORT = "slaver1:3306";
	private static final String DATABASE = "jeesite";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "hello";
	

	public static Connection getConnection() {
		String url = "jdbc:mysql://" + HOST_PORT + "/" + DATABASE;
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) throws SQLException {
		Connection conn = DBFactory.getConnection();
//		String sql = "insert into time_point (time_point,sec,time_index) values (?,?,?)";
		String sql = "update time_point set sec = ? , time_index=? where time_point=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		Long start = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis());
		for (int i = 0; i < 1440; i++) {
			ps.setString(3, format(i));
			ps.setInt(1, i * 60);
			ps.setInt(2, 1);
			ps.addBatch();
		}
		Long end1 = System.currentTimeMillis();
		System.out.println(end1 - start);
		ps.executeBatch();
		Long end2 = System.currentTimeMillis();
		System.out.println(end2 - end1);
		
		
		
	}

	public static String format(int i) {
		int tail = i % 60;
		int head = i / 60;
		String _tail = "";
		String _head = "";
		if (tail < 10) {
			_tail = "0" + tail;
		} else {
			_tail = "" + tail;
		}

		if (head < 10) {
			_head = "0" + head;
		} else {
			_head = "" + head;
		}
		return _head + ":" + _tail;
	}

}
