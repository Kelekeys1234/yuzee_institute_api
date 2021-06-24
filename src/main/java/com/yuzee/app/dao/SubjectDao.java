package com.yuzee.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;

public interface SubjectDao {

	void saveSubjects(List<Subject> subjectList, List<EducationSystem> educationSystems);

	Page<Subject> findByNameContainingIgnoreCaseAndEducationSystemId(String name, String educationSystemId, Pageable pageable);
}
