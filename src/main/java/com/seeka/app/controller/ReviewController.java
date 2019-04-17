package com.seeka.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.User;
import com.seeka.app.bean.UserInstCourseReview;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.UserReviewRequestDto;
import com.seeka.app.service.IUserInstCourseReviewService;
import com.seeka.app.service.IUserService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	IUserService userService; 

	@Autowired
	IUserInstCourseReviewService userInstCourseReviewService; 
	
	
	@RequestMapping(value = "/prevalidate", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> preValidate(@Valid @RequestBody UserReviewRequestDto request) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if(null == request.getUserId()){
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid user data.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		if(null == request.getCourseId() && null == request.getInstituteId()){
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Valid course id or institute id required to fetch reviews.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Boolean isReviewWritten = userInstCourseReviewService.findReviewByFilters(request);
		response.put("disableReview", isReviewWritten);
        response.put("status", 1);
		response.put("message","Success.!");
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getAllArticles(@Valid @RequestBody UserReviewRequestDto request) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if(null == request.getUserId()){
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid user data.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		if(null == request.getCourseId() && null == request.getInstituteId()){
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Valid course id or institute id required to fetch reviews.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		List<UserInstCourseReview> reviewsList = userInstCourseReviewService.getAllReviewsByFilter(request);
		UserInstCourseReview overAllReviewObj = userInstCourseReviewService.getOverAllReview(request);
         
		Integer maxCount = 0,totalCount =0;
		if(null != reviewsList && !reviewsList.isEmpty()) {
			totalCount = reviewsList.get(0).getTotalCount();
			maxCount = reviewsList.size();
		}
		boolean showMore;
		if(request.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("paginationObj",new PaginationDto(totalCount,showMore));
		response.put("reviewMasterObj",overAllReviewObj);
		response.put("reviewsList",reviewsList);
		return ResponseEntity.accepted().body(response);
	}
	
	 
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllArticlesByPage(@Valid @RequestBody UserInstCourseReview request ){
		Map<String, Object> response = new HashMap<String, Object>();
		User user = userService.get(request.getUserId());
		String userName = user.getFirstName()+" "+user.getLastName();
		request.setReviewStars(request.getOverAllRating()/2);
		request.setCreatedBy(userName);
		request.setCreatedOn(new Date());
		request.setIsActive(true);
		userInstCourseReviewService.save(request);
        response.put("status", 1);
		response.put("message","Success.!");
		return ResponseEntity.accepted().body(response);
		
	}
	
	
	 
}
