package com.klindziuk.offlinelibrary.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.klindziuk.offlinelibrary.dao.UserDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.util.DBconnector;
import com.klindziuk.offlinelibrary.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SQLUserDAO implements UserDAO {
	private static final boolean USER_ADMIN_ON_INITIALIZATION = false;
	private static final boolean USER_BANNED_ON_INITIALIZATION = true;
	private static final boolean USER_ENABLE_BAN = true;
	private static final boolean USER_DISABLE_BAN = false;
	private static final boolean USER_ENABLE_ADMIN = true;
	private static final boolean USER_DISABLE_ADMIN = false;
	private static final String USER_ID_COLUMN_LABEL = "id";
	private static final String USER_LOGIN_COLUMN_LABEL = "login";
	private static final String USER_PASSWORD_COLUMN_LABEL = "password";
	private static final String USER_NAME_COLUMN_LABEL = "name";
	private static final String USER_IS_ADMIN_COLUMN_LABEL = "isAdmin";
	private static final String USER_IS_BANNED_LABEL = "isBanned";
	private static final String USER_CREATION_DATE_COLUMN_LABEL = "creationDate";
	private static final String LOGIN_EXCEPTION_MESSAGE = "Login error.There are no users with this credentials.";
	private static final String DUPLICATE_LOGIN_EXCEPTION_MESSAGE = "User with this name already registered.";
	private static final String SQL_EXCEPTION_MESSAGE = "Cannot perform SQL command";
	private static final String EMPTY_EXCEPTION_MESSAGE = "There are no users";
	private static final String ADD_BOOK_TO_WISHLIST_QUERY = "INSERT INTO wishes (w_user, w_book) VALUES (?, ? )";
	private static final String REMOVE_BOOK_FROM_WISHLIST_QUERY = "DELETE FROM wishes WHERE w_user = ? AND w_book = ?";
	private static final String GET_USER_QUERY = "SELECT id, login, password, name, isAdmin, isBanned, creationDate FROM users WHERE id = ?";
	private static final String USER_CREDENTIALS_QUERY = "SELECT login, password, isAdmin, isBanned FROM users ";
	private static final String USER_REGISTER_QUERY = "INSERT INTO users (login, password, name, isAdmin, isBanned) VALUES (?, ?, ?, ?, ?)";
	private static final String USER_UPDATE_QUERY = "UPDATE users SET name = ?  WHERE id = ?";
	private static final String SELECT_ALL_USERS_QUERY = "SELECT id, login, password, name, isAdmin, isBanned, creationDate FROM users ";
	private static final String ADMIN_QUERY = "UPDATE users SET isAdmin = ?  WHERE id = ?";
	private static final String BAN_QUERY = "UPDATE users SET isBanned = ?  WHERE id = ?";
	
	DBconnector connector;
	PreparedStatement preparedStatement;
	Statement statement;
	ResultSet resultSet;

	public SQLUserDAO() {
		connector = DBconnector.getInstance();
	}

	@Override
	public boolean signIn(String login, String password) throws DAOException {
		String searchedLogin = "";
		String searchedPassword = "";
		boolean isAdmin = false;
		boolean isBanned = false;
		int matchLogin = 0;
		int matchPassword = 0;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(USER_CREDENTIALS_QUERY);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				searchedLogin = resultSet.getString(USER_LOGIN_COLUMN_LABEL);
				searchedPassword = resultSet.getString(USER_PASSWORD_COLUMN_LABEL);
				isAdmin = resultSet.getBoolean(USER_IS_ADMIN_COLUMN_LABEL);
				isBanned = resultSet.getBoolean(USER_IS_BANNED_LABEL);
				if (searchedLogin.equals(login)) {
					matchLogin++;
				}
				if (searchedPassword.equals(password)) {
					matchPassword++;
				}
				if (login.equals(searchedLogin) && (password.equals(searchedPassword) && isAdmin && !isBanned)) {
					return true;
				} else if (login.equals(searchedLogin)
						&& (password.equals(searchedPassword) && !isAdmin && !isBanned)) {
					return false;
				}
			}
			if ((0 == matchLogin) || (0 == matchPassword)) {
				throw new DAOException(LOGIN_EXCEPTION_MESSAGE);
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, LOGIN_EXCEPTION_MESSAGE);
		} finally {
			try {
				resultSet.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return false;
	}

	@Override
	public boolean registration(User user) throws DAOException {
		boolean rowInserted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(USER_REGISTER_QUERY);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setBoolean(4, USER_ADMIN_ON_INITIALIZATION);
			preparedStatement.setBoolean(5, USER_BANNED_ON_INITIALIZATION);
			rowInserted = preparedStatement.executeUpdate() > 0;
		} catch (MySQLIntegrityConstraintViolationException ex) {
			throw new DAOException(ex, DUPLICATE_LOGIN_EXCEPTION_MESSAGE);
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
	public User getUser(int id) throws DAOException {
		User user = null;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(GET_USER_QUERY);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String login = resultSet.getString(USER_LOGIN_COLUMN_LABEL);
				String password = resultSet.getString(USER_PASSWORD_COLUMN_LABEL);
				String name = resultSet.getString(USER_NAME_COLUMN_LABEL);
				boolean isAdmin = resultSet.getBoolean(USER_IS_ADMIN_COLUMN_LABEL);
				boolean isBanned = resultSet.getBoolean(USER_IS_BANNED_LABEL);
				Timestamp createdDate = resultSet.getTimestamp(USER_CREATION_DATE_COLUMN_LABEL);
				user = new User(id, login, password, name, isAdmin, isBanned, createdDate);
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				preparedStatement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return user;
	}

	@Override
	public boolean addBookToWishList(int userId, int bookId) throws DAOException {
		boolean rowInserted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(ADD_BOOK_TO_WISHLIST_QUERY);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, bookId);
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
	public boolean removeBookFromWishList(int userId, int bookId) throws DAOException {
		boolean rowInserted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(REMOVE_BOOK_FROM_WISHLIST_QUERY);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, bookId);
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
	public boolean updateProfile(int userId, String name) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(USER_UPDATE_QUERY);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, userId);
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
	public boolean giveAdminRole(int userId) throws DAOException {
		return changeUserPrivilegies(userId, ADMIN_QUERY, USER_ENABLE_ADMIN);
	}

	@Override
	public boolean removeAdminRole(int userId) throws DAOException {
		return changeUserPrivilegies(userId, ADMIN_QUERY, USER_DISABLE_ADMIN);
	}

	@Override
	public boolean banUser(int userId) throws DAOException {
		return changeUserPrivilegies(userId, BAN_QUERY, USER_ENABLE_BAN);
	}

	@Override
	public boolean unBanUser(int userId) throws DAOException {
		return changeUserPrivilegies(userId, BAN_QUERY, USER_DISABLE_BAN);
	}

	@Override
	public List<User> getAllUsers() throws DAOException {
		List<User> users = new ArrayList<>();
		try {
			connector.connect();
			statement = connector.getJdbcConnection().createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_USERS_QUERY);
			while (resultSet.next()) {
				int id = resultSet.getInt(USER_ID_COLUMN_LABEL);
				String login = resultSet.getString(USER_LOGIN_COLUMN_LABEL);
				String password = resultSet.getString(USER_PASSWORD_COLUMN_LABEL);
				String name = resultSet.getString(USER_NAME_COLUMN_LABEL);
				boolean isAdmin = resultSet.getBoolean(USER_IS_ADMIN_COLUMN_LABEL);
				boolean isBanned = resultSet.getBoolean(USER_IS_BANNED_LABEL);
				Timestamp creationDate = resultSet.getTimestamp(USER_CREATION_DATE_COLUMN_LABEL);
				User user = new User(id, login, password, name, isAdmin, isBanned, creationDate);
				users.add(user);
			}
			if(users.isEmpty()){
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
		return users;
	}

	private boolean changeUserPrivilegies(int userId, String query, boolean isEnable) throws DAOException {
		boolean rowUpdated = false;
		try {
			String sql = query;
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(sql);
			preparedStatement.setBoolean(1, isEnable);
			preparedStatement.setInt(2, userId);
			rowUpdated = preparedStatement.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
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
	public boolean signOut(String login, String password) throws DAOException {
		// TODO Will be implemented ASAP
		return false;
	}
}
