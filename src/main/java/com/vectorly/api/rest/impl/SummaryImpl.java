package com.vectorly.api.rest.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.vectorly.api.rest.dto.Summary;

class SummaryImpl implements Summary {

	protected LocalDateTime start;
	protected LocalDateTime end;
	protected Integer playsCount;
	protected Set<Detail> details;

	public SummaryImpl(LocalDateTime start, LocalDateTime end, Integer playsCount, Set<Detail> details) {
		super();
		if (end == null || start == null)
			throw new IllegalArgumentException("Start and end times should not be null");
		this.start = start;
		this.end = end;
		this.playsCount = playsCount;
		this.details = details;
	}

	@Override
	public LocalDateTime getStart() {
		return start;
	}

	@Override
	public LocalDateTime getEnd() {
		return end;
	}

	@Override
	public Integer getPlaysCount() {
		return playsCount;
	}

	@Override
	public Set<Detail> getDetails() {
		return details == null ? new HashSet<>() : details;
	}

	@Override
	public String toString() {
		return String.format("Summary(start=%s, end=%s, plays=%d, details=%s)", start, end, playsCount, details);
	}

	/**
	 * 
	 * @author HP
	 *
	 */
	public static class DetailImpl implements Detail {

		protected String videoId;
		protected String videoTitle;
		protected Integer playsCount;

		public DetailImpl(String videoId, String videoTitle, Integer playsCount) {
			super();
			if (videoId == null)
				throw new IllegalArgumentException("Video Id should not be null");
			this.videoId = videoId;
			this.videoTitle = videoTitle;
			this.playsCount = playsCount;
		}

		@Override
		public String getVideoId() {
			return videoId;
		}

		@Override
		public String getVideoTitle() {
			return videoTitle;
		}

		@Override
		public Integer getPlaysCount() {
			return playsCount;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			Detail d = (Detail) obj;
			if (d.getVideoId().compareToIgnoreCase(videoId) == 0)
				return true;
			else
				return false;
		}

		@Override
		public int hashCode() {
			return videoId.hashCode();
		}

		@Override
		public String toString() {
			return String.format("Detail(id=%s, title=%s, plays=%d)", videoId, videoTitle, playsCount);
		}

	}

}
