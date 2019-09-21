package com.vectorly.api.rest;

import java.io.File;

public interface Uploader {

	/**
	 * 
	 * @param file
	 * @return
	 */
	Upload upload(File file);

}
