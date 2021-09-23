package com.enrollment.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

public class Course implements Comparable<Course> {

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

	@Override
	public int compareTo(Course course) {
		if (startTime == null || course.getStartTime() == null)
			return 0;

		return course.getStartTime().compareTo(course.getStartTime());
	}

	public boolean overlaps(Course course) {
		return startTime.isBefore(course.getEndTime()) && course.getStartTime().isBefore(endTime);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Course))
			return false;

		Course course = (Course) o;
		if (course.getCourseName().equals(courseName) && course.getStartTime().equals(startTime)
				&& course.getEndTime().equals(endTime) && course.getProfessor().equals(professor))
			return true;

		return false;
	}

}
