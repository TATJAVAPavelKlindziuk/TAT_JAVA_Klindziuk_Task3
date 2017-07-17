package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MarkReturnRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void markReturnSmokeTest() {
		String command = setRequest("markreturn/markreturn.xml");
		String response = requester.sendRequest(command);
		String expected = "Book returned successfully";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] markReturnUserIdDp() {
		return new Object[][] {
				{ "markreturn/markreturnlettersuserid.xml",NUMBER_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnspecuserid.xml",	NUMBER_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnemptyuserid.xml", EMPTY_STRING_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnnulluserid.xml", NUMBER_EXCEPTION_MESSAGE},
			    };
	}

	@Test(priority = 1,dataProvider = "markReturnUserIdDp")
	public void markReturnUserIdTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
	
	@DataProvider
	public Object[][] markReturnBookIdDp() {
		return new Object[][] {
				{ "markreturn/markreturnlettersbookid.xml", NUMBER_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnspecbookid.xml",	NUMBER_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnemptybookid.xml", EMPTY_STRING_EXCEPTION_MESSAGE},
				{ "markreturn/markreturnnullbookid.xml", NUMBER_EXCEPTION_MESSAGE},
				};
	}

	@Test(priority = 2,dataProvider = "markReturnBookIdDp")
	public void markReturnBookIdTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
