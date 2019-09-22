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
	 * Sets a custom name to be used to name the uploaded file on Vectorly Api The
	 * fileName should include the same extension as the source file
	 * 
	 * @param fileName new filename
	 * @throws IllegalArgumentException if the file name is invalid
	 */
	void setCustomName(String fileName) throws IllegalArgumentException;

	void addUploadListener(UploadListener listener);

	/**
	 * 
	 * @throws FileNotFoundException             file to upload not found
	 * @throws VectorlyApiAuthorizationException Forbidden or UnAuthorized case
	 * @throws VectorlyApiException              Wrap other exceptions
	 */
	void execute() throws FileNotFoundException, VectorlyApiAuthorizationException, VectorlyApiException;

	/**
	 * 
	 * @author NOUNI EL bachir
	 *
	 */
	public static interface UploadListener {
		void onProgress(Progress value);

		void onFinished(Uploaded uploaded);

		/**
		 * 
		 * @author NOUNI EL bachir
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
		 * @author NOUNI EL bachir
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
