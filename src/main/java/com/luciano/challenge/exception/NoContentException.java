package com.luciano.challenge.exception;

public class NoContentException extends Exception{
	private static final long serialVersionUID = -5110881218160359247L;

	public NoContentException(String errorMessage) {
		super(errorMessage);
	}
	
	public NoContentException() {}
}
