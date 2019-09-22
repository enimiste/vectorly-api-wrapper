package com.vectorly.api.rest.dto;

public interface Video {

	/**
	 * Returns the mapped value of the video status with one of the VideoStatus enum
	 * If not listed in enums, this method returns VideoStatus.UNKNOWN To get the
	 * original raw value use Video.getRawStatus() method
	 * 
	 * @return Video status
	 */
	VideoStatus getStatus();

	/**
	 * @return the original raw value of the video status attribute received from
	 *         Vectorly API
	 */
	String getRawType();

	/**
	 * 
	 * @return videoId
	 */
	String getId();

	/**
	 * @return Original Size in Bytes
	 */
	Long getOriginalSize();

	/**
	 * @return Compressed Size in Bytes
	 */
	Long getSize();

	Boolean getIsPrivate();

	/**
	 * @return Email
	 */
	String getClientId();

	/**
	 * 
	 * @return Video(id=%s, name=%s, status=%s, csize=%d Bytes, orsize=%d Bytes,
	 *         private=%b, clientId=%s)
	 */
	String toString();

	public static enum VideoStatus {
		READY, PROCESSING, ERROR, UPLOADING, UPLOAD_STALLED, UNKNOWN,
		// UNKNOWN is used when we receive an unknown status for this library
	}

}
