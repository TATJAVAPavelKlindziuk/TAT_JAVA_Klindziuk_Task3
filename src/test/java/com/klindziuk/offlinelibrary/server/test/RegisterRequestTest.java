package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterRequestTest extends BaseRequestTest {
		
		//only letters allowed for registration
		@Test(priority = 0)
		public void registerSmokeTest() {
			String command = setRequest("registration/registration.xml");
			String response = requester.sendRequest(command);
			String expected = "Registration completed successfully.";
			Assert.assertEquals(response, expected);
		}

		@DataProvider
		public Object[][] registerDp() {
			return new Object[][] { 
					{ "registration/emptylogin.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
					{ "registration/emptypassword.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
					{ "registration/emptyname.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
					{ "registration/speclogin.xml", SPEC_STRING_EXCEPTION_MESSAGE },
					{ "registration/specpassword.xml", SPEC_STRING_EXCEPTION_MESSAGE },
					{ "registration/specname.xml", SPEC_STRING_EXCEPTION_MESSAGE },
					{ "registration/numberslogin.xml", SPEC_STRING_EXCEPTION_MESSAGE },
					{ "registration/numberspassword.xml", SPEC_STRING_EXCEPTION_MESSAGE },
					{ "registration/numbersname.xml", SPEC_STRING_EXCEPTION_MESSAGE },};
			}

		@Test(priority = 1, dataProvider = "registerDp")
		public void registerTest(String request, String expected) {
			String command = setRequest(request);
			String response = requester.sendRequest(command);
			Assert.assertEquals(response, expected);
		}
}
