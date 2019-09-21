package com.vectorly.api.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.vectorly.api.rest.dto.AnalyticsEvent;
import com.vectorly.api.rest.dto.SecuredUrl;
import com.vectorly.api.rest.dto.Summary;
import com.vectorly.api.rest.dto.Video;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;
import com.vectorly.api.rest.impl.VectorlyRestImpl;

public interface VectorlyRest {
	/**
	 * 
	 * @param apiKey
	 * @return
	 * @throws MalformedURLException
	 */
	static VectorlyRest build(String apiKey) throws MalformedURLException {
		return build(apiKey, new URL("https://tus.vectorly.io/files/"), new URL("https://api.vectorly.io/videos/"),
				new URL("https://api.vectorly.io/analytics/"));
	}

	/**
	 * 
	 * @param apiKey
	 * @param apiFilesUrl
	 * @return
	 * @throws MalformedURLException
	 */
	static VectorlyRest build(String apiKey, URL apiUploadUrl, URL apiUrl, URL apiAnalyticsUrl) {
		return new VectorlyRestImpl(apiKey, apiUploadUrl, apiUrl, apiAnalyticsUrl);
	}

	/**
	 * 
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Video> fetchAll() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @param keyword
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Video> search(String keyword) throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @param videoId
	 * @return
	 */
	Download download(String videoId) throws VectorlyApiException;

	/**
	 * 
	 * @param id
	 * @return
	 */
	DownloadStream downloadAsStream(String id) throws VectorlyApiException;

	/**
	 * 
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Summary> analyticsSummary() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @param videoId
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<AnalyticsEvent> analyticsEvents(String videoId)
			throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @return
	 */
	Uploader uploader(URL apiFilesUrl);

	/**
	 * Uses default files url https://tus.vectorly.io/files/
	 * 
	 * @return
	 */
	Uploader uploader() throws MalformedURLException;

	/**
	 * Return a secured URL to access Private videos Expiration time default to 10
	 * minutes
	 * 
	 * @param videoId
	 * @return
	 */
	SecuredUrl secured(String videoId) throws VectorlyApiException;

	/**
	 * Return a secured URL to access Private videos
	 * 
	 * @param videoId
	 * @param expiresAt
	 * @return
	 */
	SecuredUrl secured(String videoId, LocalDateTime expiresAt) throws VectorlyApiException;

}
