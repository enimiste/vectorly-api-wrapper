package com.vectorly.api.rest.dto;

import java.time.LocalDateTime;

public interface AnalyticsEvent {
	Boolean getIsLiveStream();

	Integer getSound();

	/**
	 * 
	 * @return in Bytes
	 */
	Long getTotalLength();

	Integer getPosition();

	/**
	 * 
	 * @return ex "standard"
	 */
	String getQuality();

	String getSessionId();

	String getVideoPlayer();

	Boolean getAdEnabled();

	/**
	 * The video Id
	 * 
	 * @return videoId
	 */
	String getContentAssetId();

	Boolean getIsFullScreen();

	/**
	 * 
	 * @return in GMT zone time
	 */
	LocalDateTime getTimestamp();

	/**
	 * Returns the mapped value of the event with one of the EventType enum If not
	 * listed in enums, this method returns EventType.UNKNOWN To get the original
	 * raw value use AnalyticsEvent.getRawType() method
	 * 
	 * @return event type
	 */
	EventType getType();

	/**
	 * 
	 * @return original raw value of the event attribute received from Vectorly API
	 */
	String getRawType();

	/**
	 * @return Event(type=%s, time=%s, videoId=%s, player=%s, liveStream=%b)
	 */
	String toString();

	public static enum EventType {
		VIDEO_PLAY, VIDEO_SEEK, VIDEO_PAUSE, VIDEO_END, VIDEO_BUFFER_END, VIDEO_BUFFER_START, VIDEO_LOAD, UNKNOWN
	}
}
