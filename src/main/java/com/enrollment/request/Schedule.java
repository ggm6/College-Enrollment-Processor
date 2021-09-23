package com.enrollment.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

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

	public int findThisCourse(String comparisonCourse) {
		for (int coursePos = 0; coursePos < courses.size(); ++coursePos) {
			String course = courses.get(coursePos).getCourseName();
			if (course.equals(comparisonCourse))
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
}
