package com.klindziuk.offlinelibrary.controller.command;

import java.util.HashMap;

import javax.naming.OperationNotSupportedException;

import com.klindziuk.offlinelibrary.controller.Command;
import com.klindziuk.offlinelibrary.controller.command.impl.AddBook;
import com.klindziuk.offlinelibrary.controller.command.impl.AddBookToWishList;
import com.klindziuk.offlinelibrary.controller.command.impl.BanUser;
import com.klindziuk.offlinelibrary.controller.command.impl.DeleteBook;
import com.klindziuk.offlinelibrary.controller.command.impl.DeleteBookById;
import com.klindziuk.offlinelibrary.controller.command.impl.FindByAuthor;
import com.klindziuk.offlinelibrary.controller.command.impl.FindByName;
import com.klindziuk.offlinelibrary.controller.command.impl.GetAllBooks;
import com.klindziuk.offlinelibrary.controller.command.impl.GetAllSubscriptions;
import com.klindziuk.offlinelibrary.controller.command.impl.GetAllUsers;
import com.klindziuk.offlinelibrary.controller.command.impl.GetBook;
import com.klindziuk.offlinelibrary.controller.command.impl.GetUser;
import com.klindziuk.offlinelibrary.controller.command.impl.GetUserBooks;
import com.klindziuk.offlinelibrary.controller.command.impl.GiveAdminRole;
import com.klindziuk.offlinelibrary.controller.command.impl.MarkIssuance;
import com.klindziuk.offlinelibrary.controller.command.impl.MarkReturn;
import com.klindziuk.offlinelibrary.controller.command.impl.RemoveAdminRole;
import com.klindziuk.offlinelibrary.controller.command.impl.RemoveBookFromWishList;
import com.klindziuk.offlinelibrary.controller.command.impl.SetBookDeprecated;
import com.klindziuk.offlinelibrary.controller.command.impl.UnBanUser;
import com.klindziuk.offlinelibrary.controller.command.impl.UpdateBookDescription;
import com.klindziuk.offlinelibrary.controller.command.impl.UpdateProfile;

public enum AdminCommandName {	

		ADDBOOK(new AddBook()),
		SETBOOKDEPRECATED(new SetBookDeprecated()),
		DELETEBOOK(new DeleteBook()),
		DELETEBOOKBYID(new DeleteBookById ()),
		GIVEADMINROLE(new GiveAdminRole()),
		REMOVEADMINROLE(new RemoveAdminRole()),
		BANUSER(new BanUser()),
		UNBANUSER(new UnBanUser()),
		MARKISSUANCE(new MarkIssuance()),
		MARKRETURN(new MarkReturn()),
		UPDATEBOOKDESCRIPTION(new UpdateBookDescription()),
		GETBOOK(new GetBook()),
	    GETALLBOOKS(new GetAllBooks()),
	    GETUSERBOOKS(new GetUserBooks()),
	    FINDBYNAME(new FindByName()),
	    FINDBYAUTHOR(new FindByAuthor()),
		GETUSER(new GetUser()),
		GETALLUSERS(new GetAllUsers()),
		GETALLSUBSCRIPTIONS(new GetAllSubscriptions()),
		ADDBOOKTOWISHLIST(new AddBookToWishList()),
	    REMOVEBOOKFROMWISHLIST(new RemoveBookFromWishList()),
	    UPDATEPROFILE(new UpdateProfile());
	    	    
		private static final String UNSUPPORTED_OPERATION_MESSAGE = " - this command unfortunately unsupported.";
		private Command command;
	    private static HashMap<String, Command> adminCommandMap;

	    static {
	        adminCommandMap = new HashMap<>();
	        for (AdminCommandName s : AdminCommandName.values())
	            adminCommandMap.put(s.name().toUpperCase(), s.command);
	    }

	    AdminCommandName(Command command) {
	        this.command = command;
	    }

	    public static Command getCommandByName(String name) throws OperationNotSupportedException {
	    	if(!adminCommandMap.containsKey(name.toUpperCase()) || name.isEmpty())  {
	        	 throw new OperationNotSupportedException(name + UNSUPPORTED_OPERATION_MESSAGE );
	        }
	        return adminCommandMap.get(name.toUpperCase());
	    }
	}


