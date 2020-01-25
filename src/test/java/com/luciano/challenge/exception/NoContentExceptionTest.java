package com.luciano.challenge.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoContentExceptionTest {

	private NoContentException noContentExceptionA;
	private NoContentException noContentExceptionB;

	@BeforeEach
	void setUp() throws Exception {
		noContentExceptionA = new NoContentException();
		noContentExceptionB = new NoContentException("NoContentExceptionString");
	}

	@Test
	void testNoContentExceptionString() {
		assertEquals("NoContentExceptionString", noContentExceptionB.getMessage());
	}

	@Test
	void testNoContentException() {
		assertSame(NoContentException.class, noContentExceptionA.getClass());
	}

}
