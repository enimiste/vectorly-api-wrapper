package com.vectorly.api.rest;

import java.io.File;

import com.vectorly.api.rest.exception.FileTypeNotSupportedException;

/**
 * 
 * @author NOUNI El bachir
 *
 */
public interface Uploader {

	/**
	 * Builds a Upload object that will be executed by the caller. This method
	 * should not run any query against the Vectorly Api
	 * 
	 * @param file The path to the file to be uploaded
	 * @return Upload object that will be executed by the caller
	 * @throws FileTypeNotSupportedException In case a not supported file is given
	 */
	Upload upload(File file) throws FileTypeNotSupportedException;

}
