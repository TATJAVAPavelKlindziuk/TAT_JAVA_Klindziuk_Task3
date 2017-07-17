package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SignInRequestTest extends BaseRequestTest {
	private static final String UNREGISTERED_MESSAGE = "Cannot perform this operation.Login error."
			+ "There are no users with this credentials.";

	// string variable "false" when client is logged as user
	@Test(priority = 0)
	public void signInAsUserSmokeTest() {
		String command = setRequest("signin/signinasuser.xml");
		String response = requester.sendRequest(command);
		String expected = "false";
		Assert.assertEquals(response, expected);
	}

	// string variable "true" when client is logged as admin
	@Test(priority = 1)
	public void signInAsAdminSmokeTest() {
		String command = setRequest("signin/signinasadmin.xml");
		String response = requester.sendRequest(command);
		String expected = "true";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] signInDp() {
		return new Object[][] { { "signin/emptylogin.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "signin/emptypassword.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "signin/speclogin.xml", SPEC_STRING_EXCEPTION_MESSAGE },
				{ "signin/specpassword.xml", SPEC_STRING_EXCEPTION_MESSAGE },
				{ "signin/unregistered.xml", UNREGISTERED_MESSAGE }, };
	}

	@Test(priority = 2, dataProvider = "signInDp")
	public void signInTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
