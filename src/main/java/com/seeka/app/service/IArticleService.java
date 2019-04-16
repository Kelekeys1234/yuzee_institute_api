package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.PageLookupDto;

public interface IArticleService {
	
	public List<Article> getAll();
	public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto);
}
