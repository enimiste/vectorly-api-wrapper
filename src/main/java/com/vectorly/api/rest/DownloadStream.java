package com.vectorly.api.rest;

import java.io.OutputStream;

public interface DownloadStream {

	void addDownloadListener(DownloadStreamListener downloadStreamListener);

	/**
	 * Write the downloaded file into the output stream
	 * It is to the caller to close the output stream
	 * 
	 * @param out
	 */
	void execute(OutputStream out) throws VectorlyApiException;

}
