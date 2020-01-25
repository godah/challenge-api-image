package com.luciano.challenge.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotFoundExceptionTest {

	private NotFoundException notFoundExceptionA;
	private NotFoundException notFoundExceptionB;

	@BeforeEach
	void setUp() throws Exception {
		notFoundExceptionA = new NotFoundException();
		notFoundExceptionB = new NotFoundException("NotFoundExceptionString");
	}

	@Test
	void testNotFoundExceptionString() {
		assertEquals("NotFoundExceptionString", notFoundExceptionB.getMessage());
	}

	@Test
	void testNotFoundException() {
		assertSame(NotFoundException.class, notFoundExceptionA.getClass());
	}

}