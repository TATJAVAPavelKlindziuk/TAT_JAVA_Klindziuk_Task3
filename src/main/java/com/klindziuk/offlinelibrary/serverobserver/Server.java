package com.klindziuk.offlinelibrary.serverobserver;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

public class Server implements RequestListener {
	private static final Logger logger = Logger.getLogger(Server.class);
	private static final String INVALID_REQUEST_EXCEPTION_MESSAGE = "Invalid request parameters.";
	private static final String UNSUCCESSFUL_OPERATION_MESSAGE = "Cannot perform this operation.";
	private static final String UNSUCCESSFUL_EXECUTION_MESSAGE = "Cannot finish task in this thread.";
	private static final String NEW_REQUEST_START = "Starting performing new request.";
	private static final int POOL_QUANTITY = 10;
	private static Server instance = null;
	private final ExecutorService service;
	private final RequestManager requestManager;
	
	private Server() {
		service = Executors.newFixedThreadPool(POOL_QUANTITY);
		requestManager = RequestManager.getInstance();
	}

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}
	
	public String processRequest(String request) {
		String response = UNSUCCESSFUL_OPERATION_MESSAGE;
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		try {
			logger.info(NEW_REQUEST_START);
			Callable<String> callable = requestManager.getResponse(request);
            Future<String> result = service.submit(callable);
            response = result.get();
          } catch (Exception ex) {
			logger.error(UNSUCCESSFUL_EXECUTION_MESSAGE);
		}
		return response;
	}
}
