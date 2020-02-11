package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.ITop10CourseService;

@RestController("top10CourseControllerV1")
@RequestMapping("/api/v1/top10")
public class Top10CourseController {

	@Autowired
	private ITop10CourseService iTop10CourseService;

	@GetMapping("/random")
	public ResponseEntity<?> getTop10RandomCoursesForGlobalSearchLandingPage(@RequestHeader final String userId) throws ValidationException {

		List<CourseResponseDto> courseResponseDto = iTop10CourseService.getTop10RandomCoursesForGlobalSearchLandingPage();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Random List of Courses.").setData(courseResponseDto).create();
	}
}
