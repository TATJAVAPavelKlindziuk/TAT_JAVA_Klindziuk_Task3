package com.klindziuk.offlinelibrary.controller.util;

import java.util.List;

public final class ResponseBuilder {
	private final static String NEWLINE_APPENDER = "\n";
	
	private ResponseBuilder() {}
	
	public static <T> String build(List<T> items) {
		StringBuilder builder = new StringBuilder();
		for (T b : items) {
			builder.append(b.toString());
			builder.append(NEWLINE_APPENDER);
		}
		return builder.toString();
	}
}
