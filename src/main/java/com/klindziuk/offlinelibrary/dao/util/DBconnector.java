package com.klindziuk.offlinelibrary.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides connection to database.
 */
public class DBconnector {
	private static final String URL = "jdbc:mysql://localhost:3306/library";
	private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static DBconnector instance = null;
	private Connection jdbcConnection;
	
	private DBconnector() {}

	public static DBconnector getInstance() {
		if (instance == null) {
			instance = new DBconnector();
		}
		return instance;
	}

	public void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName(CLASS_NAME);
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
	}

	public void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public Connection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(Connection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}

	public void setAutoCommitFalse() throws SQLException {
		jdbcConnection.setAutoCommit(false);
	}

	public void setAutoCommitTrue() throws SQLException {
		jdbcConnection.setAutoCommit(true);
	}
}
