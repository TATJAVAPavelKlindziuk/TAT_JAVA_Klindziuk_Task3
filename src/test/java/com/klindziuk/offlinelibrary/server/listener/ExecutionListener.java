package com.klindziuk.offlinelibrary.server.listener;

import org.apache.log4j.Logger;
import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {
	private static final Logger logger = Logger.getLogger(ExecutionListener.class);
	protected static final String TEST_FINISH_MESSAGE = "Tests runs are finished, took around - ";
	protected static final String PERFORMING_MESSAGE = "Performing tests.";
	private long startTime;

	@Override
	public void onExecutionFinish() {
		logger.info(TEST_FINISH_MESSAGE + (System.currentTimeMillis() - startTime) + "ms.");
	}

	@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
		logger.info(PERFORMING_MESSAGE);
		}
}
