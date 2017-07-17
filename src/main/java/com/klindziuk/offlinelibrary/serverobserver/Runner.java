package com.klindziuk.offlinelibrary.serverobserver;

import org.apache.log4j.Logger;

public class Runner {
	private static final Logger logger = Logger.getLogger(XmlFileReader.class);
	private static final String XMLFILEPATH_GET = "requests/get";
	private static final String XMLFILEPATH_FINDBY = "requests/findby";
	private static final String XMLFILEPATH_SIGNIN = "requests/signin";
	private static final String XMLFILEPATH_UPDATE = "requests/updateprofile";

	public static void main(String[] args) {
		try {
		new Thread(new XmlFileReader(XMLFILEPATH_GET)).start();
		new Thread(new XmlFileReader(XMLFILEPATH_FINDBY)).start();
		new Thread(new XmlFileReader(XMLFILEPATH_SIGNIN)).start();
		new Thread(new XmlFileReader(XMLFILEPATH_UPDATE)).start();
	} catch (IllegalArgumentException iaex){
		logger.error(iaex.getMessage());
}
	}
}
