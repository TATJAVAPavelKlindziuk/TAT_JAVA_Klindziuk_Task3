package com.klindziuk.offlinelibrary.service;

import java.util.List;

import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;

public interface LibraryService {																				
	boolean addBookToWishList(int userId, int bookId) throws ServiceException;							
	boolean removeBookFromWishList(int userId, int bookId) throws ServiceException;    					
	boolean updateProfile(int userId, String name) throws ServiceException;								
	Book getBook(int bookId) throws ServiceException;													
	List<Book> getAllBooks() throws ServiceException; 													
	List<Book> getUserBooks(int userId) throws ServiceException;                                        
	List<Book> findByName(String name) throws ServiceException;											
	List<Book> findByAuthor(String author) throws ServiceException;                                     
}


