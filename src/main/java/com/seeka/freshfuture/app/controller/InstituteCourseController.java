package com.seeka.freshfuture.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.freshfuture.app.service.IInstituteService;

@RestController
@RequestMapping("/institute/course")
public class InstituteCourseController {

	@Autowired
	IInstituteService instituteService;

	  

}
