package com.vectorly.api.rest;

import java.io.UnsupportedEncodingException;
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
	 * 
	 * @return the configuration used to build this object
	 */
	Configuration getConfig();

	/**
	 * Fetch all videos from the Vectorly API and return it as a stream
	 * 
	 * @return All videos
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	Stream<Video> fetchAll() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Fetch videos having the keyword in their title from the Vectorly API and
	 * return it as a stream
	 * 
	 * @param keyword Video Title
	 * @return Search result
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	Stream<Video> search(String keyword) throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Returns an object representing the Download, This method should not execute
	 * any query against the Vectorly API. The execution should be handled by the
	 * implementing class of Download interface
	 * 
	 * @param videoId Video Id
	 * @return object representing the Download
	 * @throws VectorlyApiException Wrap other exceptions
	 */
	Download download(String videoId) throws VectorlyApiException;

	/**
	 * Returns an object representing the DownloadStream This method should not
	 * execute any query against the Vectorly API. The execution should be handled
	 * by the implementing class of DownloadStream interface
	 * 
	 * @param videoId Video Id
	 * @return object representing the DownloadStream
	 * @throws VectorlyApiException Wrap other exceptions
	 */
	DownloadStream downloadAsStream(String videoId) throws VectorlyApiException;

	/**
	 * Fetch videos analytics summary from the Vectorly API and return it as a
	 * stream
	 * 
	 * @return videos analytics summary
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	Stream<Summary> analyticsSummary() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Fetch videos analytics events from the Vectorly API and return it as a stream
	 * 
	 * @param videoId VideoId
	 * @return videos analytics events
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	Stream<AnalyticsEvent> analyticsEvents(String videoId)
			throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * Returns an object representing the Uploader This method should not execute
	 * any query against the Vectorly API. Example of Uploader : TusUploaderImpl
	 * that uses the TUS protocol
	 * 
	 * Only MP4 videos are supported until now
	 * 
	 * @return object representing the Uploader
	 * @throws MalformedURLException Url building error
	 */
	Uploader uploader() throws MalformedURLException;

	/**
	 * Return a secured URL to use to embed a private video, Default expiration time
	 * is 10 minutes
	 * 
	 * @param videoId Video Id
	 * @return secured URL to use to embed
	 * @throws VectorlyApiException Wrap other exceptions
	 */
	SecuredUrl secured(String videoId) throws VectorlyApiException;

	/**
	 * Return a secured URL to use to embed a private video
	 * 
	 * @param videoId   Video Id
	 * @param expiresAt expiration time
	 * @return secured URL to use to embed
	 * @throws VectorlyApiException Wrap other exceptions
	 */
	SecuredUrl secured(String videoId, LocalDateTime expiresAt) throws VectorlyApiException;

	/**
	 * 
	 * @author NOUNI El Bachir
	 *
	 */
	public static interface Configuration {

		String getApiKey();

		/**
		 * @param key secret key
		 * @return {@link Configuration} same instance for chaining calls
		 */
		Configuration setApiKey(String key);

		Boolean getIsResumingEnabled();

		/**
		 * @param enabled true to enable resuming; otherwise false
		 * @return {@link Configuration} same instance for chaining calls
		 */
		Configuration setIsResumingEnabled(Boolean enabled);

		/**
		 * Default to : "https://tus.vectorly.io/files/"
		 * 
		 * @return The url to which will be uploaded the video files
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getUploadUrl() throws IllegalStateException {
			try {
				return new URL("https://tus.vectorly.io/files/");
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Default to : "https://api.vectorly.io/videos/"
		 * 
		 * @return The base url to be prepended to other Uri to fetch data (videos,
		 *         search, ..)
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getApiUrl() throws IllegalStateException {
			try {
				return new URL("https://api.vectorly.io/videos/");
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Default to : "https://api.vectorly.io/analytics/"
		 * 
		 * @return The base url for analytics uri
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getAnalyticsUrl() throws IllegalStateException {
			try {
				return new URL("https://api.vectorly.io/analytics/");
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Build the full Url to get videos list. Default to [apiUrl]/list
		 * 
		 * @return Url to get videos list
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getVideosListUrl() throws IllegalStateException {
			try {
				return new URL(String.format("%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/list"));
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Build the full Url to search videos. Default to [apiUrl]/search/[keyword]
		 * 
		 * @param keyword video title
		 * @return Url to search videos by title
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getVideosSearchUrl(String keyword) throws IllegalStateException {
			try {
				return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/search/",
						URLEncoder.encode(keyword, "UTF-8")));
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Build the full Url to download a given video. Default to
		 * [apiUrl]/download/[videoId]
		 * 
		 * @param videoId Video Id
		 * @return url to download the file from
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getVideoDownloadUrl(String videoId) throws IllegalStateException {
			try {
				return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getApiUrl()), "/download/",
						URLEncoder.encode(videoId, "UTF-8")));
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Build the full Url to list videos summaries. Default to [apiUrl]/summary
		 * 
		 * @return Url to list all summaries
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getAnalyticsSummaryUrl() throws IllegalStateException {
			try {
				return new URL(String.format("%s%s", UrlSupport.removeTrailingSlash(getAnalyticsUrl()), "/summary/"));
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Build the full Url to list events of a given Video by its id. Default to
		 * [apiUrl]/events/video/[videoId]
		 * 
		 * @param videoId Video id
		 * @return Url to list events of the given videoId (Video play, pause, seek,
		 *         ...)
		 * @throws IllegalStateException if any error happens when building the url
		 */
		default URL getAnalyticsEventsUrl(String videoId) throws IllegalStateException {
			try {
				return new URL(String.format("%s%s%s", UrlSupport.removeTrailingSlash(getAnalyticsUrl()),
						"/events/video/", URLEncoder.encode(videoId, "UTF-8")));
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * Default to "X-Api-Key"
		 * 
		 * @return The http request header on which the Api Key will be put to be
		 *         authenticated
		 */
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
