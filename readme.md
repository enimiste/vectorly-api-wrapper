# Vectorly REST API Java Client
Only MP4 videos are supported for upload

## Version :
RC

## Features :
- Create a `VectorlyRest` Facade object using the API Key from your Dashboard
- Upload a local file (You can set a custom name for it)
- Fetch All videos (It returns a Java Stream<Video>)
- Search Videos by keyword (It returns a Java Stream<Video>)
- Download a video by `video id` and store it to a `File` destination folder
- Download a video by `video id` and store it to an `OutputStream` (Maybe used with HTTP response output stream)
- Fetch a stream of videos analytics Summary (It returns a Java Stream<Summary>)
- Fetch a stream of events related to videos analytics (It returns a Java Stream<AnalyticsEvent>)
- Create a secured URL to use with private videos (It uses JWT token internally)

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vectorly.api.rest.*;
import com.vectorly.api.rest.Download.DownloadListener;
import com.vectorly.api.rest.DownloadStream.DownloadStreamListener;
import com.vectorly.api.rest.Upload.UploadListener;
import com.vectorly.api.rest.dto.AnalyticsEvent;
import com.vectorly.api.rest.dto.SecuredUrl;
import com.vectorly.api.rest.dto.Summary;
import com.vectorly.api.rest.dto.Video;
import com.vectorly.api.rest.dto.Video.VideoStatus;
import com.vectorly.api.rest.exception.VectorlyApiAuthorizationException;
import com.vectorly.api.rest.exception.VectorlyApiException;
import com.vectorly.api.rest.impl.VectorlyRestBuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
			VectorlyRest rest = VectorlyRestBuilder
					.build("1ce9d977-7baa-4b98-be65-9135fff3164e");

			// Uploading
			Uploader uper = rest.uploader();
			Upload up = uper.upload(new File(args[0]));
			up.setCustomName("my-new-name.mp4");
			up.addUploadListener(new UploadListener() {

				@Override
				public void onProgress(Progress value) {
					//System.out.printf("Upload at %06.2f%%.\n", value.getProgress());
				}

				@Override
				public void onFinished(Uploaded uploaded) {
					System.out.println("Upload finished.");
					System.out.println(String.format("Upload available at: %s", uploaded.getUploadUrl().toString()));
				}
			});
			up.execute();

			// Listing
			Stream<Video> videos = rest.fetchAll();
			videos.limit(10).forEach(System.out::println);
			Video video = rest.fetchAll().findFirst().get();
			System.out.println(video);
			if (video.getStatus() == VideoStatus.READY) {
				Download dw1 = rest.download(video.getId());// Download
				dw1.setDestinationFolder(new File(args[1]));
				dw1.addDownloadListener(new DownloadListener() {
					@Override
					public void onProgress(Progress value) {
						//System.out.printf("Download at %06.2f%%.\n", value.getProgress());
					}

					@Override
					public void onFinished(Downloaded download) {
						System.out.println("Download finished.");
						System.out.println(String.format("Download available at: %s", download.getPath().toString()));
					}
				});
				dw1.execute();
			}
			
			// Download as stream instead of saving directly to a file
			DownloadStream dws1 = rest.downloadAsStream(video.getId());
			OutputStream out = new FileOutputStream(new File(args[1] + "/download.mp4"));
			dws1.addDownloadListener(new DownloadStreamListener() {
				@Override
				public void onProgress(Progress value) {
					//System.out.printf("Download at %06.2f%%.\n", value.getProgress());
				}

				@Override
				public void onFinished() {
					System.out.println("Download finished.");
				}
			});
			
			dws1.execute(out);

			// Search
			Stream<Video> videos2 = rest.search("cat");
			videos2.forEach(System.out::println);
			// Analytics
			Stream<Summary> summ = rest.analyticsSummary();
			summ.limit(10).forEach(System.out::println);

			Stream<AnalyticsEvent> events = rest.analyticsEvents(video.getId());
			events.limit(10).forEach(System.out::println);

			// SecuredUrl secUrl = rest.secured("video-id", LocalDateTime.now().plus(1,
			// ChronoUnit.HOURS));
			SecuredUrl secUrl = rest.secured(video.getId());//10 minutes
			System.out.println(secUrl);
		} catch(VectorlyApiAuthorizationException vaae) {
			System.err.println("Authorization error to Vectorly API");
			vaae.printStackTrace();
		} catch(VectorlyApiException vae) {
			System.err.println("Vectorly Api lib error");
			vae.printStackTrace();
		} catch(Exception e) {
			System.err.println("General error");
			e.printStackTrace();
		}
	}

}


```
## Entities :
#### Video :
```java
String id;
String name;
Long size;
Long originalSize;
VideoStatus status (READY, PROCESSING, ERROR, UNKNOWN);
String rawStatus;//Raw status without mapping with the VideoStatus
Boolean isPrivate;
String clientId;
```
#### Summary and Detail :
```java
LocalDateTime start;
LocalDateTime end;
Integer playsCount;
Set<Detail> details;
```
Detail class: 

```java 
String videoId;
String videoTitle;
Integer playsCount;
```

#### Analytics Event
```java
boolean isLiveStream;
int sound;
long totalLength;
int position;
String quality;
String sessionId;
String videoPlayer;
isAdEnabled;
String contentAssetId;
isFullScreen;
LocalDateTime timestamp;
EventType type (VIDEO_SEEK, VIDEO_PAUSE, VIDEO_END, VIDEO_BUFFER_END, VIDEO_BUFFER_START, VIDEO_LOAD, UNKNOWN);
String rawType;//event type without mapping with the Enum EventType
```
## Credits :
Vectorly Team
