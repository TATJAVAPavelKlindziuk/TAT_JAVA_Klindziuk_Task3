package com.klindziuk.offlinelibrary.dao;

import java.util.List;

import com.klindziuk.offlinelibrary.dao.exception.DAOException;
import com.klindziuk.offlinelibrary.model.Book;

public interface BookDAO {
	boolean addBook(Book book) throws DAOException;                    								
	boolean updateBookDescription(int bookId, String name, String author) throws DAOException;      
	boolean setBookAvailable(int bookId) throws DAOException;       							    
	boolean setBookUnavailable(int bookId) throws DAOException;      							    
	boolean setBookDeprecated(Book book) throws DAOException;                                       
	boolean deleteBook(int id) throws DAOException;													
	boolean deleteBook(Book book) throws DAOException;												
	Book getBook(int id) throws DAOException;														
	List<Book> getAllBooks() throws DAOException; 													
	List<Book> getUserBooks(int userId) throws DAOException; 										
	List<Book> findByName(String name) throws DAOException;											
	List<Book> findByAuthor(String author) throws DAOException;										
}
