package com.vectorly.api.rest.exception;

/**
 * This Exception is thrown when the user try to upload a file that is not
 * supported
 * 
 * @author NOUNI EL Bachir
 *
 */
public class FileTypeNotSupportedException extends VectorlyApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String filename;

	public FileTypeNotSupportedException(String message) {
		super(message);
	}

	public FileTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileTypeNotSupportedException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @return the filename that causes this exception
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 
	 * @param value the filename
	 * @return the same instance to enable chaining
	 */
	public FileTypeNotSupportedException setFilename(String value) {
		this.filename = value;
		return this;
	}

}
