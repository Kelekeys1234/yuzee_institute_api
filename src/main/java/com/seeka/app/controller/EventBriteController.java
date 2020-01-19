package com.seeka.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EventBriteDto;
import com.seeka.app.service.EventBriteService;

@RestController
@RequestMapping("/eventbrite")
public class EventBriteController {

	@Autowired
	private EventBriteService eventBriteService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllEventCategories() {
		List <EventBriteDto> categoryList = eventBriteService.getAllCategories();
		return new GenericResponseHandlers.Builder().setData(categoryList).setMessage("Category List Displayed Successfully").setStatus(HttpStatus.OK).create();
	}
}
