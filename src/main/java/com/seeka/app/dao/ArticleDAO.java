package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Category;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.bean.SubCategory;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SearchDto;
import com.seeka.app.dto.SubCategoryDto;

@Repository
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class ArticleDAO implements IArticleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SeekaArticles> getAll(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, final List<BigInteger> categoryId, List<String> tags, String status) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeekaArticles.class, "seeka_article");

		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		
		if(categoryId != null && !categoryId.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryId));
		}
		
		if(tags != null && !tags.isEmpty()) {
			criteria.add(Restrictions.in("tags", tags));
		}
		
		if(status != null) {
			criteria.add(Restrictions.in("published", status));
		}

		if ( sortByType != null) {
			if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc(sortByField));
				} else {
					criteria.addOrder(Order.desc(sortByField));
				}
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}

		return criteria.list();
	}

	@Override
	public List<SeekaArticles> getArticlesByLookup(final PageLookupDto pageLookupDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select id, heading, content, url, imagePath, created_at, category_id, subcategory_id from "
				+ "seeka_articles order by created_at desc";
		/*
		 * sqlQuery += " OFFSET (" + pageLookupDto.getPageNumber() + "-1)*" +
		 * pageLookupDto.getMaxSizePerPage() + " ROWS FETCH NEXT " +
		 * pageLookupDto.getMaxSizePerPage() + " ROWS ONLY";
		 */
		sqlQuery = sqlQuery + " LIMIT " + pageLookupDto.getPageNumber() + " ," + pageLookupDto.getMaxSizePerPage();
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();

		String totalQuery = "select count(*) from seeka_articles";
		Query query1 = session.createSQLQuery(totalQuery);
		List<Object[]> rows1 = query1.list();

		List<SeekaArticles> list = new ArrayList<>();
		SeekaArticles obj = null;
		for (Object[] row : rows) {
			obj = new SeekaArticles();
			obj.setId(new BigInteger(String.valueOf(row[0])));
			obj.setHeading(String.valueOf(row[1]));
			obj.setContent(String.valueOf(row[2]));
			obj.setUrl(String.valueOf(row[3]));
			obj.setImagepath(String.valueOf(row[4]));
//            obj.setTotalCount(rows1.size());

			if (String.valueOf(row[6]) != null && !String.valueOf(row[6]).equals("null")) {
				obj.setCategory(session.get(Category.class, new BigInteger(String.valueOf(row[6]))));

			}
			if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
				SubCategory subCategory = session.get(SubCategory.class, new BigInteger(String.valueOf(row[7])));
				SubCategoryDto subCategoryDto = null;
				if (subCategory != null) {
					subCategoryDto = new SubCategoryDto();
					subCategoryDto.setId(subCategory.getId());
					subCategoryDto.setName(subCategory.getName());
				}
				obj.setSubCategoryDropDownDto(subCategoryDto);
			}
			list.add(obj);
		}
		return list;
	}

	@Override
	public SeekaArticles findById(final BigInteger uId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(SeekaArticles.class, uId);
	}

	@Override
	public SeekaArticles deleteArticle(final SeekaArticles article) {
		Session session = sessionFactory.getCurrentSession();
		session.update(article);
		return article;

	}

	@Override
	public List<SeekaArticles> fetchAllArticleByPage(final BigInteger page, final BigInteger size, final String queryValue, final boolean status) {
		int statusType = 0;
		if (status) {
			statusType = 1;
		}
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.active = "
				+ statusType + " and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";
		sqlQuery = sqlQuery + " LIMIT " + page + " ," + size;
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SeekaArticles> articles = new ArrayList<>();
		for (Object[] row : rows) {
			SeekaArticles article = new SeekaArticles();
			article.setId(new BigInteger(String.valueOf(row[0])));
			article.setHeading(String.valueOf(row[1]));
			article.setContent(String.valueOf(row[2]));
			article.setImagepath(String.valueOf(row[3]));
			article.setCreatedAt((Date) row[6]);
			if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
				article.setCategory(session.get(Category.class, new BigInteger(String.valueOf(row[7]))));
			}
			if (String.valueOf(row[8]) != null && !String.valueOf(row[8]).equals("null")) {
				SubCategory subCategory = session.get(SubCategory.class, new BigInteger(String.valueOf(row[8])));
				SubCategoryDto subCategoryDto = null;
				if (subCategory != null) {
					subCategoryDto = new SubCategoryDto();
					subCategoryDto.setId(subCategory.getId());
					subCategoryDto.setName(subCategory.getName());
				}
				article.setSubCategoryDropDownDto(subCategoryDto);
			}

			article.setLink(String.valueOf(row[9]));
//            if (String.valueOf(row[11]) != null && !String.valueOf(row[11]).equals("null")) {
//                article.setCountry(new BigInteger((String.valueOf(row[11]))));
//            }
//            if (String.valueOf(row[12]) != null && !String.valueOf(row[12]).equals("null")) {
//                article.setCity(new BigInteger((String.valueOf(row[12]))));
//            }
//            if (String.valueOf(row[13]) != null && !String.valueOf(row[13]).equals("null")) {
//                article.setInstitute(new BigInteger((String.valueOf(row[13]))));
//            }
//            if (String.valueOf(row[14]) != null && !String.valueOf(row[14]).equals("null")) {
//                article.setCourses(new BigInteger((String.valueOf(row[14]))));
//            }
			// article.setGender(String.valueOf(row[15]));
			articles.add(article);
		}
		return articles;
	}

	@Override
	public int findTotalCount() {
		int status = 1;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from seeka_articles sa where sa.active = " + status + " and sa.deleted_on IS NULL";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public SeekaArticles save(final SeekaArticles article) {
		Session session = sessionFactory.getCurrentSession();
		session.save(article);
		return article;
	}

	@Override
	public void updateArticle(final BigInteger subCAtegory, final BigInteger id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "UPDATE seeka_articles SET subcategory_id = '" + subCAtegory + "' where id='" + id + "'";
			session.createSQLQuery(query).executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public List<SeekaArticles> searchArticle(final SearchDto articleDto) {
		boolean status;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = null;
		List<SeekaArticles> articles = new ArrayList<>();
		if (articleDto != null && articleDto.getCategory() != null && articleDto.getSubcategory() != null && articleDto.getStatus().equals("All")) {
			sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.category_id = '"
					+ articleDto.getCategory() + "' and sa.subcategory_id = '" + articleDto.getSubcategory()
					+ "' and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";
		} else if (articleDto != null && articleDto.getCategory() != null && articleDto.getSubcategory() != null && !articleDto.getStatus().equals("All")) {
			if (articleDto.getStatus().equals("1")) {
				status = true;
			} else {
				status = false;
			}
			sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.category_id = '"
					+ articleDto.getCategory() + "' and sa.subcategory_id = '" + articleDto.getSubcategory() + "' and sa.active = '" + status
					+ "'  and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";
		} else if (articleDto != null && articleDto.getSubcategory() == null && articleDto.getStatus().equals("All")) {
			sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.subcategory_id = '"
					+ articleDto.getSubcategory() + "' and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";
		} else if (articleDto != null && articleDto.getSubcategory() == null && !articleDto.getStatus().equals("All")) {
			if (articleDto.getStatus().equals("1")) {
				status = true;
			} else {
				status = false;
			}
			sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.subcategory_id = '"
					+ articleDto.getSubcategory() + "' and sa.active = '" + status + "'  and sa.deleted_on IS NULL ORDER BY sa.created_at DESC ";
		}
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			SeekaArticles article = new SeekaArticles();
			article.setId(new BigInteger(String.valueOf(row[0])));
			article.setHeading(String.valueOf(row[1]));
			article.setContent(String.valueOf(row[2]));
			article.setImagepath(String.valueOf(row[3]));
			article.setCreatedAt((Date) row[6]);
			if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
				article.setCategory(session.get(Category.class, new BigInteger(String.valueOf(row[7]))));

			}
			if (String.valueOf(row[8]) != null) {
				SubCategory subCategory = session.get(SubCategory.class, new BigInteger(String.valueOf(row[8])));
				SubCategoryDto subCategoryDto = null;
				if (subCategory != null) {
					subCategoryDto = new SubCategoryDto();
					subCategoryDto.setId(subCategory.getId());
					subCategoryDto.setName(subCategory.getName());
				}
				article.setSubCategoryDropDownDto(subCategoryDto);
			}

			article.setLink(String.valueOf(row[9]));
//            if (String.valueOf(row[11]) != null && !String.valueOf(row[11]).equals("null")) {
//                article.setCountry(new BigInteger((String.valueOf(row[11]))));
//            }
//            if (String.valueOf(row[12]) != null && !String.valueOf(row[12]).equals("null")) {
//                article.setCity(new BigInteger((String.valueOf(row[12]))));
//            }
//            if (String.valueOf(row[13]) != null && !String.valueOf(row[13]).equals("null")) {
//                article.setInstitute(new BigInteger((String.valueOf(row[13]))));
//            }
//            if (String.valueOf(row[14]) != null && !String.valueOf(row[14]).equals("null")) {
//                article.setCourses(new BigInteger((String.valueOf(row[14]))));
//            }
//            article.setGender(String.valueOf(row[15]));
			articles.add(article);
		}
		return articles;
	}

	@Override
	public List<SeekaArticles> articleByFilter(final String sqlQuery) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SeekaArticles> articles = new ArrayList<>();
		for (Object[] row : rows) {
			SeekaArticles article = new SeekaArticles();
			article.setId(new BigInteger(String.valueOf(row[0])));
			article.setHeading(String.valueOf(row[1]));
			article.setContent(String.valueOf(row[2]));
			article.setImagepath(String.valueOf(row[3]));
			article.setCreatedAt((Date) row[6]);
			if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
				article.setCategory(session.get(Category.class, new BigInteger(String.valueOf(row[7]))));
			}
			if (String.valueOf(row[8]) != null && !String.valueOf(row[8]).equals("null")) {
				SubCategory subCategory = session.get(SubCategory.class, new BigInteger(String.valueOf(row[8])));
				SubCategoryDto subCategoryDto = null;
				if (subCategory != null) {
					subCategoryDto = new SubCategoryDto();
					subCategoryDto.setId(subCategory.getId());
					subCategoryDto.setName(subCategory.getName());
				}
				article.setSubCategoryDropDownDto(subCategoryDto);
			}

			article.setLink(String.valueOf(row[9]));
//            if (String.valueOf(row[11]) != null && !String.valueOf(row[11]).equals("null")) {
//                article.setCountry(new BigInteger((String.valueOf(row[11]))));
//            }
//            if (String.valueOf(row[12]) != null && !String.valueOf(row[12]).equals("null")) {
//                article.setCity(new BigInteger((String.valueOf(row[12]))));
//            }
//            if (String.valueOf(row[13]) != null && !String.valueOf(row[13]).equals("null")) {
//                article.setInstitute(new BigInteger((String.valueOf(row[13]))));
//            }
//            if (String.valueOf(row[14]) != null && !String.valueOf(row[14]).equals("null")) {
//                article.setCourses(new BigInteger((String.valueOf(row[14]))));
//            }
//            article.setGender(String.valueOf(row[15]));
			articles.add(article);
		}
		return articles;
	}

	@Override
	public int findTotalCountBasedOnCondition(final String countQuery) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(countQuery);
		Query query = session.createSQLQuery(countQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<SeekaArticles> searchBasedOnNameAndContent(final String searchText) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id, sa.heading, sa.content, sa.imagepath, sa.active, sa.deleted_on, sa.created_at, sa.category_id, sa.subcategory_id, sa.link, sa.updated_at, sa.country, sa.city, sa.institute, sa.courses, sa.gender from seeka_articles sa where sa.active = 1"
				+ " and sa.deleted_on IS NULL and (sa.heading like '%" + searchText.trim() + "%' or sa.content like '%" + searchText.trim()
				+ "%') ORDER BY sa.created_at DESC ";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SeekaArticles> articles = new ArrayList<>();
		for (Object[] row : rows) {
			SeekaArticles article = new SeekaArticles();
			article.setId(new BigInteger(String.valueOf(row[0])));
			article.setHeading(String.valueOf(row[1]));
			article.setContent(String.valueOf(row[2]));
			article.setImagepath(String.valueOf(row[3]));
			article.setCreatedAt((Date) row[6]);
			if (String.valueOf(row[7]) != null && !String.valueOf(row[7]).equals("null")) {
				article.setCategory(session.get(Category.class, new BigInteger(String.valueOf(row[7]))));
			}
			if (String.valueOf(row[8]) != null && !String.valueOf(row[8]).equals("null")) {
				SubCategory subCategory = session.get(SubCategory.class, new BigInteger(String.valueOf(row[8])));
				SubCategoryDto subCategoryDto = null;
				if (subCategory != null) {
					subCategoryDto = new SubCategoryDto();
					subCategoryDto.setId(subCategory.getId());
					subCategoryDto.setName(subCategory.getName());
				}
				article.setSubCategoryDropDownDto(subCategoryDto);
			}

			article.setLink(String.valueOf(row[9]));
//            if (String.valueOf(row[11]) != null && !String.valueOf(row[11]).equals("null")) {
//                article.setCountry(new BigInteger((String.valueOf(row[11]))));
//            }
//            if (String.valueOf(row[12]) != null && !String.valueOf(row[12]).equals("null")) {
//                article.setCity(new BigInteger((String.valueOf(row[12]))));
//            }
//            if (String.valueOf(row[13]) != null && !String.valueOf(row[13]).equals("null")) {
//                article.setInstitute(new BigInteger((String.valueOf(row[13]))));
//            }
//            if (String.valueOf(row[14]) != null && !String.valueOf(row[14]).equals("null")) {
//                article.setCourses(new BigInteger((String.valueOf(row[14]))));
//            }
//            article.setGender(String.valueOf(row[15]));
			articles.add(article);
		}
		return articles;
	}

	@Override
	public Integer getTotalSearchCount(final Integer startIndex, final Integer pageSize, final String sortByField, final String sortByType,
			final String searchKeyword, List<BigInteger> categoryIdList, List<String> tagList, String status) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeekaArticles.class, "seeka_article");
		if (searchKeyword != null) {
			criteria.add(Restrictions.ilike("seeka_article.heading", searchKeyword, MatchMode.ANYWHERE));
		}
		if(categoryIdList != null && !categoryIdList.isEmpty()) {
			criteria.createAlias("category", "category");
			criteria.add(Restrictions.in("category.id", categoryIdList));
		}
		
		if(tagList != null && !tagList.isEmpty()) {
			criteria.add(Restrictions.in("tags", tagList));
		}
		
		if(status != null) {
			criteria.add(Restrictions.in("published", status));
		}
		
		if ( sortByType != null) {
			if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc(sortByField));
				} else {
					criteria.addOrder(Order.desc(sortByField));
				}
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		List<Object[]> rows = criteria.list();
		return rows.size();
	}
}
