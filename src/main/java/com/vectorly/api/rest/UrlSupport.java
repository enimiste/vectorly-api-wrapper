package com.vectorly.api.rest;

import java.net.URL;

/**
 * 
 * @author NOUNI El Bachir
 *
 */
public final class UrlSupport {
	/**
	 * Return a string representation of the {@see URL} without the trailing slashs
	 * 
	 * @param url
	 * @return
	 */
	public static String removeTrailingSlash(URL url) {
		return url.toString().replaceAll("/+$", "");
	}
}
