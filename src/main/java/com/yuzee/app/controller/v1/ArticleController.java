package com.yuzee.app.controller.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.ArticleFolder;
import com.yuzee.app.bean.Articles;
import com.yuzee.app.dto.ArticleFilterDto;
import com.yuzee.app.dto.ArticleFolderDto;
import com.yuzee.app.dto.ArticleFolderMapDto;
import com.yuzee.app.dto.ArticleResponseDetailsDto;
import com.yuzee.app.dto.ArticlesDto;
import com.yuzee.app.processor.ArticleProcessor;
import com.yuzee.common.lib.dto.PaginationUtilDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.PaginationUtil;

@RestController("articleControllerV1")
@RequestMapping("/api/v1/article")
public class ArticleController {

	@Autowired
	private ArticleProcessor articleProcessor;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllArticles(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String sortByField,
			@RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final String categoryId,
			@RequestParam(required = false) final String tags,
			@RequestParam(required = false) final Boolean status,
			@RequestParam(required = false) final String date
			) throws ValidationException, ParseException, NotFoundException, InvokeException {
		Date filterDate = null;
		if(date != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			filterDate = df.parse(date);
		}
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ArticleResponseDetailsDto> articleList = articleProcessor.getArticleList(startIndex.intValue(), pageSize, sortByField,
				sortByType, searchKeyword, categoryId, tags, status, filterDate);
		Integer totalCount = articleProcessor.getTotalSearchCount(startIndex.intValue(), pageSize, sortByField,
				sortByType, searchKeyword, categoryId, tags, status, filterDate);
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
	
	@PostMapping("/filter")
	public ResponseEntity<?> getAllArticlesByFilter(@RequestBody ArticleFilterDto articleFilterDTO) throws ValidationException, NotFoundException, InvokeException {
		if(articleFilterDTO.getPageNumber() == null ) {
			throw new ValidationException("Please Specify Page Number");
		}
		if(articleFilterDTO.getPageSize() == null) {
			throw new ValidationException("Please Specify Page Size");
		}
		if(articleFilterDTO.getPageSize() == 0) {
			throw new ValidationException("Page Size Cannot Be 0");
		}
		Long startIndex = PaginationUtil.getStartIndex(articleFilterDTO.getPageNumber(), articleFilterDTO.getPageSize());
		List<ArticleResponseDetailsDto> articleList = articleProcessor.getArticleList(startIndex.intValue(), articleFilterDTO.getPageSize(), 
				articleFilterDTO.getSortByField(), articleFilterDTO.getSortByType(), articleFilterDTO.getSearchKeyword(), articleFilterDTO.getCategoryId(), 
				articleFilterDTO.getTags(), articleFilterDTO.getStatus(), null);
		Integer totalCount = articleProcessor.getTotalSearchCount(startIndex.intValue(), articleFilterDTO.getPageSize(), 
				articleFilterDTO.getSortByField(), articleFilterDTO.getSortByType(), articleFilterDTO.getSearchKeyword(), articleFilterDTO.getCategoryId(), 
				articleFilterDTO.getTags(), articleFilterDTO.getStatus(), null);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, articleFilterDTO.getPageSize(), totalCount);
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
		Articles article = articleProcessor.deleteArticle(id);
		return new GenericResponseHandlers.Builder().setData(article).setMessage("Article deleted Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable final String id) throws ValidationException, NotFoundException, InvokeException {
		ArticleResponseDetailsDto articleDto = articleProcessor.getArticleById(id);
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Data Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@PostMapping()
	public ResponseEntity<?> saveMultiArticle(@RequestBody ArticlesDto article,
			@RequestHeader(name = "userId") final String userId) throws ParseException, ValidationException {
		ArticlesDto articleDto = articleProcessor.saveMultiArticle(article, userId);
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Article Created Successfully")
					.setStatus(HttpStatus.OK).create();
	}
	
	
	@PutMapping()
	public ResponseEntity<?> updateArticle(@RequestBody ArticlesDto article,
			@RequestHeader(name = "userId") final String userId) throws ParseException, ValidationException {
		ArticlesDto articleDto = articleProcessor.saveMultiArticle(article, userId);
		if(article == null || article.getId() ==null) {
			throw new ValidationException("Please specify Id to update article");
		}
		return new GenericResponseHandlers.Builder().setData(articleDto).setMessage("Article Updated Successfully")
					.setStatus(HttpStatus.OK).create();

	}	

	@PostMapping(value = "/folder")
	public ResponseEntity<Object> saveArticleFolder(@RequestBody ArticleFolderDto articleFolderDto, @RequestHeader final String language) throws NotFoundException, ValidationException{
		articleFolderDto = articleProcessor.saveArticleFolder(articleFolderDto, language);
		return new GenericResponseHandlers.Builder().setData(articleFolderDto).setMessage("Folder Created Successfully").setStatus(HttpStatus.OK).create();
	}
	
	@PutMapping(value = "/folder")
	public ResponseEntity<Object> updateArticleFolder(@RequestBody ArticleFolderDto articleFolderDto, @RequestHeader final String language) throws NotFoundException, ValidationException{
		if(articleFolderDto.getId() ==null) {
			throw new ValidationException("Please specify Id to update folder date");
		}
		articleFolderDto = articleProcessor.saveArticleFolder(articleFolderDto, language);
		return new GenericResponseHandlers.Builder().setData(articleFolderDto).setMessage("folder Updated Successfully")
					.setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/folder/{id}")
	public ResponseEntity<Object> getArticleFolderById(@PathVariable final String id) throws ValidationException {
		return new GenericResponseHandlers.Builder().setData(articleProcessor.getArticleFolderById(id)).setMessage("get Folder Successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/folder/all")
	public ResponseEntity<?> getAllArticleFolder() {
		List<ArticleFolder> articleFolderList =  articleProcessor.getAllArticleFolder();
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Article List successfully");
		responseMap.put("data", articleFolderList);		
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@DeleteMapping(value = "/folder/{id}")
	public ResponseEntity<?> deleteArticleFolderById(@PathVariable final String id) throws ValidationException {
		return new GenericResponseHandlers.Builder().setData(articleProcessor.deleteArticleFolderById(id)).setMessage("Folder deleted Successfully").setStatus(HttpStatus.OK).create();
	}

	@PostMapping(value = "/folder/user/mapping")
	public ResponseEntity<Object> mapArticleFolder(@RequestHeader String language, @RequestBody @Valid ArticleFolderMapDto articleFolderMapDto, BindingResult result) throws ValidationException{
		
		List<FieldError> fieldErrors = result.getFieldErrors();
		if(!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		articleFolderMapDto = articleProcessor.mapArticleFolder(articleFolderMapDto,language);
		return new GenericResponseHandlers.Builder().setData(articleFolderMapDto).setMessage("User mapping Inserted Successfully").setStatus(HttpStatus.OK).create();
		
	}

	@GetMapping(value = "/folder/user/{userId}")
	public ResponseEntity<?> getFolderWithArticle(@PathVariable final String userId) throws ValidationException {
		return new GenericResponseHandlers.Builder().setData( articleProcessor.getFolderByUserId(userId)).setMessage("get Folder by User Successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/folder/{folderId}/all/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getArticleByFolderId(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize,@PathVariable final String folderId) throws ValidationException {
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ArticleResponseDetailsDto> articleList  = articleProcessor.getArticleByFolderId(startIndex.intValue(), pageSize,folderId);
		return new GenericResponseHandlers.Builder().setData(articleList).setMessage("get Articles Successfully").setStatus(HttpStatus.OK).create();

	}

	@GetMapping(value = "/authors")
	public ResponseEntity<?> getAuthors(@RequestParam(required = false) final String searchString, @RequestParam final Integer pageSize,
			@RequestParam final Integer pageNumber) throws ValidationException {
		if(pageSize == null || pageSize == 0) {
			throw new ValidationException("Please Specify Proper Page Size");
		}
		if(pageNumber == null || pageNumber <= 0) {
			throw new ValidationException("Please Specify Proper Page Number");
		}
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = articleProcessor.getTotalAuthorCount(searchString);
		List<String> authorList  = articleProcessor.getAuthors(startIndex, pageSize,searchString);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Author List successfully");
		responseMap.put("data", authorList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);

	}
}
