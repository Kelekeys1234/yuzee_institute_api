package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCampus;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteDAO {

    void save(Institute obj);

    void save(InstituteCampus instituteCampusObj);
    
    void update(Institute obj);

    Institute get(BigInteger id);

    List<Institute> getAllInstituteByCountry(BigInteger countryId);

    List<Institute> getAll();

    List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

    List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj);

    InstituteResponseDto getInstituteByID(BigInteger instituteId);

    List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId);

    List<InstituteResponseDto> getInstituteByListOfCityId(String citisId);

    List<Institute> searchInstitute(String sqlQuery);

    int findTotalCount();

    List<Institute> getAll(Integer pageNumber, Integer pageSize);

    List<com.seeka.app.bean.Service> getAllServices();

    List<InstituteCampus> getInstituteCampusByInstituteId(BigInteger instituteId);

    void delete(Institute obj);
}
