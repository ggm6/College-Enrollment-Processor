package com.enrollment.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import com.enrollment.request.Course;
import com.enrollment.request.Schedule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class SortControllerTest {

	@Value("classpath:full-integration-input.json")
	Resource inputFullIntegrationFile;

	@Value("classpath:full-integration-output.json")
	Resource outputFullIntegrationFile;
	
	@Value("classpath:duplicate-courses-input.json")
	Resource inputDuplicateCoursesFile;

	@Value("classpath:duplicate-courses-output.json")
	Resource outputDuplicateCoursesFile;

	@Autowired
	private SortController controller;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void doFullIntegrationTest() throws IOException {
		ArrayList<Schedule> processedSchedules = null;
		ArrayList<Schedule> outputCoursesFromFile = null;

		try (Reader reader = new InputStreamReader(inputFullIntegrationFile.getInputStream())) {
			ArrayList<Course> coursesToProcess = mapper.readValue(inputFullIntegrationFile.getFile(),
					new TypeReference<ArrayList<Course>>(){});
			processedSchedules = controller.processScheduleSortingRequest(coursesToProcess);
		}
		try (Reader reader = new InputStreamReader(outputFullIntegrationFile.getInputStream())) {
			outputCoursesFromFile = mapper.readValue(outputFullIntegrationFile.getFile(),
					new TypeReference<ArrayList<Schedule>>(){});
		}
		assertIterableEquals(outputCoursesFromFile, processedSchedules);
	}
	
	@Test
	public void doRemoveSchedulesWithDuplicateCourseNamesTest() throws Exception {
		ArrayList<Schedule> schedulesToProcess = null;
		ArrayList<Schedule> outputSchedulesFromFile = null;
		
		try (Reader reader = new InputStreamReader(inputDuplicateCoursesFile.getInputStream())) {
			schedulesToProcess = mapper.readValue(inputDuplicateCoursesFile.getFile(),
					new TypeReference<ArrayList<Schedule>>(){});
			controller.removeSchedulesWithDuplicateCourseNames(schedulesToProcess);
		}
		try (Reader reader = new InputStreamReader(outputDuplicateCoursesFile.getInputStream())) {
			outputSchedulesFromFile = mapper.readValue(outputDuplicateCoursesFile.getFile(),
					new TypeReference<ArrayList<Schedule>>(){});
		}
		assertIterableEquals(outputSchedulesFromFile, schedulesToProcess);
	}

}
