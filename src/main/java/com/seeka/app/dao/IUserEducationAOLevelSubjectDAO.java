package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.UserEducationAOLevelSubjects;

public interface IUserEducationAOLevelSubjectDAO {
	public void save(UserEducationAOLevelSubjects hobbiesObj);
	public void update(UserEducationAOLevelSubjects hobbiesObj);
	public UserEducationAOLevelSubjects get(String id);
	public List<UserEducationAOLevelSubjects> getUserLevelSubjectGrades(String userId);
	public List<UserEducationAOLevelSubjects> getActiveUserLevelSubjectGrades(String userId);
	
}
