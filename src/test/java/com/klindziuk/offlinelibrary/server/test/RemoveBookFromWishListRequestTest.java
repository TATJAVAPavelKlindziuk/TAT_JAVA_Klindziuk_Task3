package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RemoveBookFromWishListRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void removeBookSmokeTest() {
		String command = setRequest("removebookfromwishlist/removebookfromwishlist.xml");
		String response = requester.sendRequest(command);
		String expected = "Book succefully removed from your wishlist.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] removeBookFromWishListDp() {
		return new Object[][] { 
			    { "removebookfromwishlist/emptyuserid.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "removebookfromwishlist/emptybookid.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "removebookfromwishlist/lettersuserid.xml", NUMBER_EXCEPTION_MESSAGE  }, 
				{ "removebookfromwishlist/lettersbookid.xml", NUMBER_EXCEPTION_MESSAGE }, 
				{ "removebookfromwishlist/specuserid.xml", NUMBER_EXCEPTION_MESSAGE}, 
				{ "removebookfromwishlist/specbookid.xml", NUMBER_EXCEPTION_MESSAGE }, 
				{ "removebookfromwishlist/nulluserid.xml", NUMBER_EXCEPTION_MESSAGE }, 
				{ "removebookfromwishlist/nullbookid.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}
	
	@Test(priority = 1, dataProvider = "removeBookFromWishListDp")
	public void addBookToWishListTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
