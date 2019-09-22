package com.vectorly.api.rest.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vectorly.api.rest.Download;
import com.vectorly.api.rest.DownloadStream;
import com.vectorly.api.rest.Uploader;
import com.vectorly.api.rest.VectorlyRest;
import com.vectorly.api.rest.dto.AnalyticsEvent;
import com.vectorly.api.rest.dto.AnalyticsEvent.EventType;
import com.vectorly.api.rest.dto.SecuredUrl;
import com.vectorly.api.rest.dto.Summary;
import com.vectorly.api.rest.dto.Video;
import com.vectorly.api.rest.dto.Video.VideoStatus;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

class VectorlyRestImpl implements VectorlyRest {

	protected Configuration config;

	public VectorlyRestImpl(Configuration config) {
		this.config = config;
	}

	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param fn
	 * @return
	 * @throws VectorlyApiException
	 */
	private <T> T httpCall(URL url, Function<String, T> fn)
			throws VectorlyApiAuthorizationException, VectorlyApiException {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty(config.getApiKeyRequestHeader(), config.getApiKey());
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();

			int responseCode = con.getResponseCode();
			if (!(responseCode >= 200 && responseCode < 300)) {
				if (responseCode == 403 || responseCode == 401)
					throw new VectorlyApiAuthorizationException(
							"unexpected status code (" + responseCode + ") while connecting to " + url.toString());
				else
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
		} catch (VectorlyApiAuthorizationException e) {
			throw e;
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
		JSONObject json = (JSONObject) ob;

		String id = json.getString("id");
		String name = json.getString("name");
		VideoStatus status = null;
		String rawStatus = json.getString("status");
		try {
			status = VideoStatus.valueOf(rawStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			// To avoid bloc code if we receive a not defined status
			status = VideoStatus.UNKNOWN;
		}
		long originalSize = json.getLong("original_size");
		boolean isPrivate = json.getBoolean("private");
		long size = json.getLong("size");
		String clientId = json.getString("client_id");

		return new VideoImpl(id, status, name, size, originalSize, clientId, isPrivate, rawStatus);
	}

	@Override
	public Stream<Video> fetchAll() throws VectorlyApiAuthorizationException, VectorlyApiException {
		try {

			return httpCall(config.getVideosListUrl(), content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::videoFromJsonObject);
			});
		} catch (VectorlyApiAuthorizationException e) {
			throw e;
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Stream<Video> search(String keyword) throws VectorlyApiAuthorizationException, VectorlyApiException {
		try {

			return httpCall(config.getVideosSearchUrl(keyword), content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::videoFromJsonObject);
			});
		} catch (VectorlyApiAuthorizationException e) {
			throw e;
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Download download(String videoId) throws VectorlyApiException {
		try {
			return new DownloadImpl(videoId, config.getApiKey(), config.getVideoDownloadUrl(videoId));
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public DownloadStream downloadAsStream(String videoId) throws VectorlyApiException {
		try {
			return new DownloadStreamImpl(videoId, config.getApiKey(), config.getVideoDownloadUrl(videoId));
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	static LocalDateTime dateTimeFrom(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
		return LocalDateTime.parse(time, formatter);
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	static Summary summaryfromJsonObject(Object ob) {
		JSONObject json = (JSONObject) ob;
		LocalDateTime start = dateTimeFrom(json.getString("start"));
		LocalDateTime end = dateTimeFrom(json.getString("end"));//
		int plays = json.getInt("plays");
		JSONObject byVideoJson = json.getJSONObject("by_video");
		Set<Summary.Detail> details = byVideoJson.keySet().stream().<Summary.Detail>map(k -> {
			JSONObject v = byVideoJson.getJSONObject(k);
			String title = v.getString("title");
			int pl = v.getInt("plays");
			return new SummaryImpl.DetailImpl(k, title, pl);
		}).collect(Collectors.toSet());
		return new SummaryImpl(start, end, plays, details);
	}

	@Override
	public Stream<Summary> analyticsSummary() throws VectorlyApiAuthorizationException, VectorlyApiException {
		try {
			return httpCall(config.getAnalyticsSummaryUrl(), content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::summaryfromJsonObject);
			});
		} catch (VectorlyApiAuthorizationException e) {
			throw e;
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
		JSONObject json = (JSONObject) ob;
		boolean isLiveStream = json.getBoolean("livestream");
		int sound = json.getInt("sound");
		long totalLength = json.getLong("total_length");
		int position = json.getInt("position");
		String quality = json.getString("quality");
		String sessionId = json.getString("session_id");
		String videoPlayer = json.getString("video_player");
		boolean isAdEnabled = json.getBoolean("ad_enabled");
		String contentAssetId = json.getString("content_asset_id");
		boolean isFullScreen = json.getBoolean("full_screen");
		LocalDateTime timestamp = dateTimeFrom(json.getString("timestamp"));
		EventType type = null;
		String stype = null;
		try {
			stype = json.getString("event").toUpperCase();
			String[] stypes = stype.split(" ");
			type = EventType.valueOf(String.join("_", stypes));
		} catch (IllegalArgumentException e) {
			System.out.println(stype);
			// To avoid bloc code if we receive a not defined status
			type = EventType.UNKNOWN;
		}
		return new AnalyticsEventImpl(isLiveStream, sound, totalLength, position, quality, sessionId, videoPlayer,
				isAdEnabled, contentAssetId, isFullScreen, timestamp, type, stype);
	}

	@Override
	public Stream<AnalyticsEvent> analyticsEvents(String videoId)
			throws VectorlyApiAuthorizationException, VectorlyApiException {
		try {
			return httpCall(config.getAnalyticsEventsUrl(videoId), content -> {
				JSONArray jsonArr = new JSONArray(content);
				Iterable<Object> itr = () -> jsonArr.iterator();
				return StreamSupport.stream(itr.spliterator(), false).map(VectorlyRestImpl::eventFromJsonObject);
			});
		} catch (VectorlyApiAuthorizationException e) {
			throw e;
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Uploader uploader() throws MalformedURLException {
		return new TusProtocolUploaderImpl(config.getUploadUrl(), config.getApiKey(), config.getIsResumingEnabled());
	}

	@Override
	public SecuredUrl secured(String videoId) throws VectorlyApiException {
		return secured(videoId, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
	}

	@Override
	public SecuredUrl secured(String videoId, LocalDateTime expiresAt) throws VectorlyApiException {
		try {
			return (new JWTSecuredUrlImpl(config.getApiKey(), config.getApiUrl())).generate(videoId, expiresAt);
		} catch (IllegalStateException e) {
			throw new VectorlyApiException(e);
		}
	}

	@Override
	public Configuration getConfig() {
		return config;
	}
}
