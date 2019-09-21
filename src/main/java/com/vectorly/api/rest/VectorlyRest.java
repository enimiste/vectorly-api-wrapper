package com.vectorly.api.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Stream;

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
	 */
	Stream<Video> fetchAll() throws VectorlyApiException;

	/**
	 * 
	 * @param keyword
	 * @return
	 */
	Stream<Video> search(String keyword) throws VectorlyApiException;

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
	 * @param videoId
	 * @return
	 */
	Summary analyticsSummary() throws VectorlyApiException;

	/**
	 * 
	 * @param videoId
	 * @return
	 */
	Stream<AnalyticsEvent> analyticsEvents(String videoId) throws VectorlyApiException;

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
