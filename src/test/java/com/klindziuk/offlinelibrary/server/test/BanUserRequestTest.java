package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BanUserRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void banUserSmokeTest() {
		String command = setRequest("banuser/banuser.xml");
		String actual = requester.sendRequest(command);
		String response = "User successfully banned.";
		Assert.assertEquals(actual, response);
	}

	@DataProvider
	public Object[][] banUserDp() {
		return new Object[][] { { "banuser/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "banuser/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "banuser/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "banuser/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "banUserDp")
	public void banUserTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
