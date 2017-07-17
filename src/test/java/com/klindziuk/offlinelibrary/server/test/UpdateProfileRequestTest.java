package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateProfileRequestTest extends BaseRequestTest{
	
	@Test(priority = 0)
	public void updateProfileSmokeTest() {
		String command = setRequest("updateprofile/updateprofile.xml");
		String actual = requester.sendRequest(command);
		String expected = "Profile successfully updated.";
		Assert.assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] updateProfileDp() {
		return new Object[][] { 
			    { "updateprofile/idempty.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "updateprofile/idzero.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "updateprofile/idletters.xml", SPEC_STRING_EXCEPTION_MESSAGE },
				{ "updateprofile/idspec.xml", SPEC_STRING_EXCEPTION_MESSAGE },
				{ "updateprofile/nameempty.xml", EMPTY_STRING_EXCEPTION_MESSAGE }, 
				{ "updateprofile/namenumbers.xml", SPEC_STRING_EXCEPTION_MESSAGE },
				{ "updateprofile/namespec.xml", SPEC_STRING_EXCEPTION_MESSAGE }, };
	}

	@Test(priority = 1, dataProvider = "updateProfileDp")
	public void updateProfileTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
