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

	/**
	 * 
	 * @return
	 */
	Integer getPlaysCount();

	/**
	 * Details by videos
	 * 
	 * @return
	 */
	Set<Detail> getDetails();

	/**
	 * Summary(start=%s, end=%s, plays=%d, details=%s)
	 * 
	 * @return
	 */
	String toString();

	public static interface Detail {
		/**
		 * 
		 * @return
		 */
		String getVideoId();

		/**
		 * 
		 * @return
		 */
		String getVideoTitle();

		/**
		 * 
		 * @return
		 */
		Integer getPlaysCount();

		/**
		 * Two Detail objects are same if they have the same videId
		 * 
		 * @param obj
		 * @return
		 */
		boolean equals(Object obj);

		/**
		 * Two Detail objects are same if they have the same videId
		 * 
		 * @return
		 */
		int hashCode();

		/**
		 * Detail(id=%s, title=%s, plays=%d)
		 * 
		 * @return
		 */
		String toString();
	}
}
