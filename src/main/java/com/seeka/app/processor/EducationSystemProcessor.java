package com.seeka.app.processor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.GradeDetails;
import com.seeka.app.bean.Subject;
import com.seeka.app.dao.EducationSystemDao;
import com.seeka.app.dao.GradeDao;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.dto.SubjectDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class EducationSystemProcessor {

	@Autowired
	private EducationSystemDao educationSystemDAO;

	@Autowired
	private GradeDao gradeDao;

	public List<EducationSystemDto> getEducationSystemsByCountryName(final String countryName) {
		log.debug("Inside getEducationSystemsByCountryId() method");
		List<EducationSystemDto> educationSystemDtos = new ArrayList<>();
		List<SubjectDto> subjectDtos = new ArrayList<>();
		log.info("Fetching education system from DB based on countryName = " + countryName);
		List<EducationSystem> educationSystems = educationSystemDAO.getEducationSystemsByCountryId(countryName);
		if (!CollectionUtils.isEmpty(educationSystems)) {
			log.info("Education system fetched from DB based on countryName");
			educationSystems.stream().forEach(educationSystem -> {
				log.info("Fetching education subject from DB based on educationSystemIn = "+educationSystem.getId());
				List<Subject> subjects = educationSystemDAO.getSubjectByEducationSystem(educationSystem.getId());
				if(!CollectionUtils.isEmpty(subjects)) {
					log.info("Subjects fetched from DB start iterating data");
					subjects.stream().forEach(subject -> {
						SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getSubjectName());
						subjectDtos.add(subjectDto);
					});
				}
				log.info("Adding values in esducation system DTO");
				EducationSystemDto educationSystemDto = new EducationSystemDto(educationSystem.getId(),
						educationSystem.getCountryName(), educationSystem.getName(), educationSystem.getCode(),
						educationSystem.getDescription(), educationSystem.getStateName(), subjectDtos, null);
				educationSystemDtos.add(educationSystemDto);

			});
		}
		return educationSystemDtos;
	}

	public void saveEducationSystems(final EducationSystemDto educationSystem) throws ValidationException, NotFoundException {
		log.debug("Inside saveEducationSystems() method");
		if (!ObjectUtils.isEmpty(educationSystem) && !StringUtils.isEmpty(educationSystem.getId())) {
			log.info("Education system Id found in request, hence Fetching education system from"
					+ "	DB based on educationSystemId = " + educationSystem.getId());
			EducationSystem educationSystemFromDB = educationSystemDAO.get(educationSystem.getId());
			if (educationSystemFromDB != null) {
				log.info("Education system fetched from DB, going to update existing educationSystem");
				if (!StringUtils.isEmpty(educationSystem.getCountryName())) {
					educationSystemFromDB.setUpdatedBy("API");
					educationSystemFromDB.setUpdatedOn(new Date());
					educationSystemFromDB.setName(educationSystem.getName());
					educationSystemFromDB.setDescription(educationSystem.getDescription());
					educationSystemFromDB.setCode(educationSystem.getCode());
					educationSystemFromDB.setCountryName(educationSystem.getCountryName());
				} else {
					log.error("CountryName is required in request");
					throw new ValidationException("CountryName is required in request");
				}
			} else {
				log.error("Education System not found for educationSystemId = " + educationSystem.getId());
				throw new NotFoundException(
						"Education System not found for educationSystemId = " + educationSystem.getId());
			}
		} else {
			log.info("Education system Id not found in request, hencce going to save new education system in DB");
			if (!StringUtils.isEmpty(educationSystem.getCountryName())) {
				log.info("Start adding new educationSystem in DB");
				EducationSystem system = new EducationSystem();
				system.setCode(educationSystem.getCode());
				system.setCountryName(educationSystem.getCountryName());
				system.setCreatedBy("API");
				system.setCreatedOn(new Date());
				system.setDescription(educationSystem.getDescription());
				system.setIsActive(true);
				system.setName(educationSystem.getName());
				educationSystemDAO.save(system);
			} else {
				log.error("CountryName is required in request");
				throw new ValidationException("CountryName is required in request");
			}
		}
	}

	public Double calculateGrade(final GradeDto gradeDto) {
		log.debug("Inside calculateGrade() method");
		Double averageGpa = 0.0;
		try {
			averageGpa = calculateGpa(gradeDto);
		} catch (Exception exception) {
			log.error("Exception while calculating grade having exception = "+exception);
		}
		return averageGpa;
	}

	private Double calculateGpa(final GradeDto gradeDto) {
		log.info("Calculating GPA on basis of globalGPA and grade");
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Double averageGpa = null;
		Double gpaGrade = 0.0;
		List<String> gpaGrades = new ArrayList<>();
		for (String grade : gradeDto.getSubjectGrades()) {
			log.info("Fetching grade details from DB having countryName = "+ gradeDto.getCountryName() +
					" and systemId = " + gradeDto.getEducationSystemId() + "and grade = "+grade);
			gpaGrades.add(gradeDao.getGradeDetails(gradeDto.getCountryName(), gradeDto.getEducationSystemId(), grade));
		}
		for (String grade : gpaGrades) {
			gpaGrade = gpaGrade + Double.valueOf(grade);
		}
		averageGpa = gpaGrade / gpaGrades.size();
		if (averageGpa != null) {
			averageGpa = Double.valueOf(decimalFormat.format(averageGpa));
		}
		return averageGpa;
	}

	public List<GradeDto> getGrades(final String countryName, final String systemId) {
		log.debug("Inside getGrades() method");
		List<GradeDto> gradeDtos = new ArrayList<>();
		log.info("Fetching Grade details from DB having countryName =" + countryName + " and systemId ="+systemId);
		List<GradeDetails> grades = gradeDao.getGrades(countryName, systemId);
		 if(!CollectionUtils.isEmpty(grades)) {
			 log.info("Grade details fetched from DB, start iterating data to make final response");
			 grades.stream().forEach(grade -> {
				 GradeDto gradeDto = new GradeDto(grade.getId(), grade.getCountryName(), grade.getEducationSystemId(), null ,
						 grade.getGrade(), grade.getGpaGrade());
				 gradeDtos.add(gradeDto);
			 });
		 }
		return gradeDtos;
	}
	
	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) {
		return educationSystemDAO.getEducationSystemByCountryNameAndStateName(countryName, stateName);
	}

}
