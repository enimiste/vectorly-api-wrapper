package com.vectorly.api.rest;

public class VectorlyApiAuthorizationException extends VectorlyApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220246041864905215L;

	public VectorlyApiAuthorizationException(String message) {
		super(message);
	}

	public VectorlyApiAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VectorlyApiAuthorizationException(Throwable cause) {
		super(cause);
	}

}
