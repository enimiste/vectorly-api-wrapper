package com.vectorly.api.rest;

import java.net.URL;

public interface UploadListener {

	/**
	 * 
	 * @param value
	 */
	void onProgress(Progress value);

	/**
	 * 
	 * @param url
	 */
	void onFinished(Uploaded uploaded);

	/**
	 * 
	 * @author HP
	 *
	 */
	public static class Progress {
		protected long totalBytes;
		protected long bytesUploaded;
		protected double progress;// bytesUploaded / totalBytes * 100

		public Progress(long totalBytes, long bytesUploaded, double progress) {
			super();
			this.totalBytes = totalBytes;
			this.bytesUploaded = bytesUploaded;
			this.progress = progress;
		}

		public long getTotalBytes() {
			return totalBytes;
		}

		public long getBytesUploaded() {
			return bytesUploaded;
		}

		public double getProgress() {
			return progress;
		}
	}

	/**
	 * 
	 * @author HP
	 *
	 */
	public static class Uploaded {
		protected String uploadId;
		protected URL uploadUrl;

		public Uploaded(String uploadId, URL uploadUrl) {
			super();
			this.uploadId = uploadId;
			this.uploadUrl = uploadUrl;
		}

		public String getUploadId() {
			return uploadId;
		}

		public URL getUploadUrl() {
			return uploadUrl;
		}

	}
}
