package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;

public interface UserRecommendationDao {

	List<Course> getRecommendCourse(BigInteger facultyId, BigInteger instituteId, BigInteger countryId, BigInteger cityId, Double price, Double variablePrice,
			int pageSize, List<BigInteger> courseIds);

	List<Course> getRelatedCourse(BigInteger facultyId, BigInteger instituteId, BigInteger countryId, BigInteger cityId, Double price, Double variablePrice,
			int pageSize, List<BigInteger> courseIds, String courseName);

	List<Course> getCourseNoResultRecommendation(BigInteger facultyId, BigInteger countryId, List<BigInteger> courseIds, Integer startIndex, Integer pageSize);

	List<Course> getCheapestCourse(BigInteger facultyId, BigInteger countryId, BigInteger levelId, BigInteger cityId, List<BigInteger> courseIds,
			Integer startIndex, Integer pageSize);

}
