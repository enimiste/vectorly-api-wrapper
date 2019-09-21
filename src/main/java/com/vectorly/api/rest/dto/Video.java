package com.vectorly.api.rest.dto;

public interface Video {

	/**
	 * 
	 * @return
	 */
	VideoStatus getStatus();

	/**
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Original Size in Bytes
	 * 
	 * @return
	 */
	Long getOriginalSize();

	/**
	 * Compressed Size in Bytes
	 * 
	 * @return
	 */
	Long getSize();

	/**
	 * 
	 * @return
	 */
	Boolean getIsPrivate();

	/**
	 * Email
	 * 
	 * @return
	 */
	String getClientId();

	/**
	 * Video(id=%s, name=%s, status=%s, csize=%d Bytes, orsize=%d Bytes, private=%b, clientId=%s)
	 * @return
	 */
	String toString();
	
	public static enum VideoStatus {
		READY, PROCESSING, ERROR, UNKNOWN,// UNKNOWN is used when we receive an unknown status for this library
	}

}
