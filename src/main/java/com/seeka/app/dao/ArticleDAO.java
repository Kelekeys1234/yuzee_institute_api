package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Article;
import com.seeka.app.bean.Category;
import com.seeka.app.bean.SubCategory;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SubCategoryDto;

@Repository
@SuppressWarnings("unchecked")
public class ArticleDAO implements IArticleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Article> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Article> list = session.createCriteria(Article.class).list();
        return list;
    }

    @Override
    public List<Article> getArticlesByLookup(PageLookupDto pageLookupDto) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select A.*,count(1) over () totalRows from  (select id,heading,content,url,imagePath,created_at from "
                        + "seeka_articles with(nolock) ) A order by A.created_at desc";
        sqlQuery += " OFFSET (" + pageLookupDto.getPageNumber() + "-1)*" + pageLookupDto.getMaxSizePerPage() + " ROWS FETCH NEXT " + pageLookupDto.getMaxSizePerPage()
                        + " ROWS ONLY";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Article> list = new ArrayList<Article>();
        Article obj = null;
        for (Object[] row : rows) {
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

    @Override
    public Article findById(UUID uId) {
        Article article = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            article = session.get(Article.class, uId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public void deleteArticle(Article article) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Article> fetchAllArticleByPage(Integer page, Integer size, String queryValue, boolean status) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.active = '"
                        + status + "' and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";

        sqlQuery = sqlQuery + " OFFSET (" + page + ")*" + size + " ROWS FETCH NEXT " + size + " ROWS ONLY";

        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Article> articles = new ArrayList<Article>();
        for (Object[] row : rows) {
            Article article = new Article();
            article.setId(UUID.fromString((String.valueOf(row[0]))));
            article.setHeading(String.valueOf(row[1]));
            article.setContent(String.valueOf(row[2]));
            article.setImagePath(String.valueOf(row[3]));
            article.setCreatedAt((Date) row[6]);
            if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
                article.setCategory(session.get(Category.class, UUID.fromString((String.valueOf(row[7])))));

            }
            if (String.valueOf(row[8]) != null) {
                article.setSubCategory(UUID.fromString((String.valueOf(row[8]))));
                SubCategory subCategory = session.get(SubCategory.class, UUID.fromString((String.valueOf(row[8]))));
                SubCategoryDto subCategoryDto = null;
                if (subCategory != null) {
                    subCategoryDto = new SubCategoryDto();
                    subCategoryDto.setId(subCategory.getId());
                    subCategoryDto.setName(subCategory.getName());
                }
                article.setSubCategoryDropDownDto(subCategoryDto);
            }

            article.setLink(String.valueOf(row[9]));
            if (String.valueOf(row[11]) != null && !String.valueOf(row[11]).equals("null")) {
                article.setCountry(UUID.fromString((String.valueOf(row[11]))));
            }
            if (String.valueOf(row[12]) != null && !String.valueOf(row[12]).equals("null")) {
                article.setCity(UUID.fromString((String.valueOf(row[12]))));
            }
            if (String.valueOf(row[13]) != null && !String.valueOf(row[13]).equals("null")) {
                article.setInstitute(UUID.fromString((String.valueOf(row[13]))));
            }
            if (String.valueOf(row[14]) != null && !String.valueOf(row[14]).equals("null")) {
                article.setCourses(UUID.fromString((String.valueOf(row[14]))));
            }
            article.setGender(String.valueOf(row[15]));
            articles.add(article);
        }
        return articles;
    }

    @Override
    public int findTotalCount() {
        boolean status = true;
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sa.id from seeka_articles sa where sa.active = '" + status + "' and sa.deleted_on IS NULL";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    @Override
    public void save(Article article) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(article);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
