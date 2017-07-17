package com.klindziuk.offlinelibrary.controller.command.impl;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.util.RequestParser;
import com.klindziuk.offlinelibrary.controller.util.ResponseBuilder;
import com.klindziuk.offlinelibrary.service.LibraryService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;
import com.klindziuk.offlinelibrary.service.factory.ServiceFactory;

public class FindByAuthor implements Command {
	private static final Logger logger = Logger.getLogger(FindByAuthor.class);
	private static final String UNSUCCESSFUL_OPERATION_MESSAGE = "Cannot perform this operation.";
	
	@Override
	public String execute(String request)  {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		logger.info(PERFORMING_COMMAND_MESSAGE + this.getClass().getSimpleName());
		String response = UNSUCCESSFUL_OPERATION_MESSAGE;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		LibraryService libraryService = serviceFactory.getLibraryService();
		try {
			String author = RequestParser.getName(request);
			response = ResponseBuilder.build(libraryService.findByAuthor(author));
			logger.info(response);
		} catch (ServiceException seex) {
			logger.error(seex.getMessage(), seex);
		} catch (IllegalArgumentException ieax) {
			logger.error(ieax.getMessage(), ieax);
		}
		return response;
	}
}
