package com.vectorly.api.rest;

import java.net.URL;

public interface SecuredUrl {
	/**
	 * 
	 * @return Returns the full url containing the signed token
	 */
	URL getUrl();

	/**
	 * 
	 * @return the signed token
	 */
	String getToken();

	/**
	 * 
	 * getUrl().toString()
	 * 
	 * @return the final url to be used to play the video
	 */
	String toString();
}
