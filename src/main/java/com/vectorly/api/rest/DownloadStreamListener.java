package com.vectorly.api.rest;

public interface DownloadStreamListener {
	/**
	 * 
	 * @param value
	 */
	void onProgress(Progress value);

	/**
	 * 
	 */
	void onFinished();

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
}
