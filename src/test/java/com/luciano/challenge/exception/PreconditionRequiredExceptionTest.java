package com.luciano.challenge.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PreconditionRequiredExceptionTest {
	
	private PreconditionRequiredException preconditionRequiredException;
	
	@BeforeEach
	void setUp() throws Exception {
		preconditionRequiredException = new PreconditionRequiredException();
	}

	@Test
	void testPreconditionRequiredException() {
		assertSame(PreconditionRequiredException.class, preconditionRequiredException.getClass());
	}

}