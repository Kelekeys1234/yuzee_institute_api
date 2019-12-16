package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.UserViewData;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
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

	@Autowired
	private ICityDAO iCityDAO;

	@Autowired
	private ICountryDAO iCountryDAO;

	@Override
	public void createUserViewData(final UserViewDataRequestDto userViewDataRequestDto) {
		UserViewData userViewData = new UserViewData();
		BeanUtils.copyProperties(userViewDataRequestDto, userViewData);
		userViewData.setCreatedBy("API");
		userViewData.setCreatedOn(new Date());
		iViewDataDao.createUserViewData(userViewData);
	}

	@Override
	public List<UserViewData> getUserViewData(final BigInteger userId, final String entityType, final boolean isUnique, final Integer startIndex,
			final Integer pageSize) {
		List<UserViewData> resultList = new ArrayList<>();
		List<Object> objectList = iViewDataDao.getUserViewData(userId, entityType, isUnique, startIndex, pageSize);
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			UserViewData UserViewData = new UserViewData();
			UserViewData.setId((BigInteger) obj1[0]);
			UserViewData.setUserId((BigInteger) obj1[1]);
			UserViewData.setEntityId((BigInteger) obj1[2]);
			UserViewData.setEntityType((String) obj1[3]);
			UserViewData.setCreatedOn((Date) obj1[4]);
			UserViewData.setCreatedBy((String) obj1[5]);
			resultList.add(UserViewData);
		}
		return resultList;
	}

	@Override
	public List<CourseResponseDto> getUserViewDataCourse(final BigInteger userId, final boolean isUnique, final Integer startIndex, final Integer pageSize) {
		List<UserViewData> userViewDatas = getUserViewData(userId, "COURSE", isUnique, startIndex, pageSize);
		return iCourseDAO.getAllCoursesByFilter(new CourseSearchDto(), null, userViewDatas.stream().map(x -> x.getEntityId()).collect(Collectors.toList()),
				null, false);

	}

	@Override
	public List<BigInteger> getUserViewDataBasedOnEntityIdList(final BigInteger userId, final String entityType, final List<BigInteger> entityIds) {
		List<BigInteger> resultList = new ArrayList<>();
		List<Object> objectList = iViewDataDao.getUserViewDataBasedOnEntityIdList(userId, entityType, true, entityIds);
		for (Object object : objectList) {
			Object[] obj1 = (Object[]) object;
			resultList.add((BigInteger) obj1[0]);
		}
		return resultList;
	}

	@Override
	public int getUserViewDataCountBasedOnUserId(final BigInteger userId, final BigInteger entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
	}

	@Override
	public int getUserViewDataCountBasedOnEntityId(final BigInteger entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnEntityId(entityId, entityType);
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOncity(final String cityName, final Date fromDate, final Date toDate) throws ValidationException {
		City city = iCityDAO.getCityByName(cityName);
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
		if (city != null) {
			return iViewDataDao.userVisistedCourseBasedOncity(city.getId(), fromDate, toDate);
		} else {
			throw new ValidationException("Users city not found");
		}
	}

	@Override
	public List<UserCourseView> userVisistedCourseBasedOnCountry(final String countryName, final Date fromDate, final Date toDate) throws ValidationException {
		Country country = iCountryDAO.getCountryByName(countryName);
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
		if (country != null) {
			return iViewDataDao.userVisistedCourseBasedOnCountry(country.getId(), fromDate, toDate);
		} else {
			throw new ValidationException("Users country not found");
		}
	}

}
