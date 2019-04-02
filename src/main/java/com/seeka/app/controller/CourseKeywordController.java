package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.SearchKeywords;
import com.seeka.app.service.ISearchKeywordsService;

@RestController
@RequestMapping("/coursekeyword")
public class CourseKeywordController {
	 
	@Autowired
	ISearchKeywordsService searchKeywordsService;
	  
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") String keyword) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<SearchKeywords> searchkeywordList  = searchKeywordsService.searchCourseKeyword(keyword);		
		response.put("status", 1);
		response.put("searchkeywordList", searchkeywordList);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<SearchKeywords> searchkeywordList  = searchKeywordsService.getAll();
		response.put("status", 1);
		response.put("searchkeywordList", searchkeywordList);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
}
         