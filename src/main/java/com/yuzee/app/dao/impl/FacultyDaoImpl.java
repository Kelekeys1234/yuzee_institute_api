package com.yuzee.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dao.FacultyDao;
import com.yuzee.app.repository.FacultyRepository;

@Component
public class FacultyDaoImpl implements FacultyDao {

	@Autowired
	private FacultyRepository facultyRepository;

	@Override
	public void saveOrUpdateFaculty(final Faculty obj) {
		facultyRepository.save(obj);
	}

	@Override
	public List<Faculty> getAll() {
		return facultyRepository.findAll();
	}

	@Override
	public Faculty getFacultyByFacultyName(String facultyName) {
		return facultyRepository.findByNameIgnoreCase(facultyName);
	}

	@Override
	public Map<UUID, String> getFacultyNameIdMap() {
		Map<UUID, String> facultyListMap = new HashMap<>();
		List<Faculty> faculties = facultyRepository.findAll();
		faculties.stream().forEach(faculty -> {
			facultyListMap.put(faculty.getId(), faculty.getName());
		});
		return facultyListMap;

	}

	@Override
	public Faculty getFaculty(String id) {
		Optional<Faculty> optFaculty = facultyRepository.findById(id);
		if (optFaculty.isPresent()) {
			return optFaculty.get();
		}
		return null;
	}

	@Override
	public Optional<Faculty> get(String facultyId) {
		// TODO Auto-generated method stub
		return facultyRepository.findById(facultyId);
	}

}
