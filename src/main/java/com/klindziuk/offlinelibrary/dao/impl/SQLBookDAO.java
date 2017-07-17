package com.klindziuk.offlinelibrary.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.util.DBconnector;
import com.klindziuk.offlinelibrary.model.Book;

public class SQLBookDAO implements BookDAO {

	private static final boolean SET_BOOK_DEPRECATED = true;
	private static final boolean SET_IS_AVAILABLE = true;
	private static final boolean SET_IS_UNAVAILABLE = false;
	private static final String BOOK_ID_COLUMN_LABEL = "id";
	private static final String BOOK_NAME_COLUMN_LABEL = "name";
	private static final String BOOK_AUTHOR_COLUMN_LABEL = "author";
	private static final String BOOK_YEAR_COLUMN_LABEL = "year";
	private static final String BOOK_IS_AVAILABLE_COLUMN_LABEL = "isAvailable";
	private static final String BOOK_IS_DEPRECATED_LABEL = "isDeprecated";
	private static final String BOOKS_ADDITION_DATE_COLUMN_LABEL = "additionDate";
	private static final String SQL_EXCEPTION_MESSAGE = "Cannot perform SQL command";
	private static final String EMPTY_EXCEPTION_MESSAGE = "Unfortunatelly we don't have this books.";
	private static final String FINDBY_NAME_EXCEPTION_MESSAGE = "There are no books with this name";
	private static final String FINDBY_AUTHOR_EXCEPTION_MESSAGE = "There are no authors with this name";
	private static final String BOOK_UPDATE_QUERY = "UPDATE books SET isAvailable = ?  WHERE id = ?";
	private static final String BOOK_NAME_QUERY = "SELECT id, name from books";
	private static final String BOOK_AUTHOR_QUERY = "SELECT id, author from books";
	private static final String ADD_BOOK_QUERY = "INSERT INTO books (name, author, year, isAvailable, isDeprecated) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_BOOK_QUERY = "UPDATE books SET name = ?, author = ?  WHERE id = ?";
	private static final String DELETE_BOOK_QUERY = "DELETE FROM books where id = ?";
	private static final String DEPRECATE_BOOK_QUERY = "UPDATE books SET name = ?, author = ?, year = ?, isAvailable = ?, isDeprecated = ?"
			+ " WHERE id = ?";
	private static final String GET_BOOK_QUERY = "SELECT books.id, books.name, books.author, books.year,"
			+ " books.isAvailable, books.isDeprecated, books.additionDate FROM books WHERE id = ?";
	private static final String SELECT_ALL_BOOK_QUERY = "SELECT books.id, books.name, books.author, books.year,"
			+ " books.isAvailable, books.isDeprecated, books.additionDate FROM books ";
	private static final String SELECT_USER_BOOKS_QUERY = "SELECT DISTINCT books.id, books.name, books.author, books.year,"
			+ " books.isAvailable, books.isDeprecated, books.additionDate FROM library.books "
			+ "INNER JOIN library.subscriptions ON library.books.id = library.subscriptions.sb_book "
			+ "INNER JOIN library.users ON library.users.id = library.subscriptions.sb_user "
			+ "WHERE library.users.id = ? AND subscriptions.sb_is_active = 1";

	DBconnector connector;
	PreparedStatement preparedStatement;
	Statement statement;
	ResultSet resultSet;

	public SQLBookDAO() {
		connector = DBconnector.getInstance();
	}

	@Override
	public boolean addBook(Book book) throws DAOException {
		boolean rowInserted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(ADD_BOOK_QUERY);
			preparedStatement.setString(1, book.getName());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setInt(3, book.getYear());
			preparedStatement.setBoolean(4, book.isAvailable());
			preparedStatement.setBoolean(5, book.isDeprecated());
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
	public boolean updateBookDescription(int bookId, String name, String author) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(UPDATE_BOOK_QUERY);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, author);
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
	public boolean setBookDeprecated(Book book) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(DEPRECATE_BOOK_QUERY);
			preparedStatement.setString(1, book.getName());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setInt(3, book.getYear());
			preparedStatement.setBoolean(4, book.isAvailable());
			preparedStatement.setBoolean(5, SET_BOOK_DEPRECATED);
			preparedStatement.setInt(6, book.getId());
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
	public boolean deleteBook(int id) throws DAOException {
		boolean rowDeleted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(DELETE_BOOK_QUERY);
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
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
		return rowDeleted;
	}

	@Override
	public boolean deleteBook(Book book) throws DAOException {
		boolean rowDeleted = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(DELETE_BOOK_QUERY);
			preparedStatement.setInt(1, book.getId());
			rowDeleted = preparedStatement.executeUpdate() > 0;
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
		return rowDeleted;
	}

	@Override
	public Book getBook(int id) throws DAOException {
		Book book = null;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(GET_BOOK_QUERY);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString(BOOK_NAME_COLUMN_LABEL);
				String author = resultSet.getString(BOOK_AUTHOR_COLUMN_LABEL);
				int year = resultSet.getInt(BOOK_YEAR_COLUMN_LABEL);
				boolean isAvailable = resultSet.getBoolean(BOOK_IS_AVAILABLE_COLUMN_LABEL);
				boolean isDeprecated = resultSet.getBoolean(BOOK_IS_DEPRECATED_LABEL);
				Timestamp additionDate = resultSet.getTimestamp(BOOKS_ADDITION_DATE_COLUMN_LABEL);
				book = new Book(id, name, author, year, isAvailable, isDeprecated, additionDate);
			} else {
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
				preparedStatement.close();
			} catch (Exception e) {
				/* ignored */ }
			try {
				connector.disconnect();
			} catch (Exception e) {
				/* ignored */ }
		}
		return book;
	}

	@Override
	public List<Book> getAllBooks() throws DAOException {
		List<Book> books = new ArrayList<>();
		try {
			connector.connect();
			statement = connector.getJdbcConnection().createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_BOOK_QUERY);
			while (resultSet.next()) {
				int id = resultSet.getInt(BOOK_ID_COLUMN_LABEL);
				String name = resultSet.getString(BOOK_NAME_COLUMN_LABEL);
				String author = resultSet.getString(BOOK_AUTHOR_COLUMN_LABEL);
				int year = resultSet.getInt(BOOK_YEAR_COLUMN_LABEL);
				boolean isAvailable = resultSet.getBoolean(BOOK_IS_AVAILABLE_COLUMN_LABEL);
				boolean isDeprecated = resultSet.getBoolean(BOOK_IS_DEPRECATED_LABEL);
				Timestamp additionDate = resultSet.getTimestamp(BOOKS_ADDITION_DATE_COLUMN_LABEL);
				Book book = new Book(id, name, author, year, isAvailable, isDeprecated, additionDate);
				books.add(book);
			}
			if (books.isEmpty()) {
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
		return books;
	}

	@Override
	public List<Book> getUserBooks(int userId) throws DAOException {
		List<Book> books = new ArrayList<>();
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(SELECT_USER_BOOKS_QUERY);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(BOOK_ID_COLUMN_LABEL);
				String name = resultSet.getString(BOOK_NAME_COLUMN_LABEL);
				String author = resultSet.getString(BOOK_AUTHOR_COLUMN_LABEL);
				int year = resultSet.getInt(BOOK_YEAR_COLUMN_LABEL);
				boolean isAvailable = resultSet.getBoolean(BOOK_IS_AVAILABLE_COLUMN_LABEL);
				boolean isDeprecated = resultSet.getBoolean(BOOK_IS_DEPRECATED_LABEL);
				Timestamp additionDate = resultSet.getTimestamp(BOOKS_ADDITION_DATE_COLUMN_LABEL);
				Book book = new Book(id, name, author, year, isAvailable, isDeprecated, additionDate);
				books.add(book);
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
		return books;
	}
	
	@Override
	public List<Book> findByName(String name) throws DAOException {
		List<Book> list = new ArrayList<>();
		Map<Integer, String> map = new HashMap<>();
		int bookId = 0;
		String searchedBookName = "";
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(BOOK_NAME_QUERY);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				bookId = resultSet.getInt(BOOK_ID_COLUMN_LABEL);
				searchedBookName = resultSet.getString(BOOK_NAME_COLUMN_LABEL);
				map.put(bookId, searchedBookName);
			}
			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				if (name.contains(entry.getValue())) {
					list.add(getBook(entry.getKey()));
				}
			}
			if (list.isEmpty()) {
				throw new DAOException(EMPTY_EXCEPTION_MESSAGE);
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, FINDBY_NAME_EXCEPTION_MESSAGE);
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
		return list;
	}

	@Override
	public List<Book> findByAuthor(String author) throws DAOException {
		List<Book> list = new ArrayList<>();
		Map<Integer, String> map = new HashMap<>();
		int bookId = 0;
		String searchedAuthorName = "";
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(BOOK_AUTHOR_QUERY);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				bookId = resultSet.getInt(BOOK_ID_COLUMN_LABEL);
				searchedAuthorName = resultSet.getString(BOOK_AUTHOR_COLUMN_LABEL);
				map.put(bookId, searchedAuthorName);
			}
			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				if (author.contains(entry.getValue())) {
					list.add(getBook(entry.getKey()));
				}
			}
			if (list.isEmpty()) {
				throw new DAOException(EMPTY_EXCEPTION_MESSAGE);
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			throw new DAOException(sqlex, FINDBY_AUTHOR_EXCEPTION_MESSAGE);
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
		return list;
	}

	@Override
	public boolean setBookAvailable(int bookId) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(BOOK_UPDATE_QUERY);
			preparedStatement.setBoolean(1, SET_IS_AVAILABLE);
			preparedStatement.setInt(2, bookId);
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
	public boolean setBookUnavailable(int bookId) throws DAOException {
		boolean rowUpdated = false;
		try {
			connector.connect();
			preparedStatement = connector.getJdbcConnection().prepareStatement(BOOK_UPDATE_QUERY);
			preparedStatement.setBoolean(1, SET_IS_UNAVAILABLE);
			preparedStatement.setInt(2, bookId);
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
}
