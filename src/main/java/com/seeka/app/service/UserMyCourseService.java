package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.dao.IUserMyCourseDAO;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
@Service
@Transactional
public class UserMyCourseService implements IUserMyCourseService {

	@Autowired
	private IUserMyCourseDAO iUserMyCourseDAO;

	@Autowired
	private ICourseService iCourseService;

	@Override
	public void createUserMyCourse(final UserCourseRequestDto courseRequestDto) throws ValidationException {
		UserMyCourse existingUserMyCourse = iUserMyCourseDAO.getDataByUserIDAndCourseID(courseRequestDto.getUserId(), courseRequestDto.getCourseId());
		Date now = new Date();
		if (existingUserMyCourse != null) {
			existingUserMyCourse.setUpdatedBy("API");
			existingUserMyCourse.setUpdatedOn(now);
			iUserMyCourseDAO.save(existingUserMyCourse);
		} else {
			Course existingCourse = iCourseService.getCourseData(courseRequestDto.getCourseId());
			if (existingCourse == null) {
				throw new ValidationException("Course not found for Id : " + courseRequestDto.getCourseId());
			}
			UserMyCourse userMyCourse = new UserMyCourse();
			Course course = new Course();
			course.setId(courseRequestDto.getCourseId());
			userMyCourse.setCourse(course);
			userMyCourse.setUserId(courseRequestDto.getUserId());
			userMyCourse.setIsActive(true);
			userMyCourse.setCreatedBy("API");
			userMyCourse.setCreatedOn(now);
			userMyCourse.setUpdatedBy("API");
			userMyCourse.setUpdatedOn(now);
			iUserMyCourseDAO.save(userMyCourse);
		}
	}

	@Override
	public void save(final UserMyCourse reviewObj) {
		iUserMyCourseDAO.save(reviewObj);
	}

	@Override
	public void update(final UserMyCourse reviewObj) {
		iUserMyCourseDAO.update(reviewObj);
	}

	@Override
	public UserMyCourse get(final BigInteger userId) {
		return iUserMyCourseDAO.get(userId);
	}

	@Override
	public UserMyCourse getDataByUserIDAndCourseID(final BigInteger userId, final String courseId) {
		return iUserMyCourseDAO.getDataByUserIDAndCourseID(userId, courseId);
	}

	@Override
	public List<UserMyCourse> getUserMyCourseByUserID(final BigInteger userId) {
		return iUserMyCourseDAO.getDataByUserID(userId);
	}

	@Override
	public List<BigInteger> getAllCourseIdsByUser(final BigInteger userId) {
		return iUserMyCourseDAO.getAllCourseIdsByUser(userId);
	}
}
