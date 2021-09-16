package com.enrollment.errorHandling;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

public class ExceptionResponse {

	@Getter
	@Setter
	private String errorMessage;

	@Getter
	@Setter
	private String errorCode;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
}
