package com.klindziuk.offlinelibrary.dao.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 4056649654031672378L;
	
	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}
	public DAOException(Exception e, String message) {
		super(message);
	}
}
