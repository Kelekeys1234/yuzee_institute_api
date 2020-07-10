package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.ArticleUserDemographic;

public interface IArticleUserDemographicDao {

	ArticleUserDemographic save(ArticleUserDemographic a);

	void deleteByArticleId(String id);

	List<ArticleUserDemographic> getbyArticleId(String id);

	List<ArticleUserDemographic> getArticleCityListbyCountryId(String id, String articleId);
}
