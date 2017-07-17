package com.klindziuk.offlinelibrary.controller.command;

import java.util.HashMap;

import javax.naming.OperationNotSupportedException;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.command.impl.GetAllBooks;
import com.klindziuk.offlinelibrary.controller.command.impl.Registration;
import com.klindziuk.offlinelibrary.controller.command.impl.SignOut;
import com.klindziuk.offlinelibrary.controller.command.impl.SingIn;

public enum ClientCommandName {

		SIGNIN(new SingIn()),
		SIGNOUT(new SignOut()),
	    REGISTRATION(new Registration()),
		GETALLBOOKS(new GetAllBooks());
	    
	    private static final String UNSUPPORTED_OPERATION_MESSAGE = " - this command unfortunately unsupported.";
	    private Command command;
	    private static HashMap<String, Command> clientCommandMap;

	    static {
	        clientCommandMap = new HashMap<>();
	        for (ClientCommandName s : ClientCommandName.values())
	            clientCommandMap.put(s.name().toUpperCase(), s.command);
	    }

	     ClientCommandName(Command command) {
	        this.command = command;
	    }

	    public static Command getCommandByName(String name) throws OperationNotSupportedException {
	        if(!clientCommandMap.containsKey(name.toUpperCase()) || name.isEmpty()) {
	        	 throw new OperationNotSupportedException(name + UNSUPPORTED_OPERATION_MESSAGE );
	        }
	        return clientCommandMap.get(name.toUpperCase());
	    }
	}
