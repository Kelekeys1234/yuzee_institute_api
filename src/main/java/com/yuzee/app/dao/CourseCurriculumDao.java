package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.yuzee.app.bean.CourseCurriculum;

public interface CourseCurriculumDao {
	public Optional<CourseCurriculum> getById(String id);
	
	@Cacheable(value = "cacheCourseCurriculumList", unless = "#result == null")
	public List<CourseCurriculum> getAll();
}
