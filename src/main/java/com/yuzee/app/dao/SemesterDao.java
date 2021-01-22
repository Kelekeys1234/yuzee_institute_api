package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Semester;
import com.yuzee.app.exception.ValidationException;

public interface SemesterDao {

	public List<Semester> saveAll(List<Semester> semesters) throws ValidationException;

	public Semester save(Semester semester) throws ValidationException;

	public Optional<Semester> getById(String id);

	public Page<Semester> getAll(Pageable pageable);
	
	public void delete(String id);

	public List<Semester> findByIdIn(List<String> ids);
}
