package com.enrollment.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

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
@TestPropertySource(locations = "classpath:application.yml")
public class SortControllerTest {

	@Value("classpath:full-integration-input.json")
	Resource inputResourceFile;

	@Value("classpath:full-integration-output.json")
	Resource outputResourceFile;

	@Autowired
	private SortController controller;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void doFullIntegrationTest() throws Exception {
		ArrayList<Schedule> processedSchedules = null;
		ArrayList<Schedule> outputCoursesFromFile = null;

		try (Reader reader = new InputStreamReader(inputResourceFile.getInputStream())) {
			ArrayList<Course> coursesToProcess = mapper.readValue(inputResourceFile.getFile(),
					new TypeReference<ArrayList<Course>>(){});
			processedSchedules = controller.processScheduleSortingRequest(coursesToProcess);
		}
		try (Reader reader = new InputStreamReader(outputResourceFile.getInputStream())) {
			outputCoursesFromFile = mapper.readValue(outputResourceFile.getFile(),
					new TypeReference<ArrayList<Schedule>>(){});
		}
		assertIterableEquals(processedSchedules, outputCoursesFromFile);
	}

}
