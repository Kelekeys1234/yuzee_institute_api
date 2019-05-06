package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        response.put("message", "Success.!");
        response.put("articleList", articleList);
        return ResponseEntity.accepted().body(response);

    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllArticlesByPage(@RequestBody PageLookupDto lookupDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Article> articleList = articleService.getArticlesByLookup(lookupDto);
        Integer maxCount = 0, totalCount = 0;
        if (null != articleList && !articleList.isEmpty()) {
            totalCount = articleList.get(0).getTotalCount();
            maxCount = articleList.size();
        }
        boolean showMore = false;
        if (lookupDto.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("articleList", articleList);
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        return ResponseEntity.accepted().body(response);

    }

    @RequestMapping(value = "/deleteArticle/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteArticle(@PathVariable String articleId) {
        return ResponseEntity.accepted().body(articleService.deleteArticle(articleId));
    }

    @RequestMapping(value = "/getArticleById/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<?> getArticleById(@PathVariable String articleId) {
        return ResponseEntity.accepted().body(articleService.getArticleById(articleId));
    }

    @RequestMapping(value = "/getArticleByPageWise/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<?> getArticleByPageWise(@PathVariable Integer page, @PathVariable Integer size, @RequestParam(required = false, name = "query") String query) {
        return ResponseEntity.accepted().body(articleService.fetchAllArticleByPage(page, size, query, true));
    }

    @RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
    public ResponseEntity<?> saveArticle(@ModelAttribute("articleobj") com.seeka.app.dto.ArticleDto article, @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.accepted().body(articleService.saveArticle(file, article));
    }
}
