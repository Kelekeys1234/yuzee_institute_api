package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SearchDto;

public interface IArticleDAO {

	List<SeekaArticles> getAll(Integer startIndex, Integer pageSize, String sortByField, String sortByType, String searchKeyword);

	List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto);

	SeekaArticles findById(BigInteger uId);

	SeekaArticles deleteArticle(SeekaArticles article);

	List<SeekaArticles> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status);

	int findTotalCount();

	SeekaArticles save(SeekaArticles article);

	void updateArticle(BigInteger subCAtegory, BigInteger id);

	List<SeekaArticles> searchArticle(SearchDto article);

	List<SeekaArticles> articleByFilter(String sqlQuery);

	int findTotalCountBasedOnCondition(String countQuery);

	List<SeekaArticles> searchBasedOnNameAndContent(String searchText);

	Integer getTotalSearchCount(String searchKeyword);
}
