package com.liantuo.tourism.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.liantuo.tourism.config.Global;

public class DBManager {
	public static Connection getConnection() {
		String dirver = Global.getConfig("jdbc.driver");
		String url = Global.getConfig("jdbc.url");
		String username = Global.getConfig("jdbc.username");
		String password = Global.getConfig("jdbc.password");
		Connection conn = null;
		try {
			Class.forName(dirver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
