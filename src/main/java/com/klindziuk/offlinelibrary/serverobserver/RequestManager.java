package com.klindziuk.offlinelibrary.serverobserver;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.Controller;

public class RequestManager {
	private static final Logger logger = Logger.getLogger(Server.class);
	private static RequestManager instance = null;
	public static final String NEW_THREAD_START = "Starting new thread - ";
	private static final String RESPONSE_MESSAGE = "Sending response... Response : ";
	public static final String SUCCESS_MESSAGE = " - finished.";
	
	private RequestManager() {
	}
	
	public static RequestManager getInstance() {
		if (instance == null) {
			instance = new RequestManager();
		}
		return instance;
	}
				
	public Callable<String> getResponse(String request) {
        return new Callable<String>() {
        	@Override
            public String call() throws Exception {
        		logger.info(NEW_THREAD_START + Thread.currentThread().getName());
            	String response = Controller.getInstance().executeAdminTask(request);
            	logger.info(RESPONSE_MESSAGE + response);
            	logger.info(Thread.currentThread().getName() + SUCCESS_MESSAGE);
            	return response; 
   		      }
        };
    }
}
