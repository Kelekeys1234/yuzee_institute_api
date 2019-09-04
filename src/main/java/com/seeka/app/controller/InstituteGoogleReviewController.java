package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.InstituteGoogleReview;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.service.IInstituteGoogleReviewService;

@RestController
@RequestMapping("/review/google")
public class InstituteGoogleReviewController {

	@Autowired
	private IInstituteGoogleReviewService iInstituteGoogleReviewService;

	@GetMapping("/{instituteId}")
	public ResponseEntity<?> getInstituteGoogleReviewDetail(@PathVariable final BigInteger instituteId) {
		InstituteGoogleReview instituteGoogleReview = iInstituteGoogleReviewService.getInstituteGoogleReviewDetail(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteGoogleReview)
				.setMessage("Get Institute google review successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getInstituteGoogleReview(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) {
		List<InstituteGoogleReview> instituteGoogleReviewList = iInstituteGoogleReviewService.getInstituteGoogleReview(pageNumber, pageSize);
		int totalCount = iInstituteGoogleReviewService.getCountOfGooglereview();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteGoogleReviewList).setTotalCount(totalCount)
				.setMessage("Get Institute google review successfully").create();
	}

	@PostMapping()
	public ResponseEntity<?> addInstituteGoogleReview() {
		iInstituteGoogleReviewService.addInstituteGoogleReview();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created Institute google review successfully").create();
	}

}
