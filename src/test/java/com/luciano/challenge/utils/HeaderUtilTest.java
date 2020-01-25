package com.luciano.challenge.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest
class HeaderUtilTest {
	HttpHeaders httpHeaders;

	@BeforeEach
	void setUp() throws Exception {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	void testGetHeader() {
		HeaderUtil headerUtil = new HeaderUtil();
		assertNotNull(headerUtil);
		assertNotNull(HeaderUtil.getHeader());
		assertEquals(MediaType.APPLICATION_JSON, HeaderUtil.getHeader().getContentType());
	}

}
