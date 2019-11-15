package com.seeka.app.service;

import java.math.BigInteger;
import java.text.ParseException;
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
	
	SeekaArticleDto saveMultiArticle(SeekaArticleDto article, BigInteger userId) throws ValidationException, ParseException;
	
	ArticleFolderDto saveArticleFolder(ArticleFolderDto articleFolder, String language) throws NotFoundException, ValidationException;
	
	ArticleFolder getArticleFolderById(BigInteger articleFolderId) throws ValidationException;
	
	List<ArticleFolder> getAllArticleFolder();
	
	List<ArticleFolder> getFolderByUserId(BigInteger userId) throws ValidationException;
	
	List<ArticleResponseDetailsDto> getArticleByFolderId(Integer startIndex,Integer pageSize,BigInteger folderId) throws ValidationException;
	
	Integer getTotalSearchCount(String searchKeyword);
	
	ArticleFolderMapDto mapArticleFolder(final ArticleFolderMapDto articleFolderMapDto, String language) throws ValidationException;
	
	ArticleFolder deleteArticleFolderById(final BigInteger articleFolderId) throws ValidationException;
	
	List<ArticleResponseDetailsDto> getArticleList(Integer startIndex, Integer pageSize, String sortByField, String sortByType, String searchKeyword, BigInteger categoryId, String tags, Boolean status) throws ValidationException;

	List<ArticleResponseDetailsDto> getArticleList(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryId, List<String> tags, Boolean status) throws ValidationException;
	
	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, BigInteger categoryId, String tags, Boolean status);
	
	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, Boolean status);
}
