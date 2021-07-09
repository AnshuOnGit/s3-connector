package com.anshu.platform.s3connector.model;

public class Wish {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    // Don't remove this otherwise jackson mapper will fail
	public Wish() {
	}

}
