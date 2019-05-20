package com.seeka.app.dao;

import java.util.UUID;

import com.seeka.app.bean.ArticleUserCitizenship;

public interface IUserArticleDAO {

    void saveArticleUserCitizenship(ArticleUserCitizenship userCitizenship);

    ArticleUserCitizenship findArticleUserCitizenshipDetails(UUID id);

}
