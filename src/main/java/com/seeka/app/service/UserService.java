package com.seeka.app.service;import java.math.BigInteger;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserInfo;
import com.seeka.app.dao.IUserDAO;
import com.seeka.app.enumeration.SignUpType;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	IUserDAO userDAO;

	@Override
	public void save(UserInfo user) {
		userDAO.save(user);
	}
	
	@Override
	public void update(UserInfo user) {
		userDAO.update(user);
	}
	
	@Override
	public UserInfo get(BigInteger userId) {
		return userDAO.get(userId);
	}
	
	@Override
	public UserInfo getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}
	
	@Override
	public UserInfo getUserBySocialAccountId(String accountId, SignUpType signUpType) {
		return userDAO.getUserBySocialAccountId(accountId,signUpType);
	}
	
}
