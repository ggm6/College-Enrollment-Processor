package com.enrollment.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
	"name",
	"professor",
	"startTime",
	"endTime"
})
public class Course implements Comparable<Course> {

	@Getter
	@Setter
	@JsonProperty("courseName")
	private String name;

	@Getter
	@Setter
	private String professor;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime startTime;

	@Getter
	@Setter
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
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
		if (course.getName().equals(name) && course.getStartTime().compareTo(startTime) == 0		
				&& course.getEndTime().compareTo(endTime) == 0 && course.getProfessor().equals(professor))
			return true;

		return false;
	}

}
