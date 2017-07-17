package com.klindziuk.offlinelibrary.serverobserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Requester {
	private static final Logger logger = Logger.getLogger(Server.class);
	public static final String LISTEN_MESSAGE = "Starting listening requests from - ";
	public static final String INVALID_REQUEST_EXCEPTION_MESSAGE = "Invalid request parameters.";
	public static final String NO_CONNECTION_EXCEPTION_MESSAGE = "There is no connection.";
	private List<RequestListener> listeners = new ArrayList<RequestListener>();

	public void addListener(RequestListener toAdd) {
		listeners.add(toAdd);
		logger.info(LISTEN_MESSAGE + this.toString());
	}

	public String sendRequest(String request) {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		if(listeners.isEmpty()) {
			return NO_CONNECTION_EXCEPTION_MESSAGE;
		}
		String result = "";
		result = listeners.get(0).processRequest(request);
		return result;
	}
}
