package com.vectorly.api.rest;

import java.io.File;
import java.nio.file.Path;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

public interface Download {

	/**
	 * 
	 */
	String getVideoId();

	/**
	 * The file name is the [videoId].mp4
	 * 
	 * @param file
	 */
	void setDestinationFolder(File file);

	/**
	 * 
	 * @throws VectorlyApiException
	 * @throws VectorlyApiAuthorizationException
	 */
	void execute() throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @param listener
	 */
	void addDownloadListener(DownloadListener listener);

	/**
	 * 
	 * @author HP
	 *
	 */
	public static interface DownloadListener {

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

}
