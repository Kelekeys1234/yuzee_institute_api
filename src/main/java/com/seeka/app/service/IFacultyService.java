package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.Faculty;

public interface IFacultyService {

    public void save(Faculty obj);

    public void update(Faculty obj);

    public Faculty get(String id);

    public List<Faculty> getAll();

    public List<Faculty> getFacultyByCountryIdAndLevelId(String countryID, String levelId);

    public List<Faculty> getAllFacultyByCountryIdAndLevel();

    public List<Faculty> getFacultyByInstituteId(String instituteId);

    public List<Faculty> getFacultyByListOfInstituteId(String instituteId);

    public List<Faculty> getCourseFaculty(String countryId, String levelId);
    
    List<Faculty> getFacultyListByName(List<String> facultyNames);
    
    Faculty getFacultyByFacultyName(String facultyName);
    
    public List<String> getFacultyNameByInstituteId(final String id);
}
