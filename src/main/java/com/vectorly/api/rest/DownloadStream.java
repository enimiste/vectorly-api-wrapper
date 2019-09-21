package com.vectorly.api.rest;

import java.io.OutputStream;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

public interface DownloadStream {

	void addDownloadListener(DownloadStreamListener downloadStreamListener);

	/**
	 * Write the downloaded file into the output stream This method closes the
	 * output stream after the success of the operation
	 * 
	 * @param out
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	void execute(OutputStream out) throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @author HP
	 *
	 */
	public static interface DownloadStreamListener {
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
}
