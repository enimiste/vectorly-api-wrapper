package com.vectorly.api.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.vectorly.api.rest.DownloadStreamListener.Progress;

class DownloadStreamImpl implements DownloadStream {

	final static String API_KEY_HEADER = "X-Api-Key";

	protected String videoId;
	protected Set<DownloadStreamListener> listeners;
	protected String apiKey;
	protected URL url;

	/**
	 * 
	 * @param videoId
	 * @param handler (total bytes, input stream, output file)
	 */
	public DownloadStreamImpl(String videoId, String apiKey, URL url) {
		this.videoId = videoId;
		this.apiKey = apiKey;
		this.url = url;
		this.listeners = new HashSet<>();
	}

	@Override
	public void addDownloadListener(DownloadStreamListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void execute(OutputStream out) throws VectorlyApiException {
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
			long totalBytes = con.getHeaderFieldLong("Content-Length", 0);
			InputStream in = con.getInputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out, 1024);
			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			long bytesDownloaded = 0;
			while ((bytesRead = in.read(buffer)) > 0) {
				bout.write(buffer, 0, bytesRead);
				bytesDownloaded += bytesRead;
				double progress = (double) bytesDownloaded / totalBytes * 100;

				try {
					Progress p = new Progress(totalBytes, bytesDownloaded, progress);
					listeners.forEach(l -> l.onProgress(p));
				} catch (Exception e) {
					// ignore any exception here
				}
			}
			try {
				bin.close();
			} catch (Exception e) {
				// ignore it
			}
			try {
				listeners.forEach(l -> l.onFinished());
			} catch (Exception e) {
				// ignore any exception here
			}
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

}
