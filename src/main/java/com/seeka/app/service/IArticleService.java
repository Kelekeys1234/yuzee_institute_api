package com.seeka.app.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.ArticleDto2;
import com.seeka.app.dto.ArticleFolderDto;
import com.seeka.app.dto.ArticleFolderMapDto;
import com.seeka.app.dto.PageLookupDto;

public interface IArticleService {

    public List<Article> getAll();

    public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto);

    public Map<String, Object> deleteArticle(String articleId);

    public Map<String, Object> getArticleById(String articleId);

    public Map<String, Object> fetchAllArticleByPage(Integer page, Integer size, String query, boolean status);

    public Map<String, Object> saveArticle(MultipartFile file, ArticleDto article);

    public Map<String, Object> searchArticle(ArticleDto article);

    public Map<String, Object> saveMultiArticle(ArticleDto2 article);

    public Map<String, Object> saveArticleFolder(ArticleFolderDto articleFolder);

    public Map<String, Object> getArticleFolderById(UUID articleFolderId);

    public Map<String, Object> getAllArticleFolder();

    public Map<String, Object> deleteArticleFolderById(UUID articleFolderId);

    public Map<String, Object> mapArticleFolder(ArticleFolderMapDto articleFolderMapDto);

    public Map<String, Object> getFolderWithArticle(UUID userId);
}
