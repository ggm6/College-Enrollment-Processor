package com.enrollment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.enrollment.request.SortRequest;

@Controller
public class SortController {

	@PostMapping("/sort")
	public void processScheduleSortingRequest(@RequestBody SortRequest request) {
		System.out.println();
	}
}
