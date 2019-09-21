package com.vectorly.api.rest.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.vectorly.api.rest.Upload;
import com.vectorly.api.rest.Upload.UploadListener.Progress;
import com.vectorly.api.rest.Upload.UploadListener.Uploaded;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;

import io.tus.java.client.ProtocolException;
import io.tus.java.client.TusClient;
import io.tus.java.client.TusExecutor;
import io.tus.java.client.TusUpload;
import io.tus.java.client.TusUploader;

class TusUploadImpl implements Upload {

	final static String API_KEY = "api_key";
	final static String FILENAME = "filename";
	final static String FILETYPE = "filetype";

	protected String apiKey;
	protected TusClient tusClient;
	protected String customFileName = null;
	protected File file;
	protected Set<UploadListener> listeners;

	public TusUploadImpl(TusClient tusClient, String apiKey, File file) {
		this.apiKey = apiKey;
		this.tusClient = tusClient;
		this.file = file;
		this.listeners = new HashSet<>();
	}

	@Override
	public void setCustomName(String fileName) throws IllegalArgumentException {
		try {
			// Validate the file name
			Paths.get(fileName);
		} catch (InvalidPathException e) {
			throw new IllegalArgumentException(String.format("File name %s is invalid", fileName), e);
		}
		this.customFileName = fileName;
	}

	@Override
	public void addUploadListener(UploadListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void execute() throws FileNotFoundException, VectorlyApiAuthorizationException, VectorlyApiException {
		final TusUpload upload = new TusUpload(file);

		if (customFileName != null) {
			upload.getMetadata().put(FILENAME, customFileName);
		}
		upload.getMetadata().put(API_KEY, apiKey);
		upload.getMetadata().put(FILETYPE, "video/mp4");

		TusExecutor executor = new TusExecutor() {
			@Override
			protected void makeAttempt() throws ProtocolException, IOException {
				TusUploader uploader = tusClient.resumeOrCreateUpload(upload);
				uploader.setChunkSize(1024);

				// Upload the file as long as data is available. Once the
				// file has been fully uploaded the method will return -1
				do {
					// Calculate the progress using the total size of the uploading file and
					// the current offset.
					long totalBytes = upload.getSize();
					long bytesUploaded = uploader.getOffset();
					double progress = (double) bytesUploaded / totalBytes * 100;

					try {
						Progress p = new Progress(totalBytes, bytesUploaded, progress);
						listeners.forEach(l -> l.onProgress(p));
					} catch (Exception e) {
						// ignore any exception here
					}
				} while (uploader.uploadChunk() > -1);

				// Allow the HTTP connection to be closed and cleaned up
				uploader.finish();
				try {
					URL url = uploader.getUploadURL();
					String uploadId = url.toString().split("\\+")[0];
					Uploaded u = new Uploaded(uploadId, url);
					listeners.forEach(l -> l.onFinished(u));
				} catch (Exception e) {
					// ignore any exception here
				}
			}
		};
		try {
			executor.makeAttempts();
		} catch (ProtocolException | IOException e) {
			if (e instanceof ProtocolException) {
				try {
					int respCode = ((ProtocolException) e).getCausingConnection().getResponseCode();
					if (respCode == 403 || respCode == 401) {
						throw new VectorlyApiAuthorizationException(e);
					}
				} catch (IOException e1) {
					// nothing
				}
			}
			throw new VectorlyApiException(e);
		}
	}

}
