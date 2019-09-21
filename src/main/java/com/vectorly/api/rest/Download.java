package com.vectorly.api.rest;

import java.io.File;

public interface Download {

	/**
	 * 
	 * @param file
	 */
	void setDestinationFolder(File file);

	/**
	 * 
	 * @throws VectorlyApiException
	 */
	void execute() throws VectorlyApiException;

	/**
	 * 
	 * @param listener
	 */
	void addDownloadListener(DownloadListener listener);

}
