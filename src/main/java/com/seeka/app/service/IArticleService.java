package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.ArticleResponseDetailsDto;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SearchDto;
import com.seeka.app.dto.SeekaArticleDto;

public interface IArticleService {

	List<SeekaArticles> getAll();

	List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto);

	SeekaArticles deleteArticle(String articleId);

	ArticleResponseDetailsDto getArticleById(String articleId);

	Map<String, Object> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status, BigInteger categoryId, String tag,
			String status2);

	SeekaArticleDto saveMultiArticle(SeekaArticleDto article, BigInteger userId) throws Throwable, Exception;

	Map<String, Object> saveArticleFolder(ArticleFolderDto articleFolder);

	Map<String, Object> searchArticle(final SearchDto article);
	 
	Map<String, Object> getArticleFolderById(BigInteger articleFolderId);

	Map<String, Object> getAllArticleFolder();

	Map<String, Object> deleteArticleFolderById(BigInteger articleFolderId);

	Map<String, Object> mapArticleFolder(ArticleFolderMapDto articleFolderMapDto);

	Map<String, Object> getFolderWithArticle(BigInteger userId);

	Map<String, Object> searchBasedOnNameAndContent(String searchText);

	Map<String, Object> addArticleImage(MultipartFile file, BigInteger articleId);

	SeekaArticles findByArticleId(BigInteger articleId);

    Map<String, Object> unMappedFolder(BigInteger articleId, BigInteger folderId);

    Map<String, Object> getArticleByFolderId(BigInteger folderId);

	List<ArticleResponseDetailsDto> getArticleList();
}
