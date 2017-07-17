package com.klindziuk.offlinelibrary.controller.command.impl;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.util.RequestParser;
import com.klindziuk.offlinelibrary.service.AdminService;
import com.klindziuk.offlinelibrary.service.exception.ServiceException;
import com.klindziuk.offlinelibrary.service.factory.ServiceFactory;

public class BanUser implements Command {
	private static final Logger logger = Logger.getLogger(BanUser.class);
	private static final String SUCCESSFULL_UPDATE_MESSAGE = "User successfully banned.";

	@Override
	public String execute(String request) {
		if (null == request || request.isEmpty()) {
			logger.error(INVALID_REQUEST_EXCEPTION_MESSAGE);
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		logger.info(PERFORMING_COMMAND_MESSAGE + this.getClass().getSimpleName());
		String response = UNSUCCESSFUL_OPERATION_MESSAGE;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		AdminService adminService = serviceFactory.getAdminService();
		try {
			int userId = RequestParser.getId(request);
			if (adminService.banUser(userId)) {
				response = SUCCESSFULL_UPDATE_MESSAGE;
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
