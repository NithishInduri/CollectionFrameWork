package com.insurence.exceptions;

public class PlanNotFoundException extends RuntimeException {
	String message;

	public PlanNotFoundException(String message) {
		this.message = message;
	}
}
