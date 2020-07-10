package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.ArticleFolderMap;

public interface IArticleFolderMapDao {

	ArticleFolderMap save(ArticleFolderMap articleFolderMap);

	List<ArticleFolderMap> getArticleByFolderId( Integer startIndex,Integer pageSize, String folderId);

}
