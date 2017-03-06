package com.uzmanposta.exception;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException() {

	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	public UnauthorizedException(String message) {
		super(message, null);
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

}
