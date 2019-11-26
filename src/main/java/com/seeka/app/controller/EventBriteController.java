package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.service.EventBriteService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/eventbrite")
public class EventBriteController {

	@Autowired
	private EventBriteService eventBriteService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllEventCategories(@RequestParam(value ="pageSize", required = true) Integer pageSize, @RequestParam(value = "pageNumber", required= true) Integer pageNumber) {
		List <String> categoryList = eventBriteService.getAllCategories();
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		Integer totalCount = eventBriteService.getTotalCount();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Category List displayed successfully");
		responseMap.put("data", categoryList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
}
