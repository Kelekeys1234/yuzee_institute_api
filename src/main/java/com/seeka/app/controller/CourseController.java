package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.UserCourseReview;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.JobsDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.service.ICourseEnglishEligibilityService;
import com.seeka.app.service.ICourseGradeEligibilityService;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IUserInstCourseReviewService;
import com.seeka.app.service.IUserMyCourseService;
import com.seeka.app.service.IUserService;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private IInstituteService instituteService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICoursePricingService coursePricingService;

    @Autowired
    private ICourseKeywordService courseKeywordService;

    @Autowired
    private IUserInstCourseReviewService userInstCourseReviewService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICourseEnglishEligibilityService courseEnglishService;

    @Autowired
    private ICourseGradeEligibilityService courseGradeService;

    @Autowired
    private IUserMyCourseService myCourseService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody CourseRequest course) throws Exception {
        return ResponseEntity.badRequest().body(courseService.save(course));
    }

    @RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllCourse(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(courseService.getAllCourse(pageNumber, pageSize));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@Valid @PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(courseService.deleteCourse(id));
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getCourseTypeByCountry(@RequestBody CourseSearchDto courseSearchDto) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        ErrorDto errorDto = null;
        if (courseSearchDto.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        UserInfo user = userService.get(courseSearchDto.getUserId());

        if (null == user) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid user.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        Currency userCurrency = CurrencyUtil.getCurrencyObjById(user.getPreferredCurrencyId());
        Currency currency = null;
        String message = "";
        if (null != courseSearchDto.getCurrencyId() && !user.getPreferredCurrencyId().equals(courseSearchDto.getCurrencyId())) {
            currency = CurrencyUtil.getCurrencyObjById(courseSearchDto.getCurrencyId());
            response.put("showCurrencyPopup", true);
            message = "Do you want to change " + currency.getName() + " (" + currency.getCode() + ") as your currency.?";
        } else {
            currency = CurrencyUtil.getCurrencyObjById(user.getPreferredCurrencyId());
            response.put("showCurrencyPopup", false);
        }
        response.put("currencyPopupMsg", message);

        List<UserMyCourse> userMyCourses = myCourseService.getDataByUserID(courseSearchDto.getUserId());
        Map<BigInteger, Boolean> favouriteMap = new HashMap<>();
        for (UserMyCourse obj : userMyCourses) {
            favouriteMap.put(obj.getCourse().getId(), true);
        }

        List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto, currency, user.getPreferredCountryId());

        for (CourseResponseDto obj : courseList) {
            try {
                Boolean isFav = favouriteMap.get(obj.getCourseId());
                if (null != isFav) {
                    obj.setIsFavourite(isFav);
                }
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer maxCount = 0, totalCount = 0;
        if (null != courseList && !courseList.isEmpty()) {
            totalCount = courseList.get(0).getTotalCount();
            maxCount = courseList.size();
        }
        boolean showMore;
        if (courseSearchDto.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }

        String oldCurrencyCode = null;
        if (userCurrency != null && userCurrency.getCode() != null) {
            oldCurrencyCode = userCurrency.getCode();
        }
        CourseFilterCostResponseDto costResponseDto = courseService.getAllCoursesFilterCostInfo(courseSearchDto, currency, oldCurrencyCode);
        if (currency != null) {
            costResponseDto.setCurrencyId(currency.getId());
            costResponseDto.setCurrencySymbol(currency.getSymbol());
            costResponseDto.setCurrencyCode(currency.getCode());
            costResponseDto.setCurrencyName(currency.getName());
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        response.put("courseList", courseList);
        response.put("costCurrency", costResponseDto);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/mycourses", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllMyCourses(@RequestBody CourseSearchDto courseSearchDto) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        ErrorDto errorDto = null;
        if (courseSearchDto.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }
        UserInfo user = userService.get(courseSearchDto.getUserId());
        if (null == user) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid user.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        // Currency userCurrency = CurrencyUtil.getCurrencyObjById(user.getCurrencyId());
        Currency currency = null;
        String message = "";
        if (null != courseSearchDto.getCurrencyId() && !user.getPreferredCurrencyId().equals(courseSearchDto.getCurrencyId())) {
            currency = CurrencyUtil.getCurrencyObjById(courseSearchDto.getCurrencyId());
            response.put("showCurrencyPopup", true);
            message = "Do you want to change " + currency.getName() + " (" + currency.getCode() + ") as your currency.?";
        } else {
            currency = CurrencyUtil.getCurrencyObjById(user.getPreferredCurrencyId());
            response.put("showCurrencyPopup", false);
        }
        response.put("currencyPopupMsg", message);

        List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto, currency, user.getPreferredCountryId());
        for (CourseResponseDto obj : courseList) {
            try {
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer maxCount = 0, totalCount = 0;
        if (null != courseList && !courseList.isEmpty()) {
            totalCount = courseList.get(0).getTotalCount();
            maxCount = courseList.size();
        }
        boolean showMore;
        if (courseSearchDto.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }
        /*
         * CourseFilterCostResponseDto costResponseDto = courseService.getAllCoursesFilterCostInfo(courseSearchDto, currency, userCurrency.getCode());
         * costResponseDto.setCurrencyId(currency.getId()); costResponseDto.setCurrencySymbol(currency.getSymbol()); costResponseDto.setCurrencyCode(currency.getCode());
         * costResponseDto.setCurrencyName(currency.getName());
         */

        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        response.put("courseList", courseList);
        // response.put("costCurrency",costResponseDto);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@Valid @RequestParam("searchkey") String searchkey) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        CourseSearchDto courseSearchDto = new CourseSearchDto();
        courseSearchDto.setSearchKey(searchkey);
        List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto, null, null);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("courseList", courseList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@Valid @PathVariable BigInteger id) throws Exception {
        ErrorDto errorDto = null;
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> map = courseService.getCourse(id);
        if (map == null || map.isEmpty() || map.size() <= 0) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid course.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }
        CourseDto courseResObj = (CourseDto) map.get("courseObj");
        InstituteResponseDto instituteObj = (InstituteResponseDto) map.get("instituteObj");
        instituteObj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(instituteObj.getCountryName(), instituteObj.getInstituteName()));
        instituteObj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(instituteObj.getCountryName(), instituteObj.getInstituteName()));
        List<CourseEnglishEligibility> englishCriteriaList = courseEnglishService.getAllEnglishEligibilityByCourse(id);
        CourseGradeEligibility gradeCriteriaObj = courseGradeService.get(id);
        List<UserCourseReview> reviewsList = userInstCourseReviewService.getTopReviewsByFilter(courseResObj.getCourseId(), instituteObj.getInstituteId());
        JobsDto jobsDto = new JobsDto();
        jobsDto.setCityId(instituteObj.getCityId());
        jobsDto.setCountryId(instituteObj.getCountryId());
        jobsDto.setNoOfJobs(250000);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("courseObj", courseResObj);
        response.put("englishCriteriaList", englishCriteriaList);
        response.put("gradeCriteriaObj", gradeCriteriaObj);
        response.put("instituteObj", instituteObj);
        response.put("jobsObj", jobsDto);
        if (null != reviewsList && !reviewsList.isEmpty() && reviewsList.size() > 0) {
            response.put("reviewObj", reviewsList.get(0));
        } else {
            response.put("reviewObj", null);
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/institute/{instituteId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable BigInteger instituteId, @Valid @RequestBody CourseSearchDto request) throws Exception {
        ErrorDto errorDto = null;
        Map<String, Object> response = new HashMap<String, Object>();

        InstituteResponseDto instituteResponseDto = instituteService.getInstituteByID(instituteId);
        if (null == instituteResponseDto) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid institute selection.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }
        instituteResponseDto.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(instituteResponseDto.getCountryName(), instituteResponseDto.getInstituteName()));
        instituteResponseDto.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(instituteResponseDto.getCountryName(), instituteResponseDto.getInstituteName()));

        List<CourseResponseDto> courseList = courseService.getAllCoursesByInstitute(instituteId, request);

        Integer maxCount = 0, totalCount = 0;
        if (null != courseList && !courseList.isEmpty()) {
            totalCount = courseList.get(0).getTotalCount();
            maxCount = courseList.size();
        }
        boolean showMore;
        if (request.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        response.put("instituteObj", instituteResponseDto);
        response.put("courseList", courseList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/pricing", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveService(@RequestBody CoursePricing obj) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        coursePricingService.save(obj);
        response.put("status", 1);
        response.put("message", "Success");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/keyword", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") String keyword) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CourseKeywords> searchkeywordList = courseKeywordService.searchCourseKeyword(keyword);
        response.put("status", 1);
        response.put("searchkeywordList", searchkeywordList);
        response.put("message", "Success");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCouresesByFacultyId(@Valid @PathVariable BigInteger facultyId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CourseResponseDto> courseDtos = courseService.getCouresesByFacultyId(facultyId);
        if (courseDtos != null && !courseDtos.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.COURSES_NOT_FOUND);
        }
        response.put("courses", courseDtos);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/multiple/faculty/{facultyId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCouresesByListOfFacultyId(@Valid @PathVariable String facultyId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CourseResponseDto> courseDtos = courseService.getCouresesByListOfFacultyId(facultyId);
        if (courseDtos != null && !courseDtos.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.COURSES_NOT_FOUND);
        }
        response.put("courses", courseDtos);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/eligibility/update", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> updateGradeAndEnglishEligibility() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Course> courseList = courseService.getAll();
        Date now = new Date();

        CourseEnglishEligibility englishEligibility = null;
        CourseGradeEligibility courseGradeEligibility = null;

        int size = courseList.size(), i = 1;

        for (Course course : courseList) {
            System.out.println("Total:  " + size + ",  Completed:  " + i + ",  CourseID:  " + course.getId());
            i++;
            try {
                courseGradeEligibility = new CourseGradeEligibility();
                courseGradeEligibility.setCourseId(course.getId());
                courseGradeEligibility.setGlobalALevel1("A");
                courseGradeEligibility.setGlobalALevel2("A");
                courseGradeEligibility.setGlobalALevel3("A");
                courseGradeEligibility.setGlobalALevel4("A");
                // courseGradeEligibility.setGlobalALevel5("");
                courseGradeEligibility.setGlobalGpa(3.5);
                courseGradeEligibility.setIsActive(true);
                courseGradeEligibility.setIsDeleted(false);
                courseGradeEligibility.setCreatedBy("AUTO");
                courseGradeEligibility.setCreatedOn(now);
                courseGradeService.save(courseGradeEligibility);

                englishEligibility = new CourseEnglishEligibility();
                englishEligibility.setCourse(course);
                englishEligibility.setEnglishType(EnglishType.IELTS.toString());
                // englishEligibility.setId(BigInteger.randomBigInteger());
                englishEligibility.setIsActive(true);
                englishEligibility.setListening(4.0);
                englishEligibility.setOverall(4.5);
                englishEligibility.setReading(4.0);
                englishEligibility.setSpeaking(5.0);
                englishEligibility.setWriting(5.0);
                englishEligibility.setIsDeleted(false);
                englishEligibility.setCreatedBy("AUTO");
                englishEligibility.setCreatedOn(now);
                courseEnglishService.save(englishEligibility);

                englishEligibility = new CourseEnglishEligibility();
                englishEligibility.setCourse(course);
                englishEligibility.setEnglishType(EnglishType.TOEFL.toString());
                // englishEligibility.setId(BigInteger.randomBigInteger());
                englishEligibility.setIsActive(true);
                englishEligibility.setListening(4.0);
                englishEligibility.setOverall(4.5);
                englishEligibility.setReading(4.0);
                englishEligibility.setSpeaking(5.0);
                englishEligibility.setWriting(5.0);
                englishEligibility.setIsDeleted(false);
                englishEligibility.setCreatedBy("AUTO");
                englishEligibility.setCreatedOn(now);
                courseEnglishService.save(englishEligibility);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("courseList", courseList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/user/favourite", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> markUserFavoriteCourse(@RequestBody UserMyCourse obj) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        UserMyCourse dbObj = myCourseService.getDataByUserIDAndCourseID(obj.getUserInfo().getUserId(), obj.getCourse().getId());
        Date now = new Date();
        String message = "Added to my course.";
        if (null != dbObj) {
            dbObj.setIsActive(false);
            dbObj.setUpdatedBy("");
            dbObj.setUpdatedOn(now);
            myCourseService.update(dbObj);
            message = "Removed to my course.";
        } else {
            obj.setIsActive(true);
            obj.setCreatedBy("");
            obj.setCreatedOn(now);
            myCourseService.save(obj);
        }
        response.put("status", 1);
        response.put("message", message);
        return ResponseEntity.accepted().body(response);
    }
}