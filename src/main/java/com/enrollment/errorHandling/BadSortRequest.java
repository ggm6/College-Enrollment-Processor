package com.enrollment.errorHandling;

public class BadSortRequest extends RuntimeException {

	public BadSortRequest(String message) {
		super(message);
	}

}
