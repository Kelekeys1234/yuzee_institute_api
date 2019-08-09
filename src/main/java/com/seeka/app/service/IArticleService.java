package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SearchDto;

public interface IArticleService {

	List<SeekaArticles> getAll();

	List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto);

	Map<String, Object> deleteArticle(String articleId);

	Map<String, Object> getArticleById(String articleId);

	Map<String, Object> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status, BigInteger categoryId, String tag,
			String status2);

	Map<String, Object> saveArticle(MultipartFile file, ArticleDto article);

	Map<String, Object> searchArticle(SearchDto article);

	Map<String, Object> saveMultiArticle(ArticleDto2 article, MultipartFile file);

	Map<String, Object> saveArticleFolder(ArticleFolderDto articleFolder);

	Map<String, Object> getArticleFolderById(BigInteger articleFolderId);

	Map<String, Object> getAllArticleFolder();

	Map<String, Object> deleteArticleFolderById(BigInteger articleFolderId);

	Map<String, Object> mapArticleFolder(ArticleFolderMapDto articleFolderMapDto);

	Map<String, Object> getFolderWithArticle(BigInteger userId);

	Map<String, Object> searchBasedOnNameAndContent(String searchText);

	Map<String, Object> addArticleImage(MultipartFile file, BigInteger articleId);
}
