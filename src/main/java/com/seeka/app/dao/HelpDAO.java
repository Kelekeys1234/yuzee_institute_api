package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.exception.NotFoundException;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class HelpDAO implements IHelpDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final SeekaHelp seekaHelp) {
		Session session = sessionFactory.getCurrentSession();
		session.save(seekaHelp);
	}

	@Override
	public HelpCategory getHelpCategory(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		HelpCategory obj = session.get(HelpCategory.class, id);
		return obj;
	}

	@Override
	public HelpSubCategory getHelpSubCategory(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		HelpSubCategory obj = session.get(HelpSubCategory.class, id);
		return obj;
	}

	@Override
	public SeekaHelp get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		SeekaHelp obj = session.get(SeekaHelp.class, id);
		return obj;
	}

	@Override
	public void update(final SeekaHelp seekaHelp) {
		Session session = sessionFactory.getCurrentSession();
		session.update(seekaHelp);
	}

	@Override
	public int findTotalHelpRecord(final BigInteger userId, final Boolean isArchive) {
		int status = 1;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) from seeka_help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL";
		if (userId != null) {
			sqlQuery = sqlQuery + " and sa.user_id = " + userId;
		}
		if (isArchive != null) {
			sqlQuery = sqlQuery + " and sa.is_archive = " + isArchive;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		BigInteger count = (BigInteger) query.uniqueResult();
		return Integer.parseInt(String.valueOf(count));
	}

	@Override
	public List<SeekaHelp> getAll(final Integer startIndex, final Integer pageSize, final BigInteger userId, final Boolean isArchive) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
		if (userId != null) {
			crit.add(Restrictions.eq("seekaHelp.userId", userId));
		}
		crit.add(Restrictions.eq("seekaHelp.isActive", true));
		if (isArchive != null) {
			crit.add(Restrictions.eq("seekaHelp.isArchive", isArchive));
		}

		if ((startIndex != null) && (pageSize != null)) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		crit.addOrder(Order.desc("seekaHelp.isFavourite"));
		crit.addOrder(Order.desc("seekaHelp.createdOn"));
		return crit.list();

	}

	@Override
	public void save(final HelpCategory helpCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.save(helpCategory);
	}

	@Override
	public void save(final HelpSubCategory helpSubCategory) {
		Session session = sessionFactory.getCurrentSession();
		session.save(helpSubCategory);
	}

	@Override
	public List<HelpSubCategory> getSubCategoryByCategory(final BigInteger categoryId, final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HelpSubCategory.class, "helpSubCategory");
		crit.createAlias("helpSubCategory.categoryId", "helpCategory");
		crit.add(Restrictions.eq("helpCategory.id", categoryId));
		crit.add(Restrictions.eq("helpCategory.isActive", true));
		if ((startIndex != null) && (pageSize != null)) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();

	}

	@Override
	public List<SeekaHelp> getHelpByCategory(final BigInteger categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
		crit.createAlias("seekaHelp.category", "category");
		return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("isActive", true)).list();
	}

	@Override
	public Integer findTotalHelpRecordBySubCategory(final BigInteger sub_category_id) {
		int status = 1;
		Integer helpCount = null;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) from seeka_help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL and sub_category_id="
				+ sub_category_id;
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		BigInteger count = (BigInteger) query.uniqueResult();
		if (count != null) {
			helpCount = count.intValue();
		}
		return helpCount;
	}

	@Override
	public List<HelpSubCategory> getAllHelpSubCategories() {
		Session session = sessionFactory.getCurrentSession();
		List<HelpSubCategory> list = session.createCriteria(HelpSubCategory.class).list();
		return list;
	}

	@Override
	public HelpAnswer save(final HelpAnswer helpAnswer) {
		Session session = sessionFactory.getCurrentSession();
		session.save(helpAnswer);
		return helpAnswer;
	}

	@Override
	public List<HelpAnswer> getAnswerByHelpId(final BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HelpAnswer.class, "helpAnswer");
		crit.createAlias("helpAnswer.seekaHelp", "seekaHelp");
		return crit.add(Restrictions.eq("seekaHelp.id", userId)).list();
	}

	@Override
	public List<HelpCategory> getAllCategory(final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HelpCategory.class, "helpCategory");
		crit.add(Restrictions.eq("helpCategory.isActive", true));
		if ((startIndex != null) && (pageSize != null)) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@Override
	public List<SeekaHelp> findByStatus(final String status, final BigInteger categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
		return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("status", status)).add(Restrictions.eq("isActive", true)).list();
	}

	@Override
	public List<SeekaHelp> findByMostRecent(final String mostRecent, final BigInteger categoryId) {
		if ((mostRecent != null) && mostRecent.equals("asc")) {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
			return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("isActive", true)).addOrder(Order.asc("createdOn")).list();
		} else {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
			return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("isActive", true)).addOrder(Order.desc("createdOn")).list();
		}
	}

	@Override
	public List<SeekaHelp> findByStatusAndMostRecent(final String status, final String mostRecent, final BigInteger categoryId) {
		if ((mostRecent != null) && mostRecent.equals("asc")) {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
			return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("status", status)).add(Restrictions.eq("isActive", true))
					.addOrder(Order.asc("createdOn")).list();
		} else {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
			return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("status", status)).add(Restrictions.eq("isActive", true))
					.addOrder(Order.desc("createdOn")).list();
		}
	}

	@Override
	public void updateAnwser(final HelpAnswer helpAnswer) {
		Session session = sessionFactory.getCurrentSession();
		session.update(helpAnswer);
	}

	@Override
	public void setIsFavouriteFlag(final BigInteger id, final boolean isFavourite) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "update seeka_help set is_favourite = ? where id = ?";
		int updateCount = session.createNativeQuery(sqlQuery).setParameter(1, isFavourite).setParameter(2, id).executeUpdate();
		if (updateCount == 0) {
			throw new NotFoundException("No Help Found with Id : " + id);
		}

	}

	@Override
	public int getCategoryCount() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HelpCategory.class, "helpCategory");
		crit.add(Restrictions.eq("helpCategory.isActive", true));
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public int getSubCategoryCount(final BigInteger categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HelpSubCategory.class, "helpSubCategory");
		crit.createAlias("helpSubCategory.categoryId", "helpCategory");
		crit.add(Restrictions.eq("helpSubCategory.isActive", true));
		crit.add(Restrictions.eq("helpCategory.isActive", true));
		crit.add(Restrictions.eq("helpCategory.id", categoryId));
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public List<String> getRelatedSearchQuestions(final List<String> searchKeywords) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SeekaHelp.class, "seeka_help");
		Disjunction disjunction = Restrictions.disjunction();
		for (String string : searchKeywords) {
			Criterion keywordCriteria = Restrictions.ilike("seeka_help.descritpion", string, MatchMode.ANYWHERE);
			disjunction.add(keywordCriteria);
		}
		crit.add(disjunction);
		crit.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		crit.setMaxResults(3);
		crit.setProjection(Projections.property("seeka_help.title"));
		return crit.list();
	}

}
