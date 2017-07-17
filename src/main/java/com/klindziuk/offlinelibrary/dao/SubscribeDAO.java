package com.klindziuk.offlinelibrary.dao;

import java.util.List;

import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.model.Subscription;

public interface SubscribeDAO {
	boolean addNewSubscribtion(int userId, int bookId)throws DAOException;				
	boolean setSubscriptionActive(int userId, int bookId) throws DAOException;  		
	boolean setSubscriptionInActive(int userId, int bookId) throws DAOException;  		
	List<Subscription> getAllSubscriptions() throws DAOException;						
}
