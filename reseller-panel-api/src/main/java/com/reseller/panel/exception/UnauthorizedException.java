package com.reseller.panel.exception;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String message) {
		super(message);
	}
}
