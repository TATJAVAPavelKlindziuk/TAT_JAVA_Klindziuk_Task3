package com.klindziuk.offlinelibrary.controller.command.impl;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.util.RequestParser;
import com.klindziuk.offlinelibrary.service.ClientService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;
import com.klindziuk.offlinelibrary.service.factory.ServiceFactory;

public class SingIn implements Command {
	private static final Logger logger = Logger.getLogger(Registration.class);
	
	@Override
	public String execute(String request) {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		String response = UNSUCCESSFUL_OPERATION_MESSAGE;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ClientService clientService = serviceFactory.getClientService();
		try {
			String[] credentials = RequestParser.getCredentials(request);
			boolean result = clientService.signIn(credentials[0], credentials[1]);
			response = String.valueOf(result);
			logger.info(response);			
		} catch (ServiceException seex) {
			logger.error(UNSUCCESSFUL_OPERATION_MESSAGE + seex.getMessage(), seex);
			response = UNSUCCESSFUL_OPERATION_MESSAGE + seex.getMessage();
		} catch (IllegalArgumentException ieax) {
			logger.error(ieax.getMessage(), ieax);
			response = UNSUCCESSFUL_OPERATION_MESSAGE + ieax.getMessage();
		}
		return response;
	}
}
