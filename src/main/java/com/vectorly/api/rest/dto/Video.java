package com.vectorly.api.rest.dto;

public interface Video {

	/**
	 * Returns the mapped value of the video status with one of the
	 * VideoStatus enum If not listed in enums, this method returns
	 * VideoStatus.UNKNOWN To get the original raw value use
	 * Video.getRawStatus() method
	 * 
	 * @return
	 */
	VideoStatus getStatus();

	/**
	 * Returns the original raw value of the video status attribute received from
	 * Vectorly API
	 * 
	 * @return
	 */
	String getRawType();

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
	 * Video(id=%s, name=%s, status=%s, csize=%d Bytes, orsize=%d Bytes, private=%b,
	 * clientId=%s)
	 * 
	 * @return
	 */
	String toString();

	public static enum VideoStatus {
		READY, PROCESSING, ERROR, UPLOADING, UNKNOWN,// UNKNOWN is used when we receive an unknown status for this
														// library
	}

}
