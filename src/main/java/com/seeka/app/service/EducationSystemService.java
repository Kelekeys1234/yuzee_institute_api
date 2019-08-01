package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEducationDetails;
import com.seeka.app.bean.UserEnglishScore;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IEducationSystemDAO;
import com.seeka.app.dao.UserEducationAOLevelSubjectDAO;
import com.seeka.app.dao.UserEducationDetailDAO;
import com.seeka.app.dao.UserEnglishScoreDAO;
import com.seeka.app.dto.EducationAOLevelSubjectDto;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.dto.EnglishScoresDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class EducationSystemService implements IEducationSystemService {

    @Autowired
    private IEducationSystemDAO dao;

    @Autowired
    private ICountryDAO countryDAO;

    @Autowired
    private UserEducationDetailDAO educationDetailDAO;

    @Autowired
    private UserEnglishScoreDAO englishScoreDAO;

    @Autowired
    private UserEducationAOLevelSubjectDAO educationAOLevelSubjectDAO;

    @Override
    public void save(EducationSystem hobbiesObj) {
        dao.save(hobbiesObj);
    }

    @Override
    public void update(EducationSystem hobbiesObj) {
        dao.update(hobbiesObj);
    }

    @Override
    public List<EducationSystem> getAll() {
        return dao.getAll();
    }

    @Override
    public EducationSystem get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<EducationSystem> getAllGlobeEducationSystems() {
        return dao.getAllGlobeEducationSystems();
    }

    @Override
    public ResponseEntity<?> getEducationSystemsByCountryId(BigInteger countryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<EducationSystem> educationSystems = null;
        try {
            educationSystems = dao.getEducationSystemsByCountryId(countryId);
            if (educationSystems != null && !educationSystems.isEmpty()) {
                response.put("message", IConstant.EDUCATION_SUCCESS);
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", IConstant.EDUCATION_NOT_FOUND);
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", educationSystems);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> save(@Valid EducationSystemDto educationSystem) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (educationSystem != null && educationSystem.getId() != null) {
                EducationSystem system = dao.get(educationSystem.getId());
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
                    dao.save(system);
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
    public ResponseEntity<?> saveEducationDetails(@Valid EducationSystemRequest educationSystemDetails) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (educationSystemDetails != null) {
                UserEducationDetails educationDetails = null;
                UserEducationDetails educationDetail = educationDetailDAO.getUserEducationDetails(educationSystemDetails.getUserId());
                if (educationDetail != null) {
                    educationDetails = getUserEducationDetails(educationSystemDetails, educationDetail, true);
                    educationDetailDAO.update(educationDetails);
                    response.put("message", "Education system details updated successfully");
                    response.put("status", HttpStatus.OK.value());
                } else {
                    educationDetails = getUserEducationDetails(educationSystemDetails, new UserEducationDetails(), false);
                    educationDetailDAO.save(educationDetails);
                    response.put("messagzse", "Education system details added successfully");
                    response.put("status", HttpStatus.OK.value());
                }
                if (educationSystemDetails.getEnglishScoresList() != null && !educationSystemDetails.getEnglishScoresList().isEmpty()) {
                    englishScoreDAO.deleteEnglishScoreByUserId(educationSystemDetails.getUserId());
                    saveEnglishScore(educationSystemDetails);
                }
                if (educationSystemDetails.getEducationAOLevelSubjectList() != null && !educationSystemDetails.getEducationAOLevelSubjectList().isEmpty()) {
                    educationAOLevelSubjectDAO.deleteEducationAOLevelByUserId(educationSystemDetails.getUserId());
                    saveEducationAOLevelSubject(educationSystemDetails);
                }
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

    private void saveEducationAOLevelSubject(@Valid EducationSystemRequest educationSystemDetails) {
        for (EducationAOLevelSubjectDto dto : educationSystemDetails.getEducationAOLevelSubjectList()) {
            UserEducationAOLevelSubjects levelSubjects = new UserEducationAOLevelSubjects();
            levelSubjects.setSubName(dto.getSubName());
            levelSubjects.setGrade(dto.getGrade());
            levelSubjects.setCreatedBy(educationSystemDetails.getCreatedBy());
            levelSubjects.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            levelSubjects.setUpdatedBy(educationSystemDetails.getUpdatedBy());
            levelSubjects.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            levelSubjects.setIsActive(true);
            levelSubjects.setUserId(educationSystemDetails.getUserId());
            educationAOLevelSubjectDAO.save(levelSubjects);
        }
    }

    private void saveEnglishScore(@Valid EducationSystemRequest educationSystemDetails) {
        for (EnglishScoresDto dto : educationSystemDetails.getEnglishScoresList()) {
            UserEnglishScore userEduIelTofScore = new UserEnglishScore();
            if (dto.getEnglishType() != null) {
                if (dto.getEnglishType().equals(EnglishType.IELTS)) {
                    userEduIelTofScore.setEnglishType(EnglishType.IELTS);
                }
                if (dto.getEnglishType().equals(EnglishType.TOEFL)) {
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
            userEduIelTofScore.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            userEduIelTofScore.setUpdatedBy(educationSystemDetails.getUpdatedBy());
            userEduIelTofScore.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            userEduIelTofScore.setIsActive(true);
            englishScoreDAO.save(userEduIelTofScore);
        }
    }

    private UserEducationDetails getUserEducationDetails(@Valid EducationSystemRequest educationSystemDetails, UserEducationDetails educationDetails, boolean status) {
        if (status) {
            educationDetails.setUpdatedBy(educationSystemDetails.getUpdatedBy());
            educationDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        } else {
            educationDetails.setCreatedBy(educationSystemDetails.getCreatedBy());
            educationDetails.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            educationDetails.setUpdatedBy(educationSystemDetails.getUpdatedBy());
            educationDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            educationDetails.setIsActive(true);
        }
        if (educationSystemDetails.getEducationDetail() != null) {
            educationDetails.setEduCountry(educationSystemDetails.getEducationDetail().getEduCountryId());
            educationDetails.setEduInstitue(educationSystemDetails.getEducationDetail().getEduInstitue());
            educationDetails.setEduLevel(educationSystemDetails.getEducationDetail().getEduLevel());
            educationDetails.setEduSysScore(educationSystemDetails.getEducationDetail().getEduSysScore());
            educationDetails.setEduSystemId(educationSystemDetails.getEducationDetail().getEduSystemId());
            educationDetails.setEnglishLevel(educationSystemDetails.getEducationDetail().getEnglishLevel());
            educationDetails.setGpaScore(educationSystemDetails.getEducationDetail().getGpaScore());
            educationDetails.setIsEnglishMedium(educationSystemDetails.getEducationDetail().getEnglishLevel());
        }
        educationDetails.setUserId(educationSystemDetails.getUserId());
        return educationDetails;
    }

    @Override
    public ResponseEntity<?> getEducationSystemsDetailByUserId(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        EducationSystemResponse systemResponse = new EducationSystemResponse();
        try {
            UserEducationDetails educationDetails = educationDetailDAO.getUserEducationDetails(userId);
            if (educationDetails != null) {
                systemResponse.setEducationDetail(educationDetails);
                systemResponse.setEnglishScoresList(englishScoreDAO.getEnglishEligibiltyByUserID(userId));
                systemResponse.setEducationAOLevelSubjectList(educationAOLevelSubjectDAO.getUserLevelSubjectGrades(userId));
                systemResponse.setUserId(userId);
                response.put("message", IConstant.EDUCATION_SUCCESS);
                response.put("status", HttpStatus.OK.value());
                response.put("data", systemResponse);
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
    public ResponseEntity<?> deleteEducationSystemDetailByUserId(@Valid BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            UserEducationDetails educationDetails = educationDetailDAO.getUserEducationDetails(userId);
            if (educationDetails != null) {
                educationDetails.setIsActive(false);
                educationDetails.setIsDeleted(true);
                educationDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                educationDetails.setUpdatedBy(educationDetails.getCreatedBy());
                educationDetails.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
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
}
