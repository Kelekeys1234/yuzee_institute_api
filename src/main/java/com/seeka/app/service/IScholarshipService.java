package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipFilterDto;

public interface IScholarshipService {

    Map<String, Object> save(ScholarshipDto scholarship);

    void save(Scholarship obj);

    Map<String, Object> getAllScholarship(Integer pageNumber, Integer pageSize);

    Map<String, Object> get(BigInteger id);

    Map<String, Object> deleteScholarship(BigInteger id);

    Map<String, Object> updateScholarship(@Valid BigInteger id, ScholarshipDto scholarshipDto);

    List<ScholarshipDto> getScholarshipBySearchKey(String searchKey);

    Object scholarshipFilter(ScholarshipFilterDto scholarshipFilterDto);
}
