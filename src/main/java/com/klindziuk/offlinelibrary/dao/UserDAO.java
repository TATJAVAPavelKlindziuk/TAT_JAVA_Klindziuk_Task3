package com.klindziuk.offlinelibrary.dao;

import java.util.List;

import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.model.User;

public interface UserDAO {
	boolean giveAdminRole(int userId) throws DAOException;											
	boolean removeAdminRole(int userId) throws DAOException;										
	boolean banUser(int userId) throws DAOException;												
	boolean unBanUser(int userId) throws DAOException;												
	boolean signIn(String login, String password) throws DAOException;
	boolean signOut(String login, String password) throws DAOException;
	boolean registration(User user) throws DAOException;											
	boolean updateProfile(int userId, String name) throws DAOException;								
	boolean addBookToWishList(int userId, int bookId) throws DAOException;							
	boolean removeBookFromWishList(int userId, int bookId) throws DAOException;    					
	User getUser(int id) throws DAOException;									   					
	List<User> getAllUsers() throws DAOException;                      								
}
