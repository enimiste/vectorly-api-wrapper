package com.vectorly.api.rest;

import java.io.File;
import java.net.URL;

import io.tus.java.client.TusClient;
import io.tus.java.client.TusURLMemoryStore;

class TusUploaderImpl implements Uploader {
	protected String apiKey;
	protected URL apiFilesUrl;
	private TusClient tusClient;

	public TusUploaderImpl(URL apiFilesUrl, String apiKey) {
		this.apiKey = apiKey;
		this.apiFilesUrl = apiFilesUrl;
		this.tusClient = new TusClient();
		this.tusClient.setUploadCreationURL(apiFilesUrl);
		this.tusClient.enableResuming(new TusURLMemoryStore());
	}

	@Override
	public Upload upload(File file) {
		return new TusUploadImpl(tusClient, apiKey, file);
	}

}
