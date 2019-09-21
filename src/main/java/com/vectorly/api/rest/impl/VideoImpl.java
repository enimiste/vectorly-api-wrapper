package com.vectorly.api.rest.impl;

import com.vectorly.api.rest.dto.Video;

class VideoImpl implements Video {

	protected String id;
	protected String name;
	protected Long size;
	protected Long originalSize;
	protected VideoStatus status;
	protected Boolean isPrivate;
	protected String clientId;

	public VideoImpl(String id, VideoStatus status, String name, long size, long originalSize, String clientId,
			boolean isPrivate) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.size = size;
		this.originalSize = originalSize;
		this.isPrivate = isPrivate;
		this.clientId = clientId;
	}

	@Override
	public VideoStatus getStatus() {
		return status;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Long getOriginalSize() {
		return originalSize;
	}

	@Override
	public Long getSize() {
		return size;
	}

	@Override
	public Boolean getIsPrivate() {
		return isPrivate;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return String.format(
				"Video(id=%s, name=%s, status=%s, csize=%d Bytes, orsize=%d Bytes, private=%b, clientId=%s)", id, name,
				status, size, originalSize, isPrivate, clientId);
	}
}
