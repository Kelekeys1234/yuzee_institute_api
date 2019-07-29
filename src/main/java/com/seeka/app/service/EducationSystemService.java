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
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IEducationSystemDAO;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class EducationSystemService implements IEducationSystemService {

    @Autowired
    private IEducationSystemDAO dao;

    @Autowired
    private ICountryDAO countryDAO;

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
            response.put("message", IConstant.SQL_ERROR);
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
                    response.put("message", IConstant.EDUCATION_SUCCESS);
                    response.put("status", HttpStatus.OK.value());
                } else {
                    response.put("message", IConstant.COUNTY_NOT_FOUND);
                    response.put("status", HttpStatus.NOT_FOUND.value());
                }
            }
        } catch (Exception exception) {
            response.put("message", IConstant.SQL_ERROR);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }
}
