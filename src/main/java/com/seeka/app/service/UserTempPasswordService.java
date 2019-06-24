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
	IUserTempPasswordDAO dao;

	@Override
	public void save(UserTempPassword UserTempPassword) {
		dao.save(UserTempPassword);
	}
	
	@Override
	public void update(UserTempPassword UserTempPassword) {
		dao.update(UserTempPassword);
	}
	
	@Override
	public UserTempPassword get(BigInteger userId) {
		return dao.get(userId);
	}
	
	@Override
	public UserTempPassword getUserByEmail(String email) {
		return dao.getUserByEmail(email);
	}
	
	@Override
	public List<UserTempPassword> getActiveTempPasswordUserId(BigInteger userId){
		return dao.getActiveTempPasswordUserId(userId);
	}
	
	 
}
