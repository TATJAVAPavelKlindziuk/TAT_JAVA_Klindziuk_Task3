package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SetBookDeprecatedRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void setBookDeprecatedSmokeTest() {
		String command = setRequest("setbookdeprecated/setbookdeprecated.xml");
		String response = requester.sendRequest(command);
		String expected = "Book deprecated successfully.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] setBookDeprecatedDp() {
		return new Object[][] { { "setbookdeprecated/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "setbookdeprecated/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "setbookdeprecated/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "setbookdeprecated/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "setBookDeprecatedDp")
	public void setBookDeprecatedTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
