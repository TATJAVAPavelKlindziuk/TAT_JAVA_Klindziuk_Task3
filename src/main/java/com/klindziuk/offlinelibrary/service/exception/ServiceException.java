package com.klindziuk.offlinelibrary.service.exception;

public class ServiceException extends Exception {
		private static final long serialVersionUID = 511481760025447920L;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Exception e) {
		super(e);
	}
	public ServiceException(Exception e, String message) {
		super(message);
	}
}
