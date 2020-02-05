package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ArticleUserDemographic;
import com.seeka.app.bean.City;

public interface IArticleUserDemographicDao {

	ArticleUserDemographic save(ArticleUserDemographic a);

	void deleteByArticleId(String id);

	List<ArticleUserDemographic> getbyArticleId(String id);

	List<ArticleUserDemographic> getArticleCityListbyCountryId(BigInteger id, String articleId);
}
