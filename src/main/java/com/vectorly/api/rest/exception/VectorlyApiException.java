package com.vectorly.api.rest.exception;

public class VectorlyApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VectorlyApiException(String message) {
		super(message);
	}

	public VectorlyApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public VectorlyApiException(Throwable cause) {
		super(cause);
	}

}
