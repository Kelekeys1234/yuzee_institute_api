package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.SeekaArticles;

public interface IArticleDAO {

	List<SeekaArticles> getAll(Integer startIndex, Integer pageSize, String sortByField, String sortByType, String searchKeyword, 
			List<BigInteger> categoryId, List<String> tags, Boolean status);

	SeekaArticles findById(BigInteger uId);

	SeekaArticles deleteArticle(SeekaArticles article);

	SeekaArticles save(SeekaArticles article);
	
	Integer getTotalSearchCount(String searchKeyword);

	Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, Boolean status);
}
