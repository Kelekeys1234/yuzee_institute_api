package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ArticleFolder;

public interface IArticleFolderDao {

	void save(ArticleFolder articleFolder);

	ArticleFolder findById(BigInteger id);

	List<ArticleFolder> getAllArticleFolder();

	List<ArticleFolder> getAllArticleFolderByUserId(BigInteger userId);

	List<ArticleFolder> getAllFolderByUserId(BigInteger userId);


}
