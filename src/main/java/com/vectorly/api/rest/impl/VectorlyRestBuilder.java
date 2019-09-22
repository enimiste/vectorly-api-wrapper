package com.vectorly.api.rest.impl;

import java.net.MalformedURLException;

import com.vectorly.api.rest.VectorlyRest;
import com.vectorly.api.rest.VectorlyRest.Configuration;

/**
 * This is a facade class to get an implementation of the VectorlyRest interface
 * 
 * @author HP
 *
 */
public final class VectorlyRestBuilder {
	/**
	 * Builds a {@link VectorlyRest} instance using this api secret and a defaut
	 * configuration
	 * 
	 * @param apiKey secret key from the Vectorly Dashboard
	 * @return instance of {@link VectorlyRest}
	 */
	public static VectorlyRest build(String apiKey) {
		return build(new ConfigurationImpl().setApiKey(apiKey));
	}

	/**
	 * Builds a {@link VectorlyRest} instance using the given configuration
	 * 
	 * @param config configuration to be used to initialize a {@link VectorlyRest}
	 *               instance
	 * @return instance of {@link VectorlyRest}
	 */
	public static VectorlyRest build(Configuration config) {
		return new VectorlyRestImpl(config);
	}
}
