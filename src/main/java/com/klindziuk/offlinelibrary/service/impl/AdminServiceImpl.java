package com.klindziuk.offlinelibrary.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.SubscribeDAO;
import com.klindziuk.offlinelibrary.dao.UserDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.factory.DAOFactory;
import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.model.Subscription;
import com.klindziuk.offlinelibrary.model.User;
import com.klindziuk.offlinelibrary.service.AdminService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public class AdminServiceImpl extends CommonBookServiceImpl implements AdminService {
	private static final String NULL_BOOK_EXCEPTION_MESSAGE = "Cannot add null.";
	private static final String NULL_BOOK_DESCPRIPTION_MESSAGE = "Cannot update description with null.";
	private static final String NULL_BOOK_UPDATE_MESSAGE = "Cannot update with zero parameters.";
	private static final String NULL_BOOK_DELETE_EXCEPTION_MESSAGE = "Cannot remove null.";
	private static final String NULL_USER_EXCEPTION_MESSAGE = "Cannot perform operation with null user.";
	private final UserDAO userDao = DAOFactory.getInstance().getUserDAO();
	private final BookDAO bookDao = DAOFactory.getInstance().getBookDAO();
	private final SubscribeDAO sbDao = DAOFactory.getInstance().getSubscriptionDAO();

	@Override
	public boolean addBook(Book book) throws ServiceException {
		if (null == book) {
			throw new ServiceException(NULL_BOOK_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = bookDao.addBook(book);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean setBookDeprecated(Book book) throws ServiceException {
		if (null == book) {
			throw new ServiceException(NULL_BOOK_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = bookDao.setBookDeprecated(book);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean deleteBook(int bookId) throws ServiceException {
		if (0 == bookId) {
			throw new ServiceException(NULL_BOOK_DELETE_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = bookDao.deleteBook(bookId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean deleteBook(Book book) throws ServiceException {
		if (null == book) {
			throw new ServiceException(NULL_BOOK_DELETE_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = bookDao.deleteBook(book);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean giveAdminRole(int userId) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(NULL_USER_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = userDao.giveAdminRole(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean removeAdminRole(int userId) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(NULL_USER_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = userDao.removeAdminRole(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean banUser(int userId) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(NULL_USER_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = userDao.banUser(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean unBanUser(int userId) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(NULL_USER_EXCEPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = userDao.unBanUser(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean markIssuance(int userId, int bookId) throws ServiceException {
		if (0 == userId || 0 == bookId) {
			throw new ServiceException(NULL_BOOK_UPDATE_MESSAGE);
		}
		boolean result = false;
		try {
			result = sbDao.addNewSubscribtion(userId, bookId) && bookDao.setBookUnavailable(bookId);
			userDao.removeBookFromWishList(userId, bookId);	
			} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean markReturn(int userId, int bookId) throws ServiceException {
		if (0 == userId || 0 == bookId) {
			throw new ServiceException(NULL_BOOK_UPDATE_MESSAGE);
		}
		boolean result = false;
		try {
			result = sbDao.setSubscriptionInActive(userId, bookId) && bookDao.setBookAvailable(bookId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean updateBookDescription(int userId, String name, String author) throws ServiceException {
		if (0 == userId) {
			throw new ServiceException(NULL_BOOK_UPDATE_MESSAGE);
		}
		if (null == name || null == author) {
			throw new ServiceException(NULL_BOOK_DESCPRIPTION_MESSAGE);
		}
		boolean result = false;
		try {
			result = bookDao.updateBookDescription(userId, name, author);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public User getUser(int userId) throws ServiceException {
		User user = null;
		if (0 == userId) {
			throw new ServiceException(BOOK_NULL_MESSAGE_EXCEPTION);
		}
		try {
			user = userDao.getUser(userId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() throws ServiceException {
		List<User> users = new ArrayList<>();
		try {
			users = userDao.getAllUsers();
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return users;
	}

	@Override
	public List<Subscription> getAllSubscriptions() throws ServiceException {
		List<Subscription> subs = new ArrayList<>();
		try {
			subs = sbDao.getAllSubscriptions();
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return subs;
	}
}
