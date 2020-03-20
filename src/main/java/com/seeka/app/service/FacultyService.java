package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Faculty;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.util.CDNServerUtil;

@Service
@Transactional
public class FacultyService implements IFacultyService {

	@Autowired
	private IFacultyDAO dao;

	@Override
	public void save(final Faculty obj) {
		dao.save(obj);
	}

	@Override
	public void update(final Faculty obj) {
		dao.update(obj);
	}

	@Override
	public Faculty get(final String id) {
		Faculty faculty = dao.get(id);
		if (!ObjectUtils.isEmpty(faculty)) {
			faculty.setIcon(CDNServerUtil.getFacultyIconUrl(faculty.getName()));
		}
		return faculty;
	}

	@Override
	public List<Faculty> getAll() {
		List<Faculty> faculties = dao.getAll();
		for (Faculty faculty : faculties) {
			faculty.setIcon(CDNServerUtil.getFacultyIconUrl(faculty.getName()));
		}
		return faculties;
	}

	@Override
	public List<Faculty> getFacultyByCountryIdAndLevelId(final String countryID, final String levelId) {
		return dao.getFacultyByCountryIdAndLevelId(countryID, levelId);
	}

	@Override
	public List<Faculty> getAllFacultyByCountryIdAndLevel() {
		return dao.getAllFacultyByCountryIdAndLevel();
	}

	@Override
	public List<Faculty> getFacultyByInstituteId(final String instituteId) {
		return dao.getFacultyByInstituteId(instituteId);
	}

	@Override
	public List<Faculty> getFacultyByListOfInstituteId(final String instituteId) {
		String[] citiesArray = instituteId.split(",");
		String tempList = "";
		for (String id : citiesArray) {
			tempList = tempList + "," + "'" + id + "'";
		}
		return dao.getFacultyByListOfInstituteId(tempList.substring(1, tempList.length()));
	}

	@Override
	public List<Faculty> getCourseFaculty(final String countryId, final String levelId) {
		return dao.getCourseFaculty(countryId, levelId);
	}

	@Override
	public List<Faculty> getFacultyListByName(final List<String> facultyNames) {
		return dao.getFacultyListByFacultyNames(facultyNames);
	}

}
