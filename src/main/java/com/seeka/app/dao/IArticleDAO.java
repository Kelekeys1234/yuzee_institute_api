package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.PageLookupDto;

public interface IArticleDAO {
	
	public List<Article> getAll();
	public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto);
}
