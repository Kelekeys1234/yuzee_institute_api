package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Article;
import com.seeka.app.dto.PageLookupDto;
@Repository
public class ArticleDAO implements IArticleDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Article> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		@SuppressWarnings("unchecked")
		List<Article> list = session.createCriteria(Article.class).list();	   					
		return list;
	}
	
	@Override
	public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto) {
		Session session = sessionFactory.getCurrentSession();	
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select id,heading,content,url,imagePath,created_date from "
				+ "seeka_articles with(nolock) ) A order by A.created_date desc";
		sqlQuery +=" OFFSET ("+pageLookupDto.getPageNumber()+"-1)*"+pageLookupDto.getMaxSizePerPage()+" ROWS FETCH NEXT "+pageLookupDto.getMaxSizePerPage()+" ROWS ONLY"; 
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Article> list = new ArrayList<Article>();
		Article obj = null;			
		for(Object[] row : rows){
			obj = new Article();	
			obj.setId(UUID.fromString(String.valueOf(row[0])));
			obj.setHeading(String.valueOf(row[1]));
			obj.setContent(String.valueOf(row[2]));
			obj.setUrl(String.valueOf(row[3]));
			obj.setImagePath(String.valueOf(row[4]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[6])));
			list.add(obj);
		}   
	    return list;	   
	}
	
	
}
