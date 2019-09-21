package com.vectorly.api.rest;

import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JWTSecuredUrlImpl {

	protected String signingKey;
	protected URL baseUrl;

	public JWTSecuredUrlImpl(String signingKey, URL baseUrl) {
		this.signingKey = signingKey;
		this.baseUrl = baseUrl;
	}

	/**
	 * 
	 * @param videoId
	 * @param expiresAt
	 * @return
	 * @throws VectorlyApiException
	 */
	public SecuredUrl generate(String videoId, LocalDateTime expiresAt) throws VectorlyApiException {
		try {
			String token = token(videoId, expiresAt.toEpochSecond(ZoneOffset.UTC));
			System.out.println(token);
			URL url = new URL(
					String.format("%s/%s/token/%s", asString(baseUrl), URLEncoder.encode(videoId, "UTF-8"), token));
			return new SecuredUrlImpl(url, token);
		} catch (Exception e) {
			throw new VectorlyApiException(e);
		}
	}

	/**
	 * 
	 * @param videoId
	 * @param expiresAt
	 * @return
	 */
	private String token(String videoId, long expiresAt) {
		byte[] key = signingKey.getBytes();
		SecretKey skey = Keys.hmacShaKeyFor(key);

		return Jwts.builder().setIssuer("Vectorly").setSubject("Private Video")
				// .setExpiration(new Date(expiresAt))
				.claim("video_id", videoId).claim("expiry", expiresAt).signWith(skey).compact();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private static String asString(URL url) {
		return url.toString().replaceAll("/+$", "");
	}

}
