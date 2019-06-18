package com.seeka.app.service;import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Institute;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

@Service
@Transactional
public class InstituteService implements IInstituteService {

    @Autowired
    IInstituteDAO dao;

    @Override
    public void save(Institute obj) {
        dao.save(obj);
    }

    @Override
    public void update(Institute obj) {
        dao.update(obj);
    }

    @Override
    public Institute get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<Institute> getAllInstituteByCountry(BigInteger countryId) {
        return dao.getAllInstituteByCountry(countryId);
    }

    @Override
    public List<Institute> getAll() {
        return dao.getAll();
    }

    @Override
    public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey) {
        return dao.getInstitueBySearchKey(searchKey);
    }

    @Override
    public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj) {
        return dao.getAllInstitutesByFilter(filterObj);
    }

    @Override
    public InstituteResponseDto getInstituteByID(BigInteger instituteId) {
        return dao.getInstituteByID(instituteId);
    }

    @Override
    public List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId) {
        return dao.getInstitudeByCityId(cityId);
    }

    @Override
    public List<InstituteResponseDto> getInstituteByListOfCityId(String cityId) {
        String[] citiesArray = cityId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return dao.getInstituteByListOfCityId(tempList.substring(1, tempList.length()));
    }
}
