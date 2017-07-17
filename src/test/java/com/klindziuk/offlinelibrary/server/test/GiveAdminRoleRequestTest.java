package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GiveAdminRoleRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void giveAdminRoleSmokeTest() {
		String command = setRequest("giveadminrole/giveadminrole.xml");
		String response = requester.sendRequest(command);
		String expected = "Admin rights successfully granted.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] giveAdminRoleDp() {
		return new Object[][] { { "giveadminrole/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "giveadminrole/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "giveadminrole/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "giveadminrole/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "giveAdminRoleDp")
	public void giveAdminRoleTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
