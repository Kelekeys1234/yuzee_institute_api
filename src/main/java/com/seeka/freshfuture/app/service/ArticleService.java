package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.Article;
import com.seeka.freshfuture.app.dao.IArticleDAO;

@Service
@Transactional
public class ArticleService implements IArticleService {
	
	@Autowired
	IArticleDAO articleDAO;

	@Override
	public List<Article> getAll() {
		
		return articleDAO.getAll();
	}
	
	
}
