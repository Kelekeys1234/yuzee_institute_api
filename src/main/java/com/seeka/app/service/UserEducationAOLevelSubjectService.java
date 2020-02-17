package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.dao.IUserEducationAOLevelSubjectDAO;

@Service
@Transactional
public class UserEducationAOLevelSubjectService implements IUserEducationAOLevelSubjectService {

	@Autowired
	private IUserEducationAOLevelSubjectDAO iUserEducationAOLevelSubjectDAO;

	@Override
	public void save(UserEducationAOLevelSubjects hobbiesObj) {
		iUserEducationAOLevelSubjectDAO.save(hobbiesObj);
	}
	
	@Override
	public void update(UserEducationAOLevelSubjects hobbiesObj) {
		iUserEducationAOLevelSubjectDAO.update(hobbiesObj);
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(String userId) {
		return iUserEducationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId);
	}
	
	@Override
	public UserEducationAOLevelSubjects get(String id) {
		return iUserEducationAOLevelSubjectDAO.get(id);
	}
	
	@Override
	public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(String userId){
		return iUserEducationAOLevelSubjectDAO.getActiveUserLevelSubjectGrades(userId);
	}
}
