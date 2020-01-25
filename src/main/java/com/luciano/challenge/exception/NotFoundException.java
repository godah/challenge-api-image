package com.luciano.challenge.exception;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = -8182517031131558162L;

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}

	public NotFoundException() {}
}
