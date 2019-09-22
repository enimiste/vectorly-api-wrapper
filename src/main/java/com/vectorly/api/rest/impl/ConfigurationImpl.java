package com.vectorly.api.rest.impl;

import com.vectorly.api.rest.VectorlyRest.Configuration;

/**
 * 
 * @author NOUNI EL Bachir
 *
 */
class ConfigurationImpl implements Configuration {
	protected String apiKey;
	protected Boolean resumingEnabled = true;

	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public Configuration setApiKey(String key) {
		this.apiKey = key;
		return this;
	}

	@Override
	public Boolean getIsResumingEnabled() {
		return resumingEnabled;
	}

	@Override
	public Configuration setIsResumingEnabled(Boolean enabled) {
		this.resumingEnabled = enabled;
		return this;
	}

}
