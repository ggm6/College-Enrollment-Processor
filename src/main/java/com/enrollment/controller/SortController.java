package com.enrollment.controller;

import java.util.ArrayList;
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
		ArrayList<Schedule> allPossibleSchedules = getAllPossibleSchedules(courses);
		removeSchedulesWithDuplicateCourseNames(allPossibleSchedules);
		resolveTimeConflictsInSchedules(allPossibleSchedules);
		removeDuplicateSchedules(allPossibleSchedules);
		orderSchedules(allPossibleSchedules);
		return allPossibleSchedules;
	}

	/*
	 * Following code adapted from:
	 * https://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-
	 * combinations-of-size-n-from-an-array-java by answerer: Alex Salauyou
	 */
	public ArrayList<Schedule> getAllPossibleSchedules(final List<Course> courses) {

		ArrayList<Schedule> allPossibleSchedules = new ArrayList<Schedule>();

		// Accommodates variable number of classes in a schedule
		int[] sequenceLengths = IntStream.range(1, courses.size() + 1).toArray();

		for (int sequenceLength : sequenceLengths) {
			int[] courseIndices = new int[sequenceLength];

			// first index sequence: 0, 1, 2, ...
			for (int i = 0; (courseIndices[i] = i) < sequenceLength - 1; i++)
				;
			Schedule schedule = getScheduleByIndexSequence(courses, courseIndices);
			allPossibleSchedules.add(schedule);

			for (;;) {
				int i;
				// find position of item that can be incremented
				for (i = sequenceLength - 1; i >= 0 && courseIndices[i] == courses.size() - sequenceLength + i; i--)
					;
				if (i < 0)
					break;

				courseIndices[i]++; // increment this item
				for (++i; i < sequenceLength; i++) // fill up remaining items
					courseIndices[i] = courseIndices[i - 1] + 1;

				schedule = getScheduleByIndexSequence(courses, courseIndices);
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

	public void resolveTimeConflictsInSchedules(ArrayList<Schedule> allPossibleSchedules) {
		for (Schedule schedule : allPossibleSchedules) {
			ListIterator<Course> iter = schedule.getCourses().listIterator(0);
			while (iter.hasNext()) {
				Course course1 = iter.next();
				ListIterator<Course> iter2 = schedule.getCourses().listIterator(iter.nextIndex());
				while (iter2.hasNext()) {
					Course course2 = iter2.next();
					if (course2.overlaps(course1)) {
						iter2.remove();
						iter = schedule.getCourses().listIterator(iter.nextIndex() - 1);
						break;
					}
				}
			}
		}
	}

	public void removeDuplicateSchedules(ArrayList<Schedule> allPossibleSchedules) {
		ListIterator<Schedule> iter = allPossibleSchedules.listIterator(0);
		while (iter.hasNext()) {
			Schedule schedule1 = iter.next();
			ListIterator<Schedule> iter2 = allPossibleSchedules.listIterator(iter.nextIndex());
			while (iter2.hasNext()) {
				Schedule schedule2 = iter2.next();
				if (schedule2.isDuplicateOf(schedule1)) {
					iter2.remove();
					iter = allPossibleSchedules.listIterator(iter.nextIndex() - 1);
					break;
				}
			}
		}
	}

	private void orderSchedules(ArrayList<Schedule> allPossibleSchedules) {
		for (Schedule schedule : allPossibleSchedules)
			schedule.orderByStartTimeAscending();
	}
}
