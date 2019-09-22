package com.vectorly.api.rest.exception;

/**
 * Thrown in case of Forbidden access or UnAuthorized access by the Vectorly Api
 * 
 * @author NOUNI El Bachir
 *
 */
public class VectorlyApiAuthorizationException extends VectorlyApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
