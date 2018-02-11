package com.mkyong.rest;

public class CustomReasonPhraseException extends Exception {

	private static final long serialVersionUID = -271582074543512905L;

	private final String businessCode;

	public CustomReasonPhraseException(String businessCode, String message) {
		super(message);
		this.businessCode = businessCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

}