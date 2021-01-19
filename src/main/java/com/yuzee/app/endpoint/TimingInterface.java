package com.yuzee.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/timing")
public interface TimingInterface {

	@PostMapping
	public ResponseEntity<?> saveOrUpdate(@RequestHeader(value = "userId", required = true) final String userId,
			@Valid @RequestBody(required = true) final TimingRequestDto courseFundingDto)
			throws ValidationException, NotFoundException;

	@DeleteMapping("/{timingId}")
	public ResponseEntity<?> deleteByTimingId(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String timingId) throws ValidationException, NotFoundException, ForbiddenException;
}