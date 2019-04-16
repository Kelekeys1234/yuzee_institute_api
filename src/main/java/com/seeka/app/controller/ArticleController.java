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
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.service.IArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	IArticleService articleService;
	 
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllArticles() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
        List<Article> articleList = articleService.getAll();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("articleList",articleList);
		return ResponseEntity.accepted().body(response);
		
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllArticlesByPage(@RequestBody PageLookupDto lookupDto ){
		Map<String, Object> response = new HashMap<String, Object>();
        List<Article> articleList = articleService.getArticlesByLookup(lookupDto);
        Integer maxCount = 0,totalCount =0;
		if(null != articleList && !articleList.isEmpty()) {
			totalCount = articleList.get(0).getTotalCount();
			maxCount = articleList.size();
		}
		boolean showMore = false;
		if(lookupDto.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("articleList",articleList);
		response.put("paginationObj",new PaginationDto(totalCount,showMore));
		return ResponseEntity.accepted().body(response);
		
	}
}
