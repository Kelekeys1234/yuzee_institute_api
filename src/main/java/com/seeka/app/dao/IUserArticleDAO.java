package com.seeka.app.dao;import java.math.BigInteger;

import com.seeka.app.bean.ArticleUserCitizenship;

public interface IUserArticleDAO {

    void saveArticleUserCitizenship(ArticleUserCitizenship userCitizenship);

    ArticleUserCitizenship findArticleUserCitizenshipDetails(BigInteger id);

}
