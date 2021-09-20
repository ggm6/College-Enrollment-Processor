package com.enrollment.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

public class Course {

	@Getter
	@Setter
	private String courseName;

	@Getter
	@Setter
	private String professor;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
	private LocalTime startTime;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
	private LocalTime endTime;

}
