package com.vectorly.api.rest.impl;

import java.time.LocalDateTime;

import com.vectorly.api.rest.dto.AnalyticsEvent;

class AnalyticsEventImpl implements AnalyticsEvent {

	protected boolean isLiveStream;
	protected int sound;
	protected long totalLength;
	protected int position;
	protected String quality;
	protected String sessionId;
	protected String videoPlayer;
	boolean isAdEnabled;
	protected String contentAssetId;
	boolean isFullScreen;
	protected LocalDateTime timestamp;
	protected EventType type;
	protected String rawType;

	public AnalyticsEventImpl(boolean isLiveStream, int sound, long totalLength, int position, String quality,
			String sessionId, String videoPlayer, boolean isAdEnabled, String contentAssetId, boolean isFullScreen,
			LocalDateTime timestamp, EventType type, String rawType) {
		super();
		this.isLiveStream = isLiveStream;
		this.sound = sound;
		this.totalLength = totalLength;
		this.position = position;
		this.quality = quality;
		this.sessionId = sessionId;
		this.videoPlayer = videoPlayer;
		this.isAdEnabled = isAdEnabled;
		this.contentAssetId = contentAssetId;
		this.isFullScreen = isFullScreen;
		this.timestamp = timestamp;
		this.type = type;
		this.rawType = rawType;
	}

	@Override
	public Boolean getIsLiveStream() {
		return isLiveStream;
	}

	@Override
	public Integer getSound() {
		return sound;
	}

	@Override
	public Long getTotalLength() {
		return totalLength;
	}

	@Override
	public Integer getPosition() {
		return position;
	}

	@Override
	public String getQuality() {
		return quality;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String getVideoPlayer() {
		return videoPlayer;
	}

	@Override
	public Boolean getAdEnabled() {
		return isAdEnabled;
	}

	@Override
	public String getContentAssetId() {
		return contentAssetId;
	}

	@Override
	public Boolean getIsFullScreen() {
		return isFullScreen;
	}

	@Override
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public EventType getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("Event(type=%s, time=%s, videoId=%s, player=%s, liveStream=%b)",
				(type == EventType.UNKNOWN ? rawType : type), timestamp, contentAssetId, videoPlayer, isLiveStream);
	}

	@Override
	public String getRawType() {
		return rawType;
	}

}
