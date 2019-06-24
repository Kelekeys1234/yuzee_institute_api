package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.dao.IUserEducationAOLevelSubjectDAO;

@Service
@Transactional
public class UserEducationAOLevelSubjectService implements IUserEducationAOLevelSubjectService {

	@Autowired
	IUserEducationAOLevelSubjectDAO dao;

	@Override
	public void save(UserEducationAOLevelSubjects hobbiesObj) {
		dao.save(hobbiesObj);
	}
	
	@Override
	public void update(UserEducationAOLevelSubjects hobbiesObj) {
		dao.update(hobbiesObj);
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(BigInteger userId) {
		return dao.getUserLevelSubjectGrades(userId);
	}
	
	@Override
	public UserEducationAOLevelSubjects get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(BigInteger userId){
		return dao.getActiveUserLevelSubjectGrades(userId);
	}
}
