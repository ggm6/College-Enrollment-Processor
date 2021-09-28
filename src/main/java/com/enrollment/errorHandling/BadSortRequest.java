package com.enrollment.errorHandling;

public class BadSortRequest extends RuntimeException {

	private static final long serialVersionUID = 1282969135855269633L;

	public BadSortRequest(String message) {
		super(message);
	}

}
