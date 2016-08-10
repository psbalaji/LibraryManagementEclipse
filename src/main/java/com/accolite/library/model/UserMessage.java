package com.accolite.library.model;

import org.springframework.stereotype.Component;

@Component
public class UserMessage {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
