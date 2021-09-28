package com.enrollment.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	public int findCoursePositionByName(String courseName) {
		for (int coursePos = 0; coursePos < courses.size(); ++coursePos) {
			String course = courses.get(coursePos).getName();
			if (course.equals(courseName))
				return coursePos;
		}
		return -1;
	}

	public void orderByStartTimeAscending() {
		Collections.sort(courses);
	}

	public void removeHereIfAnyTimeConflicts(ListIterator<Course> iter) {
		if (!iter.hasPrevious())
			return;

		Course course = iter.previous();
		for (int comparisonCoursePos = 0; comparisonCoursePos < courses.size(); ++comparisonCoursePos) {
			Course comparisonCourse = courses.get(comparisonCoursePos);
			if (course.overlaps(comparisonCourse)) {
				iter.remove();
				break;
			}
		}
	}

	public boolean isDuplicateOf(Schedule schedule) {
		for (Course thisCourse : courses) {
			for (Course course : schedule.getCourses()) {
				if (!course.equals(thisCourse))
					return false;

			}
		}

		return true;
	}
	
	@JsonIgnore
	public HashSet<String> getDistinctCourseNames() {
		HashSet<String> courseNames = new HashSet<String>();
		for (Course course : courses)
			courseNames.add(course.getName());

		return courseNames;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Schedule))
			return false;

		Schedule schedule = (Schedule) o;
		for (int i = 0; i < courses.size(); ++i) {
			if ( !courses.get(i).equals(schedule.getCourses().get(i)) )
				return false;
		}
		
		return true;
	}
}
