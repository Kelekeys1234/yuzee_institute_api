package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.service.IArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    IArticleService articleService;

    @RequestMapping(value = "/get/all")
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

    @RequestMapping(value = "/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable String articleId) {
        return ResponseEntity.accepted().body(articleService.deleteArticle(articleId));
    }

    @RequestMapping(value = "/byId/{articleId}")
    public ResponseEntity<?> getArticleById(@PathVariable String articleId) {
        return ResponseEntity.accepted().body(articleService.getArticleById(articleId));
    }

    @RequestMapping(value = "/byPageWise/{page}/{size}")
    public ResponseEntity<?> getArticleByPageWise(@PathVariable Integer page, @PathVariable Integer size, @RequestParam(required = false, name = "query") String query) {
        return ResponseEntity.accepted().body(articleService.fetchAllArticleByPage(page, size, query, true));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchArticle(@RequestBody com.seeka.app.dto.ArticleDto article) {
        return ResponseEntity.accepted().body(articleService.searchArticle(article));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveMultiArticle(@RequestBody ArticleDto2 article) {
        return ResponseEntity.accepted().body(articleService.saveMultiArticle(article));
    }

    @RequestMapping(value = "/saveFolder", method = RequestMethod.POST)
    public ResponseEntity<?> saveArticleFolder(@RequestBody ArticleFolderDto articleFolder) {
        return ResponseEntity.accepted().body(articleService.saveArticleFolder(articleFolder));
    }

    @RequestMapping(value = "/folderById/{articleFolderId}")
    public ResponseEntity<?> getArticleFolderById(@PathVariable UUID articleFolderId) {
        return ResponseEntity.accepted().body(articleService.getArticleFolderById(articleFolderId));
    }

    @RequestMapping(value = "/allFolder")
    public ResponseEntity<?> getAllArticleFolder() {
        return ResponseEntity.accepted().body(articleService.getAllArticleFolder());
    }

    @RequestMapping(value = "/deleteFolder/{articleFolderId}")
    public ResponseEntity<?> deleteArticleFolderById(@PathVariable UUID articleFolderId) {
        return ResponseEntity.accepted().body(articleService.deleteArticleFolderById(articleFolderId));
    }

    @RequestMapping(value = "/saveFolderMapping", method = RequestMethod.POST)
    public ResponseEntity<?> mapArticleFolder(@RequestBody ArticleFolderMapDto articleFolderMapDto) {
        return ResponseEntity.accepted().body(articleService.mapArticleFolder(articleFolderMapDto));
    }

    @RequestMapping(value = "/folderByUserId/{userId}")
    public ResponseEntity<?> getFolderWithArticle(@PathVariable UUID userId) {
        return ResponseEntity.accepted().body(articleService.getFolderWithArticle(userId));
    }
}
