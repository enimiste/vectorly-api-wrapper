package com.vectorly.api.rest;

import java.net.URL;

/**
 * 
 * @author NOUNI El Bachir
 *
 */
public final class UrlSupport {
	/**
	 * @param url url to be converted
	 * @return string representation of the URL without the trailing slashs
	 */
	public static String removeTrailingSlash(URL url) {
		return url.toString().replaceAll("/+$", "");
	}
}
