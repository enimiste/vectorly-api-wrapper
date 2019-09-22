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
	 * @return
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public FileTypeNotSupportedException setFilename(String value) {
		this.filename = value;
		return this;
	}

}
