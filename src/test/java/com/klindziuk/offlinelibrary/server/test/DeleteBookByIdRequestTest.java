package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeleteBookByIdRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void deleteBookByIdSmokeTest() {
		String command = setRequest("deletebookbyid/deletebookbyid.xml");
		String response = requester.sendRequest(command);
		String expected = "Book deleted successfully.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] deleteBookByIdDp() {
		return new Object[][] { { "deletebookbyid/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "deletebookbyid/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "deletebookbyid/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "deletebookbyid/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "deleteBookByIdDp")
	public void deleteBookByIdTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
