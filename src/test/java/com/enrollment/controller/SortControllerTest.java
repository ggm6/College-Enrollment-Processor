package com.enrollment.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import com.enrollment.request.Course;
import com.enrollment.request.Schedule;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class SortControllerTest {
	
	@Value("classpath:all-permutations-input.json")
	Resource inputAllPermutationsFile;

	@Value("classpath:all-permutations-output.json")
	Resource outputAllPermutationsFile;
	
	@Value("classpath:duplicate-courses-input.json")
	Resource inputDuplicateCoursesFile;

	@Value("classpath:duplicate-courses-output.json")
	Resource outputDuplicateCoursesFile;
	
	@Value("classpath:time-conflicts-input.json")
	Resource inputTimeConflictsFile;
	
	@Value("classpath:time-conflicts-output.json")
	Resource outputTimeConflictsFile;
	
	@Value("classpath:full-integration-input.json")
	Resource inputFullIntegrationFile;

	@Value("classpath:full-integration-output.json")
	Resource outputFullIntegrationFile;

	@Autowired
	private SortController controller;

	ObjectMapper mapper = new ObjectMapper();

	
	@Test
	public void doGetAllSchedulePermutationsTest() throws JsonParseException, JsonMappingException, IOException {
		ArrayList<Schedule> processedSchedules = null;
		ArrayList<Schedule> outputCoursesFromFile = null;

		ArrayList<Course> coursesToProcess = mapper.readValue(inputAllPermutationsFile.getFile(),
				new TypeReference<ArrayList<Course>>(){});
		
		processedSchedules = controller.getAllSchedulePermutations(coursesToProcess);
	
		outputCoursesFromFile = mapper.readValue(outputAllPermutationsFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});
		
		assertIterableEquals(outputCoursesFromFile, processedSchedules);
	}
	
	@Test
	public void doRemoveSchedulesWithDuplicateCourseNamesTest() throws Exception {
		ArrayList<Schedule> schedulesToProcess = null;
		ArrayList<Schedule> outputSchedulesFromFile = null;
		
		schedulesToProcess = mapper.readValue(inputDuplicateCoursesFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});
		
		controller.removeSchedulesWithDuplicateCourseNames(schedulesToProcess);
	
		outputSchedulesFromFile = mapper.readValue(outputDuplicateCoursesFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});
		
		assertIterableEquals(outputSchedulesFromFile, schedulesToProcess);
	}
	
	@Test
	public void doRemoveSchedulesWithTimeConflictsTest() throws Exception {
		ArrayList<Schedule> schedulesToProcess = null;
		ArrayList<Schedule> outputSchedulesFromFile = null;
		
		schedulesToProcess = mapper.readValue(inputTimeConflictsFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});
		
		controller.removeSchedulesWithTimeConflicts(schedulesToProcess);

		outputSchedulesFromFile = mapper.readValue(outputTimeConflictsFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});

		assertIterableEquals(outputSchedulesFromFile, schedulesToProcess);
	}
	
	@Test
	public void doFullIntegrationTest() throws IOException {
		ArrayList<Schedule> processedSchedules = null;
		ArrayList<Schedule> outputCoursesFromFile = null;

		ArrayList<Course> coursesToProcess = mapper.readValue(inputFullIntegrationFile.getFile(),
				new TypeReference<ArrayList<Course>>(){});
		
		processedSchedules = controller.processScheduleSortingRequest(coursesToProcess);
	
		outputCoursesFromFile = mapper.readValue(outputFullIntegrationFile.getFile(),
				new TypeReference<ArrayList<Schedule>>(){});
		
		assertIterableEquals(outputCoursesFromFile, processedSchedules);
	}

}
