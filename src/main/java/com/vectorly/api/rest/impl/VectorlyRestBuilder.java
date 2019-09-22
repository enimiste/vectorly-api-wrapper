package com.vectorly.api.rest.impl;

import java.net.MalformedURLException;

import com.vectorly.api.rest.VectorlyRest;
import com.vectorly.api.rest.VectorlyRest.Configuration;

/**
 * This is a facade class to get an implementation of the VectorlyRest
 * interface
 * 
 * @author HP
 *
 */
public final class VectorlyRestBuilder {
	/**
	 * 
	 * @param apiKey
	 * @return
	 */
	public static VectorlyRest build(String apiKey) {
		return build(new ConfigurationImpl().setApiKey(apiKey));
	}

	/**
	 * 
	 * @param apiKey
	 * @param apiFilesUrl
	 * @return
	 */
	public static VectorlyRest build(Configuration config) {
		return new VectorlyRestImpl(config);
	}
}
