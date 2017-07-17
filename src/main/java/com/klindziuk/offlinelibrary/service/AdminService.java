package com.klindziuk.offlinelibrary.service;

import java.util.List;

import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.model.Subscription;
import com.klindziuk.offlinelibrary.model.User;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public interface AdminService {																			
	boolean addBook(Book book) throws ServiceException;                    									
	boolean setBookDeprecated(Book book) throws ServiceException;                                       	
	boolean deleteBook(int bookId) throws ServiceException;													
	boolean deleteBook(Book book) throws ServiceException;													
	boolean giveAdminRole(int userId) throws ServiceException;												
	boolean removeAdminRole(int userId) throws ServiceException;											
	boolean banUser(int userId) throws ServiceException;													
	boolean unBanUser(int userId) throws ServiceException;													
	boolean markIssuance(int userId,int bookId) throws ServiceException;									
	boolean markReturn(int userId,int bookId) throws ServiceException;										
	boolean updateBookDescription(int bookId, String name, String author) throws ServiceException;          
	Book getBook(int bookId) throws ServiceException;													   
	List<Book> getAllBooks() throws ServiceException; 													   
	List<Book> getUserBooks(int userId) throws ServiceException; 										  	
	List<Book> findByName(String name) throws ServiceException;							                   	
	List<Book> findByAuthor(String author) throws ServiceException;						                   	
	User getUser(int userId) throws ServiceException;									   					
	List<User> getAllUsers() throws ServiceException;                      									
	List<Subscription> getAllSubscriptions() throws ServiceException;										
}
