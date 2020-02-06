package com.seeka.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.IAlternativeService;

@RestController("displayAlternativeControllerV1")
@RequestMapping("/v1/alternative")
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
