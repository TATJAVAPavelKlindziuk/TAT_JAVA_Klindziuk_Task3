package com.klindziuk.offlinelibrary.serverobserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

/**
 * @author Pavel_Klindziuk
 * Read files from specified directories
 * using java.nio.SimpleFileReader<T>
 */
public class XmlFileReader implements Runnable {
	private static final Logger logger = Logger.getLogger(XmlFileReader.class);
	private static final String NEW_FILEWALK_THREAD_START = "Starting new filewalk thread - ";
	private static final String INITIALIZATION_MESSAGE_EXCEPTION = "Cannot initialize filewalker.Invalid path.";
	private String filePath;
	private Path directory;
	
	public XmlFileReader(String filePath) {
		if(null == filePath || filePath.isEmpty() ) {
			throw new IllegalArgumentException(INITIALIZATION_MESSAGE_EXCEPTION);
		}
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try {
			logger.info(NEW_FILEWALK_THREAD_START + Thread.currentThread().getName());
			directory = Paths.get(filePath);
			FileVisitor visitor = new FileVisitor();
			Files.walkFileTree(directory, visitor);
			} catch (NoSuchFileException nsfex) {
			logger.error("Unfortunately \"" + directory + "\" does not exists");
		} catch (IOException ioex) {
			logger.error(ioex);
		}
	}
}

