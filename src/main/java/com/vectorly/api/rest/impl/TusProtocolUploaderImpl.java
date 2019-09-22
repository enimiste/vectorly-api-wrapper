package com.vectorly.api.rest.impl;

import java.io.File;
import java.net.URL;

import com.vectorly.api.rest.Upload;
import com.vectorly.api.rest.Uploader;
import com.vectorly.api.rest.VectorlyRest.VideoType;
import com.vectorly.api.rest.exception.FileTypeNotSupportedException;

import io.tus.java.client.TusClient;
import io.tus.java.client.TusURLMemoryStore;

/**
 * 
 * @author NOUNI El bachir
 *
 */
class TusProtocolUploaderImpl implements Uploader {
	protected String apiKey;
	protected URL apiFilesUrl;
	private TusClient tusClient;

	public TusProtocolUploaderImpl(URL apiFilesUrl, String apiKey, boolean enableResuming) {
		this.apiKey = apiKey;
		this.apiFilesUrl = apiFilesUrl;
		this.tusClient = new TusClient();
		this.tusClient.setUploadCreationURL(apiFilesUrl);
		if (enableResuming)
			this.tusClient.enableResuming(new TusURLMemoryStore());
	}

	@Override
	public Upload upload(File file) throws FileTypeNotSupportedException {
		if (!file.toPath().toString().endsWith(".mp4"))
			throw (new FileTypeNotSupportedException("Only MP4 files are supported")).setFilename(file.getName());

		return new TusProtocolUploadImpl(tusClient, apiKey, file, VideoType.VIDEO_MP4);
	}

}
