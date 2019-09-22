package com.vectorly.api.rest.dto;

import java.time.LocalDateTime;

public interface AnalyticsEvent {
	/**
	 * 
	 * @return
	 */
	Boolean getIsLiveStream();

	/**
	 * 
	 * @return
	 */
	Integer getSound();

	/**
	 * 
	 * @return
	 */
	Long getTotalLength();

	/**
	 * 
	 * @return
	 */
	Integer getPosition();

	/**
	 * 
	 * @return
	 */
	String getQuality();

	/**
	 * 
	 * @return
	 */
	String getSessionId();

	/**
	 * 
	 * @return
	 */
	String getVideoPlayer();

	/**
	 * 
	 * @return
	 */
	Boolean getAdEnabled();

	/**
	 * The video Id
	 * 
	 * @return
	 */
	String getContentAssetId();

	/**
	 * 
	 * @return
	 */
	Boolean getIsFullScreen();

	/**
	 * 
	 * @return
	 */
	LocalDateTime getTimestamp();

	/**
	 * Returns the mapped value of the event with one of the {@see EventType} enum
	 * If not listed in enums, this method returns {@see EventType.UNKNOWN}
	 * To get the original raw value use {@see AnalyticsEvent.getRawType()} method
	 * 
	 * @return
	 */
	EventType getType();
	
	/**
	 * Returns the original raw value of the event attribute received from Vectorly API
	 * 
	 * @return
	 */
	String getRawType();

	/**
	 * Event(type=%s, time=%s, videoId=%s, player=%s, liveStream=%b)
	 * @return
	 */
	String toString();

	public static enum EventType {
		VIDEO_PLAY, VIDEO_SEEK, VIDEO_PAUSE, VIDEO_END, VIDEO_BUFFER_END, VIDEO_BUFFER_START, VIDEO_LOAD, UNKNOWN
	}
}
