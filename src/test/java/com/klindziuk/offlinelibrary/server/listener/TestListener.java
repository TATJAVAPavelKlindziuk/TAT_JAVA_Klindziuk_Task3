package com.klindziuk.offlinelibrary.server.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
	private static final Logger logger = Logger.getLogger(TestListener.class);

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("on test method " + getTestMethodName(result) + " start.");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// not implemented yet
		}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.error("on test method " + getTestMethodName(result) + " failure.");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.warn("test method " + getTestMethodName(result) + " skipped.");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logger.warn("test failed but within success % " + getTestMethodName(result));
	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("on start of test " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("on finish of test " + context.getName());
	}
	
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}
}
