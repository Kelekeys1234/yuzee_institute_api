package com.seeka.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ArticleFolderMap;


@Repository
public class ArticleFolderMapDao implements IArticleFolderMapDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ArticleFolderMap save(ArticleFolderMap articleFolderMap) {
            Session session = sessionFactory.getCurrentSession();
            session.save(articleFolderMap);
           return articleFolderMap;
    }
    @Override
    public List<ArticleFolderMap> getArticleByFolderId(Integer startIndex,Integer pageSize, String folderId) {
        Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ArticleFolderMap.class, "article_folder_mapping");
		criteria.add(Restrictions.eq("folderId", folderId));
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
    }

    
    
//
//    public List<ArticleNameDto> getFolderArticles(BigInteger id) {
//        List<ArticleNameDto> articleNameDtos = new ArrayList<ArticleNameDto>();
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            Query query = session.createSQLQuery(
//                            "SELECT sc.id, sc.heading FROM article_folder_mapping auc inner join seeka_articles sc on auc.article_id = sc.id  where auc.folder_id='" + id + "'");
//            List<Object[]> rows = query.list();
//            for (Object[] row : rows) {
//                ArticleNameDto bean = new ArticleNameDto();
//                bean.setTitle((row[1].toString()));
//                bean.setArticleId((new BigInteger((row[0].toString()))));
//                articleNameDtos.add(bean);
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return articleNameDtos;
//    }
//
//  
//    public String getFolderImageUrl(BigInteger id) {
//        String imageUrl = null;
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            Query query = session.createSQLQuery(
//                            "SELECT sc.imagepath FROM article_folder_mapping auc inner join seeka_articles sc on auc.article_id = sc.id  where auc.folder_id='" + id + "'"
//                                            + "ORDER BY auc.article_id DESC LIMIT 1");
//             imageUrl = (String) query.uniqueResult();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return imageUrl;
//    }
    


}
