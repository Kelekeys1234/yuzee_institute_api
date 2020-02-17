package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.UserMyCourseService;

/**
 *
 * @author SeekADegree
 *
 */
@RestController("userMyCourseControllerV1")
@RequestMapping("/api/v1/course")
public class UserMyCourseController {

	@Autowired
	private UserMyCourseService userMyCourseService;

	@PostMapping(value = "/user/favourite")
	public ResponseEntity<?> markUserFavoriteCourse(@RequestBody final UserCourseRequestDto courseRequestDto) throws ValidationException {
		userMyCourseService.createUserMyCourse(courseRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to my course.").create();
	}

	@GetMapping(value = "/user/favourite/{userId}")
	public ResponseEntity<?> markUserFavoriteCourse(@PathVariable final String userId) throws Exception {
		List<UserMyCourse> userMyCourseList = userMyCourseService.getUserMyCourseByUserID(userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(userMyCourseList).setMessage("Get my course successfully").create();
	}

}
