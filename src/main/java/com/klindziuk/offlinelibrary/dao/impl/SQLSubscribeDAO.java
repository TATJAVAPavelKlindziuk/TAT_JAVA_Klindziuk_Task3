package com.klindziuk.offlinelibrary.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.klindziuk.offlinelibrary.dao.SubscribeDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.util.DBconnector;
import com.klindziuk.offlinelibrary.model.Subscription;

public class SQLSubscribeDAO implements SubscribeDAO {
	private static final boolean SET_SB_INACTIVE = false;
	private static final boolean SB_IS_ACTIVE = true;
	private static final String SB_ID_COLUMN_LABEL = "sb_id";
	private static final String SB_USER_COLUMN_LABEL = "sb_user";
	private static final String SB_BOOK_COLUMN_LABEL = "sb_book";
	private static final String SB_START_COLUMN_LABEL = "sb_start";
	private static final String SB_FINISH_COLUMN_LABEL = "sb_finish";
	private static final String SB_IS_ACTIVE_LABEL = "sb_is_active";
	private static final String SQL_EXCEPTION_MESSAGE = "Cannot perform SQL command";
	private static final String EMPTY_EXCEPTION_MESSAGE = "There are no subscriptions";
	private static final String ADD_SB_QUERY = "INSERT INTO subscriptions (sb_user, sb_book, sb_is_active) VALUES (?, ?, ?)";
	private static final String UPDATE_SB_QUERY = "UPDATE subscriptions SET sb_is_active = ? WHERE sb_user = ? AND sb_book = ?";
	private static final String SELECT_ALL_SUBSCRIPTIONS_QUERY = "SELECT sb_id, sb_user, sb_book, sb_start, sb_finish,"
			+ " sb_is_active FROM subscriptions";

	DBconnector connector;
	PreparedStatement preparedStatement;
	Statement statement;
	ResultSet resultSet;

	public SQLSubscribeDAO() {
		connector = DBconnector.getInstance();
	}

	@Override
	public boolean addNewSubscribtion(int userId, int bookId) throws DAOException {
		boolean rowInserted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(ADD_SB_QUERY);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, bookId);
			preparedStatement.setBoolean(3, SB_IS_ACTIVE);
			rowInserted = preparedStatement.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, SQL_EXCEPTION_MESSAGE);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return rowInserted;
	}

	@Override
	public boolean setSubscriptionInActive(int userId, int bookId) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(UPDATE_SB_QUERY);
			preparedStatement.setBoolean(1, SET_SB_INACTIVE);
			preparedStatement.setInt(2, userId);
			preparedStatement.setInt(3, bookId);
			rowUpdated = preparedStatement.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, SQL_EXCEPTION_MESSAGE);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return rowUpdated;
	}

	@Override
	public boolean setSubscriptionActive(int userId, int bookId) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(UPDATE_SB_QUERY);
			preparedStatement.setBoolean(1, SB_IS_ACTIVE);
			preparedStatement.setInt(2, userId);
			preparedStatement.setInt(3, bookId);
			rowUpdated = preparedStatement.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, SQL_EXCEPTION_MESSAGE);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return rowUpdated;
	}

	@Override
	public List<Subscription> getAllSubscriptions() throws DAOException {
		List<Subscription> subscriptions = new ArrayList<>();
		try {
			connector.connect();
			statement = connector.getJdbcConnection().createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_SUBSCRIPTIONS_QUERY);
			while (resultSet.next()) {
				int id = resultSet.getInt(SB_ID_COLUMN_LABEL);
				int userId = resultSet.getInt(SB_USER_COLUMN_LABEL);
				int bookId = resultSet.getInt(SB_BOOK_COLUMN_LABEL);
				Timestamp start = resultSet.getTimestamp(SB_START_COLUMN_LABEL);
				Timestamp finish = resultSet.getTimestamp(SB_FINISH_COLUMN_LABEL);
				boolean isActive = resultSet.getBoolean(SB_IS_ACTIVE_LABEL);
				Subscription sb = new Subscription(id, userId, bookId, start, finish, isActive);
				subscriptions.add(sb);
			}
			if(subscriptions.isEmpty()) {
				throw new DAOException(EMPTY_EXCEPTION_MESSAGE);
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, SQL_EXCEPTION_MESSAGE);
		} finally {
			try {
				resultSet.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				statement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return subscriptions;
	}
}
