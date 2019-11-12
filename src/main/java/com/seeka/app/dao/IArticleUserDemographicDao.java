package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ArticleUserDemographic;

public interface IArticleUserDemographicDao {

	ArticleUserDemographic save(ArticleUserDemographic a);

	void deleteByArticleId(BigInteger id);

	List<ArticleUserDemographic> getbyArticleId(BigInteger id);
}
