package com.insurence.exceptions;

public class EntityNotFoundException extends RuntimeException {
	String message;

	public EntityNotFoundException(String message) {

		this.message = message;
	}

}
