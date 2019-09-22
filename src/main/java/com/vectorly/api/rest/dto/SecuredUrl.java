package com.vectorly.api.rest.dto;

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
	 * Should be a shortcut to getUrl().toString()
	 * 
	 * @return the url to be embedd in iframes
	 */
	String toString();
}
