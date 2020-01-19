package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ArticleUserDemographic;
import com.seeka.app.bean.City;

public interface IArticleUserDemographicDao {

	ArticleUserDemographic save(ArticleUserDemographic a);

	void deleteByArticleId(BigInteger id);

	List<ArticleUserDemographic> getbyArticleId(BigInteger id);

	List<ArticleUserDemographic> getArticleCityListbyCountryId(BigInteger id,BigInteger articleId);
}
