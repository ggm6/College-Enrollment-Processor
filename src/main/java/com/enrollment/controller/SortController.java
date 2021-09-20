package com.enrollment.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enrollment.request.Course;

@RestController
public class SortController {

	@PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void processScheduleSortingRequest(@RequestBody List<Course> courses) {
		System.out.println();
	}
}
