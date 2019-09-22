package com.vectorly.api.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.vectorly.api.rest.dto.AnalyticsEvent;
import com.vectorly.api.rest.dto.SecuredUrl;
import com.vectorly.api.rest.dto.Summary;
import com.vectorly.api.rest.dto.Video;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

/**
 * 
 * @author NOUNI El Bachir
 *
 */
public interface VectorlyRest {

	/**
	 * Fetch all videos from the Vectorly API and return it as a stream
	 * 
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Video> fetchAll() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Fetch videos having the keyword in their title from the Vectorly API and
	 * return it as a stream
	 * 
	 * @param keyword
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Video> search(String keyword) throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Returns an object representing the Download, This method should not execute
	 * any query against the Vectorly API. The execution should be handled by the
	 * implementing class of Download interface
	 * 
	 * @param videoId
	 * @return
	 */
	Download download(String videoId) throws VectorlyApiException;

	/**
	 * Returns an object representing the DownloadStream This method should not
	 * execute any query against the Vectorly API. The execution should be handled
	 * by the implementing class of DownloadStream interface
	 * 
	 * @param id
	 * @return
	 */
	DownloadStream downloadAsStream(String id) throws VectorlyApiException;

	/**
	 * Fetch videos analytics summary from the Vectorly API and return it as a
	 * stream
	 * 
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<Summary> analyticsSummary() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Fetch videos analytics events from the Vectorly API and return it as a stream
	 * 
	 * @param videoId
	 * @return
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	Stream<AnalyticsEvent> analyticsEvents(String videoId)
			throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Returns an object representing the Uploader This method should not execute
	 * any query against the Vectorly API. Example of Uploader : TusUploaderImpl
	 * that uses the TUS protocol
	 * 
	 * Only MP4 videos are supported
	 * 
	 * @return
	 */
	Uploader uploader() throws MalformedURLException;

	/**
	 * Return a secured URL to use to embed a private video, Default expiration time
	 * is 10 minutes
	 * 
	 * @param videoId
	 * @return
	 */
	SecuredUrl secured(String videoId) throws VectorlyApiException;

	/**
	 * Return a secured URL to use to embed a private video
	 * 
	 * @param videoId
	 * @param expiresAt
	 * @return
	 */
	SecuredUrl secured(String videoId, LocalDateTime expiresAt) throws VectorlyApiException;

	/**
	 * 
	 * @author NOUNI El Bachir
	 *
	 */
	public static interface Configuration {

		/**
		 * 
		 * @return
		 */
		String getApiKey();

		/**
		 * @param key
		 * @return {@link Configuration} for chaining calls
		 */
		Configuration setApiKey(String key);

		/**
		 * 
		 * @return
		 */
		Boolean getIsResumingEnabled();

		/**
		 * @param enabled
		 * @return {@link Configuration} for chaining calls
		 */
		Configuration setIsResumingEnabled(Boolean enabled);

		/**
		 * 
		 * @return
		 */
		default URL getUploadUrl() throws MalformedURLException {
			return new URL("https://tus.vectorly.io/files/");
		}

		/**
		 * 
		 * @return
		 */
		default URL getApiUrl() throws MalformedURLException {
			return new URL("https://api.vectorly.io/videos/");
		}

		/**
		 * 
		 * @return
		 */
		default URL getAnalyticsUrl() throws MalformedURLException {
			return new URL("https://api.vectorly.io/analytics/");
		}

		/**
		 * 
		 * @return
		 * @throws MalformedURLException
		 */
		default URL getVideosListUrl() throws MalformedURLException {
			return new URL(String.format("%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/list"));
		}

		default URL getVideosSearchUrl(String keyword) throws MalformedURLException, Exception {
			return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/search/",
					URLEncoder.encode(keyword, "UTF-8")));
		}

		default URL getVideoDownloadUrl(String videoId) throws MalformedURLException, Exception {
			return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/download/",
					URLEncoder.encode(videoId, "UTF-8")));
		}

		default URL getAnalyticsSummaryUrl() throws MalformedURLException {
			return new URL(String.format("%s%s", UrlSupport.removeTrailingSlash(getAnalyticsUrl()), "/summary/"));
		}

		default URL getAnalyticsEventsUrl(String videoId) throws MalformedURLException, Exception {
			return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getAnalyticsUrl()), "/events/video/",
					URLEncoder.encode(videoId, "UTF-8")));
		}

		default String getApiKeyRequestHeader() {
			return "X-Api-Key";
		}
	}
	
	/**
	 * 
	 * @author NOUNI EL Bachir
	 *
	 */
	public static enum VideoType {
		VIDEO_MP4,
	}
}
