package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDeliveryMethod;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CourseMinRequirement;
import com.seeka.app.bean.Currency;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dao.CourseGradeEligibilityDAO;
import com.seeka.app.dao.CurrencyDAO;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.ICourseEnglishEligibilityDAO;
import com.seeka.app.dao.ICourseMinRequirementDao;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.IGlobalStudentDataDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IUserMyCourseDAO;
import com.seeka.app.dao.ViewDao;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.GlobalData;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserCompareCourseResponse;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.enumeration.SeekaEntityType;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
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

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private ICourseMinRequirementDao courseMinRequirementDao;

	@Autowired
	private EducationSystemService educationSystemService;

	@Autowired
	private CourseGradeEligibilityDAO courseGradeEligibilityDao;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;

	@Autowired
	private ICountryService iCountryService;

	@Autowired
	private IViewService iViewService;

	@Autowired
	private ViewDao viewDao;

	@Autowired
	private ILevelService iLevelService;

	@Autowired
	private UserRecommendationService userRecommedationService;

	@Autowired
	private ElasticSearchService elasticSearchService;

	@Autowired
	private IGlobalStudentDataDAO iGlobalStudentDataDAO;

	@Autowired
	private ITop10CourseService iTop10CourseService;

	@Autowired
	private IInstituteGoogleReviewService iInstituteGoogleReviewService;

	@Autowired
	private IUserReviewService iUserReviewService;

	@Override
	public Course get(final BigInteger id) {
		return iCourseDAO.get(id);
	}

	@Override
	public List<Course> getAll() {
		return iCourseDAO.getAll();
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto, final Integer startIndex, final Integer pageSize,
			final String searchKeyword) throws ValidationException {
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.getAllCoursesByFilter(courseSearchDto, searchKeyword, null, startIndex, false);
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);
	}

	private List<CourseResponseDto> getExtraInfoOfCourseFilter(final CourseSearchDto courseSearchDto, final List<CourseResponseDto> courseResponseDtos)
			throws ValidationException {
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			return new ArrayList<>();
		}
		List<BigInteger> courseIds = courseResponseDtos.stream().map(CourseResponseDto::getId).collect(Collectors.toList());
		List<BigInteger> viewedCourseIds = new ArrayList<>();
		if (courseSearchDto.getUserId() != null) {
			viewedCourseIds = iViewService.getUserViewDataBasedOnEntityIdList(courseSearchDto.getUserId(), "COURSE", courseIds);
		}
		List<StorageDto> storageDTOList = iStorageService.getStorageInformationBasedOnEntityIdList(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), ImageCategory.INSTITUTE.toString(), null,
				"en");
		List<CourseIntake> courseIntake = iCourseDAO.getCourseIntakeBasedOnCourseIdList(courseIds);
		List<CourseDeliveryMethod> courseDeliveryMethods = iCourseDAO.getCourseDeliveryMethodBasedOnCourseIdList(courseIds);
		for (CourseResponseDto courseResponseDto : courseResponseDtos) {
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				List<StorageDto> storageDTO = storageDTOList.stream().filter(x -> courseResponseDto.getInstituteId().equals(x.getEntityId()))
						.collect(Collectors.toList());
				courseResponseDto.setStorageList(storageDTO);
				storageDTOList.removeAll(storageDTO);
			} else {
				courseResponseDto.setStorageList(new ArrayList<>(1));
			}
			if (!viewedCourseIds.isEmpty() && viewedCourseIds.contains(courseResponseDto.getId())) {
				courseResponseDto.setIsViewed(true);
			}

			if (courseIntake != null && !courseIntake.isEmpty()) {
				List<CourseIntake> courseIntakeList = courseIntake.stream().filter(x -> courseResponseDto.getId().equals(x.getCourse().getId()))
						.collect(Collectors.toList());
				courseResponseDto.setIntake(courseIntakeList.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				courseIntake.removeAll(courseIntakeList);
			} else {
				courseResponseDto.setIntake(new ArrayList<>());
			}
			if (courseDeliveryMethods != null && !courseDeliveryMethods.isEmpty()) {
				List<CourseDeliveryMethod> courseDeliveryMethodsList = courseDeliveryMethods.stream()
						.filter(x -> courseResponseDto.getId().equals(x.getCourse().getId())).collect(Collectors.toList());
				courseResponseDto.setDeliveryMethod(courseDeliveryMethodsList.stream().map(CourseDeliveryMethod::getName).collect(Collectors.toList()));
				courseDeliveryMethods.removeAll(courseDeliveryMethodsList);
			} else {
				courseResponseDto.setDeliveryMethod(new ArrayList<>());
			}
		}
		return courseResponseDtos;
	}

	@Override
	public int getCountforNormalCourse(final CourseSearchDto courseSearchDto, final String searchKeyword) {
		return iCourseDAO.getCountforNormalCourse(courseSearchDto, searchKeyword);
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

	/**
	 * Save courses based on parameter.
	 *
	 */
	@Override
	public BigInteger save(@Valid final CourseRequest courseDto) throws ValidationException {
		Course course = new Course();
		course.setInstitute(getInstititute(courseDto.getInstituteId()));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		course.setcId(courseDto.getcId());
		if (courseDto.getDuration() != null && !courseDto.getDuration().isEmpty()) {
			course.setDuration(Double.valueOf(courseDto.getDuration()));
		}
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		course.setCity(getCity(courseDto.getCityId()));
		course.setCountry(getCountry(courseDto.getCountryId()));
		course.setIsActive(true);
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setUpdatedBy("API");
		course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		if (courseDto.getStars() != null && !courseDto.getStars().isEmpty()) {
			course.setStars(Integer.valueOf(courseDto.getStars()));
		}
		// Course Details
		course.setLink(courseDto.getLink());
		course.setDomesticFee(courseDto.getDomasticFee());
		course.setInternationalFee(courseDto.getInternationalFee());
		course.setCampusLocation(courseDto.getCampusLocation());
		/**
		 * Part full's possible values : PART,FULL,BOTH
		 *
		 */
		course.setPartFull(courseDto.getPartFull());
		if (courseDto.getWorldRanking() != null && !courseDto.getWorldRanking().isEmpty()) {
			course.setWorldRanking(Integer.valueOf(courseDto.getWorldRanking()));
		}
		course.setDurationTime(courseDto.getDurationTime());
		course.setWebsite(courseDto.getWebsite());
		if (courseDto.getLevelId() != null) {
			course.setLevel(iLevelService.get(courseDto.getLevelId()));
		}
		course.setAvailbilty(courseDto.getAvailbility());
		course.setOpeningHourFrom(courseDto.getOpeningHourFrom());
		course.setOpeningHourTo(courseDto.getOpeningHourTo());
		course.setContact(courseDto.getContact());
		course.setJobFullTime(courseDto.getJobFullTime());
		course.setJobPartTime(courseDto.getJobPartTime());

		/**
		 * Here we convert price in USD and everywhere we used USD price column only.
		 *
		 */
		if (courseDto.getCurrency() != null) {
			course.setCurrency(courseDto.getCurrency());

			CurrencyRate currencyRate = getCurrencyRate(courseDto.getCurrency());
			if (currencyRate == null) {
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
			if (currencyRate != null) {
				if (courseDto.getCurrency() != null) {
					if (courseDto.getDomasticFee() != null) {
						Double convertedRate = Double.valueOf(courseDto.getDomasticFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							course.setUsdDomasticFee(convertedRate);
						}
					}
					if (courseDto.getInternationalFee() != null) {
						Double convertedRate = Double.valueOf(courseDto.getInternationalFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							course.setUsdInternationFee(convertedRate);
						}
					}
				}
			}
		}
		iCourseDAO.save(course);
		/**
		 * Here multiple intakes are possible.
		 */
		List<Date> intakeList = new ArrayList<>();
		if (courseDto.getIntake() != null) {
			for (Date intake : courseDto.getIntake()) {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setCourse(course);
				courseIntake.setIntakeDates(intake);
				intakeList.add(intake);
				iCourseDAO.saveCourseIntake(courseIntake);
			}
		}
		/**
		 * Here possible deliveryMethods : 1."Classroom", 2."Online", 3."Distance",
		 * 4."Blended"
		 */
		if (courseDto.getDeliveryMethod() != null) {
			for (String deliveryMethod : courseDto.getDeliveryMethod()) {
				CourseDeliveryMethod courseDeliveryMethod = new CourseDeliveryMethod();
				courseDeliveryMethod.setCourse(course);
				courseDeliveryMethod.setName(deliveryMethod);
				iCourseDAO.saveCourseDeliveryMethod(courseDeliveryMethod);
			}
		}

		/**
		 * Course can have multiple language
		 *
		 */
		if (courseDto.getLanguage() != null && !courseDto.getLanguage().isEmpty()) {
			for (String language : courseDto.getLanguage()) {
				CourseLanguage courseLanguage = new CourseLanguage();
				courseLanguage.setCourse(course);
				courseLanguage.setName(language);
				iCourseDAO.saveCourseLanguage(courseLanguage);
			}
		}

		/**
		 * There are EnglishEligibility means IELTS & TOFEL score
		 *
		 */
		if (courseDto.getEnglishEligibility() != null) {
			for (CourseEnglishEligibility e : courseDto.getEnglishEligibility()) {
				e.setCourse(course);
				e.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
				courseEnglishEligibilityDAO.save(e);
			}
		}

		/**
		 * Here we converted course request to elastic search form few changes in
		 * Elastic search object
		 *
		 * Example : If in partFull value suppose BOTH then we will send in ES Like :
		 * PART,FULL
		 *
		 */
		CourseDTOElasticSearch courseElasticSearch = new CourseDTOElasticSearch();
		BeanUtils.copyProperties(course, courseElasticSearch);
		courseElasticSearch.setCountryName(course.getCountry() != null ? course.getCountry().getName() : null);
		courseElasticSearch.setCityName(course.getCity() != null ? course.getCity().getName() : null);
		courseElasticSearch.setFacultyName(course.getFaculty() != null ? course.getFaculty().getName() : null);
		courseElasticSearch.setFacultyDescription(course.getFaculty() != null ? course.getFaculty().getDescription() : null);
		courseElasticSearch.setInstituteName(course.getInstitute() != null ? course.getInstitute().getName() : null);
		courseElasticSearch.setLatitute(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLatitute()) : null);
		courseElasticSearch.setLongitude(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLongitude()) : null);
		courseElasticSearch.setLevelCode(course.getLevel() != null ? course.getLevel().getCode() : null);
		courseElasticSearch.setLevelName(course.getLevel() != null ? course.getLevel().getName() : null);
		courseElasticSearch.setIntake(!intakeList.isEmpty() ? intakeList : null);
		courseElasticSearch.setDeliveryMethod(courseDto.getDeliveryMethod());
		courseElasticSearch.setLanguage(courseDto.getLanguage());
		List<CourseDTOElasticSearch> courseListElasticDTO = new ArrayList<>();
		courseListElasticDTO.add(courseElasticSearch);
		elasticSearchService.saveCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE, SeekaEntityType.COURSE.name().toLowerCase(), courseListElasticDTO,
				IConstant.ELASTIC_SEARCH);

		return course.getId();
	}

	/**
	 * Update course details.
	 *
	 */
	@Override
	public BigInteger update(final CourseRequest courseDto, final BigInteger id) throws ValidationException {
		Course course = new Course();
		course = iCourseDAO.get(id);
		course.setId(id);
		course.setInstitute(getInstititute(courseDto.getInstituteId()));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		course.setcId(courseDto.getcId());
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		course.setCity(getCity(courseDto.getCityId()));
		course.setCountry(getCountry(courseDto.getCountryId()));
		course.setIsActive(true);
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setUpdatedBy("API");
		course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		if (courseDto.getDuration() != null && !courseDto.getDuration().isEmpty()) {
			course.setDuration(Double.valueOf(courseDto.getDuration()));
		}
		if (courseDto.getStars() != null && !courseDto.getStars().isEmpty()) {
			course.setStars(Integer.valueOf(courseDto.getStars()));
		}

		// Course Details
		course.setLink(courseDto.getLink());
		course.setDomesticFee(courseDto.getDomasticFee());
		course.setInternationalFee(courseDto.getInternationalFee());
		course.setCampusLocation(courseDto.getCampusLocation());
		course.setPartFull(courseDto.getPartFull());
		if (courseDto.getWorldRanking() != null && !courseDto.getWorldRanking().isEmpty()) {
			course.setWorldRanking(Integer.valueOf(courseDto.getWorldRanking()));
		}
		course.setDurationTime(courseDto.getDurationTime());
		course.setWebsite(courseDto.getWebsite());
		if (courseDto.getLevelId() != null) {
			course.setLevel(iLevelService.get(courseDto.getLevelId()));
		}
		course.setAvailbilty(courseDto.getAvailbility());
		course.setOpeningHourFrom(courseDto.getOpeningHourFrom());
		course.setOpeningHourTo(courseDto.getOpeningHourTo());
		course.setContact(courseDto.getContact());
		course.setJobFullTime(courseDto.getJobFullTime());
		course.setJobPartTime(courseDto.getJobPartTime());
		if (courseDto.getCurrency() != null) {
			course.setCurrency(courseDto.getCurrency());
			Currency toCurrency = currencyDAO.getCurrencyByCode("USD");
			BigInteger toCurrencyId = null;
			if (toCurrency != null) {
				toCurrencyId = toCurrency.getId();
			}

			CurrencyRate currencyRate = getCurrencyRate(courseDto.getCurrency()/* , currencies */);
			if (currencyRate == null) {
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
			if (currencyRate != null) {
				if (toCurrencyId != null) {
					if (courseDto.getDomasticFee() != null) {
						Double convertedRate = Double.valueOf(courseDto.getDomasticFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							course.setUsdDomasticFee(convertedRate);
						}
					}
					if (courseDto.getInternationalFee() != null) {
						Double convertedRate = Double.valueOf(courseDto.getInternationalFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							course.setUsdInternationFee(convertedRate);
						}
					}
				}
			}
		}
		iCourseDAO.update(course);
		iCourseDAO.deleteCourseIntake(id);
		iCourseDAO.deleteCourseDeliveryMethod(id);
		iCourseDAO.deleteCourseLanguage(id);
		List<Date> intakeList = new ArrayList<>();
		for (Date intake : courseDto.getIntake()) {
			CourseIntake courseIntake = new CourseIntake();
			courseIntake.setCourse(course);
			courseIntake.setIntakeDates(intake);
			intakeList.add(intake);
			iCourseDAO.saveCourseIntake(courseIntake);
		}

		for (String deliveryMethod : courseDto.getDeliveryMethod()) {
			CourseDeliveryMethod courseDeliveryMethod = new CourseDeliveryMethod();
			courseDeliveryMethod.setCourse(course);
			courseDeliveryMethod.setName(deliveryMethod);
			iCourseDAO.saveCourseDeliveryMethod(courseDeliveryMethod);
		}

		if (courseDto.getLanguage() != null && !courseDto.getLanguage().isEmpty()) {
			for (String language : courseDto.getLanguage()) {
				CourseLanguage courseLanguage = new CourseLanguage();
				courseLanguage.setCourse(course);
				courseLanguage.setName(language);
				iCourseDAO.saveCourseLanguage(courseLanguage);
			}
		}

		System.out.println("courseDto.getEnglishEligibility(): " + courseDto.getEnglishEligibility());
		if (courseDto.getEnglishEligibility() != null) {
			List<CourseEnglishEligibility> courseEnglishEligibilityList = courseEnglishEligibilityDAO.getAllEnglishEligibilityByCourse(id);
			System.out.println("The English Eligibility Size: " + courseEnglishEligibilityList.size());
			if (!courseEnglishEligibilityList.isEmpty()) {
				for (CourseEnglishEligibility courseEnglishEligibility : courseEnglishEligibilityList) {
					if (courseEnglishEligibility.getIsActive()) {
						courseEnglishEligibility.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
						courseEnglishEligibility.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
						courseEnglishEligibility.setIsActive(false);
						courseEnglishEligibilityDAO.update(courseEnglishEligibility);
					}
				}
			}
			for (CourseEnglishEligibility e : courseDto.getEnglishEligibility()) {
				e.setCourse(course);
				e.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				courseEnglishEligibilityDAO.save(e);
			}
		}

		CourseDTOElasticSearch courseElasticSearch = new CourseDTOElasticSearch();
		BeanUtils.copyProperties(course, courseElasticSearch);
		courseElasticSearch.setCountryName(course.getCountry() != null ? course.getCountry().getName() : null);
		courseElasticSearch.setCityName(course.getCity() != null ? course.getCity().getName() : null);
		courseElasticSearch.setFacultyName(course.getFaculty() != null ? course.getFaculty().getName() : null);
		courseElasticSearch.setFacultyDescription(course.getFaculty() != null ? course.getFaculty().getDescription() : null);
		courseElasticSearch.setInstituteName(course.getInstitute() != null ? course.getInstitute().getName() : null);
		courseElasticSearch.setLevelCode(course.getLevel() != null ? course.getLevel().getCode() : null);
		courseElasticSearch.setLevelName(course.getLevel() != null ? course.getLevel().getName() : null);
		courseElasticSearch.setLatitute(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLatitute()) : null);
		courseElasticSearch.setLongitude(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLongitude()) : null);
		courseElasticSearch.setIntake(!intakeList.isEmpty() ? intakeList : null);
		courseElasticSearch.setDeliveryMethod(courseDto.getDeliveryMethod());
		courseElasticSearch.setLanguage(courseDto.getLanguage());

		List<CourseDTOElasticSearch> courseListElasticDTO = new ArrayList<>();
		courseListElasticDTO.add(courseElasticSearch);
		elasticSearchService.updateCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE, SeekaEntityType.COURSE.name().toLowerCase(),
				courseListElasticDTO, IConstant.ELASTIC_SEARCH);

		return id;
	}

	private CurrencyRate getCurrencyRate(final String courseCurrency/* , final List<Currency> currencies */) {
		// BigInteger fromCurrencyId = getCurrencyId(currencies, courseCurrency);
		CurrencyRate currencyRate = currencyDAO.getCurrencyRate(/* fromCurrencyId */courseCurrency);
		return currencyRate;
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

			int startIndex = (pageNumber - 1) * pageSize;

			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			courses = iCourseDAO.getAll(startIndex, pageSize);
			List<CourseRequest> resultList = new ArrayList<>();

			for (CourseRequest courseRequest : courses) {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(),
						null, "en");
				courseRequest.setStorageList(storageDTOList);
				resultList.add(courseRequest);
			}
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

				CourseDTOElasticSearch elasticSearchCourseDto = new CourseDTOElasticSearch();
				elasticSearchCourseDto.setId(courseId);
				List<CourseDTOElasticSearch> courseDtoESList = new ArrayList<>();
				courseDtoESList.add(elasticSearchCourseDto);
				elasticSearchService.deleteCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE, SeekaEntityType.COURSE.name().toLowerCase(),
						courseDtoESList, IConstant.ELASTIC_SEARCH);

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
	public Map<String, Object> getUserCourse(final BigInteger userId, final Integer pageNumber, final Integer pageSize, final String currencyCode,
			final String sortBy, final Boolean sortAsscending) throws ValidationException {
		Map<String, Object> response = new HashMap<>();
		String status = IConstant.SUCCESS;
		List<CourseRequest> courses;
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		List<CourseRequest> resultList = new ArrayList<>();
		totalCount = iCourseDAO.findTotalCountByUserId(userId);
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		courses = iCourseDAO.getUserCourse(userId, startIndex, pageSize, currencyCode, sortBy, sortAsscending);
		for (CourseRequest courseRequest : courses) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(), null,
					"en");
			courseRequest.setStorageList(storageDTOList);
			resultList.add(courseRequest);
		}
		response.put("status", HttpStatus.OK.value());
		response.put("message", status);
		response.put("courses", resultList);
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

	private List<CourseRequest> getCourses(final String compareValue) throws ValidationException {
		List<CourseRequest> courses = new ArrayList<>();
		String[] compareValues = compareValue.split(",");
		for (String id : compareValues) {
			CourseRequest courseRequest = iCourseDAO.getCourseById(Integer.valueOf(id));
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(), null,
					"en");
			courseRequest.setStorageList(storageDTOList);
			courses.add(courseRequest);
		}
		return courses;
	}

	@Override
	public List<YoutubeVideo> getYoutubeDataforCourse(final BigInteger courseId, final Integer startIndex, final Integer pageSize) {
		Course courseData = iCourseDAO.getCourseData(courseId);
		if (courseData == null) {
			return new ArrayList<>();
		}
		String courseName = courseData.getName();
		BigInteger instituteId = courseData.getInstitute().getId();
		return getYoutubeDataforCourse(instituteId, courseName, startIndex, pageSize);
	}

	@Override
	public List<YoutubeVideo> getYoutubeDataforCourse(final BigInteger instituteId, final String courseName, final Integer startIndex, final Integer pageSize) {
		List<String> skipWords = Arrays.asList("Master`s", "Master", "International", "Bachelor", "of", "Bachelor's", "degree", "&", "and");
		List<String> courseKeyword = Arrays.asList(courseName.split("\\s|,|\\.|-|:|\\(|\\)"));
		Set<String> keyword = courseKeyword.stream().filter(i -> !i.isEmpty() && !skipWords.contains(i)).collect(Collectors.toSet());
		return iCourseDAO.getYoutubeDataforCourse(instituteId, keyword, startIndex, pageSize);
	}

	@Override
	public Course getCourseData(final BigInteger courseId) {
		return iCourseDAO.getCourseData(courseId);
	}

	@Override
	public Map<String, Object> getAllServices() {
		Map<String, Object> response = new HashMap<>();
		List<com.seeka.app.bean.Service> services = new ArrayList<>();
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
	public List<CourseResponseDto> advanceSearch(final AdvanceSearchDto courseSearchDto) throws ValidationException {
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.advanceSearch(courseSearchDto);
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			return new ArrayList<>();
		}
		List<BigInteger> courseIds = courseResponseDtos.stream().map(CourseResponseDto::getId).collect(Collectors.toList());
		List<BigInteger> viewedCourseIds = iViewService.getUserViewDataBasedOnEntityIdList(courseSearchDto.getUserId(), "COURSE", courseIds);
		List<StorageDto> storageDTOList = iStorageService.getStorageInformationBasedOnEntityIdList(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), ImageCategory.INSTITUTE.name(), null, "en");
		List<CourseDeliveryMethod> courseDeliveryMethods = iCourseDAO.getCourseDeliveryMethodBasedOnCourseIdList(courseIds);
		List<CourseIntake> courseIntake = iCourseDAO.getCourseIntakeBasedOnCourseIdList(courseIds);
		Map<BigInteger, Double> googleReviewMap = iInstituteGoogleReviewService
				.getInstituteAvgGoogleReviewForList(courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()));
		Map<BigInteger, Double> seekaReviewMap = iUserReviewService.getUserAverageReviewBasedOnDataList(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), "INSTITUTE");
		for (CourseResponseDto courseResponseDto : courseResponseDtos) {
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				List<StorageDto> storageDTO = storageDTOList.stream().filter(x -> courseResponseDto.getInstituteId().equals(x.getEntityId()))
						.collect(Collectors.toList());
				courseResponseDto.setStorageList(storageDTO);
				storageDTOList.removeAll(storageDTO);
			} else {
				courseResponseDto.setStorageList(new ArrayList<>(1));
			}
			if (!viewedCourseIds.isEmpty() && viewedCourseIds.contains(courseResponseDto.getId())) {
				courseResponseDto.setIsViewed(true);
			} else {
				courseResponseDto.setIsViewed(false);
			}

			if (courseIntake != null && !courseIntake.isEmpty()) {
				List<CourseIntake> courseIntakeList = courseIntake.stream().filter(x -> courseResponseDto.getId().equals(x.getCourse().getId()))
						.collect(Collectors.toList());
				courseResponseDto.setIntake(courseIntakeList.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				courseIntake.removeAll(courseIntakeList);
			} else {
				courseResponseDto.setIntake(new ArrayList<>());
			}

			if (courseDeliveryMethods != null && !courseDeliveryMethods.isEmpty()) {
				List<CourseDeliveryMethod> courseDeliveryMethodsList = courseDeliveryMethods.stream()
						.filter(x -> courseResponseDto.getId().equals(x.getCourse().getId())).collect(Collectors.toList());
				courseResponseDto.setDeliveryMethod(courseDeliveryMethodsList.stream().map(CourseDeliveryMethod::getName).collect(Collectors.toList()));
				courseDeliveryMethods.removeAll(courseDeliveryMethodsList);
			} else {
				courseResponseDto.setDeliveryMethod(new ArrayList<>());
			}

			courseResponseDto
					.setStars(calculateAverageRating(googleReviewMap, seekaReviewMap, courseResponseDto.getStars(), courseResponseDto.getInstituteId()));

		}
		return courseResponseDtos;
	}

	@Override
	public double calculateAverageRating(final Map<BigInteger, Double> googleReviewMap, final Map<BigInteger, Double> seekaReviewMap, final Double courseStar,
			final BigInteger instituteId) {
		Double courseStars = 0d;
		Double googleReview = 0d;
		Double seekaReview = 0d;
		int count = 0;
		if (courseStar != null) {
			courseStars = courseStar;
			count++;
		}
		if (googleReviewMap.get(instituteId) != null) {
			googleReview = googleReviewMap.get(instituteId);
			count++;
		}
		if (seekaReviewMap.get(instituteId) != null) {
			seekaReview = seekaReviewMap.get(instituteId);
			count++;
		}
		System.out.println("course Rating" + courseStar);
		System.out.println("course Google Rating" + googleReview);
		System.out.println("course Seeka Rating" + seekaReview);

		Double rating = Double.sum(courseStars, googleReview);
		if (count != 0) {
			Double finalRating = Double.sum(rating, seekaReview);
			return finalRating / count;
		} else {
			return 0d;
		}
	}

	@Override
	public Map<String, Object> getAllCourse() {
		Map<String, Object> response = new HashMap<>();
		List<Course> courses = new ArrayList<>();
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

	@Override
	public Map<String, Object> courseFilter(final CourseFilterDto courseFilter) {
		Map<String, Object> response = new HashMap<>();
		List<CourseRequest> courses = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = iCourseDAO.findTotalCountCourseFilter(courseFilter);
			int startIndex = (courseFilter.getPageNumber() - 1) * courseFilter.getMaxSizePerPage();
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseFilter.getMaxSizePerPage(), totalCount);
			courses = iCourseDAO.courseFilter(startIndex, courseFilter.getMaxSizePerPage(), courseFilter);

			List<CourseRequest> resultList = new ArrayList<>();

			for (CourseRequest courseRequest : courses) {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(),
						null, "en");
				courseRequest.setStorageList(storageDTOList);
				resultList.add(courseRequest);
			}

			if (resultList != null && !resultList.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.COURSE_GET_SUCCESS_MESSAGE);
				response.put("courses", resultList);
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
	public Map<String, Object> autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		Map<String, Object> response = new HashMap<>();
		List<CourseRequest> courses = new ArrayList<>();
		List<CourseRequest> resultList = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = iCourseDAO.autoSearchTotalCount(searchKey);
			int startIndex = (pageNumber - 1) * pageSize;
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			courses = iCourseDAO.autoSearch(startIndex, pageSize, searchKey);
			for (CourseRequest courseRequest : courses) {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(),
						null, "en");
				courseRequest.setStorageList(storageDTOList);
				resultList.add(courseRequest);
			}

			if (resultList != null && !resultList.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.COURSE_GET_SUCCESS_MESSAGE);
				response.put("courses", resultList);
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
	public List<Course> facultyWiseCourseForInstitute(final List<Faculty> facultyList, final Institute institute) {
		return iCourseDAO.facultyWiseCourseForTopInstitute(facultyList, institute);
	}

	@Override
	public void saveCourseMinrequirement(final CourseMinRequirementDto courseMinRequirementDto) {
		convertDtoToCourseMinRequirement(courseMinRequirementDto);
	}

	public void convertDtoToCourseMinRequirement(final CourseMinRequirementDto courseMinRequirementDto) {
		Integer i = 0;
		GradeDto gradeDto = new GradeDto();
		List<String> subjectGrade = new ArrayList<>();
		for (String subject : courseMinRequirementDto.getSubject()) {
			System.out.println(subject);
			CourseMinRequirement courseMinRequirement = new CourseMinRequirement();
			courseMinRequirement.setCountry(countryDAO.get(courseMinRequirementDto.getCountry()));
			courseMinRequirement.setSystem(courseMinRequirementDto.getSystem());
			courseMinRequirement.setSubject(courseMinRequirementDto.getSubject().get(i));
			courseMinRequirement.setGrade(courseMinRequirementDto.getGrade().get(i));
			subjectGrade.add(courseMinRequirementDto.getGrade().get(i));
			courseMinRequirement.setCourse(iCourseDAO.get(courseMinRequirementDto.getCourse()));
			courseMinRequirementDao.save(courseMinRequirement);
			i++;
		}
		gradeDto.setCountryId(courseMinRequirementDto.getCountry());
		gradeDto.setEducationSystemId(courseMinRequirementDto.getSystem());
		gradeDto.setSubjectGrades(subjectGrade);
		Double averageGPA = educationSystemService.calGpa(gradeDto);
		CourseGradeEligibility courseGradeEligibility = null;
		courseGradeEligibility = courseGradeEligibilityDao.getAllEnglishEligibilityByCourse(courseMinRequirementDto.getCourse());
		if (courseGradeEligibility != null) {
			courseGradeEligibility.setCountryLevelGpa(averageGPA);
			courseGradeEligibilityDao.update(courseGradeEligibility);
		} else {
			courseGradeEligibility = new CourseGradeEligibility();
			courseGradeEligibility.setGlobalGpa(0.0);
			courseGradeEligibility.setIsActive(true);
			courseGradeEligibility.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			courseGradeEligibility.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			courseGradeEligibility.setCountryLevelGpa(averageGPA);
			courseGradeEligibilityDao.save(courseGradeEligibility);
		}
	}

	@Override
	public List<CourseMinRequirementDto> getCourseMinRequirement(final BigInteger course) {
		return convertCourseMinRequirementToDto(courseMinRequirementDao.get(course));
	}

	public List<CourseMinRequirementDto> convertCourseMinRequirementToDto(final List<CourseMinRequirement> courseMinRequirement) {
		List<CourseMinRequirementDto> resultList = new ArrayList<>();
		Set<BigInteger> countryIds = courseMinRequirement.stream().map(c -> c.getCountry().getId()).collect(Collectors.toSet());
		for (BigInteger countryId : countryIds) {
			List<String> subject = new ArrayList<>();
			List<String> grade = new ArrayList<>();
			List<CourseMinRequirement> filterList = courseMinRequirement.stream().filter(x -> x.getCountry().getId().equals(countryId))
					.collect(Collectors.toList());
			CourseMinRequirementDto courseMinRequirementDto = new CourseMinRequirementDto();
			for (CourseMinRequirement courseMinRequirements : filterList) {
				subject.add(courseMinRequirements.getSubject());
				grade.add(courseMinRequirements.getGrade());
				courseMinRequirementDto.setCountry(courseMinRequirements.getCountry().getId());
				courseMinRequirementDto.setSystem(courseMinRequirements.getSystem());
				courseMinRequirementDto.setCourse(courseMinRequirements.getCourse().getId());
			}
			courseMinRequirementDto.setSubject(subject);
			courseMinRequirementDto.setGrade(grade);
			resultList.add(courseMinRequirementDto);
		}

		return resultList;
	}

	@Override
	public Map<String, Object> autoSearchByCharacter(final String searchKey) {
		Map<String, Object> response = new HashMap<>();
		List<CourseRequest> courses = new ArrayList<>();
		try {
			courses = iCourseDAO.autoSearchByCharacter(1, 50, searchKey);
			if (courses != null && !courses.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.COURSE_GET_SUCCESS_MESSAGE);
				response.put("courses", courses);
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
	public long checkIfCoursesPresentForCountry(final Country country) {
		return iCourseDAO.getCourseCountForCountry(country);
	}

	@Override
	public List<Course> getTopRatedCoursesForCountryWorldRankingWise(final Country country) {
		return iCourseDAO.getTopRatedCoursesForCountryWorldRankingWise(country);
	}

	@Override
	public List<Course> getAllCourseUsingFaculty(final Long facultyId) {
		return iCourseDAO.getAllCourseForFacultyWorldRankingWise(facultyId);
	}

	@Override
	public List<BigInteger> getAllCourseUsingFaculty(final BigInteger facultyId) {
		return iCourseDAO.getAllCourseForFacultyWorldRankingWise(facultyId);
	}

	@Override
	public List<BigInteger> getTopSearchedCoursesByOtherUsers(final BigInteger userId) {
		return viewDao.getOtherUserWatchCourse(userId, "COURSE");
	}

	@Override
	public List<Course> getCoursesById(final List<BigInteger> allSearchCourses) {
		return iCourseDAO.getCoursesFromId(allSearchCourses);
	}

	@Override
	public Map<BigInteger, BigInteger> facultyWiseCourseIdMapForInstitute(final List<Faculty> facultyList, final BigInteger instituteId) {
		return iCourseDAO.facultyWiseCourseIdMapForInstitute(facultyList, instituteId);
	}

	@Override
	public List<Course> getAllCoursesUsingId(final List<BigInteger> listOfRecommendedCourseIds) {
		return iCourseDAO.getAllCoursesUsingId(listOfRecommendedCourseIds);
	}

	@Override
	public List<BigInteger> getTopRatedCourseIdForCountryWorldRankingWise(final Country country) {
		return iCourseDAO.getTopRatedCourseIdsForCountryWorldRankingWise(country);
	}

	@Override
	public List<BigInteger> getTopSearchedCoursesByUsers(final BigInteger userId) {
		return viewDao.getUserWatchCourseIds(userId, "COURSE");
	}

	@Override
	public Set<Course> getRelatedCoursesBasedOnPastSearch(final List<BigInteger> courseList) throws ValidationException {
		Set<Course> relatedCourses = new HashSet<>();
		for (BigInteger courseId : courseList) {
			relatedCourses.addAll(userRecommedationService.getRelatedCourse(courseId));
		}
		return relatedCourses;
	}

	@Override
	public Long getCountOfDistinctInstitutesOfferingCoursesForCountry(final UserDto userDto, final Country country) {
		return iCourseDAO.getCountOfDistinctInstitutesOfferingCoursesForCountry(userDto, country);
	}

	@Override
	public List<BigInteger> getCountryForTopSearchedCourses(final List<BigInteger> topSearchedCourseIds) throws ValidationException {
		if (topSearchedCourseIds == null || topSearchedCourseIds.isEmpty()) {
			throw new ValidationException(messageByLocaleService.getMessage("no.course.id.specified", new Object[] {}));
		}
		return iCourseDAO.getDistinctCountryBasedOnCourses(topSearchedCourseIds);
	}

	private List<BigInteger> getCourseListBasedForCourseOnParameters(final BigInteger courseId, final BigInteger instituteId, final BigInteger facultyId,
			final BigInteger countryId, final BigInteger cityId) {
		List<BigInteger> courseIdList = iCourseDAO.getCourseListForCourseBasedOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		return courseIdList;
	}

	@Override
	public List<Long> getUserListForUserWatchCourseFilter(final BigInteger courseId, final BigInteger instituteId, final BigInteger facultyId,
			final BigInteger countryId, final BigInteger cityId) {
		List<BigInteger> courseIdList = getCourseListBasedForCourseOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		if (courseIdList == null || courseIdList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> userIdList = iCourseDAO.getUserListFromUserWatchCoursesBasedOnCourses(courseIdList);
		return userIdList;
	}

	@Override
	public List<BigInteger> courseIdsForCountry(final Country country) {
		return iCourseDAO.getCourseIdsForCountry(country);
	}

	@Override
	public List<BigInteger> courseIdsForMigratedCountries(final Country country) {
		List<GlobalData> countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());
		List<BigInteger> otherCountryIds = new ArrayList<>();
		if (countryWiseStudentCountListForUserCountry == null || countryWiseStudentCountListForUserCountry.isEmpty()) {
			countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList("China");
		}

		for (GlobalData globalDataDto : countryWiseStudentCountListForUserCountry) {
			Country con = iCountryService.getCountryBasedOnCitizenship(globalDataDto.getDestinationCountry());
			if (!(con == null || con.getId() == null)) {
				otherCountryIds.add(con.getId());
			}
		}
		if (!otherCountryIds.isEmpty()) {
			List<BigInteger> courseIds = iCourseDAO.getAllCoursesForCountry(otherCountryIds);
			return courseIds;
		}
		return new ArrayList<>();
	}

	@Override
	public void updateCourseForCurrency(final CurrencyRate currencyRate) {
		iCourseDAO.updateCourseForCurrency(currencyRate);
	}

	@Override
	public int getCountOfAdvanceSearch(final AdvanceSearchDto courseSearchDto) throws ValidationException, NotFoundException {

		return iCourseDAO.getCountOfAdvanceSearch(courseSearchDto);
	}

	@Override
	public List<CourseDTOElasticSearch> getUpdatedCourses(final Date date, final Integer startIndex, final Integer limit) {
		return iCourseDAO.getUpdatedCourses(date, startIndex, limit);
	}

	@Override
	public Integer getCountOfTotalUpdatedCourses(final Date utCdatetimeAsOnlyDate) {
		return iCourseDAO.getCountOfTotalUpdatedCourses(utCdatetimeAsOnlyDate);
	}

	@Override
	public List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(final List<BigInteger> courseIds, final Integer startIndex, final Integer limit) {
		return iCourseDAO.getCoursesToBeRetriedForElasticSearch(courseIds, startIndex, limit);
	}

	@Override
	public List<CourseIntake> getCourseIntakeBasedOnCourseId(final BigInteger courseId) {
		return iCourseDAO.getCourseIntakeBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseId(final BigInteger courseId) {
		return iCourseDAO.getCourseDeliveryMethodBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseLanguage> getCourseLanguageBasedOnCourseId(final BigInteger courseId) {
		return iCourseDAO.getCourseLanguageBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseResponseDto> getCourseNoResultRecommendation(final String userCountry, final BigInteger facultyId, final BigInteger countryId,
			final Integer startIndex, final Integer pageSize) throws ValidationException {
		/**
		 * Find course based on faculty and country.
		 */
		List<CourseResponseDto> courseResponseDtos = userRecommedationService.getCourseNoResultRecommendation(facultyId, countryId, startIndex, pageSize);
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		/**
		 * If the desired result not found with faculty and country then find based on
		 * global_student_data.
		 *
		 * Courses find based on user's citizenship -> based on citizenship will find
		 * from user citizenship(country)'s students are migrated in which country. and
		 * based on that we will find courses for that migrated country.
		 *
		 * For Example Users' citizenship is India & Indian students are most migrated
		 * in the USA. So, as a result, we will fetch the USA's courses.
		 *
		 */
		if (courseResponseDtos.size() <= pageSize) {
			List<GlobalData> globalDatas = iGlobalStudentDataDAO.getCountryWiseStudentList(userCountry);
			if (!globalDatas.isEmpty()) {
				Country country = countryDAO.getCountryByName(globalDatas.get(0).getDestinationCountry());
				courseSearchDto.setCountryIds(Arrays.asList(country.getId()));
				courseSearchDto.setMaxSizePerPage(pageSize - courseResponseDtos.size());
				List<CourseResponseDto> courseResponseDtos2 = iCourseDAO.getAllCoursesByFilter(courseSearchDto, null, null, startIndex, false);
				courseResponseDtos.addAll(courseResponseDtos2);
			}
		}
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);

	}

	@Override
	public List<String> getCourseKeywordRecommendation(final BigInteger facultyId, final BigInteger countryId, final BigInteger levelId,
			final Integer startIndex, final Integer pageSize) {
		List<String> courseKeywordRecommended = new ArrayList<>();
		/**
		 * We will find course keyword based on faculty in top10Course.
		 *
		 */
		if (startIndex < 10) {
			courseKeywordRecommended = iTop10CourseService.getTop10CourseKeyword(facultyId);
		}
		/**
		 * If we want more result or no result found from top10Course then we will find
		 * based on faculty in course table.
		 *
		 * In that we will find unique course's name from course tables for same
		 * faculty,country and level.
		 *
		 */
		if (courseKeywordRecommended.isEmpty()) {
			CourseSearchDto courseSearchDto = new CourseSearchDto();
			courseSearchDto.setCountryIds(Arrays.asList(countryId));
			courseSearchDto.setFacultyIds(Arrays.asList(facultyId));
			courseSearchDto.setLevelIds(Arrays.asList(levelId));
			courseSearchDto.setMaxSizePerPage(pageSize);
			courseSearchDto.setSortBy("instituteName");
			List<CourseResponseDto> courseResponseDtos = iCourseDAO.getAllCoursesByFilter(courseSearchDto, null, null, startIndex, true);
			courseKeywordRecommended = courseResponseDtos.stream().map(CourseResponseDto::getName).collect(Collectors.toList());
		}
		return courseKeywordRecommended;
	}

	@Override
	public int getDistinctCourseCount(String courseName) {
		return iCourseDAO.getDistinctCourseCountbyName(courseName);
	}

	@Override
	public List<CourseResponseDto> getDistinctCourseList(Integer startIndex, Integer pageSize,String courseName) {
		return iCourseDAO.getDistinctCourseListByName(startIndex, pageSize, courseName);
		
	}
}
