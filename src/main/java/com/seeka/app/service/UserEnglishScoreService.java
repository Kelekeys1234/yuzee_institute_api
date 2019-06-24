package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserEnglishScore;
import com.seeka.app.dao.IUserEnglishScoreDAO;
 

@Service
@Transactional
public class UserEnglishScoreService implements IUserEnglishScoreService{
	
	@Autowired
	IUserEnglishScoreDAO dao;
	
	@Override
	public void save(UserEnglishScore obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(UserEnglishScore obj) {
		dao.update(obj);
	}
	
	@Override
	public List<UserEnglishScore> getAll() {		
		return dao.getAll();
	}
    
	@Override
	public UserEnglishScore get(BigInteger id) {
		return dao.get(id);
	}
	
	public List<UserEnglishScore> getEnglishEligibiltyByUserID(BigInteger userId){
		return dao.getEnglishEligibiltyByUserID(userId);
	}
	
}
