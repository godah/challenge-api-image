package com.luciano.challenge.exception;

public class PreconditionRequiredException extends Exception {
	private static final long serialVersionUID = -8182517031131558162L;

	public PreconditionRequiredException(String errorMessage) {
		super(errorMessage);
	}
	
	public PreconditionRequiredException() {}
}
