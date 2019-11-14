package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ArticleFolderMap;

public interface IArticleFolderMapDao {

	ArticleFolderMap save(ArticleFolderMap articleFolderMap);

	List<ArticleFolderMap> getArticleByFolderId( Integer startIndex,Integer pageSize ,BigInteger folderId);
	
	

}
