package com.yuzee.app.dao;

import java.util.Optional;

import com.yuzee.app.bean.CourseCurriculum;

public interface CourseCurriculumDao {
	public Optional<CourseCurriculum> getById(String id);
}
