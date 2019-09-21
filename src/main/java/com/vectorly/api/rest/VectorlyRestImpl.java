package com.vectorly.api.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

class VectorlyRestImpl implements VectorlyRest {
	final static String VIDEOS_LIST = "/list";
	final static String VIDEOS_SEARCH = "/search/";
	final static String VIDEOS_DOWNLOAD = "/download/";
	final static String ANALYTICS_SUMMARY = "/summary/";
	final static String ANALYTICS_EVENTS = "/events/video/";
	final static String API_KEY_HEADER = "X-Api-Key";

	protected String apiKey;
	protected boolean enableResuming;
	protected URL apiUploadUrl;
	protected URL apiUrl;
	protected URL apiAnalyticsUrl;

	public VectorlyRestImpl(String apiKey, URL apiUploadUrl, URL apiUrl, URL apiAnalyticsUrl) {
		this.apiKey = apiKey;
		this.apiUploadUrl = apiUploadUrl;
		this.apiUrl = apiUrl;
		this.apiAnalyticsUrl = apiAnalyticsUrl;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private static String asString(URL url) {
		return url.toString().replaceAll("/+$", "");
	}

	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param fn
	 * @return
	 * @throws VectorlyApiException
	 */
	private <T> T httpCall(URL url, Function<String, T> fn) throws VectorlyApiException {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty(API_KEY_HEADER, apiKey);
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();

			int responseCode = con.getResponseCode();
			if (!(responseCode >= 200 && responseCode < 300)) {
				throw new VectorlyApiException(
						"unexpected status code (" + responseCode + ") while connecting to " + url.toString());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return fn.apply(content.toString());
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	/**
	 * 
	 * @param ob
	 * @return
	 */
	static Video videoFromJsonObject(Object ob) {
		// TODO
		// JSONObject json = (JSONObject)ob;
		return null;
	}

	@Override
	public Stream<Video> fetchAll() throws VectorlyApiException {
		try {
			URL url = new URL(String.format("%s%s", asString(apiUrl), VIDEOS_LIST));
			return httpCall(url, content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::videoFromJsonObject);
			});
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Stream<Video> search(String keyword) throws VectorlyApiException {
		try {
			URL url = new URL(
					String.format("%s%s%s", asString(apiUrl), VIDEOS_SEARCH, URLEncoder.encode(keyword, "UTF-8")));
			return httpCall(url, content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::videoFromJsonObject);
			});
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Download download(String videoId) throws VectorlyApiException {
		try {
			URL url = new URL(
					String.format("%s%s%s", asString(apiUrl), VIDEOS_DOWNLOAD, URLEncoder.encode(videoId, "UTF-8")));
			return new DownloadImpl(videoId, apiKey, url);
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}
	
	@Override
	public DownloadStream downloadAsStream(String videoId) throws VectorlyApiException {
		try {
			URL url = new URL(
					String.format("%s%s%s", asString(apiUrl), VIDEOS_DOWNLOAD, URLEncoder.encode(videoId, "UTF-8")));
			return new DownloadStreamImpl(videoId, apiKey, url);
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	private static Summary summaryfromJsonObject(JSONObject json) {
		// TODO
		return null;
	}

	@Override
	public Summary analyticsSummary() throws VectorlyApiException {
		try {
			URL url = new URL(String.format("%s%s", asString(apiAnalyticsUrl), ANALYTICS_SUMMARY));
			return httpCall(url, content -> {
				JSONObject json = new JSONObject(content);
				return summaryfromJsonObject(json);
			});
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	/**
	 * 
	 * @param ob
	 * @return
	 */
	static AnalyticsEvent eventFromJsonObject(Object ob) {
		// TODO
		// JSONObject json = (JSONObject)ob;
		return null;
	}

	@Override
	public Stream<AnalyticsEvent> analyticsEvents(String videoId) throws VectorlyApiException {
		try {
			URL url = new URL(String.format("%s%s%s", asString(apiAnalyticsUrl), ANALYTICS_EVENTS,
					URLEncoder.encode(videoId, "UTF-8")));
			return httpCall(url, content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::eventFromJsonObject);
			});
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Uploader uploader(URL apiUploadUrl) {
		return new TusUploaderImpl(apiUploadUrl, apiKey);
	}

	@Override
	public Uploader uploader() throws MalformedURLException {
		return uploader(apiUploadUrl);
	}

	@Override
	public SecuredUrl secured(String videoId) throws VectorlyApiException {
		return secured(videoId, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
	}

	@Override
	public SecuredUrl secured(String videoId, LocalDateTime expiresAt) throws VectorlyApiException {
		return (new JWTSecuredUrlImpl(apiKey, apiUrl)).generate(videoId, expiresAt);
	}
}
