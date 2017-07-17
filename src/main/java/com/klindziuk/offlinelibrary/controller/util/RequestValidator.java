package com.klindziuk.offlinelibrary.controller.util;

import java.sql.Timestamp;

public final class RequestValidator {
	private static final String ID_REGEXP_PATTERN = "(^[1-9]+[0]\\d*)|([1-9]\\d*$)";
	private static final String LINE_REGEXP_PATTERN = "[A-Za-z\\s.-]+";
	private static final String FALSE_REGEXP_PATTERN = "false";
	private static final String TRUE_REGEXP_PATTERN = "true";
	private static final String TIMESTAMP_REGEXP_PATTERN = "\\d{4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d{4}";
	private final static String ID_EXCEPTION_MESSAGE = "Only numbers allowed.Id cannot be zero.";
	private final static String LINE_EXCEPTION_MESSAGE = "Only letters,dots,minus and whitespaces are allowed.";
	private final static String BOOLEAN_EXCEPTION_MESSAGE = "Only true/false allowed.";
	private final static String TIMESTAMP_EXCEPTION_MESSAGE = "Bad timestamp format.";
	private final static String NULL_PARAMETER_EXCEPTION_MESSAGE = "Parameters cannot be null or empty.";

	private RequestValidator() {
	}

	public static int validateInt(String id) throws IllegalArgumentException {
		if(null == id || id.isEmpty()){
			throw new IllegalArgumentException(NULL_PARAMETER_EXCEPTION_MESSAGE);
		}
		if (!String.valueOf(id).matches(ID_REGEXP_PATTERN)) {
			throw new IllegalArgumentException(ID_EXCEPTION_MESSAGE);
		}
		int validatedId = Integer.parseInt(id);
		return validatedId;
	}
	
	public static String validateString(String line) throws IllegalArgumentException {
		if(null == line || line.isEmpty()){
			throw new IllegalArgumentException(NULL_PARAMETER_EXCEPTION_MESSAGE);
		}
		if (!line.matches(LINE_REGEXP_PATTERN)) {
			throw new IllegalArgumentException(LINE_EXCEPTION_MESSAGE);
		}
		return line;
	}
	
	public static boolean validateBoolean(String line) throws IllegalArgumentException {
		if(null == line || line.isEmpty()){
			throw new IllegalArgumentException(NULL_PARAMETER_EXCEPTION_MESSAGE);
		}
		if ( (!line.matches(TRUE_REGEXP_PATTERN) && (!line.matches(FALSE_REGEXP_PATTERN)))) {
			throw new IllegalArgumentException(BOOLEAN_EXCEPTION_MESSAGE);
		}
		boolean variable = Boolean.parseBoolean(line);
		return variable;
	}
	
	public static Timestamp validateTimestamp(String line) throws IllegalArgumentException {
		if(null == line || line.isEmpty()){
			throw new IllegalArgumentException(NULL_PARAMETER_EXCEPTION_MESSAGE);
		}
		if (!line.matches(TIMESTAMP_REGEXP_PATTERN)) {
			throw new IllegalArgumentException(TIMESTAMP_EXCEPTION_MESSAGE);
		}
		Timestamp ts = Timestamp.valueOf(line);
		return ts;
	}
}
