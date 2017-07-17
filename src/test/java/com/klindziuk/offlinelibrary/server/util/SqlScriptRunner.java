package com.klindziuk.offlinelibrary.server.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.common.jdbc.ScriptRunner;

public final class SqlScriptRunner {
	private static final Logger logger = Logger.getLogger(SqlScriptRunner.class);
	private static final String EXCEPTION_MESSAGE = "Failed to execute ";

	private SqlScriptRunner() {
	}

	public static void run(Connection connection, String path) throws ClassNotFoundException, SQLException {
		try {
			ScriptRunner runner = new ScriptRunner(connection, false, false);
			Reader reader = new BufferedReader(new FileReader(path));
			runner.runScript(reader);
		} catch (IOException ioex) {
			logger.error(EXCEPTION_MESSAGE + path, ioex);
		}
	}
}
