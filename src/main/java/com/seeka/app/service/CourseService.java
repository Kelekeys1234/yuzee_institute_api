package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDetails;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.ICourseDetailsDAO;
import com.seeka.app.dao.ICourseEnglishEligibilityDAO;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class CourseService implements ICourseService {

    @Autowired
    private ICourseDAO iCourseDAO;

    @Autowired
    private ICourseDetailsDAO courseDetailsDAO;

    @Autowired
    private ICourseEnglishEligibilityDAO courseEnglishEligibilityDAO;

    @Autowired
    private IInstituteDAO dao;

    @Autowired
    private ICountryDAO countryDAO;

    @Autowired
    private ICityDAO cityDAO;

    @Autowired
    private IFacultyDAO facultyDAO;

    @Override
    public void save(Course course) {
        iCourseDAO.save(course);
    }

    @Override
    public void update(Course course) {
        iCourseDAO.update(course);
    }

    @Override
    public Course get(BigInteger id) {
        return iCourseDAO.get(id);
    }

    @Override
    public List<Course> getAll() {
        return iCourseDAO.getAll();
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto courseSearchDto, Currency currency, BigInteger userCountryId) {
        return iCourseDAO.getAllCoursesByFilter(courseSearchDto, currency, userCountryId);
    }

    @Override
    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto courseSearchDto, Currency currency, String oldCurrencyCode) {
        return iCourseDAO.getAllCoursesFilterCostInfo(courseSearchDto, currency, oldCurrencyCode);
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByInstitute(BigInteger instituteId, CourseSearchDto courseSearchDto) {
        return iCourseDAO.getAllCoursesByInstitute(instituteId, courseSearchDto);
    }

    @Override
    public Map<String, Object> getCourse(BigInteger courseId) {
        return iCourseDAO.getCourse(courseId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByFacultyId(BigInteger facultyId) {
        return iCourseDAO.getCouresesByFacultyId(facultyId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByListOfFacultyId(String facultyId) {
        String[] citiesArray = facultyId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return iCourseDAO.getCouresesByListOfFacultyId(tempList.substring(1, tempList.length()));
    }

    @Override
    public Map<String, Object> save(@Valid CourseRequest courseDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            Course course = new Course();
            course.setInstitute(getInstititute(courseDto.getInstituteId()));
            course.setDescription(courseDto.getDescription());
            course.setName(courseDto.getName());
            course.setcId(courseDto.getcId());
            course.setDuration(courseDto.getDuration());
            course.setFaculty(getFaculty(courseDto.getFacultyId()));
            course.setCity(getCity(courseDto.getCityId()));
            course.setCourseLang(courseDto.getLanguaige());
            course.setCountry(getCountry(courseDto.getCountryId()));
            course.setIsActive(true);
            course.setCreatedBy("API");
            course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            course.setUpdatedBy("API");
            course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            iCourseDAO.save(course);

            CourseDetails courseDetails = new CourseDetails();
            courseDetails.setIntake(courseDto.getIntake());
            courseDetails.setCourseLink(courseDto.getCourseLink());
            courseDetails.setDomesticFee(courseDto.getDomasticFee());
            courseDetails.setInternationalFee(courseDto.getInternationalFee());
            courseDetails.setGrades(courseDto.getGrades());
            courseDetails.setJobFullTime(courseDto.getFullTime());
            courseDetails.setJobPartTime(courseDto.getPartTime());
            courseDetails.setFileUrl(courseDto.getDocumentUrl());
            courseDetails.setContact(courseDto.getContact());
            courseDetails.setOpeningHour(courseDto.getOpeningHourFrom() + "-" + courseDto.getOpeningHourTo());
            courseDetails.setCampusLocation(courseDto.getCampusLocation());
            courseDetails.setWebiste(courseDto.getWebsite());
            courseDetails.setCourse(course);
            courseDetailsDAO.save(courseDetails);

            if (courseDto.getEnglishEligibility() != null) {
                courseEnglishEligibilityDAO.save(courseDto.getEnglishEligibility());
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }

    private Country getCountry(BigInteger countryId) {
        Country country = null;
        if (countryId != null) {
            country = countryDAO.get(countryId);
        }
        return country;
    }

    private City getCity(BigInteger cityId) {
        City city = null;
        if (cityId != null) {
            city = cityDAO.get(cityId);
        }
        return city;
    }

    private Faculty getFaculty(BigInteger facultyId) {
        Faculty faculty = null;
        if (facultyId != null) {
            faculty = facultyDAO.get(facultyId);
        }
        return faculty;
    }

    private Institute getInstititute(BigInteger instituteId) {
        Institute institute = null;
        if (instituteId != null) {
            institute = dao.get(instituteId);
        }
        return institute;
    }

    @Override
    public Map<String, Object> getAllCourse(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<CourseRequest> courses = new ArrayList<CourseRequest>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iCourseDAO.findTotalCount();
            paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
            courses = iCourseDAO.getAll(pageNumber, pageSize);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        response.put("courses", courses);
        response.put("totalCount", totalCount);
        response.put("pageNumber", paginationUtilDto.getPageNumber());
        response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
        response.put("hasNextPage", paginationUtilDto.isHasNextPage());
        response.put("totalPages", paginationUtilDto.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> deleteCourse(@Valid BigInteger courseId) {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        try {
            Course course = iCourseDAO.get(courseId);
            if (course != null) {
                course.setIsActive(false);
                course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                course.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                course.setIsDeleted(true);
                iCourseDAO.update(course);
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 1);
        response.put("message", status);
        return response;
    }
}
