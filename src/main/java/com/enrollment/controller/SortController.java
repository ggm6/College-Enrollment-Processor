package com.enrollment.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enrollment.request.Course;
import com.enrollment.request.Schedule;

@RestController
public class SortController {

	@PostMapping(value = "/sort")
	public List<Schedule> processScheduleSortingRequest(@RequestBody List<Course> courses,
			@RequestParam(name="onlyReturnSchedulesWithMostPossibleClasses", defaultValue = "true") Boolean onlyReturnSchedulesWithMostPossibleClasses) {
		Set<Schedule> allPossibleSchedules = getAllSchedulePermutations(courses, onlyReturnSchedulesWithMostPossibleClasses);
		return orderSchedules(allPossibleSchedules);
	}
	
	/*
	 * Following code adapted from:
	 * https://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-
	 * combinations-of-size-n-from-an-array-java by answerer: Alex Salauyou
	 */
	public Set<Schedule> getAllSchedulePermutations(final List<Course> courses, Boolean onlyReturnSchedulesWithMostPossibleClasses) {

		Set<Schedule> allPossibleSchedules = new HashSet<Schedule>();

		// Accommodates variable number of classes in a schedule (if, say, 4 courses are chosen, schedule lengths may be anywhere from 1 to 4 classes long)
		int numberOfDistinctClasses = courses.stream().map(Course::getName).collect(Collectors.toCollection(HashSet::new)).size();

		for (int scheduleLength = numberOfDistinctClasses; scheduleLength >= 0; --scheduleLength) {
			
			// We'd usually like to stop generating schedules once we have every possible schedule containing the maximum number of distinct courses.
			// Meaning, if 4 courses are chosen, and a ton of combinations of courseName + prof + time slots are available, we generally only want the schedules with a "choose 4" combination of courses, or the highest number we can do. 
			if (Boolean.TRUE.equals(onlyReturnSchedulesWithMostPossibleClasses) && scheduleLength < numberOfDistinctClasses && !allPossibleSchedules.isEmpty())
				break;
			
			int[] courseIndices = new int[scheduleLength];
			
			// first index sequence: 0, 1, 2, ...
			if (scheduleLength == 1) {
				for (int i = 0; (courseIndices[i] = i) < scheduleLength - 1; i++)
					;
				addScheduleByIndexSequence(allPossibleSchedules, courses, courseIndices);
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

				addScheduleByIndexSequence(allPossibleSchedules, courses, courseIndices);
			}
		}

		return allPossibleSchedules;
	}

	private void addScheduleByIndexSequence(Set<Schedule> allPossibleSchedules, final List<Course> courses, int[] courseIndices) {	
		Schedule result = new Schedule();
		for (int pos = 0; pos < courseIndices.length; ++pos) {			
			int index = courseIndices[pos];
			Course course = courses.get(index);
			
			if ( scheduleHasDuplicateCourseNameOrTimeOverlap(result, course) )
				return;
			
			result.addCourseAt(pos, course);
		}
		allPossibleSchedules.add(result);
	}
	
	private boolean scheduleHasDuplicateCourseNameOrTimeOverlap(Schedule schedule, Course courseToAdd) {
		return schedule.getCourses().stream().anyMatch(c -> c.getName().equals(courseToAdd.getName()) || c.overlaps(courseToAdd));
	}

	private List<Schedule> orderSchedules(Collection<Schedule> allPossibleSchedules) {
		for (Schedule schedule : allPossibleSchedules)
			schedule.orderByStartTimeAscending();
		
		return allPossibleSchedules.stream()
				.sorted(Comparator.comparingInt((Schedule s) -> s.getCourses().size()).reversed())
			    .collect(Collectors.toList());
	}
}
