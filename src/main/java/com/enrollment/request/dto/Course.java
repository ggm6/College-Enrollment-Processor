package com.enrollment.request.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;

import com.enrollment.request.deserializer.DayOfWeekDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	"endTime",
	"days"
})
public class Course implements Comparable<Course> {

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String professor;

	@Getter
	@Setter
	private LocalTime startTime;

	@Getter
	@Setter
	private LocalTime endTime;
	
	@Getter
	@Setter
	private EnumSet<DayOfWeek> days;
	
	@JsonIgnore
	@Getter
	private int startTimeInt;
	
	@JsonIgnore
	@Getter
	private int endTimeInt;

	@JsonCreator
	public Course(
			@JsonProperty("courseName")
			String courseName,
			@JsonProperty("professor")
			String professor,
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
			@JsonSerialize(using = LocalTimeSerializer.class)
			@JsonDeserialize(using = LocalTimeDeserializer.class)
			@JsonProperty("startTime")
			LocalTime startTime,
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "h:mm a")
			@JsonSerialize(using = LocalTimeSerializer.class)
			@JsonDeserialize(using = LocalTimeDeserializer.class)
			@JsonProperty("endTime")
			LocalTime endTime,
			@JsonDeserialize(converter = DayOfWeekDeserializer.class)
			@JsonProperty("days")
			EnumSet<DayOfWeek> days) {
		super();
		this.name = courseName;
		this.professor = professor;
		this.startTime = startTime;
		this.endTime = endTime;
		this.days = days;
		startTimeInt = startTime.toSecondOfDay();
		endTimeInt = endTime.toSecondOfDay();
	}

	@Override
	public int compareTo(Course course) {
		return Integer.compare(startTimeInt, course.getStartTimeInt());
	}

	public boolean overlaps(Course course) {
		if ( Collections.disjoint(days, course.getDays()) )
			return false;
		
		return startTimeInt < course.getEndTimeInt() && course.getStartTimeInt() < endTimeInt;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, professor, startTimeInt, endTimeInt, days);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Course))
			return false;

		Course course = (Course) o;
		if (course.getName().equals(name) && Integer.compare(startTimeInt, course.getStartTimeInt()) == 0		
				&& Integer.compare(endTimeInt, course.getEndTimeInt()) == 0 && course.getProfessor().equals(professor)
				&& days.equals(course.getDays()))
			return true;

		return false;
	}

}
