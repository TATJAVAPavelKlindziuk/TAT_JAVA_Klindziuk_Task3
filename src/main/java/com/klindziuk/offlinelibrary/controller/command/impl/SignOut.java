package com.klindziuk.offlinelibrary.controller.command.impl;

import com.klindziuk.offlinelibrary.controller.Command;


public class SignOut implements Command{
	private static final String UNIMPLEMENTED_METHOD_MESSAGE = " Method will be implemented ASAP";
	@Override
	public String execute(String request) {
		if (null == request || request.isEmpty()) {
			return INVALID_REQUEST_EXCEPTION_MESSAGE;
		}
		String response = UNSUCCESSFUL_OPERATION_MESSAGE + UNIMPLEMENTED_METHOD_MESSAGE;
		return response;
	}
}
