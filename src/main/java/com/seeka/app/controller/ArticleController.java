package com.seeka.app.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IArticleService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllArticles(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String sortByField,
			@RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ArticleResponseDetailsDto> articleList = articleService.getArticleList(startIndex, pageSize, sortByField,
				sortByType, searchKeyword);
		Integer totalCount = articleService.getTotalSearchCount(searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
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

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteArticle(@PathVariable final String id) {
		SeekaArticles article = articleService.deleteArticle(id);
		return new GenericResponseHandlers.Builder().setData(article).setMessage("Article deleted Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable final String id) throws ValidationException {
		ArticleResponseDetailsDto articleDto = articleService.getArticleById(id);
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Data Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@PostMapping()
	public ResponseEntity<?> saveMultiArticle(@RequestBody SeekaArticleDto article,
			@RequestHeader(name = "userId") final BigInteger userId) throws ParseException, ValidationException {
		SeekaArticleDto articleDto = articleService.saveMultiArticle(article, userId);
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Article Created Successfully")
					.setStatus(HttpStatus.OK).create();
	}
	
	
	@PutMapping()
	public ResponseEntity<?> updateArticle(@RequestBody SeekaArticleDto article,
			@RequestHeader(name = "userId") final BigInteger userId) throws ParseException, ValidationException {
		SeekaArticleDto articleDto = articleService.saveMultiArticle(article, userId);
		if(article == null || article.getId() ==null || BigInteger.ZERO.equals(article.getId())) {
			throw new ValidationException("Please specify Id to update article");
		}
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Article Updated Successfully")
					.setStatus(HttpStatus.OK).create();

	}
	
	

//	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET)
//	public ResponseEntity<?> getArticleByPageWise(@PathVariable final BigInteger pageNumber,
//			@PathVariable final BigInteger pageSize, @RequestParam(required = false, name = "query") final String query,
//			@RequestParam(required = false) final BigInteger categoryId,
//			@RequestParam(required = false) final String tag, @RequestParam(required = false) final String status) {
//		return ResponseEntity.accepted()
//				.body(articleService.fetchAllArticleByPage(pageNumber, pageSize, query, true, categoryId, tag, status));
//	}

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
