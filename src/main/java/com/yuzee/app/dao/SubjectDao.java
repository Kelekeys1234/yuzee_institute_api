package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;

public interface SubjectDao {

	public void saveSubjects(List<Subject> subjectList , List<EducationSystem> educationSystems);
	
}
