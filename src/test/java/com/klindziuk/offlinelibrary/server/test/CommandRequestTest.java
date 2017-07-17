package com.klindziuk.offlinelibrary.server.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CommandRequestTest extends BaseRequestTest {
	
	@Test(priority = 0)
	public void epmtyCommandTest() {
		String command = setRequest("emptycommand.xml");
		String response = requester.sendRequest(command);
		String expected = "Cannot perform this operation.";
		Assert.assertEquals(response, expected);
	}
	
	@Test(priority = 1)
	public void wrongCommandTest() {
		String command = setRequest("wrongcommand.xml");
		String response = requester.sendRequest(command);
		String expected = "thiscommandisunsupported - this command unfortunately unsupported.";
		Assert.assertEquals(response, expected);
	}
}
