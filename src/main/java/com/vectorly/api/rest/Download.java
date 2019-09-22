package com.vectorly.api.rest;

import java.io.File;
import java.nio.file.Path;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

/**
 * Download and save the file to a local file relative to the
 * "destinationFolder"
 * 
 * Here is where the download job will be done
 * 
 * @author NOUNI EL Bachir
 *
 */
public interface Download {
	String getVideoId();

	/**
	 * The file name is the [videoId].mp4
	 * 
	 * @param file the output folder
	 */
	void setDestinationFolder(File file);

	/**
	 * 
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exception
	 */
	void execute() throws VectorlyApiAuthorizationException, VectorlyApiException;

	void addDownloadListener(DownloadListener listener);

	/**
	 * 
	 * @author NOUNI EL Bachir
	 *
	 */
	public static interface DownloadListener {

		void onProgress(Progress value);

		void onFinished(Downloaded download);

		/**
		 * 
		 * @author NOUNI EL Bachir
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
		 * @author NOUNI El Bachir
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
