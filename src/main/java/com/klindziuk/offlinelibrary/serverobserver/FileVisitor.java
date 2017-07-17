package com.klindziuk.offlinelibrary.serverobserver;

import java.io.IOException;
import java.nio.file.FileVisitResult;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.log4j.Logger;

import com.klindziuk.offlinelibrary.controller.util.RequestParser;

/*
 *  A simple visitor of files
 */
public class FileVisitor extends SimpleFileVisitor<Path> {
	private static final Logger logger = Logger.getLogger(FileVisitor.class);
	private static final String BAD_FILE_EXTENSION_MESSAGE = "Bad file extension for file \"";
	private static final String XML_EXTENSION = "xml";
	private static final String EXCEPTION_MESSAGE = "Cannot read extension from this file path.";
	private static final int VALID_EXTENSION_SIZE = 3;
	private final Requester requester; 
	private final Server server;
	
	public FileVisitor() {
		requester = new Requester();
		server = Server.getInstance();
		requester.addListener(server);
	}

	/*
	 * read files in current directory and sending reaquests
	 * 
	 * @ param Path file
	 * @ param BasicFileAttributes attr
	 */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (attrs.isRegularFile() && checkFileXtension(file.getFileName().toString())) {
			logger.info("Parsing file: " + file.getFileName());
			String filename = file.toAbsolutePath().toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
			String request = RequestParser.readFile(filename);
			requester.sendRequest(request);
		}
		return FileVisitResult.CONTINUE;
	}

	/*
	 * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object,
	 * java.nio.file.attribute.BasicFileAttributes)
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
		logger.info("Reading requests from \"" + arg0.toAbsolutePath().toRealPath(LinkOption.NOFOLLOW_LINKS)
				);
		return FileVisitResult.CONTINUE;
	}

		
	/**
	 * checking file extension
	 * @param fileName
	 * @return result of matching with available extensions
	 * @throws IllegalArgumentException
	 */
	private boolean checkFileXtension(String fileName) throws IllegalArgumentException {
		if(null == fileName || fileName.isEmpty()){
			throw new IllegalArgumentException(EXCEPTION_MESSAGE); 
		}
		try {
			String extension = cutFileExtension(fileName);
			if (extension.equals(XML_EXTENSION)) {
				return true;
			}
		} catch (IllegalArgumentException ieax) {
			logger.error(ieax.getMessage());
		}
		return false;
	}
	
	/**
	 * cut extension from file
	 * @param fileName
	 * @return file extension
	 */
	private String cutFileExtension(String fileName) throws IllegalArgumentException{
		if(null == fileName || fileName.isEmpty()){
			throw new IllegalArgumentException(EXCEPTION_MESSAGE); 
		}
		int extensionSize = fileName.lastIndexOf('.');
			if (VALID_EXTENSION_SIZE != fileName.substring(extensionSize + 1).length() ) {
			throw new IllegalArgumentException(BAD_FILE_EXTENSION_MESSAGE + fileName + "\"");
		}
		return fileName.substring(extensionSize + 1);
	}
}