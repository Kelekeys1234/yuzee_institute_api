package com.yuzee.app.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;

import com.yuzee.app.bean.Faculty;

public interface FacultyDao {

	public void saveOrUpdateFaculty(final Faculty obj);

	Optional<Faculty> get(String facultyId);

	public List<Faculty> getAll();

	public Faculty getFacultyByFacultyName(String facultyName);

	@Cacheable(value = "cacheFacultyMap", unless = "#result == null")
	Map<UUID, String> getFacultyNameIdMap();

	@Cacheable(value = "cacheFaculty", key = "#id", unless = "#result == null", condition = "#id!=null")
	Faculty getFaculty(final String id);

	// Faculty getFaculty(String id);
}
