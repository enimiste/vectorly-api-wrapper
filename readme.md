# Vectorly REST API Java Client
This implementation exposes only interfaces and  not the implementing classes;

## Version :
Beta
## TODO :
- Get `Video` attributes from Vectorly team
- Get `VideoStatus` list from Vectorly team
- Get `Summary` attributes from Vectorly team
- Get `AnalyticEvent` attributes from Vectorly team
- Get `Video` attributes from Vectorly team

## Features :
- Create a `VectorlyRest` Facade object
- Upload a local file (You can set a custom name for it)
- Fetch All videos (It returns a Java Stream)
- Search Videos by keyword (It returns a Java Stream)
- Download a video by `video id` and store it to a `File` destination
- Download a video by `video id` and store it to an `OutputStream` (Maybe used with Http response output stream)
- Videos analytics Summary
- Fetch a stream of events related to videos analytics (It returns a Java Stream)
- Create a secured URL to use with private videos (It uses JWT token internaly)

## Dependencies :
Java 8 or Later  

```xml
<dependency>
	<groupId>io.tus.java.client</groupId>
	<artifactId>tus-java-client</artifactId>
	<version>0.4.0</version>
</dependency>
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20180813</version>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-api</artifactId>
	<version>0.10.7</version>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-impl</artifactId>
	<version>0.10.7</version>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-jackson</artifactId>
	<version>0.10.7</version>
	<scope>runtime</scope>
</dependency>
```

## Example :
```java
package com.vectorly.api.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws Exception {
		VectorlyRest rest = VectorlyRest
				.build("yaXZhdGUgVmlkZW8iLCJ2aWRlb19pZCI6InZpZGVvLWlkIiwiZXhwaXJ5IjoxNTY5MDMwNTQ4fQ");

		// Uploading

		Uploader uper = rest.uploader();
		Upload up = uper.upload(new File(Main.class.getResource("test.mp4").getPath()));
		up.setCustomName("1280x720.mp4");
		up.addUploadListener(new UploadListener() {

			@Override
			public void onProgress(Progress value) {
				System.out.printf("Upload at %06.2f%%.\n", value.getProgress());
			}

			@Override
			public void onFinished(Uploaded uploaded) {
				System.out.println("Upload finished.");
				System.out.format("Upload available at: %s", uploaded.getUploadUrl().toString());
			}
		});
		up.execute();

		// Listing
		Stream<Video> videos = rest.fetchAll();
		videos.limit(10).forEach(System.out::println);
		Video video = videos.findFirst().get();
		if (video.getStatus() == VideoStatus.READY) {
			Download dw1 = rest.download(video.getId());// Download
			dw1.setDestinationFolder(new File(Main.class.getResource(".").getPath()));
			dw1.addDownloadListener(new DownloadListener() {
				@Override
				public void onProgress(Progress value) {
					System.out.printf("Download at %06.2f%%.\n", value.getProgress());
				}

				@Override
				public void onFinished(Downloaded download) {
					System.out.println("Download finished.");
					System.out.format("Download available at: %s", download.getPath().toString());
				}
			});
			dw1.execute();
		}
		
		// Download as stream instead of saving directly to a file
		DownloadStream dws1 = rest.downloadAsStream(video.getId());
		dws1.addDownloadListener(new DownloadStreamListener() {
			@Override
			public void onProgress(Progress value) {
				System.out.printf("Download at %06.2f%%.\n", value.getProgress());
			}

			@Override
			public void onFinished() {
				System.out.println("Download finished.");
			}
		});
		OutputStream out = new FileOutputStream(new File(Main.class.getResource("./output.mp4").getPath()));
		dws1.execute(out);

		// Search
		videos = rest.search("cat");
		video = videos.findFirst().get();

		// Analytics
		Summary summ = rest.analyticsSummary();
		System.out.println(summ);

		Stream<AnalyticsEvent> events = rest.analyticsEvents("video-id");
		events.limit(10).forEach(System.out::println);

		// SecuredUrl secUrl = rest.secured("video-id", LocalDateTime.now().plus(1,
		// ChronoUnit.HOURS));
		SecuredUrl secUrl = rest.secured("video-id-2019");
		System.out.println(secUrl);
	}

}

```

## Credits :
Vectorly Web Site
