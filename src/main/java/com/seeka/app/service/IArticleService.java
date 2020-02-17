package com.seeka.app.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.ArticleFolder;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleResponseDetailsDto;
import com.seeka.app.dto.SeekaArticleDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface IArticleService {

	SeekaArticles deleteArticle(String articleId);

	ArticleResponseDetailsDto getArticleById(String articleId) throws ValidationException;

	SeekaArticleDto saveMultiArticle(SeekaArticleDto article, String userId) throws ValidationException, ParseException;

	ArticleFolderDto saveArticleFolder(ArticleFolderDto articleFolder, String language) throws NotFoundException, ValidationException;

	ArticleFolder getArticleFolderById(String articleFolderId) throws ValidationException;

	List<ArticleFolder> getAllArticleFolder();

	List<ArticleFolder> getFolderByUserId(String userId) throws ValidationException;

	List<ArticleResponseDetailsDto> getArticleByFolderId(Integer startIndex, Integer pageSize, String folderId) throws ValidationException;

	Integer getTotalSearchCount(String searchKeyword);

	ArticleFolderMapDto mapArticleFolder(final ArticleFolderMapDto articleFolderMapDto, String language) throws ValidationException;

	ArticleFolder deleteArticleFolderById(final String articleFolderId) throws ValidationException;

	List<ArticleResponseDetailsDto> getArticleList(Integer startIndex, Integer pageSize, String sortByField, String sortByType, String searchKeyword,
			String categoryId, String tags, Boolean status, Date date) throws ValidationException;

	List<ArticleResponseDetailsDto> getArticleList(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<String> categoryId, List<String> tags, Boolean status, Date filterDate) throws ValidationException;

	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType, final String searchKeyword,
			String categoryId, String tags, Boolean status, Date filterDate);

	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType, final String searchKeyword,
			List<String> categoryIdList, List<String> tagList, Boolean status, Date date);

	List<String> getAuthors(int startIndex, Integer pageSize, String searchString);

	int getTotalAuthorCount(String searchString);

	List<SeekaArticles> findArticleByCountryId(String id, String categoryName, Integer count, List<String> viewArticleIds);
}
