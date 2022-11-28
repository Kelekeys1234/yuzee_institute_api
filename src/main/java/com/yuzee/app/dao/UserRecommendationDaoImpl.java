package com.yuzee.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Course;

@Repository
public class UserRecommendationDaoImpl implements UserRecommendationDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Course> getRecommendCourse(final String facultyId, final String instituteId, final String countryId,
			final String cityId, final Double price, final Double variablePrice, final int pageSize,
			final List<String> courseIds) {
		return getRelatedCourse(facultyId, instituteId, countryId, cityId, price, variablePrice, pageSize, courseIds,
				null);
	}

	@Override
	public List<Course> getRelatedCourse(final String facultyId, final String instituteId, final String countryId,
			final String cityId, final Double price, final Double variablePrice, final int pageSize,
			final List<String> courseIds, final String courseName) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		if (instituteId != null) {
			query.addCriteria(
					org.springframework.data.mongodb.core.query.Criteria.where("instituteId").is(instituteId));
		}

		if (price != null) {
			Double low = price - variablePrice;
			if (low < 0) {
				low = Double.valueOf(0);
			}
			Double high = price + variablePrice;
			org.springframework.data.mongodb.core.query.Criteria criteria = new org.springframework.data.mongodb.core.query.Criteria();
			criteria.andOperator(
					org.springframework.data.mongodb.core.query.Criteria.where("usdInternationalApplicationFee")
							.gte(low),
					org.springframework.data.mongodb.core.query.Criteria.where("usdInternationalApplicationFee")
							.lt(high));
			query.with(Sort.by(Sort.Direction.ASC, "usdInternationalApplicationFee"));
		}
		if (courseIds != null && !courseIds.isEmpty()) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(courseIds));
		}

		if (courseName != null) {
			query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("name").is(courseName));
		}

		Pageable pagabe = PageRequest.of(pageSize, pageSize);
		query.with(pagabe);

		return mongoTemplate.find(query, Course.class, "course");
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Course> getCourseNoResultRecommendation(final String facultyId, final String countryId,
			final List<String> courseIds, final Integer startIndex, final Integer pageSize) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();

		if (facultyId != null) {
			query.addCriteria(Criteria.where("faculty.id").is(facultyId));
		}

		if (courseIds != null && !courseIds.isEmpty()) {
			query.addCriteria(Criteria.where("id").is(courseIds));
		}
		if (startIndex != null && pageSize != null) {
			Pageable page = PageRequest.of(startIndex, pageSize);
			query.with(page);
		}
		return mongoTemplate.find(query, Course.class, "course");
	}

	@Override
	public List<Course> getCheapestCourse(final String facultyId, final String countryId, final String levelId,
			final String cityId, final List<String> courseIds, final Integer startIndex, final Integer pageSize) {
	Query query = new Query();
		

		if (!StringUtils.isEmpty(facultyId)) {
			query.addCriteria(Criteria.where("faculty.id").is(facultyId));
		}

		if (!StringUtils.isEmpty(levelId)) {
			query.addCriteria(Criteria.where("level.id").is(levelId));
		}

		if (!CollectionUtils.isEmpty(courseIds)) {
			query.addCriteria(Criteria.where("id").is(courseIds));
		}
	  Pageable page = PageRequest.of(startIndex,pageSize);
		List<Course> rows = mongoTemplate.find(query, Course.class, "course");
		List<Course> courses = new ArrayList<>();
		for (Course row : rows) {
			Course course = new Course();
			course.setId(row.getId());
			course.setName(row.getName());
			course.setWorldRanking(row.getWorldRanking());
			course.setStars(row.getStars());
			course.setAvailabilty(row.getAvailabilty());
			course.setCurrency(row.getCurrency());
			course.setWebsite(row.getWebsite());
			course.setRecognition(row.getRecognition());
			course.setDescription(row.getDescription());
			courses.add(course);
		}
		return courses;
	}
}
