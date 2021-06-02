package com.yuzee.app.dao;

import java.util.Date;
import java.util.List;

import com.yuzee.app.bean.Articles;

public interface IArticleDAO {

	List<Articles> getAll(Integer startIndex, Integer pageSize, String sortByField, String sortByType, String searchKeyword, List<String> categoryId,
			List<String> tags, Boolean status, Date filterDate);

	Articles findById(String uId);

	Articles deleteArticle(Articles article);

	Articles save(Articles article);

	Integer getTotalSearchCount(String searchKeyword);

	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType, final String searchKeyword,
			List<String> categoryIdList, List<String> tagList, Boolean status, Date date);

	List<String> getAuthors(Long startIndex, Integer pageSize, String searchString);

	int getTotalAuthorCount(String searchString);

	List<Articles> findArticleByCountryId(String countryId, String categoryName, Integer count, List<String> viewArticleIds);

}
