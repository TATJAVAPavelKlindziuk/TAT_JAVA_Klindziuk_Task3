package com.klindziuk.offlinelibrary.controller.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.klindziuk.offlinelibrary.model.Book;
import com.klindziuk.offlinelibrary.model.User;

public final class RequestParser {
	private static final Logger logger = Logger.getLogger(RequestParser.class);
	private final static int ITEM_POSITION = 0;
	private final static String XML_ID_TAG = "id";
	private final static String XML_IDUSER_TAG = "userId";
	private final static String XML_IDBOOK_TAG = "bookId";
	private final static String XML_COMMAND_TAG = "command";
	private final static String XML_NAME_TAG = "name";
	private final static String XML_LOGIN_TAG = "login";
	private final static String XML_PASSWORD_TAG = "password";
	private final static String XML_AUTHOR_TAG = "author";
	private final static String XML_YEAR_TAG = "year";
	private final static String XML_AVAILABLE_TAG = "available";
	private final static String XML_DEPRECATED_TAG = "deprecated";
	private final static String XML_TIME_TAG = "time";
	private final static String PARSE_EXCEPTION_MESSAGE = "Exception during parsing request file.";
	private final static String NULL_REQUEST_EXCEPTION_MESSAGE = "Request cannot be null or empty.";
	private final static String NULL_FILENAME_EXCEPTION_MESSAGE = "Filename cannot be null or empty.";
	private final static String NEWLINE_APPENDER = "\n";

	private RequestParser() {
	}

	public static String readFile(String fileName) throws IOException {
		if (null == fileName || fileName.isEmpty()) {
			throw new IllegalArgumentException(NULL_FILENAME_EXCEPTION_MESSAGE);
		}
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				builder.append(line);
				builder.append(NEWLINE_APPENDER);
				line = reader.readLine();
			}
			return builder.toString();
		} finally {
			reader.close();
		}
	}

	public static int getId(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		int result = 0;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			int id = RequestValidator.validateInt(doc.getElementsByTagName(XML_ID_TAG).item(ITEM_POSITION).getTextContent());
			return id;
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static String getCommand(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			String command = RequestValidator
					.validateString(doc.getElementsByTagName(XML_COMMAND_TAG).item(ITEM_POSITION).getTextContent());
			return command;
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return PARSE_EXCEPTION_MESSAGE;
	}

	public static String getName(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			String name = RequestValidator
					.validateString(doc.getElementsByTagName(XML_NAME_TAG).item(ITEM_POSITION).getTextContent());
			return name;
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return PARSE_EXCEPTION_MESSAGE;
	}

	public static String[] getCredentials(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		String[] credentials = new String[2];
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			System.out.println();
			credentials[0] = RequestValidator
					.validateString(doc.getElementsByTagName(XML_LOGIN_TAG).item(ITEM_POSITION).getTextContent());
			credentials[1] = RequestValidator
					.validateString(doc.getElementsByTagName(XML_PASSWORD_TAG).item(ITEM_POSITION).getTextContent());
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return credentials;
	}

	public static int[] getParameters(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		int[] parameters = new int[2];
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			System.out.println();
			parameters[0] = RequestValidator
					.validateInt(doc.getElementsByTagName(XML_IDUSER_TAG).item(ITEM_POSITION).getTextContent());
			parameters[1] = RequestValidator
					.validateInt(doc.getElementsByTagName(XML_IDBOOK_TAG).item(ITEM_POSITION).getTextContent());
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return parameters;
	}

	public static User getUser(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		User user = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			System.out.println();
			String login = RequestValidator
					.validateString(doc.getElementsByTagName(XML_LOGIN_TAG).item(ITEM_POSITION).getTextContent());
			String password = RequestValidator
					.validateString(doc.getElementsByTagName(XML_PASSWORD_TAG).item(ITEM_POSITION).getTextContent());
			String name = RequestValidator
					.validateString(doc.getElementsByTagName(XML_NAME_TAG).item(ITEM_POSITION).getTextContent());
			user = new User(login, name, password);
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	public static Book createBook(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		Book book = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			System.out.println();
			String name = RequestValidator
					.validateString(doc.getElementsByTagName(XML_NAME_TAG).item(ITEM_POSITION).getTextContent());
			String author = RequestValidator
					.validateString(doc.getElementsByTagName(XML_AUTHOR_TAG).item(ITEM_POSITION).getTextContent());
			int year = RequestValidator.validateInt(doc.getElementsByTagName(XML_YEAR_TAG).item(ITEM_POSITION).getTextContent());
			book = new Book(name, author, year);
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return book;
	}

	public static Book getBook(String request) throws IllegalArgumentException {
		if (null == request || request.isEmpty()) {
			throw new IllegalArgumentException(NULL_REQUEST_EXCEPTION_MESSAGE);
		}
		Book book = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(request));
			Document doc = builder.parse(src);
			System.out.println();
			int id = RequestValidator.validateInt(doc.getElementsByTagName(XML_ID_TAG).item(ITEM_POSITION).getTextContent());
			String name = RequestValidator
					.validateString(doc.getElementsByTagName(XML_NAME_TAG).item(ITEM_POSITION).getTextContent());
			String author = RequestValidator
					.validateString(doc.getElementsByTagName(XML_AUTHOR_TAG).item(ITEM_POSITION).getTextContent());
			int year = RequestValidator.validateInt(doc.getElementsByTagName(XML_YEAR_TAG).item(0).getTextContent());
			boolean isAvailable = RequestValidator
					.validateBoolean(doc.getElementsByTagName(XML_AVAILABLE_TAG).item(ITEM_POSITION).getTextContent());
			boolean isDeprecated = RequestValidator
					.validateBoolean(doc.getElementsByTagName(XML_DEPRECATED_TAG).item(ITEM_POSITION).getTextContent());
			Timestamp ts = RequestValidator
					.validateTimestamp(doc.getElementsByTagName(XML_TIME_TAG).item(ITEM_POSITION).getTextContent());
			book = new Book(id, name, author, year, isAvailable, isDeprecated, ts);
		} catch (DOMException | ParserConfigurationException | SAXException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return book;
	}
}
