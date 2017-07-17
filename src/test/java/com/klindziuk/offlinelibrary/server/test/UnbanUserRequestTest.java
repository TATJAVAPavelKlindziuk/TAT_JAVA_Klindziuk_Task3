package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UnbanUserRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void unBanUserSmokeTest() {
		String command = setRequest("unbanuser/unbanuser.xml");
		String response = requester.sendRequest(command);
		String expected = "User successfully unbanned.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] unBanUserDp()  {
		return new Object[][] { { "unbanuser/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "unbanuser/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "unbanuser/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "unbanuser/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "unBanUserDp")
	public void banUserTest(String request, String expected)  {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
