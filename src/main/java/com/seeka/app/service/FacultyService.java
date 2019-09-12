package com.seeka.app.service;

import java.math.BigInteger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Faculty;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.util.CDNServerUtil;

@Service
@Transactional
public class FacultyService implements IFacultyService {

    @Autowired
    private IFacultyDAO dao;

    @Override
    public void save(Faculty obj) {
        dao.save(obj);
    }

    @Override
    public void update(Faculty obj) {
        dao.update(obj);
    }

    @Override
    public Faculty get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<Faculty> getAll() {
        List<Faculty> faculties = dao.getAll();
        for (Faculty faculty : faculties) {
            faculty.setIcon(CDNServerUtil.getFacultyIconUrl(faculty.getName()));
        }
        return faculties;
    }

    @Override
    public List<Faculty> getFacultyByCountryIdAndLevelId(BigInteger countryID, BigInteger levelId) {
        return dao.getFacultyByCountryIdAndLevelId(countryID, levelId);
    }

    @Override
    public List<Faculty> getAllFacultyByCountryIdAndLevel() {
        return dao.getAllFacultyByCountryIdAndLevel();
    }

    @Override
    public List<Faculty> getFacultyByInstituteId(BigInteger instituteId) {
        return dao.getFacultyByInstituteId(instituteId);
    }

    @Override
    public List<Faculty> getFacultyByListOfInstituteId(String instituteId) {
        String[] citiesArray = instituteId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return dao.getFacultyByListOfInstituteId(tempList.substring(1, tempList.length()));
    }

    @Override
    public List<Faculty> getCourseFaculty(BigInteger countryId, BigInteger levelId) {
        return dao.getCourseFaculty(countryId, levelId);
    }
}
