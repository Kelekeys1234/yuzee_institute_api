package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.dao.IViewDao;
import com.seeka.app.dto.UserViewDataRequestDto;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ViewService implements IViewService {

	@Autowired
	private IViewDao iViewDataDao;

	@Override
	public void createUserViewData(final UserViewDataRequestDto userViewDataRequestDto) {
		UserViewData userViewData = new UserViewData();
		BeanUtils.copyProperties(userViewDataRequestDto, userViewData);
		userViewData.setCreatedBy("API");
		userViewData.setCreatedOn(new Date());
		iViewDataDao.createUserViewData(userViewData);
	}

	@Override
	public List<UserViewData> getUserViewData(final BigInteger userId, final String entityType, final boolean isUnique) {
		List<UserViewData> resultList = new ArrayList<>();
		List<Object> objectList = iViewDataDao.getUserViewData(userId, entityType, isUnique);
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
	public int getUserViewDataCountBasedOnUserId(final BigInteger userId, final BigInteger entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
	}

	@Override
	public int getUserViewDataCountBasedOnEntityId(final BigInteger entityId, final String entityType) {
		return iViewDataDao.getUserViewDataCountBasedOnEntityId(entityId, entityType);
	}

}
