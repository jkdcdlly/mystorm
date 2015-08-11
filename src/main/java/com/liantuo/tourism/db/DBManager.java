package com.liantuo.tourism.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.liantuo.tourism.config.Global;

public class DBManager {

	private static String DRIVER = Global.getConfig("jdbc.driver");
	private static String URL = Global.getConfig("jdbc.url");;
	private static String USERNAME = Global.getConfig("jdbc.username");;
	private static String PASSWORD = Global.getConfig("jdbc.password");;

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
