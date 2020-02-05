package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserEducationAOLevelSubjects;

public interface IUserEducationAOLevelSubjectDAO {
	public void save(UserEducationAOLevelSubjects hobbiesObj);
	public void update(UserEducationAOLevelSubjects hobbiesObj);
	public UserEducationAOLevelSubjects get(BigInteger id);
	public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(String userId);
	public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(BigInteger userId);
	
}
