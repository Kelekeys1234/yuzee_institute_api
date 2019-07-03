package com.seeka.app.controller;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.service.IArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @RequestMapping(value = "/all" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllArticles() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<SeekaArticles> articleList = articleService.getAll();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("articleList", articleList);
        return ResponseEntity.accepted().body(response);

    }

   /* @RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllArticlesByPage(@RequestBody PageLookupDto lookupDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<SeekaArticles> articleList = articleService.getArticlesByLookup(lookupDto);
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

    }*/

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticle(@PathVariable String id) {
        return ResponseEntity.accepted().body(articleService.deleteArticle(id));
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity<?> getArticleById(@PathVariable String id) {
        return ResponseEntity.accepted().body(articleService.getArticleById(id));
    }

    @RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}" , method = RequestMethod.GET)
    public ResponseEntity<?> getArticleByPageWise(@PathVariable BigInteger pageNumber, @PathVariable BigInteger pageSize, @RequestParam(required = false, name = "query") String query) {
        return ResponseEntity.accepted().body(articleService.fetchAllArticleByPage(pageNumber, pageSize, query, true));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchArticle(@RequestBody com.seeka.app.dto.SearchDto article) {
        return ResponseEntity.accepted().body(articleService.searchArticle(article));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveMultiArticle(@RequestBody ArticleDto2 article) {
        return ResponseEntity.accepted().body(articleService.saveMultiArticle(article));
    }

    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    public ResponseEntity<?> saveArticleFolder(@RequestBody ArticleFolderDto articleFolder) {
        return ResponseEntity.accepted().body(articleService.saveArticleFolder(articleFolder));
    }

    @RequestMapping(value = "/folder/{id}" , method = RequestMethod.GET)
    public ResponseEntity<?> getArticleFolderById(@PathVariable BigInteger id) {
        return ResponseEntity.accepted().body(articleService.getArticleFolderById(id));
    }

    @RequestMapping(value = "/folder/all" , method = RequestMethod.GET)
    public ResponseEntity<?> getAllArticleFolder() {
        return ResponseEntity.accepted().body(articleService.getAllArticleFolder());
    }

    @RequestMapping(value = "/folder/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticleFolderById(@PathVariable BigInteger articleFolderId) {
        return ResponseEntity.accepted().body(articleService.deleteArticleFolderById(articleFolderId));
    }

    @RequestMapping(value = "/folder/user/mapping", method = RequestMethod.POST)
    public ResponseEntity<?> mapArticleFolder(@RequestBody ArticleFolderMapDto articleFolderMapDto) {
        return ResponseEntity.accepted().body(articleService.mapArticleFolder(articleFolderMapDto));
    }

    @RequestMapping(value = "/folder/user/{userId}" , method = RequestMethod.GET)
    public ResponseEntity<?> getFolderWithArticle(@PathVariable BigInteger userId) {
        return ResponseEntity.accepted().body(articleService.getFolderWithArticle(userId));
    }
}
