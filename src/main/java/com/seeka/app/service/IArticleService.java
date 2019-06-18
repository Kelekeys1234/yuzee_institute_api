package com.seeka.app.service;import java.math.BigInteger;
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

    public List<SeekaArticles> getAll();

    public List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto);

    public Map<String, Object> deleteArticle(String articleId);

    public Map<String, Object> getArticleById(String articleId);

    public Map<String, Object> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status);

    public Map<String, Object> saveArticle(MultipartFile file, ArticleDto article);

    public Map<String, Object> searchArticle(SearchDto article);

    public Map<String, Object> saveMultiArticle(ArticleDto2 article);

    public Map<String, Object> saveArticleFolder(ArticleFolderDto articleFolder);

    public Map<String, Object> getArticleFolderById(BigInteger articleFolderId);

    public Map<String, Object> getAllArticleFolder();

    public Map<String, Object> deleteArticleFolderById(BigInteger articleFolderId);

    public Map<String, Object> mapArticleFolder(ArticleFolderMapDto articleFolderMapDto);

    public Map<String, Object> getFolderWithArticle(BigInteger userId);
}
