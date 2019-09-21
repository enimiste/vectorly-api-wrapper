package com.vectorly.api.rest;

import java.io.FileNotFoundException;

public interface Upload {

	/**
	 * 
	 * @param fileName
	 * @throws IllegalArgumentException if the file name is invalid
	 */
	void setCustomName(String fileName) throws IllegalArgumentException;

	/**
	 * 
	 * @param listener
	 */
	void addUploadListener(UploadListener listener);

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws VectorlyApiException
	 */
	void execute() throws FileNotFoundException, VectorlyApiException;

}
