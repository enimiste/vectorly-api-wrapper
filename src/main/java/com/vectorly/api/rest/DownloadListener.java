package com.vectorly.api.rest;

import java.nio.file.Path;

public interface DownloadListener {

	/**
	 * 
	 * @param value
	 */
	void onProgress(Progress value);

	/**
	 * 
	 * @param download
	 */
	void onFinished(Downloaded download);

	/**
	 * 
	 * @author HP
	 *
	 */
	public static class Progress {
		protected long totalBytes;
		protected long bytesDownloaded;
		protected double progress;// bytesDownloaded / totalBytes * 100

		public Progress(long totalBytes, long bytesDownloaded, double progress) {
			super();
			this.totalBytes = totalBytes;
			this.bytesDownloaded = bytesDownloaded;
			this.progress = progress;
		}

		public long getTotalBytes() {
			return totalBytes;
		}

		public long getBytesDownloaded() {
			return bytesDownloaded;
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
	public static class Downloaded {
		protected Path path;

		public Downloaded(Path path) {
			super();
			this.path = path;
		}

		public Path getPath() {
			return path;
		}

	}

}
