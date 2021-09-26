package com.enrollment.processor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enrollment.controller.SortController;

@SpringBootTest
class CollegeEnrollmentProcessorApplicationTests {

	@Autowired
	private SortController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}

}
