package com.klindziuk.offlinelibrary.controller.command.impl;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.util.RequestParser;
import com.klindziuk.offlinelibrary.service.LibraryService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;
import com.klindziuk.offlinelibrary.service.factory.ServiceFactory;

public class AddBookToWishList implements Command {
	private static final Logger logger = Logger.getLogger(AddBookToWishList.class);
	private static final String SUCCESSFUL_ADD_MESSAGE = "Book succefully added to your wishlist.";

	@Override
	public String execute(String request) {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		logger.info(PERFORMING_COMMAND_MESSAGE + this.getClass().getSimpleName());
		String response = UNSUCCESSFUL_OPERATION_MESSAGE;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		LibraryService libraryService = serviceFactory.getLibraryService();
		try {
			int[] parameters = RequestParser.getParameters(request);
			if (libraryService.addBookToWishList(parameters[0], parameters[1])) {
				response = SUCCESSFUL_ADD_MESSAGE;
				logger.info(response);
			}
		} catch (ServiceException seex) {
			logger.error(seex.getMessage(), seex);
			response = UNSUCCESSFUL_OPERATION_MESSAGE + seex.getMessage();
		} catch (IllegalArgumentException ieax) {
			logger.error(ieax.getMessage(), ieax);
			response = UNSUCCESSFUL_OPERATION_MESSAGE + ieax.getMessage();
		}
		return response;
	}
}
