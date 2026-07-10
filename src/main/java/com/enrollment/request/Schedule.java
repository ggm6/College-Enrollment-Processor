package com.enrollment.request;

import java.util.ArrayList;
import java.util.Collections;

public class Schedule {
	
	private ArrayList<Course> courses;

	public Schedule() {
		courses = new ArrayList<Course>();
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course course) {
		courses.add(course);
	}

	public void addCourseAt(int index, Course course) {
		courses.add(index, course);
	}

	public void removeCourse(Course course) {
		courses.remove(course);
	}

	public void removeCourseAt(int index) {
		courses.remove(index);
	}

	public void orderByStartTimeAscending() {
		Collections.sort(courses);
	}
		
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Schedule))
			return false;
		
		Schedule schedule = (Schedule) o;
		if (courses.size() != schedule.getCourses().size())
			return false;
		
		for (int i = 0; i < courses.size(); ++i) {
			if ( !courses.get(i).equals(schedule.getCourses().get(i)) )
				return false;
		}
		
		return true;
	}
}
