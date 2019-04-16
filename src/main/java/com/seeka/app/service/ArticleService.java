package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Article;
import com.seeka.app.dao.IArticleDAO;
import com.seeka.app.dto.PageLookupDto;

@Service
@Transactional
public class ArticleService implements IArticleService {
	
	@Autowired
	IArticleDAO articleDAO;

	@Override
	public List<Article> getAll() {
		return articleDAO.getAll();
	}
	
	@Override
	public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto){
		return articleDAO.getArticlesByLookup(pageLookupDto);
	}
	
	
}
