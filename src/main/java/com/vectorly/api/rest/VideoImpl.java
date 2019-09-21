package com.vectorly.api.rest;

class VideoImpl implements Video {

	protected String id;
	protected VideoStatus status;

	public VideoImpl(String id, VideoStatus status) {
		super();
		this.id = id;
		this.status = status;
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
	public String toString() {
		return String.format("Video(%s,%s)", id, status);
	}

}
