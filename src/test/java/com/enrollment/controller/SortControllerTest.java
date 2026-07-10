package com.enrollment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
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
	public void doFullIntegrationTest() throws IOException {
		List<Schedule> processedSchedules = null;
		Set<Schedule> outputCoursesFromFile = null;

		List<Course> coursesToProcess = mapper.readValue(inputFullIntegrationFile.getFile(),
				new TypeReference<List<Course>>(){});
		
		processedSchedules = controller.processScheduleSortingRequest(coursesToProcess, Boolean.FALSE);
	
		outputCoursesFromFile = mapper.readValue(outputFullIntegrationFile.getFile(),
				new TypeReference<Set<Schedule>>(){});
		
		Assertions.assertThat(processedSchedules).containsExactlyInAnyOrderElementsOf(outputCoursesFromFile);
	}

}
