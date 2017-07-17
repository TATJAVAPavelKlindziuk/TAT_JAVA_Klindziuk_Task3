package com.klindziuk.offlinelibrary.dao.factory;

import com.klindziuk.offlinelibrary.dao.BookDAO;
import com.klindziuk.offlinelibrary.dao.SubscribeDAO;
import com.klindziuk.offlinelibrary.dao.UserDAO;
import com.klindziuk.offlinelibrary.dao.impl.SQLBookDAO;
import com.klindziuk.offlinelibrary.dao.impl.SQLSubscribeDAO;
import com.klindziuk.offlinelibrary.dao.impl.SQLUserDAO;

public class DAOFactory {
	private static final DAOFactory instance = new DAOFactory();
	private final UserDAO sqlUserImpl = new SQLUserDAO();
	private final BookDAO sqlBookImpl = new SQLBookDAO();
	private final SubscribeDAO sqlSubscribeImpl = new SQLSubscribeDAO();
	
	private DAOFactory() {}
	
	public static DAOFactory getInstance() {
		return instance;
	}
		
	public UserDAO getUserDAO() {
		return sqlUserImpl;
	}
	
	public BookDAO getBookDAO() {
		return sqlBookImpl;
	}
	
	public SubscribeDAO getSubscriptionDAO() {
		return sqlSubscribeImpl;
	}
}
