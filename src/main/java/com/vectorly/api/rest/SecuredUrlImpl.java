package com.vectorly.api.rest;

import java.net.URL;

class SecuredUrlImpl implements SecuredUrl {

	protected URL url;
	protected String token;

	public SecuredUrlImpl(URL url, String token) {
		super();
		this.url = url;
		this.token = token;
	}

	@Override
	public URL getUrl() {
		return url;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return url.toString();
	}

}
