package com.vectorly.api.rest;

import java.io.FileNotFoundException;
import java.net.URL;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

/**
 * 
 * @author NOUNI EL bachir
 *
 */
public interface Upload {

	/**
	 * Sets a custom name to be used to name the uploaded file on Vectorly Api
	 * The fileName should include the same extension as the source file
	 * 
	 * @param fileName
	 * @throws IllegalArgumentException if the file name is invalid
	 */
	void setCustomName(String fileName) throws IllegalArgumentException;

	/**
	 * 
	 * @param listener
	 */
	void addUploadListener(UploadListener listener);

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws VectorlyApiAuthorizationException
	 * @throws VectorlyApiException
	 */
	void execute() throws FileNotFoundException, VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @author HP
	 *
	 */
	public static interface UploadListener {

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
}
