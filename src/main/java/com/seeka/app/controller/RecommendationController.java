package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IRecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	private IRecommendationService iRecommendationService;
	
	@GetMapping("/institute")
	public List<Object> getRecommendedInstitutes(){
		return null;
	}
	
	@GetMapping("/otherPeopleSearch")
	public List<Object> getOtherPeopleSearch(){
		return null;
	}
	
	@GetMapping("/courses")
	public List<Course> getRecommendedCourses(@RequestHeader(value="userId")BigInteger userId) throws ValidationException{
		return iRecommendationService.getRecommendedCourses(userId);
	}
}
