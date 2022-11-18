package com.yuzee.app.dao.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Help;
import com.yuzee.app.bean.HelpAnswer;
import com.yuzee.app.bean.HelpCategory;
import com.yuzee.app.bean.HelpSubCategory;
import com.yuzee.app.dao.HelpDao;
import com.yuzee.app.repository.HelpAnswerRepository;
import com.yuzee.app.repository.HelpCategoryRepository;
import com.yuzee.app.repository.HelpRepository;
import com.yuzee.app.repository.HelpSubCategoryRepository;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.local.config.MessageTranslator;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class HelpDaoImpl implements HelpDao {

	@Autowired
	private HelpRepository helpRepository;

	@Autowired
	private HelpCategoryRepository helpCategoryRepository;

	@Autowired
	private HelpSubCategoryRepository helpSubCategoryRepository;

	@Autowired
	private MessageTranslator messageTranslator;

	@Autowired
	private MongoOperations mongoOperator;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private HelpAnswerRepository helpAnswerRepository;

	@Override
	public void save(Help help) {
		helpRepository.save(help);
	}

	@Override
	public HelpCategory getHelpCategory(final String id) {
		return helpCategoryRepository.findById(id).orElseGet(HelpCategory::new);
	}

	@Override
	public HelpSubCategory getHelpSubCategory(final String id) {
		return helpSubCategoryRepository.findById(id).orElseGet(HelpSubCategory::new);
	}

	@Override
	public Help get(final String id) {
		return helpRepository.findById(id).orElseGet(Help::new);
	}

	@Override
	public void update(final Help help) {
		helpRepository.save(help);
	}

	@Override
	public int findTotalHelpRecord(final String userId, final Boolean isArchive) {
		int status = 1;
//		Session session = sessionFactory.getCurrentSession();
//		String sqlQuery = "select count(*) from help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL";
//		if (userId != null) {
//			sqlQuery = sqlQuery + " and sa.user_id = '" + userId + "'";
//		}
//		if (isArchive != null) {
//			sqlQuery = sqlQuery + " and sa.is_archive = " + isArchive;
//		}
//		System.out.println(sqlQuery);
//		Query query = session.createSQLQuery(sqlQuery);
//		BigInteger count = (BigInteger) query.uniqueResult();
		Query query = new Query();
		// query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isArchive").is(status));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isArchive").is(isArchive));
		Long count = mongoOperator.count(query, Help.class, "Help");
		return Integer.parseInt(String.valueOf(count));
	}

	@Override
	public List<Help> getAll(final Integer startIndex, final Integer pageSize, final String userId,
			final Boolean isArchive) {
		Query query = new Query();
		if (userId != null) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		}
		if (isArchive != null) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isArchive").is(isArchive));
		}

		if ((startIndex != null) && (pageSize != null)) {
			Pageable pagable = PageRequest.of(startIndex, pageSize);
			query.with(pagable);
		}
		// crit.addOrder(Order.desc("help.isFavourite"));
		Sort sort = Sort.by(Sort.Direction.DESC, "isFavourite");
		Sort sorts = Sort.by(Sort.Direction.DESC, "createdOn");
		// crit.addOrder(Order.desc("help.createdOn"));
		query.with(sorts);
		query.with(sort);
		return mongoTemplate.find(query, Help.class, "help");

	}

	@Override
	public void save(HelpCategory helpCategory) {
		helpCategoryRepository.save(helpCategory);
	}

	@Override
	public void save(final HelpSubCategory helpSubCategory) {
		helpSubCategoryRepository.save(helpSubCategory);
	}

	@Override
	public List<HelpSubCategory> getSubCategoryByCategory(final String categoryId, final Integer startIndex,
			final Integer pageSize) {
		// Session session = sessionFactory.getCurrentSession();
		Query query = new Query();
		// Criteria crit = session.createCriteria(HelpSubCategory.class,
		// "helpSubCategory");
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("helpCategory.id").is(categoryId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
		if ((startIndex != null) && (pageSize != null)) {
			Pageable pageable = PageRequest.of(startIndex, pageSize);
			query.with(pageable);
		}
		return mongoTemplate.find(query, HelpSubCategory.class, "help_subcategory");

	}

	@Override
	public List<Help> getHelpByCategory(final String categoryId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
		return mongoTemplate.find(query, Help.class, "help");
	}

	@Override
	public Integer findTotalHelpRecordBySubCategory(final String sub_category_id) {
		int status = 1;
		Integer helpCount = null;
		// Session session = sessionFactory.getCurrentSession();
//		String sqlQuery = "select count(*) from help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL and sub_category_id='"
//				+ sub_category_id + "'";
		Query query = new Query();
		query.addCriteria(
				org.springframework.data.mongodb.core.query.Criteria.where("subCategory.id").is(sub_category_id));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
		Long count = mongoOperator.count(query, Help.class, "help");
		if (count != null) {
			helpCount = count.intValue();
		}
		return helpCount;
	}

	@Override
	public List<HelpSubCategory> getAllHelpSubCategories() {
		return helpSubCategoryRepository.findAll();
	}

	@Override
	public HelpAnswer save(final HelpAnswer helpAnswer) {
		return helpAnswerRepository.save(helpAnswer);
	}

	@Override
	public List<HelpAnswer> getAnswerByHelpId(final String userId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("help.id").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
		return mongoTemplate.find(query, HelpAnswer.class, "help_answer");
	}

	@Override
	public List<HelpCategory> getAllCategory(final Integer startIndex, final Integer pageSize) {
		// Session session = sessionFactory.getCurrentSession();
		Query query = new Query();

		if ((startIndex != null) && (pageSize != null)) {
			Pageable pageable = PageRequest.of(startIndex, pageSize);
			query.with(pageable);
		}
		return mongoTemplate.findAll(HelpCategory.class);
	}

	@Override
	public List<Help> findByStatus(final String status, final String categoryId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("status").is(status));
		return mongoTemplate.find(query, Help.class, "help");
	}

	@Override
	public List<Help> findByMostRecent(final String mostRecent, final String categoryId) {
		Query query = new Query();
		if ((mostRecent != null) && mostRecent.equals("asc")) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
			return mongoTemplate.find(query, Help.class, "help");
		} else {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
			return mongoTemplate.find(query, Help.class, "help");
		}
	}

	@Override
	public List<Help> findByStatusAndMostRecent(final String status, final String mostRecent, final String categoryId) {
		if ((mostRecent != null) && mostRecent.equals("asc")) {
			Query query = new Query();
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("status").is(status));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
			return mongoTemplate.find(query, Help.class,"help");
		} else {
			Query query = new Query();
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("status").is(status));
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("isActive").is(true));
			return mongoTemplate.find(query, Help.class,"help");
		}
		
	}

	@Override
	public void updateAnwser(final HelpAnswer helpAnswer) {
		helpAnswerRepository.save(helpAnswer);
	}

	@Override
	public void setIsFavouriteFlag(final String id, final boolean isFavourite) throws NotFoundException {
		Help help = helpRepository.findById(id).orElseGet(Help::new);
		help.setIsFavourite(isFavourite);
		helpRepository.save(help);
		if (ObjectUtils.isEmpty(help)) {
			throw new NotFoundException(messageTranslator.toLocale("help.not_found.id") + id);
		}

	}

	@Override
	public int getCategoryCount() {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("helpCategory.isActive").is(true));

		return ((int) mongoOperator.count(query, HelpCategory.class, "help_category"));
	}

	@Override
	public int getSubCategoryCount(final String categoryId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("helpCategory.isActive").is(true));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("helpCategory.id").is(categoryId));
		return ((int) mongoOperator.count(query, HelpCategory.class, "help_category"));
	}

	@Override
	public List<String> getRelatedSearchQuestions(final List<String> searchKeywords) {
		Query query = new Query();
		for (String string : searchKeywords) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("description").is(string));
		}
		List<Help> help = mongoTemplate.find(query, Help.class, "class");
		List<String> string = Arrays.asList(help.stream().map(e -> e.getDescription()).toString());
		return string;
	}

}
