package com.seeka.app.processor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.GradeDetails;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Subject;
import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEducationDetails;
import com.seeka.app.bean.UserEnglishScore;
import com.seeka.app.dao.EducationSystemDao;
import com.seeka.app.dao.UserEducationAOLevelSubjectDAO;
import com.seeka.app.dao.UserEducationDetailDAO;
import com.seeka.app.dao.UserEnglishScoreDAO;
import com.seeka.app.dto.EducationAOLevelSubjectDto;
import com.seeka.app.dto.EducationDetailRequestDto;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.dto.EnglishScoresDto;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.dto.SubjectDto;
import com.seeka.app.dto.UserEducationDetailResponseDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.IConstant;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class EducationSystemProcessor {

	@Autowired
	private EducationSystemDao educationSystemDAO;

	@Autowired
	private UserEducationDetailDAO educationDetailDAO;

	@Autowired
	private UserEnglishScoreDAO englishScoreDAO;

	@Autowired
	private UserEducationAOLevelSubjectDAO educationAOLevelSubjectDAO;

	@Autowired
	private LevelProcessor levelProcessor;
	
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

	
	public EducationSystemResponse getEducationSystemsDetailByUserId(final String userId) {
		EducationSystemResponse systemResponse = new EducationSystemResponse();
		UserEducationDetails educationDetails = educationDetailDAO.getUserEducationDetails(userId);
		systemResponse.setUserId(userId);
		if (educationDetails == null) {
			return systemResponse;
		}

		UserEducationDetailResponseDto userEducationDetailResponseDto = new UserEducationDetailResponseDto();
		BeanUtils.copyProperties(educationDetails, userEducationDetailResponseDto);
		//Country country = educationDetails.getCountry();
		EducationSystem educationSystem = educationDetails.getEducationSystem();
		Level level = educationDetails.getLevel();
		//userEducationDetailResponseDto.setEducationCountryId(country.getId());
		userEducationDetailResponseDto.setEducationCountryName(educationDetails.getCountryName());
		userEducationDetailResponseDto.setEducationLevelId(level.getId());
		userEducationDetailResponseDto.setEduLevel(level.getName());
		userEducationDetailResponseDto.setEducationSystemId(educationSystem.getId());
		userEducationDetailResponseDto.setEducationSystemName(educationSystem.getName());

		systemResponse.setEducationDetail(userEducationDetailResponseDto);
		systemResponse.setEnglishScoresList(englishScoreDAO.getEnglishEligibiltyByUserID(userId));
		List<UserEducationAOLevelSubjects> educationAOLevelSubjects = educationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId);
		List<Subject> subjectList = educationSystemDAO.getSubject();
		Map<String, String> subjectMap = new HashMap<>();
		for (Subject subject : subjectList) {
			subjectMap.put(subject.getId(), subject.getSubjectName());
		}
		for (UserEducationAOLevelSubjects userEducationAOLevelSubjects : educationAOLevelSubjects) {
			userEducationAOLevelSubjects.setSubjectName(subjectMap.get(userEducationAOLevelSubjects.getSubjectId()));
		}

		systemResponse.setEducationAOLevelSubjectList(educationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId));

		return systemResponse;
	}

	
	public ResponseEntity<?> deleteEducationSystemDetailByUserId(final String userId) {
		Map<String, Object> response = new HashMap<>();
		Date now = new Date();
		try {
			UserEducationDetails educationDetails = educationDetailDAO.getUserEducationDetails(userId);
			if (educationDetails != null) {
				educationDetails.setIsActive(false);
				educationDetails.setUpdatedOn(now);
				educationDetails.setUpdatedBy(educationDetails.getCreatedBy());
				educationDetails.setDeletedOn(now);
				educationDetailDAO.update(educationDetails);
				response.put("message", "Education details deleted successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", IConstant.EDUCATION_NOT_FOUND);
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return ResponseEntity.ok().body(response);
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
			gpaGrades.add(educationDetailDAO.getGradeDetails(gradeDto.getCountryName(), gradeDto.getEducationSystemId(), grade));
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
		List<GradeDetails> grades = educationDetailDAO.getGrades(countryName, systemId);
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

	
	public void saveUserEducationDetails(final EducationSystemRequest educationSystemRequest) {
		UserEducationDetails userEducationDetails = null;
		UserEducationDetails existingUserEducationDetails = educationDetailDAO.getUserEducationDetails(educationSystemRequest.getUserId());
		if (existingUserEducationDetails != null) {
			userEducationDetails = getUpdatedUserEducationDetails(educationSystemRequest, existingUserEducationDetails, true);
			educationDetailDAO.update(userEducationDetails);
		} else {
			userEducationDetails = getUpdatedUserEducationDetails(educationSystemRequest, new UserEducationDetails(), false);
			educationDetailDAO.save(userEducationDetails);
		}
		englishScoreDAO.deleteEnglishScoreByUserId(educationSystemRequest.getUserId());
		if (educationSystemRequest.getEnglishScoresList() != null && !educationSystemRequest.getEnglishScoresList().isEmpty()) {
			saveEnglishScore(educationSystemRequest);
		}
		educationAOLevelSubjectDAO.deleteEducationAOLevelByUserId(educationSystemRequest.getUserId());
		if (educationSystemRequest.getEducationAOLevelSubjectList() != null && !educationSystemRequest.getEducationAOLevelSubjectList().isEmpty()) {
			saveEducationAOLevelSubject(educationSystemRequest);
		}
	}

	private UserEducationDetails getUpdatedUserEducationDetails(final EducationSystemRequest educationSystemDetails,
			final UserEducationDetails existingUserEducationDetails, final boolean status) {
		Date now = new Date();
		if (educationSystemDetails.getEducationDetail() != null) {
			EducationDetailRequestDto educationDetailRequestDto = educationSystemDetails.getEducationDetail();
			BeanUtils.copyProperties(educationDetailRequestDto, existingUserEducationDetails);
			if (educationDetailRequestDto.getEducationCountryId() != null) {
				existingUserEducationDetails.setCountryName(educationDetailRequestDto.getEducationCountryId());
			}
			if (educationDetailRequestDto.getEducationSystemId() != null) {
				existingUserEducationDetails.setEducationSystem(educationSystemDAO.get(educationDetailRequestDto.getEducationSystemId()));
			}
			if (educationDetailRequestDto.getEducationLevelId() != null) {
				existingUserEducationDetails.setLevel(levelProcessor.get(educationDetailRequestDto.getEducationLevelId()));
			}
		}
		if (status) {
			existingUserEducationDetails.setUpdatedBy(educationSystemDetails.getUpdatedBy());
			existingUserEducationDetails.setUpdatedOn(now);
		} else {
			existingUserEducationDetails.setCreatedBy(educationSystemDetails.getCreatedBy());
			existingUserEducationDetails.setCreatedOn(now);
			existingUserEducationDetails.setUpdatedBy(educationSystemDetails.getUpdatedBy());
			existingUserEducationDetails.setUpdatedOn(now);
			existingUserEducationDetails.setIsActive(true);
		}

		existingUserEducationDetails.setUserId(educationSystemDetails.getUserId());
		return existingUserEducationDetails;
	}

	private void saveEducationAOLevelSubject(final EducationSystemRequest educationSystemDetails) {
		Date now = new Date();
		for (EducationAOLevelSubjectDto dto : educationSystemDetails.getEducationAOLevelSubjectList()) {
			UserEducationAOLevelSubjects levelSubjects = new UserEducationAOLevelSubjects();
			levelSubjects.setSubjectId(dto.getSubjectId());
			levelSubjects.setGrade(dto.getGrade());
			levelSubjects.setCreatedBy("API");
			levelSubjects.setCreatedOn(now);
			levelSubjects.setUpdatedBy("API");
			levelSubjects.setUpdatedOn(now);
			levelSubjects.setIsActive(true);
			levelSubjects.setUserId(educationSystemDetails.getUserId());
			educationAOLevelSubjectDAO.save(levelSubjects);
		}
	}

	private void saveEnglishScore(final EducationSystemRequest educationSystemDetails) {
		Date now = new Date();
		for (EnglishScoresDto dto : educationSystemDetails.getEnglishScoresList()) {
			UserEnglishScore userEduIelTofScore = new UserEnglishScore();
			if (dto.getEnglishType() != null) {
				if (dto.getEnglishType().equals(EnglishType.IELTS.name())) {
					userEduIelTofScore.setEnglishType(EnglishType.IELTS);
				}
				if (dto.getEnglishType().equals(EnglishType.TOEFL.name())) {
					userEduIelTofScore.setEnglishType(EnglishType.TOEFL);
				}
			}
			userEduIelTofScore.setListening(dto.getListening());
			userEduIelTofScore.setOverall(dto.getOverall());
			userEduIelTofScore.setReading(dto.getReading());
			userEduIelTofScore.setSpeaking(dto.getSpeaking());
			userEduIelTofScore.setWriting(dto.getWriting());
			userEduIelTofScore.setUserId(educationSystemDetails.getUserId());
			userEduIelTofScore.setCreatedBy(educationSystemDetails.getCreatedBy());
			userEduIelTofScore.setCreatedOn(now);
			userEduIelTofScore.setUpdatedBy(educationSystemDetails.getUpdatedBy());
			userEduIelTofScore.setUpdatedOn(now);
			userEduIelTofScore.setIsActive(true);
			englishScoreDAO.save(userEduIelTofScore);
		}
	}

	
	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName) {
		return educationSystemDAO.getEducationSystemByCountryNameAndStateName(countryName, stateName);
	}

}
