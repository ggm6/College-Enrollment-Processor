package com.enrollment.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Schedule implements Comparable<Schedule> {
	
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
	
	public boolean containsCourseOverlaps() {
		ListIterator<Course> iter = courses.listIterator(0);
		while (iter.hasNext()) {
			Course course1 = iter.next();
			ListIterator<Course> iter2 = courses.listIterator(iter.nextIndex());
			while (iter2.hasNext()) {
				Course course2 = iter2.next();
				if (course2.overlaps(course1))
					return true;
			}
		}
		
		return false;
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
		if (courses.size() != schedule.getCourses().size())
			return false;
		
		for (int i = 0; i < courses.size(); ++i) {
			if ( !courses.get(i).equals(schedule.getCourses().get(i)) )
				return false;
		}
		
		return true;
	}

	@Override
	public int compareTo(Schedule schedule) {
		if (courses == null || schedule.getCourses() == null || schedule.getCourses().size() == courses.size())
			return 0;
		
		if (schedule.getCourses().size() > courses.size())
			return 1;
		
		return -1;
	}
}
