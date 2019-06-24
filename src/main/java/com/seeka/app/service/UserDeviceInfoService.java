package com.seeka.app.service;import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserDeviceInfo;
import com.seeka.app.dao.IUserDeviceInfoDAO;

@Service
@Transactional
public class UserDeviceInfoService implements IUserDeviceInfoService {

	@Autowired
	IUserDeviceInfoDAO userDeviceInfoDAO;

	public void save(UserDeviceInfo userDeviceInfo) {
		userDeviceInfoDAO.save(userDeviceInfo);
	}

}
