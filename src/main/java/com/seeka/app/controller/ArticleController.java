package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Article;
import com.seeka.app.bean.City;
import com.seeka.app.bean.SearchKeywords;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.service.IArticleService;
import com.seeka.app.service.ISearchKeywordsService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	IArticleService articleService;
    
	@Autowired
	ISearchKeywordsService searchService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllArticles() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
        List<Article> articleList = articleService.getAll();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("articleList",articleList);
		return ResponseEntity.accepted().body(response);
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveCity(@RequestBody SearchKeywords searchKeyword) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		searchService.save(searchKeyword);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}

}
