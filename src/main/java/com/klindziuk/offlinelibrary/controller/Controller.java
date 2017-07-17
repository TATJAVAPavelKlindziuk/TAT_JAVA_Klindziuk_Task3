package com.klindziuk.offlinelibrary.controller;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.command.AdminCommandName;
import com.klindziuk.offlinelibrary.controller.command.ClientCommandName;
import com.klindziuk.offlinelibrary.controller.command.LibraryCommandName;
import com.klindziuk.offlinelibrary.controller.util.RequestParser;

public class Controller {
	private static final Logger logger = Logger.getLogger(Controller.class);
	private static Controller instance = null;
	
	private Controller() {}
	
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public String executeClientTask(String request) {
		String commandName = RequestParser.getCommand(request);
		Command executionCommand;
		try {
			executionCommand = ClientCommandName.getCommandByName(commandName);
		} catch (OperationNotSupportedException onseex) {
			logger.error(onseex.getMessage(), onseex);
			return onseex.getMessage();
		}
		return executionCommand.execute(request);
	}

	public String executeAdminTask(String request) {
		String commandName = RequestParser.getCommand(request);
		Command executionCommand;
		try {
			executionCommand = AdminCommandName.getCommandByName(commandName);
		} catch (OperationNotSupportedException onsex) {
			logger.error(onsex.getMessage(), onsex);
			return onsex.getMessage();
		}
		return executionCommand.execute(request);
	}
	
	public String executeLibraryTask(String request) {
		String commandName = RequestParser.getCommand(request);
		Command executionCommand;
		try {
			executionCommand = LibraryCommandName.getCommandByName(commandName);
		} catch (OperationNotSupportedException onsex) {
			logger.error(onsex.getMessage(), onsex);
			return onsex.getMessage();
		}
		return executionCommand.execute(request);
	}
}
