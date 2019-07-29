package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.dto.EducationSystemDto;

public interface IEducationSystemService {
    public void save(EducationSystem hobbiesObj);

    public void update(EducationSystem hobbiesObj);

    public EducationSystem get(BigInteger id);

    public List<EducationSystem> getAll();

    public List<EducationSystem> getAllGlobeEducationSystems();

    public ResponseEntity<?> getEducationSystemsByCountryId(BigInteger countryId);

    public ResponseEntity<?> save(@Valid EducationSystemDto educationSystem);
}
