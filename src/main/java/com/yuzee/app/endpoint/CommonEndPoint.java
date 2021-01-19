package com.yuzee.app.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping(path = "/api/v1")
public interface CommonEndPoint {

	@GetMapping("/gallery/entityType/{entityType}/entityId/{entityId}")
	public ResponseEntity<?> getEntityGallery(@PathVariable final String entityType,
			@PathVariable final String entityId) throws InternalServerException, NotFoundException, ValidationException;
}