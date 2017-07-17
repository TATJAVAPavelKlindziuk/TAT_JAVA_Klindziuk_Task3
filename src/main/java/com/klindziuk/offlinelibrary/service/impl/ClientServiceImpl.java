package com.klindziuk.offlinelibrary.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.UserDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.factory.DAOFactory;
import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.model.User;
import com.klindziuk.offlinelibrary.service.ClientService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public class ClientServiceImpl implements ClientService {
	private static final String CREDENTIALS_NULL_MESSAGE_EXCEPTION = "Login or password cannot be null.";
	private static final String USER_NULL_MESSAGE_EXCEPTION = "There is no user found.";
	private final UserDAO userDao =  DAOFactory.getInstance().getUserDAO();
	private final BookDAO bookDao =  DAOFactory.getInstance().getBookDAO();
	
	@Override
	public boolean signIn(String login, String password) throws ServiceException {
		boolean result = false;
		if((null == login ) || ( null == password)) {
			throw new ServiceException(CREDENTIALS_NULL_MESSAGE_EXCEPTION);
		}
		try {
			result = userDao.signIn(login, password);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean registration(User user) throws ServiceException {
		boolean result = false;
		if(null == user ) {
			throw new ServiceException(USER_NULL_MESSAGE_EXCEPTION);
		}
		try {
			result = userDao.registration(user);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public List<Book> getAllBooks() throws ServiceException {
		List<Book> books = new ArrayList<>();
		try {
			books =  bookDao.getAllBooks();
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return books;
	}

	@Override
	public boolean signOut(String login, String password) throws ServiceException {
		// TODO Will be implemented ASAP
		return false;
	}
}
