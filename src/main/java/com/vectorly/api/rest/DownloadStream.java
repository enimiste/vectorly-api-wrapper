package com.vectorly.api.rest;

import java.io.OutputStream;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

/**
 * Download and pipe the bytes to an external OutputStream object
 * 
 * Here is where the download job will be done
 * 
 * @author NOUNI EL Bachir
 *
 */
public interface DownloadStream {

	void addDownloadListener(DownloadStreamListener downloadStreamListener);

	/**
	 * Write the downloaded file into the output stream This method closes the
	 * output stream after the success of the operation
	 * 
	 * @param out to writes to the input bytes
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	void execute(OutputStream out) throws VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @author NOUNI EL Bachir
	 *
	 */
	public static interface DownloadStreamListener {
		void onProgress(Progress value);

		void onFinished();

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
	}
}
