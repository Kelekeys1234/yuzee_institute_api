package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.ICourseEnglishEligibilityDAO;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IUserMyCourseDAO;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.UserCompareCourseResponse;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class CourseService implements ICourseService {

    @Autowired
    private ICourseDAO iCourseDAO;

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

    @Autowired
    private IUserMyCourseDAO myCourseDAO;

    @Override
    public void save(final Course course) {
        iCourseDAO.save(course);
    }

    @Override
    public void update(final Course course) {
        iCourseDAO.update(course);
    }

    @Override
    public Course get(final BigInteger id) {
        return iCourseDAO.get(id);
    }

    @Override
    public List<Course> getAll() {
        return iCourseDAO.getAll();
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto) {
        return iCourseDAO.getAllCoursesByFilter(courseSearchDto);
    }

    @Override
    public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(final CourseSearchDto courseSearchDto, final Currency currency, final String oldCurrencyCode) {
        return iCourseDAO.getAllCoursesFilterCostInfo(courseSearchDto, currency, oldCurrencyCode);
    }

    @Override
    public List<CourseResponseDto> getAllCoursesByInstitute(final BigInteger instituteId, final CourseSearchDto courseSearchDto) {
        return iCourseDAO.getAllCoursesByInstitute(instituteId, courseSearchDto);
    }

    @Override
    public Map<String, Object> getCourse(final BigInteger courseId) {
        return iCourseDAO.getCourse(courseId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByFacultyId(final BigInteger facultyId) {
        return iCourseDAO.getCouresesByFacultyId(facultyId);
    }

    @Override
    public List<CourseResponseDto> getCouresesByListOfFacultyId(final String facultyId) {
        String[] citiesArray = facultyId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return iCourseDAO.getCouresesByListOfFacultyId(tempList.substring(1, tempList.length()));
    }

    @Override
    public Map<String, Object> save(@Valid final CourseRequest courseDto) {
        Map<String, Object> response = new HashMap<>();
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

            // Course Details
            course.setIntake(courseDto.getIntake());
            course.setCourseLink(courseDto.getCourseLink());
            course.setDomesticFee(courseDto.getDomasticFee());
            course.setInternationalFee(courseDto.getInternationalFee());
            // course.setGrades(courseDto.getGrades());
            // course.setJobFullTime(courseDto.getFullTime());
            // course.setJobPartTime(courseDto.getPartTime());
            // course.setFileUrl(courseDto.getDocumentUrl());
            // course.setContact(courseDto.getContact());
            // course.setOpeningHour(courseDto.getOpeningHourFrom() + "-" + courseDto.getOpeningHourTo());
            course.setCampusLocation(courseDto.getCampusLocation());
            iCourseDAO.save(course);

            if (courseDto.getEnglishEligibility() != null) {
                courseEnglishEligibilityDAO.save(courseDto.getEnglishEligibility());
            }
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.COURSE_ADD_SUCCESS);
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    private Country getCountry(final BigInteger countryId) {
        Country country = null;
        if (countryId != null) {
            country = countryDAO.get(countryId);
        }
        return country;
    }

    private City getCity(final BigInteger cityId) {
        City city = null;
        if (cityId != null) {
            city = cityDAO.get(cityId);
        }
        return city;
    }

    private Faculty getFaculty(final BigInteger facultyId) {
        Faculty faculty = null;
        if (facultyId != null) {
            faculty = facultyDAO.get(facultyId);
        }
        return faculty;
    }

    private Institute getInstititute(final BigInteger instituteId) {
        Institute institute = null;
        if (instituteId != null) {
            institute = dao.get(instituteId);
        }
        return institute;
    }

    @Override
    public Map<String, Object> getAllCourse(final Integer pageNumber, final Integer pageSize) {
        Map<String, Object> response = new HashMap<>();
        List<CourseRequest> courses = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iCourseDAO.findTotalCount();
            int startIndex;
            if (pageNumber > 1) {
                startIndex = (pageNumber - 1) * pageSize + 1;
            } else {
                startIndex = pageNumber;
            }
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            courses = iCourseDAO.getAll(startIndex, pageSize);
            if (courses != null && !courses.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COURSE_GET_SUCCESS_MESSAGE);
                response.put("courses", courses);
                response.put("totalCount", totalCount);
                response.put("pageNumber", paginationUtilDto.getPageNumber());
                response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
                response.put("hasNextPage", paginationUtilDto.isHasNextPage());
                response.put("totalPages", paginationUtilDto.getTotalPages());
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COURSE_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteCourse(@Valid final BigInteger courseId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Course course = iCourseDAO.get(courseId);
            if (course != null) {
                course.setIsActive(false);
                course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                course.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                course.setIsDeleted(true);
                iCourseDAO.update(course);
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COURSE_DELETED_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COURSE_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> searchCourseBasedOnFilter(final BigInteger countryId, final BigInteger instituteId, final BigInteger facultyId, final String name,
                    final String languauge) {
        Map<String, Object> response = new HashMap<>();
        List<CourseRequest> courses = new ArrayList<>();
        try {
            String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
                            + "cd.description, cd.intake,c.duration, c.course_lang,cd.domestic_fee,cd.international_fee,"
                            + "cd.grade, cd.file_url, cd.contact, cd.opening_hours, cd.campus_location, cd.website,"
                            + " cd.job_part_time, cd.job_full_time , cd.course_link,c.updated_on, c.world_ranking, c.stars, c.duration_time  FROM course c inner join course_details cd "
                            + " on c.id = cd.course_id where c.is_active = 1 and c.deleted_on IS NULL ";
            if (countryId != null) {
                sqlQuery += " and c.country_id = " + countryId;
            } else if (instituteId != null) {
                sqlQuery += " and c.institute_id = " + instituteId;
            } else if (facultyId != null) {
                sqlQuery += " and inst.faculty_id = " + facultyId;
            } else if (name != null && !name.isEmpty()) {
                sqlQuery += " and c.name  = '" + name + "'";
            } else if (languauge != null && !languauge.isEmpty()) {
                sqlQuery += " and c.course_lang  = '" + languauge + "'";
            }
            sqlQuery += " ORDER BY c.created_on DESC";
            courses = iCourseDAO.searchCoursesBasedOnFilter(sqlQuery);
            if (courses != null && !courses.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Course fetch successfully");
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Course not found");
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.OK.value());
        }
        response.put("courses", courses);
        return response;
    }

    @Override
    public Map<String, Object> addUserCourses(@Valid final UserCourse userCourse) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userCourse.getCourses() != null && !userCourse.getCourses().isEmpty()) {
                for (BigInteger courseId : userCourse.getCourses()) {
                    UserMyCourse myCourse = new UserMyCourse();
                    myCourse.setCourse(iCourseDAO.get(courseId));
                    myCourse.setUserId(userCourse.getUserId());
                    myCourse.setIsActive(true);
                    myCourse.setCreatedBy("API");
                    myCourse.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
                    myCourse.setUpdatedBy("API");
                    myCourse.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                    myCourseDAO.save(myCourse);
                    response.put("status", HttpStatus.OK.value());
                    response.put("message", "Course added successfully");
                }
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserCourse(final BigInteger userId, final Integer pageNumber, final Integer pageSize, final String currencyCode, final String sortBy,
                    final Boolean sortAsscending) {
        Map<String, Object> response = new HashMap<>();
        String status = IConstant.SUCCESS;
        List<CourseRequest> courses = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = iCourseDAO.findTotalCountByUserId(userId);
            int startIndex;
            if (pageNumber > 1) {
                startIndex = (pageNumber - 1) * pageSize + 1;
            } else {
                startIndex = pageNumber;
            }
            paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
            courses = iCourseDAO.getUserCourse(userId, startIndex, pageSize, currencyCode, sortBy, sortAsscending);
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", HttpStatus.OK.value());
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
    public Map<String, Object> addUserCompareCourse(@Valid final UserCourse userCourse) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userCourse.getCourses() != null && !userCourse.getCourses().isEmpty()) {
                String compareValue = "";
                for (BigInteger courseId : userCourse.getCourses()) {
                    compareValue += courseId + ",";
                }
                UserCompareCourse compareCourse = new UserCompareCourse();
                compareCourse.setCompareValue(compareValue);
                compareCourse.setCreatedBy(userCourse.getCreatedBy());
                compareCourse.setCreatedOn(new Date());
                compareCourse.setUpdatedBy(userCourse.getUpdatedBy());
                compareCourse.setUpdatedOn(new Date());
                compareCourse.setUserId(userCourse.getUserId());
                iCourseDAO.saveUserCompareCourse(compareCourse);
                for (BigInteger courseId : userCourse.getCourses()) {
                    UserCompareCourseBundle compareCourseBundle = new UserCompareCourseBundle();
                    compareCourseBundle.setUserId(userCourse.getUserId());
                    compareCourseBundle.setCompareCourse(compareCourse);
                    compareCourseBundle.setCourse(iCourseDAO.get(courseId));
                    iCourseDAO.saveUserCompareCourseBundle(compareCourseBundle);
                }
                response.put("status", HttpStatus.OK.value());
                response.put("message", "User course added successfully");
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Course can't be null");
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserCompareCourse(final BigInteger userId) {
        Map<String, Object> response = new HashMap<>();
        List<UserCompareCourseResponse> compareCourseResponses = new ArrayList<>();
        try {
            List<UserCompareCourse> compareCourses = iCourseDAO.getUserCompareCourse(userId);
            for (UserCompareCourse compareCourse : compareCourses) {
                UserCompareCourseResponse courseResponse = new UserCompareCourseResponse();
                courseResponse.setUserCourseCompareId(compareCourse.getId());
                courseResponse.setCourses(getCourses(compareCourse.getCompareValue()));
                compareCourseResponses.add(courseResponse);
            }
            if (compareCourses != null && !compareCourses.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COURSE_GET_SUCCESS_MESSAGE);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COURSE_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", compareCourseResponses);
        return response;
    }

    private List<CourseRequest> getCourses(final String compareValue) {
        List<CourseRequest> courses = new ArrayList<>();
        String[] compareValues = compareValue.split(",");
        for (String id : compareValues) {
            courses.add(iCourseDAO.getCourseById(Integer.valueOf(id)));
        }
        return courses;
    }

    @Override
    public List<YoutubeVideo> getYoutubeDataforCourse(final BigInteger courseId) {
        Course courseData = iCourseDAO.getCourseData(courseId);
        if (courseData == null) {
            return new ArrayList<>();
        }
        String courseName = courseData.getName();
        BigInteger instituteId = courseData.getInstitute().getId();
        return getYoutubeDataforCourse(instituteId, courseName);
    }

    @Override
    public List<YoutubeVideo> getYoutubeDataforCourse(final BigInteger instituteId, final String courseName) {
        List<String> skipWords = Arrays.asList("Master`s", "Master", "International", "Bachelor", "of", "Bachelor's", "degree", "&", "and");
        List<String> courseKeyword = Arrays.asList(courseName.split("\\s|,|\\.|-|:"));
        Set<String> keyword = courseKeyword.stream().filter(i -> !i.isEmpty() && !skipWords.contains(i)).collect(Collectors.toSet());
        return iCourseDAO.getYoutubeDataforCourse(instituteId, keyword);
    }

    @Override
    public Course getCourseData(final BigInteger courseId) {
        return iCourseDAO.getCourseData(courseId);
    }

    @Override
    public Map<String, Object> getAllServices() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<com.seeka.app.bean.Service> services = new ArrayList<com.seeka.app.bean.Service>();
        try {
            services = dao.getAllServices();
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("status", HttpStatus.OK);
        response.put("message", "Service retrieve succesfully");
        response.put("servies", services);
        return response;
    }

    @Override
    public Map<String, Object> advanceSearch(AdvanceSearchDto courseSearchDto) {
        Map<String, Object> response = new HashMap<>();
        List<CourseResponseDto> courseResponseDtos = iCourseDAO.advanceSearch(courseSearchDto);
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            if (courseResponseDtos != null && !courseResponseDtos.isEmpty()) {
                totalCount = courseResponseDtos.get(0).getTotalCount();
                int startIndex;
                if (courseSearchDto.getPageNumber() > 1) {
                    startIndex = (courseSearchDto.getPageNumber() - 1) * courseSearchDto.getMaxSizePerPage() + 1;
                } else {
                    startIndex = courseSearchDto.getPageNumber();
                }
                paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Course retrieved successfully");
                response.put("courses", courseResponseDtos);
                response.put("totalCount", totalCount);
                response.put("pageNumber", paginationUtilDto.getPageNumber());
                response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
                response.put("hasNextPage", paginationUtilDto.isHasNextPage());
                response.put("totalPages", paginationUtilDto.getTotalPages());
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Course Not Found");
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllCourse() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Course> courses = new ArrayList<Course>();
        try {
            courses = iCourseDAO.getAllCourse();
            if (courses != null && !courses.isEmpty()) {
                response.put("status", HttpStatus.OK);
                response.put("message", "Course retrieve succesfully");
                response.put("courses", courses);
            } else {
                response.put("status", HttpStatus.NOT_FOUND);
                response.put("message", "Course Not Found");
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        return response;
    }
}
