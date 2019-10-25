package com.seeka.app.service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.GradeDetails;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Subject;
import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEducationDetails;
import com.seeka.app.bean.UserEnglishScore;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IEducationSystemDAO;
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
import com.seeka.app.dto.UserEducationDetailResponseDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class EducationSystemService implements IEducationSystemService {

	@Autowired
	private IEducationSystemDAO iEducationSystemDAO;

	@Autowired
	private ICountryDAO countryDAO;

	@Autowired
	private ICountryService iCountryService;

	@Autowired
	private UserEducationDetailDAO educationDetailDAO;

	@Autowired
	private UserEnglishScoreDAO englishScoreDAO;

	@Autowired
	private UserEducationAOLevelSubjectDAO educationAOLevelSubjectDAO;

	@Autowired
	private ILevelService iLevelService;

	@Override
	public void save(final EducationSystem hobbiesObj) {
		iEducationSystemDAO.save(hobbiesObj);
	}

	@Override
	public void update(final EducationSystem hobbiesObj) {
		iEducationSystemDAO.update(hobbiesObj);
	}

	@Override
	public List<EducationSystem> getAll() {
		return iEducationSystemDAO.getAll();
	}

	@Override
	public EducationSystem get(final BigInteger id) {
		return iEducationSystemDAO.get(id);
	}

	@Override
	public List<EducationSystem> getAllGlobeEducationSystems() {
		return iEducationSystemDAO.getAllGlobeEducationSystems();
	}

	@Override
	public List<EducationSystem> getEducationSystemsByCountryId(final BigInteger countryId) {
		List<EducationSystem> educationSystems = iEducationSystemDAO.getEducationSystemsByCountryId(countryId);
		for (EducationSystem educationSystem : educationSystems) {
			List<Subject> subjects = iEducationSystemDAO.getSubject();
			educationSystem.setSubjects(subjects);
		}
		return educationSystems;
	}

	@Override
	public ResponseEntity<?> saveEducationSystems(@Valid final EducationSystemDto educationSystem) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (educationSystem != null && educationSystem.getId() != null) {
				EducationSystem system = iEducationSystemDAO.get(educationSystem.getId());
				if (system != null) {
					if (educationSystem.getCountry() != null && educationSystem.getCountry().getId() != null) {
						system.setUpdatedBy(educationSystem.getUpdatedBy());
						system.setUpdatedOn(new Date());
						system.setName(educationSystem.getName());
						system.setDescription(educationSystem.getDescription());
						system.setCode(educationSystem.getCode());
						system.setCountry(countryDAO.get(educationSystem.getCountry().getId()));
						response.put("message", IConstant.EDUCATION_SUCCESS_UPDATE);
						response.put("status", HttpStatus.OK.value());
					} else {
						response.put("message", IConstant.COUNTY_NOT_FOUND);
						response.put("status", HttpStatus.NOT_FOUND.value());
					}
				} else {
					response.put("message", IConstant.EDUCATION_SYSTEM_NOT_FOUND);
					response.put("status", HttpStatus.NOT_FOUND.value());
				}
			} else {
				if (educationSystem.getCountry() != null && educationSystem.getCountry().getId() != null) {
					EducationSystem system = new EducationSystem();
					system.setCode(educationSystem.getCode());
					system.setCountry(countryDAO.get(educationSystem.getCountry().getId()));
					system.setCreatedBy(educationSystem.getCreatedBy());
					system.setCreatedOn(new Date());
					system.setDescription(educationSystem.getDescription());
					system.setIsActive(true);
					system.setName(educationSystem.getName());
					system.setUpdatedBy(educationSystem.getUpdatedBy());
					system.setUpdatedOn(new Date());
					iEducationSystemDAO.save(system);
					response.put("message", "Education system details added successfully");
					response.put("status", HttpStatus.OK.value());
				} else {
					response.put("message", IConstant.COUNTY_NOT_FOUND);
					response.put("status", HttpStatus.NOT_FOUND.value());
				}
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public EducationSystemResponse getEducationSystemsDetailByUserId(final BigInteger userId) {
		EducationSystemResponse systemResponse = new EducationSystemResponse();
		UserEducationDetails educationDetails = educationDetailDAO.getUserEducationDetails(userId);
		systemResponse.setUserId(userId);
		if (educationDetails == null) {
			return systemResponse;
		}

		UserEducationDetailResponseDto userEducationDetailResponseDto = new UserEducationDetailResponseDto();
		BeanUtils.copyProperties(educationDetails, userEducationDetailResponseDto);
		Country country = educationDetails.getCountry();
		EducationSystem educationSystem = educationDetails.getEducationSystem();
		Level level = educationDetails.getLevel();
		userEducationDetailResponseDto.setEducationCountryId(country.getId());
		userEducationDetailResponseDto.setEducationCountryName(country.getName());
		userEducationDetailResponseDto.setEducationLevelId(level.getId());
		userEducationDetailResponseDto.setEduLevel(level.getName());
		userEducationDetailResponseDto.setEducationSystemId(educationSystem.getId());
		userEducationDetailResponseDto.setEducationSystemName(educationSystem.getName());

		systemResponse.setEducationDetail(userEducationDetailResponseDto);
		systemResponse.setEnglishScoresList(englishScoreDAO.getEnglishEligibiltyByUserID(userId));
		List<UserEducationAOLevelSubjects> educationAOLevelSubjects = educationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId);
		List<Subject> subjectList = iEducationSystemDAO.getSubject();
		Map<BigInteger, String> subjectMap = new HashMap<>();
		for (Subject subject : subjectList) {
			subjectMap.put(subject.getId(), subject.getSubjectName());
		}
		for (UserEducationAOLevelSubjects userEducationAOLevelSubjects : educationAOLevelSubjects) {
			userEducationAOLevelSubjects.setSubjectName(subjectMap.get(userEducationAOLevelSubjects.getSubjectId()));
		}

		systemResponse.setEducationAOLevelSubjectList(educationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId));

		return systemResponse;
	}

	@Override
	public ResponseEntity<?> deleteEducationSystemDetailByUserId(@Valid final BigInteger userId) {
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

	@Override
	public ResponseEntity<?> calculate(@Valid final GradeDto gradeDto) {
		Map<String, Object> response = new HashMap<>();
		Double averageGpa = null;
		try {
			averageGpa = calculateGpa(gradeDto);
			response.put("message", "Calculated average gpa successfully");
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("data", averageGpa);
		return ResponseEntity.ok().body(response);
	}

	private Double calculateGpa(@Valid final GradeDto gradeDto) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Double averageGpa = null;
		Double gpaGrade = 0.0;
		List<String> gpaGrades = new ArrayList<>();
		for (String grade : gradeDto.getSubjectGrades()) {
			gpaGrades.add(educationDetailDAO.getGradeDetails(gradeDto.getCountryId(), gradeDto.getEducationSystemId(), grade));
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

	public Double calGpa(@Valid final GradeDto gradeDto) {
		Double avr = 0.0;
		avr = calculateGpa(gradeDto);
		return avr;
	}

	@Override
	public ResponseEntity<?> getGrades(final BigInteger countryId, final BigInteger systemId) {
		List<GradeDetails> grades = null;
		try {
			grades = educationDetailDAO.getGrades(countryId, systemId);
			if (grades != null && !grades.isEmpty()) {
				return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(grades).setMessage("Grades fetched successfully").create();
			} else {
				return new GenericResponseHandlers.Builder().setStatus(HttpStatus.NOT_FOUND).setData(grades).setMessage("Grades not found").create();
			}
		} catch (Exception exception) {
			return new GenericResponseHandlers.Builder().setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setData(grades)
					.setMessage(exception.getCause().getMessage()).create();
		}
	}

	@Override
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
		if (educationSystemRequest.getEnglishScoresList() != null && !educationSystemRequest.getEnglishScoresList().isEmpty()) {
			englishScoreDAO.deleteEnglishScoreByUserId(educationSystemRequest.getUserId());
			saveEnglishScore(educationSystemRequest);
		}
		if (educationSystemRequest.getEducationAOLevelSubjectList() != null && !educationSystemRequest.getEducationAOLevelSubjectList().isEmpty()) {
			educationAOLevelSubjectDAO.deleteEducationAOLevelByUserId(educationSystemRequest.getUserId());
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
				existingUserEducationDetails.setCountry(iCountryService.get(educationDetailRequestDto.getEducationCountryId()));
			}
			if (educationDetailRequestDto.getEducationSystemId() != null) {
				existingUserEducationDetails.setEducationSystem(iEducationSystemDAO.get(educationDetailRequestDto.getEducationSystemId()));
			}
			if (educationDetailRequestDto.getEducationLevelId() != null) {
				existingUserEducationDetails.setLevel(iLevelService.get(educationDetailRequestDto.getEducationLevelId()));
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
}
