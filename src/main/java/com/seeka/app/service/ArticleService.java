package com.seeka.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Article;
import com.seeka.app.bean.ArticleUserCitizenship;
import com.seeka.app.bean.Category;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.IUserArticleDAO;
import com.seeka.app.dto.ArticleDto;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class ArticleService implements IArticleService {

    @Autowired
    IArticleDAO articleDAO;

    @Autowired
    ICategoryDAO categoryDAO;

    @Autowired
    IUserArticleDAO userArticleDAO;

    @Override
    public List<Article> getAll() {
        return articleDAO.getAll();
    }

    @Override
    public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto) {
        return articleDAO.getArticlesByLookup(pageLookupDto);
    }

    @Override
    public Map<String, Object> deleteArticle(String articleId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.DELETE_SUCCESS;
        try {
            Article article = articleDAO.findById(UUID.fromString(articleId));
            if (article != null) {
                article.setActive(false);
                article.setDeleted(DateUtil.getUTCdatetimeAsDate());
                articleDAO.deleteArticle(article);
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND;
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    @Override
    public Map<String, Object> getArticleById(String articleId) {
        Map<String, Object> response = new HashMap<String, Object>();
        ArticleDto articleDto = null;
        String status = IConstant.SUCCESS;
        try {
            Article article = articleDAO.findById(UUID.fromString(articleId));
            if (article != null) {
                articleDto = new ArticleDto();
                articleDto.setId(article.getId());
                if (article.getCategory() != null) {
                    articleDto.setCategory(article.getCategory().getId());
                }
                articleDto.setSubcategory(article.getSubCategory());
                articleDto.setHeading(article.getHeading());
                articleDto.setContent(article.getContent());
                articleDto.setLink(article.getLink());
                articleDto.setImageUrl(article.getImagePath());
                articleDto.setCountry(article.getCountry());
                articleDto.setCity(article.getCity());
                articleDto.setInstitute(article.getInstitute());
                articleDto.setFaculty(article.getFaculty());
                articleDto.setCourses(article.getCourses());
                articleDto.setGender(article.getGender());
                ArticleUserCitizenship userCitizenship = userArticleDAO.findArticleUserCitizenshipDetails(article.getId());
                if (userCitizenship.getCountry() != null && userCitizenship.getCity() != null) {
                    articleDto.setUserCity(userCitizenship.getCity());
                    articleDto.setUserCountry(userCitizenship.getCountry());
                }
            } else {
                status = IConstant.DELETE_FAILURE_ID_NOT_FOUND;
            }
        } catch (Exception exception) {
            status = IConstant.DELETE_FAILURE;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("articleDto", articleDto);
        return response;
    }

    @Override
    public Map<String, Object> fetchAllArticleByPage(Integer page, Integer size, String query, boolean status) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        List<Article> articles = null;
        int totalCount = 0;
        try {
            totalCount = articleDAO.findTotalCount();
            articles = articleDAO.fetchAllArticleByPage(page, size, query, status);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        response.put("articles", articles);
        response.put("totalCount", totalCount);
        return response;
    }

    @Override
    public Map<String, Object> saveArticle(MultipartFile file, ArticleDto articledto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        try {
            // save data
            Article article = new Article();
            if (articledto != null && articledto.getId() != null) {
                article = articleDAO.findById(articledto.getId());
                if (article != null) {
                    article.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
                }
            } else {
                article = new Article();
                article.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
            }
            article.setImagePath(articledto.getImageUrl());
            article.setHeading(articledto.getHeading());
            article.setContent(articledto.getContent());
            article.setActive(true);
            if (articledto.getCategory() != null) {
                Category category = categoryDAO.findCategoryById(articledto.getCategory());
                if (category != null) {
                    article.setCategory(category);
                }
            }
            article.setSubCategory(articledto.getSubcategory());
            // article.setImagePath(fileDownloadUri);
            article.setLink(articledto.getLink());
            article.setCountry(articledto.getCountry());
            article.setCity(articledto.getCity());
            article.setInstitute(articledto.getInstitute());
            article.setFaculty(articledto.getFaculty());
            article.setCourses(articledto.getCourses());
            article.setGender(articledto.getGender());
            article = articleDAO.save(article);
            UUID subCAtegory = article.getSubCategory();
            articleDAO.updateArticle(subCAtegory, article.getId());
            if (articledto.getUserCountry() != null && articledto.getUserCity() != null) {
                ArticleUserCitizenship userCitizenship = new ArticleUserCitizenship();
                userCitizenship.setCity(articledto.getUserCity());
                userCitizenship.setCountry(articledto.getUserCountry());
                userCitizenship.setArticleId(article.getId());
                userCitizenship.setCreatedDate(DateUtil.getUTCdatetimeAsDate());
                userCitizenship.setUpdatedDate(DateUtil.getUTCdatetimeAsDate());
                userArticleDAO.saveArticleUserCitizenship(userCitizenship);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseStatus = IConstant.DELETE_FAILURE;
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        return response;
    }

    @Override
    public Map<String, Object> searchArticle(ArticleDto article) {
        Map<String, Object> response = new HashMap<String, Object>();
        String ResponseStatus = IConstant.SUCCESS;
        List<Article> articles = null;
        int totalCount = 0;
        try {
            totalCount = articleDAO.findTotalCount();
            articles = articleDAO.searchArticle(article);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        response.put("status", 1);
        response.put("message", ResponseStatus);
        response.put("articles", articles);
        response.put("totalCount", totalCount);
        return response;
    }
}
