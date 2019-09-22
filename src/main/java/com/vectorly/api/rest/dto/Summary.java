package com.vectorly.api.rest.dto;

import java.time.LocalDateTime;
import java.util.Set;

public interface Summary {

	/**
	 * 
	 * @return in GMT zone time
	 */
	LocalDateTime getStart();

	/**
	 * 
	 * @return in GMT zone time
	 */
	LocalDateTime getEnd();

	Integer getPlaysCount();

	/**
	 * Details by videos
	 * 
	 * @return a set of details
	 */
	Set<Detail> getDetails();

	/**
	 * 
	 * @return Summary(start=%s, end=%s, plays=%d, details=%s)
	 */
	String toString();

	public static interface Detail {
		String getVideoId();

		String getVideoTitle();

		Integer getPlaysCount();

		/**
		 * Two Detail objects are same if they have the same videoId
		 * 
		 * @param obj another detail object
		 * @return true if the two objects has the same videoId
		 */
		boolean equals(Object obj);

		/**
		 * 
		 * @return videoId.hashCode();
		 */
		int hashCode();

		/**
		 * @return Detail(id=%s, title=%s, plays=%d)
		 */
		String toString();
	}
}
