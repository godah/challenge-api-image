package com.luciano.challenge.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderUtil {
	
	public static HttpHeaders getHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    return httpHeaders;
	}
}
