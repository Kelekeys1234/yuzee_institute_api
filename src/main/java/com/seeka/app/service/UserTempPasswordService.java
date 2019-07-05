package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserTempPassword;
import com.seeka.app.dao.IUserTempPasswordDAO;

@Service
@Transactional
public class UserTempPasswordService implements IUserTempPasswordService {

	@Autowired
	private IUserTempPasswordDAO iUserTempPasswordDAO;

	@Override
	public void save(UserTempPassword UserTempPassword) {
		iUserTempPasswordDAO.save(UserTempPassword);
	}
	
	@Override
	public void update(UserTempPassword UserTempPassword) {
		iUserTempPasswordDAO.update(UserTempPassword);
	}
	
	@Override
	public UserTempPassword get(BigInteger userId) {
		return iUserTempPasswordDAO.get(userId);
	}
	
	@Override
	public UserTempPassword getUserByEmail(String email) {
		return iUserTempPasswordDAO.getUserByEmail(email);
	}
	
	@Override
	public List<UserTempPassword> getActiveTempPasswordUserId(BigInteger userId){
		return iUserTempPasswordDAO.getActiveTempPasswordUserId(userId);
	}
	
	 
}
