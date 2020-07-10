package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.ArticleFolder;

public interface IArticleFolderDao {

	void save(ArticleFolder articleFolder);

	ArticleFolder findById(String id);

	List<ArticleFolder> getAllArticleFolder();

	List<ArticleFolder> getAllArticleFolderByUserId(String userId);

	List<ArticleFolder> getAllFolderByUserId(String userId);


}
