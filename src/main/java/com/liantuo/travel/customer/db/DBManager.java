package com.liantuo.travel.customer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String HOST_PORT = "192.168.7.11:3306";
	private static final String DATABASE = "jeesite";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "hello";

	public static Connection getConnection() {
		String url = "jdbc:mysql://" + HOST_PORT + "/" + DATABASE + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
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

	public static Connection getConnection2() {
		String url = "jdbc:mysql://192.168.7.219:4376/crm_ds?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(url, "kaifa", "kaifa");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void test() throws SQLException {
		Connection conn = DBManager.getConnection2();
		String sql = "INSERT INTO `call_center` (`agency_code`) VALUES ('ceshi') ";
		PreparedStatement ps= conn.prepareStatement(sql);
		for (int k = 0; k < 1000; k++) {
			ps.addBatch();
		}
		ps.executeBatch(); // 执行批处理
		// stmt.clearBatch(); //清理批处理
	}

	public static void test1() throws SQLException {
		Connection conn = DBManager.getConnection2();
		String sql = "INSERT INTO `call_center` (`agency_code`) VALUES ('ceshi') ";
		for (int i = 0; i < 1000; i++) {
			sql += ",('ceshi') ";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.execute();
	}

	public static void main(String[] args) throws SQLException {

		long start = System.currentTimeMillis();
		test();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		test1();
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
