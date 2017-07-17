package com.klindziuk.offlinelibrary.service.impl;

import java.util.List;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.UserDAO;
import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.dao.factory.DAOFactory;
import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.service.LibraryService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public class LibraryServiceImpl extends CommonBookServiceImpl implements LibraryService {

	private static final String BOOK_REMOVE_MESSAGE_EXCEPTION = "Cannot remove book from wishlist.You subscribed to this book.";
	private static final String UPDATE_NAME_NULL_MESSAGE_EXCEPTION = "Cannot update name with empty string.";
	private static final String BOOK_NULL_MESSAGE_EXCEPTION = "There is no book with this id.";
	private final UserDAO userDao = DAOFactory.getInstance().getUserDAO();
	private final BookDAO bookDao = DAOFactory.getInstance().getBookDAO();

	@Override
	public boolean addBookToWishList(int userId, int bookId) throws ServiceException {
		boolean result = false;
		if (0 == userId) {
			throw new ServiceException(USER_NULL_MESSAGE_EXCEPTION);
		} else if (0 == bookId) {
			throw new ServiceException(BOOK_NULL_MESSAGE_EXCEPTION);
		}
		try {
			result = userDao.addBookToWishList(userId, bookId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean removeBookFromWishList(int userId, int bookId) throws ServiceException {
		boolean result = false;
		if (0 == userId) {
			throw new ServiceException(USER_NULL_MESSAGE_EXCEPTION);
		} else if (0 == bookId) {
			throw new ServiceException(BOOK_NULL_MESSAGE_EXCEPTION);
		}
		checkBookSubscription(userId, bookId);
		try {	
			result = userDao.removeBookFromWishList(userId, bookId);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
		return result;
	}

	@Override
	public boolean updateProfile(int userId, String name) throws ServiceException {
		boolean result = false;
		if (0 == userId) {
			throw new ServiceException(USER_NULL_MESSAGE_EXCEPTION);
		}
		if (null == name || name.isEmpty()) {
			throw new ServiceException(UPDATE_NAME_NULL_MESSAGE_EXCEPTION);
		}
		try {
			result = userDao.updateProfile(userId, name);
		} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
    	return result;
	}

	private void checkBookSubscription(int userId,int bookId) throws ServiceException{
		try {
			List<Book> userBooks = bookDao.getUserBooks(userId);
			for(Book b: userBooks){
				if(b.getId() == bookId){
					throw new ServiceException(BOOK_REMOVE_MESSAGE_EXCEPTION);	
				}
			}
				} catch (DAOException daoex) {
			throw new ServiceException(daoex, daoex.getMessage());
		}
	}
}
