package com.vectorly.api.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.vectorly.api.rest.DownloadListener.Downloaded;
import com.vectorly.api.rest.DownloadListener.Progress;

class DownloadImpl implements Download {

	final static String API_KEY_HEADER = "X-Api-Key";

	protected String videoId;
	protected File destinationFolder;
	protected Set<DownloadListener> listeners;
	protected String apiKey;
	protected URL url;

	/**
	 * 
	 * @param videoId
	 * @param handler (total bytes, input stream, output file)
	 */
	public DownloadImpl(String videoId, String apiKey, URL url) {
		this.videoId = videoId;
		this.apiKey = apiKey;
		this.url = url;
		this.listeners = new HashSet<>();
		this.destinationFolder = new File(System.getProperty("java.io.tmpdir"));// default
	}

	@Override
	public void setDestinationFolder(File file) {
		this.destinationFolder = file;
	}

	@Override
	public void execute() throws VectorlyApiException {
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
			File outFile = new File(destinationFolder, videoId + ".mp4");
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outFile), 1024);
			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			long bytesDownloaded = 0;
			while ((bytesRead = in.read(buffer)) > 0) {
				bout.write(buffer, 0, bytesRead);
				bytesDownloaded += bytesRead;
				double progress = (double) bytesDownloaded / totalBytes * 100;

				try {
					Progress p = new DownloadListener.Progress(totalBytes, bytesDownloaded, progress);
					listeners.forEach(l -> l.onProgress(p));
				} catch (Exception e) {
					// ignore any exception here
				}
			}
			try {
				bin.close();
				bout.close();
			} catch (Exception e) {
				// ignore it
			}
			try {
				Downloaded dw = new DownloadListener.Downloaded(outFile.toPath());
				listeners.forEach(l -> l.onFinished(dw));
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

	@Override
	public void addDownloadListener(DownloadListener listener) {
		this.listeners.add(listener);
	}

}
