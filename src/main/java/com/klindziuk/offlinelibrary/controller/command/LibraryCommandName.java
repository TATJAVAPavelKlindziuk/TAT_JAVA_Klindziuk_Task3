package com.klindziuk.offlinelibrary.controller.command;

import java.util.HashMap;

import javax.naming.OperationNotSupportedException;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.command.impl.AddBookToWishList;
import com.klindziuk.offlinelibrary.controller.command.impl.FindByAuthor;
import com.klindziuk.offlinelibrary.controller.command.impl.FindByName;
import com.klindziuk.offlinelibrary.controller.command.impl.GetAllBooks;
import com.klindziuk.offlinelibrary.controller.command.impl.GetBook;
import com.klindziuk.offlinelibrary.controller.command.impl.GetUserBooks;
import com.klindziuk.offlinelibrary.controller.command.impl.RemoveBookFromWishList;
import com.klindziuk.offlinelibrary.controller.command.impl.UpdateProfile;

public enum LibraryCommandName {
		
    ADDBOOKTOWISHLIST(new AddBookToWishList()),
    REMOVEBOOKFROMWISHLIST(new RemoveBookFromWishList()),
    UPDATEPROFILE(new UpdateProfile()),
    GETBOOK(new GetBook()),
    GETALLBOOKS(new GetAllBooks()),
    GETUSERBOOKS(new GetUserBooks()),
    FINDBYNAME(new FindByName()),
    FINDBYAUTHOR(new FindByAuthor());
 	 
	private static final String UNSUPPORTED_OPERATION_MESSAGE = " - this command unfortunately unsupported.";
    private Command command;
    private static HashMap<String, Command> libraryCommandMap;

    static {
        libraryCommandMap = new HashMap<>();
        for (LibraryCommandName s : LibraryCommandName.values())
            libraryCommandMap.put(s.name().toUpperCase(), s.command);
    }

    LibraryCommandName(Command command) {
        this.command = command;
    }

    public static Command getCommandByName(String name) throws OperationNotSupportedException {
        if(!libraryCommandMap.containsKey(name.toUpperCase()) || name.isEmpty()) {
            throw new OperationNotSupportedException(name + UNSUPPORTED_OPERATION_MESSAGE );
        }
        return libraryCommandMap.get(name.toUpperCase());
    }
}


