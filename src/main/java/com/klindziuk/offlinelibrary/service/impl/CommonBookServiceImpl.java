package com.klindziuk.offlinelibrary.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.factory.DAOFactory;
import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public class CommonBookServiceImpl {
	protected static final String NAME_NULL_MESSAGE_EXCEPTION = "There is no book with this empty name.";
	protected static final String AUTHOR_NULL_MESSAGE_EXCEPTION = "There is no user with empty author.";
	protected static final String BOOK_NULL_MESSAGE_EXCEPTION = "There is no book with this id.";
	protected static final String USER_NULL_MESSAGE_EXCEPTION = "There is no user with this id.";
	private final BookDAO bookDao = DAOFactory.getInstance().getBookDAO();

	public Book getBook(int bookId) throws ServiceException {
		Book book = null;
		if (0 == bookId) {
			throw new ServiceException(BOOK_NULL_MESSAGE_EXCEPTION);
		}
		try {
			book = bookDao.getBook(bookId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return book;
	}

	public List<Book> getAllBooks() throws ServiceException {
		List<Book> books = new ArrayList<>();
		try {
			books = bookDao.getAllBooks();
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return books;
	}

	public List<Book> getUserBooks(int userId) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(USER_NULL_MESSAGE_EXCEPTION);
		}
		List<Book> books = new ArrayList<>();
		try {
			books = bookDao.getUserBooks(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return books;
	}

	public List<Book> findByName(String name) throws ServiceException {
		if (null == name || name.isEmpty()) {
			throw new ServiceException(NAME_NULL_MESSAGE_EXCEPTION);
		}
		List<Book> books = new ArrayList<>();
		try {
			books = bookDao.findByName(name);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return books;
	}

	public List<Book> findByAuthor(String author) throws ServiceException {
		if (null == author || author.isEmpty()) {
			throw new ServiceException(AUTHOR_NULL_MESSAGE_EXCEPTION);
		}
		List<Book> books = new ArrayList<>();
		try {
			books = bookDao.findByAuthor(author);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return books;
	}
}
