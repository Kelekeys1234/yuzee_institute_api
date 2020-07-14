package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dao.FacultyDao;
import com.yuzee.app.dto.FacultyDto;
import com.yuzee.app.util.CDNServerUtil;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class FacultyProcessor {

	@Autowired
	private FacultyDao facultyDAO;

	public void saveFaculty(final FacultyDto facultyDto) {
		log.debug("Inside saveFaculty() method");
		Faculty faculty = new Faculty();
		log.info("saving faculty data in DB having facultyName = "+facultyDto.getName());
		faculty.setName(facultyDto.getName());
		faculty.setIsActive(true);
		faculty.setCreatedBy("API");
		faculty.setCreatedOn(new Date());
		log.info("Calling DAO layer to save faculty data in DB");
		facultyDAO.saveOrUpdateFaculty(faculty);
	}

	public FacultyDto getFacultyById(final String id) {
		log.debug("Inside getFacultyById() method");
		FacultyDto facultyDto = new FacultyDto();
		log.info("Fetching faculty from DB having facultyId = "+id);
		Faculty faculty = facultyDAO.get(id);
		if (!ObjectUtils.isEmpty(faculty)) {
			log.info("Faculty found in DB hence making response");
			facultyDto.setName(faculty.getName());
			facultyDto.setId(faculty.getId());
			facultyDto.setIcon(CDNServerUtil.getFacultyIconUrl(faculty.getName()));
		}
		return facultyDto;
	}

	public List<FacultyDto> getAllFaculties() {
		log.debug("Inside getAllFaculties() method");
		List<FacultyDto> facultyDtos = new ArrayList<>();
		log.info("Fetching all faculties from DB");
		List<Faculty> facultiesFromDB = facultyDAO.getAll();
		if(!CollectionUtils.isEmpty(facultiesFromDB)) {
			log.info("FAculties fetched from DB start iterating to make response");
			facultiesFromDB.stream().forEach(faculty -> {
				FacultyDto facultyDto = new FacultyDto();
				facultyDto.setName(faculty.getName());
				facultyDto.setId(faculty.getId());
				facultyDto.setIcon(CDNServerUtil.getFacultyIconUrl(faculty.getName()));
				facultyDtos.add(facultyDto);
			});
		}
		return facultyDtos;
	}
	
	public FacultyDto getFacultyByFacultyName(String facultyName) {
		log.debug("Inside getFacultyByFacultyName() method");
		FacultyDto facultyDto = new FacultyDto();
		log.info("Fetching faculty from DB having facultyName = "+facultyName);
		Faculty facultyFromDB = facultyDAO.getFacultyByFacultyName(facultyName);
		if(!ObjectUtils.isEmpty(facultyFromDB)) {
			log.info("Faculty coming from DB hence start making response");
			facultyDto.setName(facultyFromDB.getName());
			facultyDto.setId(facultyFromDB.getId());
			try {
				facultyDto.setIcon(CDNServerUtil.getFacultyIconUrl(facultyFromDB.getName()));
			} catch (Exception exception) {
				log.error("Exception while fetching faculty icon URL = "+exception);
			}
		}
		return facultyDto;
	}

	public List<Faculty> getFacultyByCountryIdAndLevelId(final String countryID, final String levelId) {
		return facultyDAO.getFacultyByCountryIdAndLevelId(countryID, levelId);
	}

	public List<FacultyDto> getAllFacultyByCountryIdAndLevel() {
		return facultyDAO.getAllFacultyByCountryIdAndLevel();
	}

	public List<FacultyDto> getFacultyByInstituteId(final String instituteId) {
		return facultyDAO.getFacultyByInstituteId(instituteId);
	}

	public List<FacultyDto> getFacultyByListOfInstituteId(final String instituteId) {
		String[] citiesArray = instituteId.split(",");
		String tempList = "";
		for (String id : citiesArray) {
			tempList = tempList + "," + "'" + id + "'";
		}
		return facultyDAO.getFacultyByListOfInstituteId(tempList.substring(1, tempList.length()));
	}

	public List<FacultyDto> getCourseFaculty(final String countryId, final String levelId) {
		return facultyDAO.getCourseFaculty(countryId, levelId);
	}

	public List<Faculty> getFacultyListByName(final List<String> facultyNames) {
		return facultyDAO.getFacultyListByFacultyNames(facultyNames);
	}

	public List<String> getFacultyNameByInstituteId(String id) {
		return facultyDAO.getAllFacultyNamesByInstituteId(id);
	}
}
