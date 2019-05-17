package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.PageLookupDto;

public interface IArticleDAO {

    public List<Article> getAll();

    public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto);

    public Article findById(UUID uId);

    public void deleteArticle(Article article);

    public List<Article> fetchAllArticleByPage(Integer page, Integer size, String query, boolean status);

    public int findTotalCount();

    public Article save(Article article);

    public void updateArticle(UUID subCAtegory, UUID id);

    public List<Article> searchArticle(ArticleDto article);
}
