package com.enrollment.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enrollment.request.Course;
import com.enrollment.request.Schedule;

@RestController
public class SortController {

	@PostMapping(value = "/sort")
	public ArrayList<Schedule> processScheduleSortingRequest(@RequestBody List<Course> courses) {
		ArrayList<Schedule> allPossibleSchedules = getAllSchedulePermutations(courses);
		removeSchedulesWithDuplicateCourseNames(allPossibleSchedules);
		removeSchedulesWithTimeConflicts(allPossibleSchedules);
		orderSchedules(allPossibleSchedules);
		return allPossibleSchedules;
	}
	
	/*
	 * Following code adapted from:
	 * https://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-
	 * combinations-of-size-n-from-an-array-java by answerer: Alex Salauyou
	 */
	public ArrayList<Schedule> getAllSchedulePermutations(final List<Course> courses) {

		ArrayList<Schedule> allPossibleSchedules = new ArrayList<Schedule>();

		// Accommodates variable number of classes in a schedule (if, say, 4 courses are chosen, schedule lengths may be anywhere from 1 to 4 classes long)
		int[] scheduleLengths = IntStream.range(1, courses.size() + 1).toArray();

		for (int scheduleLength : scheduleLengths) {
			
			int[] courseIndices = new int[scheduleLength];
			
			// first index sequence: 0, 1, 2, ...
			if (scheduleLength == 1) {
				for (int i = 0; (courseIndices[i] = i) < scheduleLength - 1; i++)
					;
				Schedule schedule = getScheduleByIndexSequence(courses, courseIndices);
				allPossibleSchedules.add(schedule);
			}

			for (;;) {
				int i;
				// find position of item that can be incremented
				for (i = scheduleLength - 1; i >= 0 && courseIndices[i] == courses.size() - scheduleLength + i; i--)
					;
				if (i < 0)
					break;

				courseIndices[i]++; // increment this item
				for (++i; i < scheduleLength; i++) // fill up remaining items
					courseIndices[i] = courseIndices[i - 1] + 1;

				Schedule schedule = getScheduleByIndexSequence(courses, courseIndices);
				if ( !allPossibleSchedules.contains(schedule) )
					allPossibleSchedules.add(schedule);
			}
		}

		return allPossibleSchedules;
	}

	private Schedule getScheduleByIndexSequence(final List<Course> courses, int[] courseIndices) {
		Schedule result = new Schedule();
		for (int pos = 0; pos < courseIndices.length; ++pos) {
			int index = courseIndices[pos];
			Course course = courses.get(index);
			result.addCourseAt(pos, course);
		}
		return result;
	}

	public void removeSchedulesWithDuplicateCourseNames(ArrayList<Schedule> allPossibleSchedules) {
		Iterator<Schedule> iter = allPossibleSchedules.iterator();
		while (iter.hasNext()) {
			Schedule schedule = iter.next();
			HashSet<String> distinctCourseNames = schedule.getDistinctCourseNames();
			if ( schedule.getCourses().size() != distinctCourseNames.size() )
				iter.remove();
		}
	}

	public void removeSchedulesWithTimeConflicts(ArrayList<Schedule> allPossibleSchedules) {
		ListIterator<Schedule> iter = allPossibleSchedules.listIterator();	
			while (iter.hasNext()) {
				Schedule schedule = iter.next();
				if ( schedule.containsCourseOverlaps() )
					iter.remove();
		}
	}

	private void orderSchedules(ArrayList<Schedule> allPossibleSchedules) {
		for (Schedule schedule : allPossibleSchedules)
			schedule.orderByStartTimeAscending();
		
		Collections.sort(allPossibleSchedules);
	}
}
