package com.seeka.app.controller;

import java.math.BigInteger;
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
import com.seeka.app.service.IAlternativeService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/alternative")
public class DisplayAlternativeController {

	@Autowired
	public IAlternativeService iAlternativeService;
	
//	@GetMapping("/courses")
//	public ResponseEntity<?> getAlternateCourses(@RequestHeader(value = "userId") BigInteger userId,
//			@RequestHeader(value = "language", required = false) String language)
//			throws ValidationException {
//		List<CourseResponseDto> recomendedCourses = iRecommendationService.getRecommendedCourses(userId);
//		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(recomendedCourses)
//				.setMessage(messageByLocalService.getMessage("list.display.successfully", new Object[] {IConstant.COURSE}, language)).create();
//	}
}
