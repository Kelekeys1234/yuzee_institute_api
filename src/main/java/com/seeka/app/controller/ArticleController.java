package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.service.IArticleService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticles() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<SeekaArticles> articleList = articleService.getAll();
		if (articleList != null && !articleList.isEmpty()) {
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.ARTICLE_GET_SUCCESS);
		} else {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", IConstant.ARTICLE_NOT_FOUND);
		}
		response.put("data", articleList);
		return ResponseEntity.accepted().body(response);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticle(@PathVariable final String id) {
		return ResponseEntity.accepted().body(articleService.deleteArticle(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleById(@PathVariable final String id) {
		return ResponseEntity.accepted().body(articleService.getArticleById(id));
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleByPageWise(@PathVariable final BigInteger pageNumber, @PathVariable final BigInteger pageSize,
			@RequestParam(required = false, name = "query") final String query, @RequestParam(required = false) final BigInteger categoryId,
			@RequestParam(required = false) final String tag, @RequestParam(required = false) final String status) {
		return ResponseEntity.accepted().body(articleService.fetchAllArticleByPage(pageNumber, pageSize, query, true, categoryId, tag, status));
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<?> searchArticle(@RequestBody final com.seeka.app.dto.SearchDto article) {
		return ResponseEntity.accepted().body(articleService.searchArticle(article));
	}

	@RequestMapping(value = "/search/content/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<?> contentSearch(@PathVariable final String searchText) {
		return ResponseEntity.accepted().body(articleService.searchBasedOnNameAndContent(searchText));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveMultiArticle(@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final ArticleDto2 article) {
		Map<String, Object> articleResponse = articleService.saveMultiArticle(article, file);
		BigInteger articleId = (BigInteger) articleResponse.remove("articleId");
		articleService.addArticleImage(file, articleId);
		return ResponseEntity.accepted().body(articleResponse);
	}

	@RequestMapping(value = "/folder", method = RequestMethod.POST)
	public ResponseEntity<?> saveArticleFolder(@RequestBody final ArticleFolderDto articleFolder) {
		return ResponseEntity.accepted().body(articleService.saveArticleFolder(articleFolder));
	}

	@RequestMapping(value = "/folder/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleFolderById(@PathVariable final BigInteger id) {
		return ResponseEntity.accepted().body(articleService.getArticleFolderById(id));
	}

	@RequestMapping(value = "/folder/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticleFolder() {
		return ResponseEntity.accepted().body(articleService.getAllArticleFolder());
	}

	@RequestMapping(value = "/folder/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticleFolderById(@PathVariable final BigInteger articleFolderId) {
		return ResponseEntity.accepted().body(articleService.deleteArticleFolderById(articleFolderId));
	}

	@RequestMapping(value = "/folder/user/mapping", method = RequestMethod.POST)
	public ResponseEntity<?> mapArticleFolder(@RequestBody final ArticleFolderMapDto articleFolderMapDto) {
		return ResponseEntity.ok().body(articleService.mapArticleFolder(articleFolderMapDto));
	}

	@RequestMapping(value = "/folder/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getFolderWithArticle(@PathVariable final BigInteger userId) {
		return ResponseEntity.accepted().body(articleService.getFolderWithArticle(userId));
	}
}
