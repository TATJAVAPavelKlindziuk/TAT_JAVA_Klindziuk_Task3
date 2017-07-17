package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MarkIssuanceRequestTest extends BaseRequestTest {
		
	@Test(priority = 0)
	public void markReturnSmokeTest() {
		String command = setRequest("markissuance/markissuance.xml");
		String response = requester.sendRequest(command);
		String expected = "Book issued successfully.";
		Assert.assertEquals(response, expected);
	}

	@DataProvider
	public Object[][] markIssuanceUserIdDp() {
		return new Object[][] {
				{ "markissuance/markissuancelettersuserid.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "markissuance/markissuancespecuserid.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "markissuance/markissuanceemptyuserid.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "markissuance/markissuancenulluserid.xml", NUMBER_EXCEPTION_MESSAGE },
			    };
	}

	@Test(priority = 1,dataProvider = "markIssuanceUserIdDp")
	public void markIssuanceUserIdTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
	
	@DataProvider
	public Object[][] markIssunaceBookIdDp() {
		return new Object[][] {
				{ "markissuance/markissuancelettersbookid.xml", NUMBER_EXCEPTION_MESSAGE },
				{ "markissuance/markissuancespecbookid.xml",	NUMBER_EXCEPTION_MESSAGE },
				{ "markissuance/markissuanceemptybookid.xml", EMPTY_STRING_EXCEPTION_MESSAGE },
				{ "markissuance/markissuancenullbookid.xml", NUMBER_EXCEPTION_MESSAGE },
				};
	}

	@Test(priority = 2,dataProvider = "markIssunaceBookIdDp")
	public void markIssuanceBookIdTest(String request, String expected) {
		String command = setRequest(request);
		String response = requester.sendRequest(command);
		Assert.assertEquals(response, expected);
	}
}
