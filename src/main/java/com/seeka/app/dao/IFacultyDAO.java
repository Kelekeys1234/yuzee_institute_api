package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.Faculty;

public interface IFacultyDAO {

    public void save(Faculty obj);

    public void update(Faculty obj);

    public Faculty get(BigInteger id);

    public List<Faculty> getAll();

    public List<Faculty> getFacultyByCountryIdAndLevelId(BigInteger countryID, BigInteger levelId);

    public List<Faculty> getAllFacultyByCountryIdAndLevel();

    public List<Faculty> getFacultyByInstituteId(BigInteger instituteId);

    public List<Faculty> getFacultyByListOfInstituteId(String instituteId);
}
