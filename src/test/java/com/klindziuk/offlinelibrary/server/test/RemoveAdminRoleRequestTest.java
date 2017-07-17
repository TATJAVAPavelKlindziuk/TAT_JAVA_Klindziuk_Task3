package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RemoveAdminRoleRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void giveAdminRoleSmokeTest() {
		String command = setRequest("removeadminrole/removeadminrole.xml");
		String response = requester.sendRequest(command);
		String expected = "Admin rights successfully removed.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] removeAdminRoleDp() {
		return new Object[][] { { "removeadminrole/empty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "removeadminrole/zero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "removeadminrole/letters.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "removeadminrole/spec.xml", NUMBER_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "removeAdminRoleDp")
	public void removeAdminRoleTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
