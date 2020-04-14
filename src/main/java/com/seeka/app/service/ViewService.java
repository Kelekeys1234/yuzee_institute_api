package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.IViewDao;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.UserCourseView;
import com.seeka.app.dto.UserViewDataRequestDto;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ViewService implements IViewService {

	@Autowired
	private IViewDao iViewDataDao;

	@Autowired
	private ICourseDAO iCourseDAO;

	@Override
	public void createUserViewData(final UserViewDataRequestDto userViewDataRequestDto) {
		UserViewData userViewData = new UserViewData();
		BeanUtils.copyProperties(userViewDataRequestDto, userViewData);
		userViewData.setCreatedBy("API");
		userViewData.setCreatedOn(new Date());
		iViewDataDao.createUserViewData(userViewData);
	}

	@Override
	public List<UserViewData> getUserViewData(final String userId, final String entityType, final boolean isUnique, final Integer startIndex,
			final Integer pageSize) {
		List<UserViewData> resultList = new ArrayList<>();
		List<Object> objectList = iViewDataDao.getUserViewData(userId, entityType, isUnique, startIndex, pageSize);
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			UserViewData UserViewData = new UserViewData();
			UserViewData.setId(obj1[0].toString());
			UserViewData.setUserId((String) obj1[1]);
			UserViewData.setEntityId((String) obj1[2]);
			UserViewData.setEntityType((String) obj1[3]);
			UserViewData.setCreatedOn((Date) obj1[4]);
			UserViewData.setCreatedBy((String) obj1[5]);
			resultList.add(UserViewData);
		}
		return resultList;
	}

	@Override
	public List<CourseResponseDto> getUserViewDataCourse(final String userId, final boolean isUnique, final Integer startIndex, final Integer pageSize) {
		List<UserViewData> userViewDatas = getUserViewData(userId, "COURSE", isUnique, startIndex, pageSize);
		return iCourseDAO.getAllCoursesByFilter(new CourseSearchDto(), null, userViewDatas.stream().map(x -> x.getEntityId()).collect(Collectors.toList()),
				null, false);

	}

	@Override
	public List<String> getUserViewDataBasedOnEntityIdList(final String userId, final String entityType, final List<String> entityIds) {
		List<String> resultList = new ArrayList<>();
		List<Object> objectList = iViewDataDao.getUserViewDataBasedOnEntityIdList(userId, entityType, true, entityIds);
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			resultList.add((String) obj1[0]);
		}
		return resultList;
	}

	@Override
	public int getUserViewDataCountBasedOnUserId(final String userId, final String entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
	}

	@Override
	public int getUserViewDataCountBasedOnEntityId(final String entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnEntityId(entityId, entityType);
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOncity(final String cityName, final Date fromDate, final Date toDate) throws ValidationException {
		/**
		 * Find unique courses viewed by how many unique users based on city.
		 *
		 * It is not required that the user's citizenship must be the same city.
		 *
		 * For Example, someone is logged in with new york city, then we will display,
		 * how many users visited courses of new york irrespective of whether visited
		 * the user is lived anywhere in the world.
		 *
		 */
		if (cityName != null) {
			return iViewDataDao.userVisistedCourseBasedOncity(cityName, fromDate, toDate);
		} else {
			throw new ValidationException("Users city not found");
		}
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOnCountry(final String countryName, final Date fromDate, final Date toDate) throws ValidationException {

		/**
		 * Find unique courses viewed by how many unique users based on country.
		 *
		 * It is not required that the user's citizenship must be the same country.
		 *
		 * For Example, someone is logged in with USA country, then we will display, how
		 * many users visited courses of USA irrespective of whether visited the user is
		 * lived anywhere in the world.
		 *
		 */
		if (StringUtils.isEmpty(countryName)) {
			return iViewDataDao.userVisistedCourseBasedOnCountry(countryName, fromDate, toDate);
		} else {
			throw new ValidationException("Users country not found");
		}
	}
}
