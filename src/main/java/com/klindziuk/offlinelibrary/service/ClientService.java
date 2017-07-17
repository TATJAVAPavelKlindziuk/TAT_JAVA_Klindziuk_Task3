package com.klindziuk.offlinelibrary.service;

import java.util.List;

import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.model.User;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public interface ClientService {																				
	boolean signIn(String login, String password) throws ServiceException;									
	boolean signOut(String login, String password) throws ServiceException;									
	boolean registration(User user) throws ServiceException;											    
	List<Book> getAllBooks() throws ServiceException;												        
}
