package com.seeka.freshfuture.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.freshfuture.app.bean.Article;
import com.seeka.freshfuture.app.service.IArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	IArticleService articleService;

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllArticles() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
        List<Article> articleList = articleService.getAll();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("articleList",articleList);
		return ResponseEntity.accepted().body(response);
		
	}
	
	

}
