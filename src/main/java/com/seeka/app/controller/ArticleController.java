package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleResponseDetailsDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.SeekaArticleDto;
import com.seeka.app.service.IArticleService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticles() {
		List<ArticleResponseDetailsDto> articleList = articleService.getArticleList();
		int totalCount = 20;
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(0, 20, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Article List successfully");
		responseMap.put("data", articleList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticle(@PathVariable final String id) {
		SeekaArticles article = articleService.deleteArticle(id);
		return new GenericResponseHandlers.Builder().setData(article).setMessage("Article deleted Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable final String id) {
		ArticleResponseDetailsDto articleDto = articleService.getArticleById(id);
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Data Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveMultiArticle(@RequestBody SeekaArticleDto article,	@RequestHeader(name = "userId") BigInteger userId) throws Exception, Throwable {
         //	,@RequestParam(name = "file", required = false) final MultipartFile file,		
		article = articleService.saveMultiArticle(article, userId);//articleService.addArticleImage(file, article.getId());
		return new GenericResponseHandlers.Builder().setData(article).setMessage("Article Created Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleByPageWise(@PathVariable final BigInteger pageNumber,
			@PathVariable final BigInteger pageSize, @RequestParam(required = false, name = "query") final String query,
			@RequestParam(required = false) final BigInteger categoryId,
			@RequestParam(required = false) final String tag, @RequestParam(required = false) final String status) {
		return ResponseEntity.accepted()
				.body(articleService.fetchAllArticleByPage(pageNumber, pageSize, query, true, categoryId, tag, status));
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<?> searchArticle(@RequestBody final com.seeka.app.dto.SearchDto article) {
		return ResponseEntity.accepted().body(articleService.searchArticle(article));
	}

	@RequestMapping(value = "/search/content/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<?> contentSearch(@PathVariable final String searchText) {
		return ResponseEntity.accepted().body(articleService.searchBasedOnNameAndContent(searchText));
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
	public ResponseEntity<?> deleteArticleFolderById(@PathVariable final BigInteger id) {
		return ResponseEntity.accepted().body(articleService.deleteArticleFolderById(id));
	}

	@RequestMapping(value = "/folder/user/mapping", method = RequestMethod.POST)
	public ResponseEntity<?> mapArticleFolder(@RequestBody final ArticleFolderMapDto articleFolderMapDto) {
		return ResponseEntity.ok().body(articleService.mapArticleFolder(articleFolderMapDto));
	}

	@RequestMapping(value = "/folder/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getFolderWithArticle(@PathVariable final BigInteger userId) {
		return ResponseEntity.accepted().body(articleService.getFolderWithArticle(userId));
	}

	@RequestMapping(value = "/folder/{folderId}/all", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleByFolderId(@PathVariable final BigInteger folderId) {
		return ResponseEntity.accepted().body(articleService.getArticleByFolderId(folderId));
	}

	@RequestMapping(value = "/{articleId}/folder/{folderId}/unmapp", method = RequestMethod.GET)
	public ResponseEntity<?> umMappedArticle(@PathVariable final BigInteger articleId,
			@PathVariable final BigInteger folderId) {
		return ResponseEntity.accepted().body(articleService.unMappedFolder(articleId, folderId));
	}
}
