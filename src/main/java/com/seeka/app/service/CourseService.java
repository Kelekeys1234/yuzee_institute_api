package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseAdditionalInfo;
import com.seeka.app.bean.CourseDeliveryMethod;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseIntake;
import com.seeka.app.bean.CourseLanguage;
import com.seeka.app.bean.CourseMinRequirement;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.UserCompareCourse;
import com.seeka.app.bean.UserCompareCourseBundle;
import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.constant.Type;
import com.seeka.app.dao.CourseAdditionalInfoDao;
import com.seeka.app.dao.CourseEnglishEligibilityDao;
import com.seeka.app.dao.CurrencyDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.ICourseMinRequirementDao;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.IGlobalStudentDataDAO;
import com.seeka.app.dao.ILevelDAO;
import com.seeka.app.dao.IUserMyCourseDAO;
import com.seeka.app.dao.InstituteDao;
import com.seeka.app.dao.ViewDao;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseAdditionalInfoDto;
import com.seeka.app.dto.CourseAddtionalInfoElasticDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.CourseEnglishEligibilityDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseMobileDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.GlobalData;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.dto.NearestCoursesDto;
import com.seeka.app.dto.PaginationResponseDto;
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
import com.seeka.app.processor.CourseAdditionalInfoProcessor;
import com.seeka.app.processor.CourseEnglishEligibilityProcessor;
import com.seeka.app.processor.InstituteGoogleReviewProcessor;
import com.seeka.app.repository.CourseRepository;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class CourseService implements ICourseService {

	@Autowired
	private ICourseDAO iCourseDAO;

	@Autowired
	private CourseEnglishEligibilityDao courseEnglishEligibilityDAO;

	@Autowired
	private InstituteDao iInstituteDAO;

	@Autowired
	private IFacultyDAO facultyDAO;

	@Autowired
	private IUserMyCourseDAO myCourseDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private ICourseMinRequirementDao courseMinRequirementDao;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;

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
	private InstituteGoogleReviewProcessor instituteGoogleReviewProcessor;
	
	@Autowired
	private IUserReviewService iUserReviewService;
	
	@Autowired
	private ILevelDAO iLevelDao;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseAdditionalInfoDao courseAdditionalInfoDao;
	
	@Autowired
	private CourseAdditionalInfoProcessor courseAdditionalInfoProcessor;
	
	@Autowired
	private CourseEnglishEligibilityProcessor courseEnglishEligibilityProcessor;
	
	@Value("${max.radius}")
	private Integer maxRadius;
	
	@Override
	public Course get(final String id) {
		return iCourseDAO.get(id);
	}

	@Override
	public List<Course> getAll() {
		return iCourseDAO.getAll();
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto, final Integer startIndex, final Integer pageSize,
			final String searchKeyword) throws ValidationException {
		log.debug("Inside getAllCoursesByFilter() method");
		log.info("CAlling DAO layer to fetch courses based on passed filters and pagination");
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.getAllCoursesByFilter(courseSearchDto, searchKeyword, null, startIndex, false);
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);
	}

	private List<CourseResponseDto> getExtraInfoOfCourseFilter(final CourseSearchDto courseSearchDto, final List<CourseResponseDto> courseResponseDtos)
			throws ValidationException {
		log.debug("Inside getExtraInfoOfCourseFilter() method");
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			log.info("Data is coming null from DB based on filter hence making new Object");
			return new ArrayList<>();
		}
		log.info("Filering course response if ID is coming duplicate in response");
		List<CourseResponseDto> courseResponseFinalResponse = courseResponseDtos.stream().filter(CommonUtil.distinctByKey(CourseResponseDto::getId))
				.collect(Collectors.toList());
		log.info("Collecting CourseIds by stream API and calling store it in list");
		List<String> courseIds = courseResponseDtos.stream().filter(CommonUtil.distinctByKey(CourseResponseDto::getId)).map(CourseResponseDto::getId).collect(Collectors.toList());

		List<String> viewedCourseIds = new ArrayList<>();
		if (courseSearchDto.getUserId() != null) {
			log.info("Fetching user view data from DB based on userId = "+courseSearchDto.getUserId());
			viewedCourseIds = iViewService.getUserViewDataBasedOnEntityIdList(courseSearchDto.getUserId(), "COURSE", courseIds);
		}
		
		log.info("Calling Storage service to get institute images from DB");
		List<StorageDto> storageDTOList = iStorageService.getStorageInformationBasedOnEntityIdList(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), 
				ImageCategory.INSTITUTE.toString(), null, "en");
		
		if(!CollectionUtils.isEmpty(courseResponseFinalResponse)) {
			log.info("Courses are coming from DB hence start iterating data");
			for (CourseResponseDto courseResponseDto : courseResponseFinalResponse) {
				if (storageDTOList != null && !storageDTOList.isEmpty()) {
					log.info("Storage data is coming hence iterating storage data and set it in response");
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
				log.info("Filtering course additional info by matching courseIds");
				List<CourseAdditionalInfoDto> additionalInfoDtos = courseResponseDto.getCourseAdditionalInfo().stream().
							filter(x -> x.getCourseId().equals(courseResponseDto.getId())).collect(Collectors.toList());
				courseResponseDto.setCourseAdditionalInfo(additionalInfoDtos);
				
				log.info("Fetching courseIntake from DB having courseIds = "+courseIds);
				List<CourseIntake> courseIntakes = iCourseDAO.getCourseIntakeBasedOnCourseId(courseResponseDto.getId());
				if (!CollectionUtils.isEmpty(courseIntakes)) {
					log.info("Filtering courseIntakes data based on courseId");
					courseResponseDto.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				} else {
					courseResponseDto.setIntake(new ArrayList<>());
				}
				
				log.info("Fetching courseLanguages from DB having courseIds = "+courseIds);
				List<CourseLanguage> courseLanguages = iCourseDAO.getCourseLanguageBasedOnCourseId(courseResponseDto.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					courseResponseDto.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				} else {
					courseResponseDto.setLanguage(new ArrayList<>());
				}
			}
		}
		return courseResponseFinalResponse;
	}

	@Override
	public int getCountforNormalCourse(final CourseSearchDto courseSearchDto, final String searchKeyword) {
		return iCourseDAO.getCountforNormalCourse(courseSearchDto, searchKeyword);
	}

	@Override
	public List<CourseResponseDto> getAllCoursesByInstitute(final String instituteId, final CourseSearchDto courseSearchDto) {
		return iCourseDAO.getAllCoursesByInstitute(instituteId, courseSearchDto);
	}

	@Override
	public Map<String, Object> getCourse(final String courseId) {
		return iCourseDAO.getCourse(courseId);
	}

	@Override
	public List<CourseResponseDto> getCouresesByFacultyId(final String facultyId) throws NotFoundException {
		log.debug("Inside getCouresesByFacultyId() method");
		log.info("fetching courses from DB for facultyId = "+facultyId);
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.getCouresesByFacultyId(facultyId);
		if(!CollectionUtils.isEmpty(courseResponseDtos)) {
			log.info("Courses coming from DB start iterating data to make final response");
			courseResponseDtos.stream().forEach(courseResponseDto -> {
				log.info("Fetching course additional info from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setCourseAdditionalInfo(courseAdditionalInfoProcessor.getCourseAdditionalInfoByCourseId(courseResponseDto.getId()));
				log.info("Fetching course intakes from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setIntake(iCourseDAO.getCourseIntakeBasedOnCourseId(courseResponseDto.getId())
							.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				log.info("Fetching course languages from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setLanguage(iCourseDAO.getCourseLanguageBasedOnCourseId(courseResponseDto.getId())
						.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
			});
		} else {
			log.error("No Courses found in DB for facultyId = "+facultyId);
			throw new NotFoundException("No Courses found in DB for facultyId = "+facultyId);
		}
		return courseResponseDtos;
	}

	@Override
	public List<CourseResponseDto> getCouresesByListOfFacultyId(final String facultyId) {
		String[] citiesArray = facultyId.split(",");
		String tempList = "";
		for (String id : citiesArray) {
			tempList = tempList + "," + "'" + id + "'";
		}
		return iCourseDAO.getCouresesByListOfFacultyId(tempList.substring(1, tempList.length()));
	}

	@Override
	public String saveCourse(@Valid final CourseRequest courseDto) throws ValidationException {
		log.debug("Inside saveCourse() method");
		Course course = new Course();
		log.info("Fetching institute details from DB for instituteId = "+courseDto.getInstituteId());
		course.setInstitute(getInstititute(courseDto.getInstituteId()));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		course.setIsActive(true);
		log.info("Fetching faculty details from DB for facultyId = "+courseDto.getFacultyId());
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		if (courseDto.getLevelId() != null) {
			log.info("Fetching level details from DB for levelId = "+courseDto.getLevelId());
			course.setLevel(iLevelService.get(courseDto.getLevelId()));
		}
		if (courseDto.getStars() != null && !courseDto.getStars().isEmpty()) {
			log.info("Course stars is present adding it in course bean class");
			course.setStars(Integer.valueOf(courseDto.getStars()));
		}
		if (courseDto.getWorldRanking() != null && !courseDto.getWorldRanking().isEmpty()) {
			log.info("World Ranking is present adding it in course bean class");
			course.setWorldRanking(Integer.valueOf(courseDto.getWorldRanking()));
		}
		
		// Adding course details in bean class
		course.setLink(courseDto.getLink() != null ? courseDto.getLink() : null);
		course.setRemarks(courseDto.getRemarks() != null ? courseDto.getRemarks() : null);
		course.setContact(courseDto.getContact() != null ? courseDto.getContact() : null);
		course.setWebsite(courseDto.getWebsite() != null ? courseDto.getWebsite() : null);
		course.setAvailabilty(courseDto.getAvailbility() != null ? courseDto.getAvailbility(): null);
		course.setRecognition(courseDto.getRecognition() != null ? courseDto.getRecognition() : null);
		course.setJobFullTime(courseDto.getJobFullTime() != null ? courseDto.getJobFullTime() : null);
		course.setJobPartTime(courseDto.getJobPartTime() != null ? courseDto.getJobPartTime() : null);
		course.setAbbreviation(courseDto.getAbbreviation() != null ? courseDto.getAbbreviation() : null);
		course.setCurrencyTime(courseDto.getCurrencyTime() != null ? courseDto.getCurrencyTime() : null);
		course.setOpeningHourTo(courseDto.getOpeningHourTo() != null ? courseDto.getOpeningHourTo() : null);
		course.setCampusLocation(courseDto.getCampusLocation() != null ? courseDto.getCampusLocation() : null);
		course.setRecognitionType(courseDto.getRecognitionType() != null ? courseDto.getRecognitionType() : null);
		course.setOpeningHourFrom(courseDto.getOpeningHourFrom() != null ? courseDto.getOpeningHourFrom() : null);
		
		// Here we convert price in USD and everywhere we used USD price column only.
		CurrencyRate currencyRate = null;
		if (courseDto.getCurrency() != null) {
			course.setCurrency(courseDto.getCurrency());
			log.info("Currency code is not null, hence fetching currencyRate from DB having currencyCode = "+courseDto.getCurrency());
			currencyRate = getCurrencyRate(courseDto.getCurrency());
			if (currencyRate == null) {
				log.error("Invalid currency, no USD conversion exists for this currency");
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
		}
		
		// Adding auditing fields in bean class object
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setUpdatedBy("API");
		course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		
		log.info("Calling DAO layer to save course in DB");
		iCourseDAO.save(course);

		// Here multiple intakes are possible.
		List<Date> intakeList = new ArrayList<>();
		if (courseDto.getIntake() != null) {
			log.info("Course saved now going to save course intakes in DB");
			for (Date intake : courseDto.getIntake()) {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setCourse(course);
				courseIntake.setIntakeDates(intake);
				intakeList.add(intake);
				log.info("Calling DAO layer to save courseIntakes");
				iCourseDAO.saveCourseIntake(courseIntake);
			}
		}
		
		// Course can have multiple language
		if (courseDto.getLanguage() != null && !courseDto.getLanguage().isEmpty()) {
			log.info("Course saved now going to save course languages in DB");
			for (String language : courseDto.getLanguage()) {
				CourseLanguage courseLanguage = new CourseLanguage();
				courseLanguage.setCourse(course);
				courseLanguage.setLanguage(language);
				courseLanguage.setCreatedBy("API");
				courseLanguage.setCreatedOn(new Date());
				log.info("Calling DAO layer to save courseLanguages");
				iCourseDAO.saveCourseLanguage(courseLanguage);
			}
		}

		// There are EnglishEligibility means IELTS & TOFEL score
		if (courseDto.getEnglishEligibility() != null) {
			log.info("Course saved now going to save course EnglishEligibility in DB");
			for (CourseEnglishEligibilityDto courseEnglishEligibilityDto : courseDto.getEnglishEligibility()) {
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				BeanUtils.copyProperties(courseEnglishEligibilityDto, courseEnglishEligibility);
				courseEnglishEligibility.setCourse(course);
				courseEnglishEligibility.setIsActive(true);
				courseEnglishEligibility.setCreatedBy("API");
				courseEnglishEligibility.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
				log.info("Calling DAO layer to save englishEligibilties in DB");
				courseEnglishEligibilityDAO.save(courseEnglishEligibility);
			}
		}
		
		// Here we are adding course additional informations like courseFee, duration, studyMode etc
		List<CourseAddtionalInfoElasticDto> courseAddtionalInfoElasticDtos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(courseDto.getCourseAdditionalInfo()) && !ObjectUtils.isEmpty(currencyRate)) {
			log.info("Course saved now going to save course AdditionalInfo in DB");
			for (CourseAdditionalInfoDto courseAdditionalInfo : courseDto.getCourseAdditionalInfo()) {
				CourseAdditionalInfo additionalInfo = new CourseAdditionalInfo();
				if (courseDto.getCurrency() != null) {
					if (courseAdditionalInfo.getDomesticFee() != null) {
						log.info("converting domestic fee into usdDomestic fee having conversionRate = "+currencyRate.getConversionRate());
						Double convertedRate = Double.valueOf(courseAdditionalInfo.getDomesticFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfo.setUsdDomesticFee(convertedRate);
						}
					}
					if (courseAdditionalInfo.getInternationalFee() != null) {
						log.info("converting international fee into usdInternational fee having conversionRate = "+currencyRate.getConversionRate());
						Double convertedRate = Double.valueOf(courseAdditionalInfo.getInternationalFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfo.setUsdInternationalFee(convertedRate);
						}
					}
				}
				additionalInfo.setCourse(course);
				additionalInfo.setCreatedBy("API");
				additionalInfo.setCreatedOn(new Date());
				log.info("Adding additional infos like deliveryType, studyMode etc");
				additionalInfo.setDeliveryType(courseAdditionalInfo.getDeliveryType());
				additionalInfo.setDomesticFee(courseAdditionalInfo.getDomesticFee());
				additionalInfo.setInternationalFee(courseAdditionalInfo.getInternationalFee());
				additionalInfo.setStudyMode(courseAdditionalInfo.getStudyMode());
				additionalInfo.setDuration(courseAdditionalInfo.getDuration());
				additionalInfo.setDurationTime(courseAdditionalInfo.getDurationTime());
				log.info("Calling DAO layer to save courseAdditionalInfo in DB");
				courseAdditionalInfoDao.saveCourseAdditionalInfo(additionalInfo);
				
				// Adding course additionalInfo in elastic DTO to save it on course elastic index
				CourseAddtionalInfoElasticDto courseAddtionalInfoElasticDto = new CourseAddtionalInfoElasticDto();
				BeanUtils.copyProperties(additionalInfo, courseAddtionalInfoElasticDto);
				courseAddtionalInfoElasticDtos.add(courseAddtionalInfoElasticDto);
			}
		}

		// Here we converted course request to elastic search form few changes in Elastic search object
		CourseDTOElasticSearch courseElasticSearch = new CourseDTOElasticSearch();
		log.info("Copying course bean class to courseElastic DTO class through beanUtils");
		BeanUtils.copyProperties(course, courseElasticSearch);
		log.info("Adding facultyName, description details in elastic DTO");
		courseElasticSearch.setFacultyName(course.getFaculty() != null ? course.getFaculty().getName() : null);
		courseElasticSearch.setFacultyDescription(course.getFaculty() != null ? course.getFaculty().getDescription() : null);
		log.info("Adding instituteName, latitude, longitude details in elastic DTO");
		courseElasticSearch.setInstituteName(course.getInstitute() != null ? course.getInstitute().getName() : null);
		courseElasticSearch.setLatitute(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLatitude()) : null);
		courseElasticSearch.setLongitude(course.getInstitute() != null ? String.valueOf(course.getInstitute().getLongitude()) : null);
		log.info("Adding levelCode, levelName in elastic DTO");
		courseElasticSearch.setLevelCode(course.getLevel() != null ? course.getLevel().getCode() : null);
		courseElasticSearch.setLevelName(course.getLevel() != null ? course.getLevel().getName() : null);
		log.info("Adding intakes in elastic DTO");
		courseElasticSearch.setIntake(!intakeList.isEmpty() ? intakeList : null);
		log.info("Adding courseAddtionalInfos in elastic DTO");
		courseElasticSearch.setCourseAdditionalInfo(courseAddtionalInfoElasticDtos);
		log.info("Adding courseLanguages in elastic DTO");
		courseElasticSearch.setLanguage(courseDto.getLanguage());
		List<CourseDTOElasticSearch> courseListElasticDTO = new ArrayList<>();
		courseListElasticDTO.add(courseElasticSearch);
		log.info("Calling elastic service to add courses on elastic index");
		elasticSearchService.saveCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE, SeekaEntityType.COURSE.name().toLowerCase(), courseListElasticDTO,
				IConstant.ELASTIC_SEARCH);
		return course.getId();
	}

	@Override
	public String updateCourse(final CourseRequest courseDto, final String id) throws ValidationException {
		log.debug("Inside updateCourse() method");
		Course course = new Course();
		log.info("fetching course from DB having courseId = "+id);
		course = iCourseDAO.get(id);
		course.setId(id);
		log.info("Fetching institute details from DB for instituteId = "+courseDto.getInstituteId());
		course.setInstitute(getInstititute(courseDto.getInstituteId()));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		log.info("Fetching faculty details from DB for facultyId = "+courseDto.getFacultyId());
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		if (courseDto.getLevelId() != null) {
			log.info("Fetching level details from DB for levelId = "+courseDto.getLevelId());
			course.setLevel(iLevelService.get(courseDto.getLevelId()));
		}
		course.setIsActive(true);
		if (courseDto.getStars() != null && !courseDto.getStars().isEmpty()) {
			log.info("Course stars is present adding it in course bean class");
			course.setStars(Integer.valueOf(courseDto.getStars()));
		}

		if (courseDto.getWorldRanking() != null && !courseDto.getWorldRanking().isEmpty()) {
			log.info("World Ranking is present adding it in course bean class");
			course.setWorldRanking(Integer.valueOf(courseDto.getWorldRanking()));
		}
		
		// Adding course details in bean class
		course.setLink(courseDto.getLink() != null ? courseDto.getLink() : null);
		course.setRemarks(courseDto.getRemarks() != null ? courseDto.getRemarks() : null);
		course.setContact(courseDto.getContact() != null ? courseDto.getContact() : null);
		course.setWebsite(courseDto.getWebsite() != null ? courseDto.getWebsite() : null);
		course.setAvailabilty(courseDto.getAvailbility() != null ? courseDto.getAvailbility(): null);
		course.setRecognition(courseDto.getRecognition() != null ? courseDto.getRecognition() : null);
		course.setJobFullTime(courseDto.getJobFullTime() != null ? courseDto.getJobFullTime() : null);
		course.setJobPartTime(courseDto.getJobPartTime() != null ? courseDto.getJobPartTime() : null);
		course.setAbbreviation(courseDto.getAbbreviation() != null ? courseDto.getAbbreviation() : null);
		course.setCurrencyTime(courseDto.getCurrencyTime() != null ? courseDto.getCurrencyTime() : null);
		course.setOpeningHourTo(courseDto.getOpeningHourTo() != null ? courseDto.getOpeningHourTo() : null);
		course.setCampusLocation(courseDto.getCampusLocation() != null ? courseDto.getCampusLocation() : null);
		course.setRecognitionType(courseDto.getRecognitionType() != null ? courseDto.getRecognitionType() : null);
		course.setOpeningHourFrom(courseDto.getOpeningHourFrom() != null ? courseDto.getOpeningHourFrom() : null);
		
		// Here we convert price in USD and everywhere we used USD price column only.
		CurrencyRate currencyRate = null;
		if (courseDto.getCurrency() != null) {
			course.setCurrency(courseDto.getCurrency());
			log.info("Currency code is not null, hence fetching currencyRate from DB having currencyCode = "+courseDto.getCurrency());
			currencyRate = getCurrencyRate(courseDto.getCurrency());
			if (currencyRate == null) {
				log.error("Invalid currency, no USD conversion exists for this currency");
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
		}
		
		// Adding auditing fields in bean class object
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setUpdatedBy("API");
		course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		
		log.info("Calling DAO layer to update course in DB for courseId = "+id);
		iCourseDAO.update(course);
		log.info("Delete existing courseIntakes from DB");
		iCourseDAO.deleteCourseIntake(id);
		log.info("Delete existing courseLanguage from DB");
		iCourseDAO.deleteCourseLanguage(id);
		
		// Here multiple intakes are possible.
		List<Date> intakeList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(courseDto.getIntake())) {
			log.info("Course saved now going to update course intakes in DB");
			for (Date intake : courseDto.getIntake()) {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setCourse(course);
				courseIntake.setIntakeDates(intake);
				intakeList.add(intake);
				log.info("Calling DAO layer to update courseIntakes");
				iCourseDAO.saveCourseIntake(courseIntake);
			}
		}

		// Course can have multiple language
		if (courseDto.getLanguage() != null && !courseDto.getLanguage().isEmpty()) {
			log.info("Course saved now going to update course languages in DB");
			for (String language : courseDto.getLanguage()) {
				CourseLanguage courseLanguage = new CourseLanguage();
				courseLanguage.setCourse(course);
				courseLanguage.setLanguage(language);
				log.info("Calling DAO layer to update courseLanguages");
				iCourseDAO.saveCourseLanguage(courseLanguage);
			}
		}

		// There are EnglishEligibility means IELTS & TOFEL score
		if (courseDto.getEnglishEligibility() != null) {
			log.info("Fetching englishEligibilty from DB for courseId = "+id);
			List<CourseEnglishEligibility> courseEnglishEligibilityList = courseEnglishEligibilityDAO
					.getAllEnglishEligibilityByCourse(id);
			log.info("The English Eligibility Size: " + courseEnglishEligibilityList.size());
			if (!courseEnglishEligibilityList.isEmpty()) {
				log.info("EnglishEligibilty is present in DB hence making it inactive");
				for (CourseEnglishEligibility courseEnglishEligibility : courseEnglishEligibilityList) {
					if (courseEnglishEligibility.getIsActive()) {
						courseEnglishEligibility.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
						courseEnglishEligibility.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
						courseEnglishEligibility.setIsActive(false);
						log.info("Going to update englishEligibilty from DB and make it inactive");
						courseEnglishEligibilityDAO.update(courseEnglishEligibility);
					}
				}
			}
			for (CourseEnglishEligibilityDto courseEnglishEligibilityDto : courseDto.getEnglishEligibility()) {
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				BeanUtils.copyProperties(courseEnglishEligibilityDto, courseEnglishEligibility);
				courseEnglishEligibility.setCourse(course);
				courseEnglishEligibility.setIsActive(true);
				courseEnglishEligibility.setCreatedBy("API");
				courseEnglishEligibility.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
				log.info("Calling DAO layer to save englishEligibilties in DB");
				courseEnglishEligibilityDAO.save(courseEnglishEligibility);
			}
		}

		// Here we are adding course additional informations like courseFee, duration, studyMode etc
		List<CourseAddtionalInfoElasticDto> courseAddtionalInfoElasticDtos = new ArrayList<>();
		if (!CollectionUtils.isEmpty(courseDto.getCourseAdditionalInfo()) && !ObjectUtils.isEmpty(currencyRate)) {
			log.info("Course saved now going to update course AdditionalInfo in DB and fetching additionalInfo from DB");
			List<CourseAdditionalInfo> courseAdditionalInfoListFromDB = courseAdditionalInfoDao.getCourseAdditionalInfoByCourseId(id);
			courseAdditionalInfoListFromDB.stream().forEach(courseAdditionalInfoFromDB -> {
				log.info("Deleting courseAdditional Info from DB for infoId = "+courseAdditionalInfoFromDB.getId());
				courseAdditionalInfoDao.deleteCourseAdditionalInfo(courseAdditionalInfoFromDB);
			});
			for (CourseAdditionalInfoDto courseAdditionalInfo : courseDto.getCourseAdditionalInfo()) {
				CourseAdditionalInfo additionalInfo = new CourseAdditionalInfo();
				if (courseDto.getCurrency() != null) {
					if (courseAdditionalInfo.getDomesticFee() != null) {
						log.info("converting domestic fee into usdDomestic fee having conversionRate = "
									+ currencyRate.getConversionRate());
						Double convertedRate = Double.valueOf(courseAdditionalInfo.getDomesticFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfo.setUsdDomesticFee(convertedRate);
						}
					}
					if (courseAdditionalInfo.getInternationalFee() != null) {
						log.info("converting international fee into usdInternational fee having conversionRate = "
									+ currencyRate.getConversionRate());
						Double convertedRate = Double.valueOf(courseAdditionalInfo.getInternationalFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							additionalInfo.setUsdInternationalFee(convertedRate);
						}
					}
				}
				additionalInfo.setCourse(course);
				additionalInfo.setCreatedBy("API");
				additionalInfo.setCreatedOn(new Date());
				log.info("Adding additional infos like deliveryType, studyMode etc");
				additionalInfo.setDeliveryType(courseAdditionalInfo.getDeliveryType());
				additionalInfo.setDomesticFee(courseAdditionalInfo.getDomesticFee());
				additionalInfo.setInternationalFee(courseAdditionalInfo.getInternationalFee());
				additionalInfo.setStudyMode(courseAdditionalInfo.getStudyMode());
				additionalInfo.setDuration(courseAdditionalInfo.getDuration());
				additionalInfo.setDurationTime(courseAdditionalInfo.getDurationTime());
				log.info("Calling DAO layer to save courseAdditionalInfo in DB");
				courseAdditionalInfoDao.saveCourseAdditionalInfo(additionalInfo);

				// Adding course additionalInfo in elastic DTO to save it on course elastic index
				CourseAddtionalInfoElasticDto courseAddtionalInfoElasticDto = new CourseAddtionalInfoElasticDto();
				BeanUtils.copyProperties(additionalInfo, courseAddtionalInfoElasticDto);
				courseAddtionalInfoElasticDtos.add(courseAddtionalInfoElasticDto);
			}
		}

		// Here we converted course request to elastic search form few changes in Elastic search object
		CourseDTOElasticSearch courseElasticSearch = new CourseDTOElasticSearch();
		log.info("Copying course bean class to courseElastic DTO class through beanUtils");
		BeanUtils.copyProperties(course, courseElasticSearch);
		log.info("Adding facultyName, description details in elastic DTO");
		courseElasticSearch.setFacultyName(course.getFaculty() != null ? course.getFaculty().getName() : null);
		courseElasticSearch
				.setFacultyDescription(course.getFaculty() != null ? course.getFaculty().getDescription() : null);
		log.info("Adding instituteName, latitude, longitude details in elastic DTO");
		courseElasticSearch.setInstituteName(course.getInstitute() != null ? course.getInstitute().getName() : null);
		courseElasticSearch.setLatitute(
				course.getInstitute() != null ? String.valueOf(course.getInstitute().getLatitude()) : null);
		courseElasticSearch.setLongitude(
				course.getInstitute() != null ? String.valueOf(course.getInstitute().getLongitude()) : null);
		log.info("Adding levelCode, levelName in elastic DTO");
		courseElasticSearch.setLevelCode(course.getLevel() != null ? course.getLevel().getCode() : null);
		courseElasticSearch.setLevelName(course.getLevel() != null ? course.getLevel().getName() : null);
		log.info("Adding intakes in elastic DTO");
		courseElasticSearch.setIntake(!intakeList.isEmpty() ? intakeList : null);
		log.info("Adding courseAddtionalInfos in elastic DTO");
		courseElasticSearch.setCourseAdditionalInfo(courseAddtionalInfoElasticDtos);
		log.info("Adding courseLanguages in elastic DTO");
		courseElasticSearch.setLanguage(courseDto.getLanguage());
		List<CourseDTOElasticSearch> courseListElasticDTO = new ArrayList<>();
		courseListElasticDTO.add(courseElasticSearch);
		log.info("Calling elastic service to update courses on elastic index having entityId = "+id);
		/*elasticSearchService.updateCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE,
				SeekaEntityType.COURSE.name().toLowerCase(), courseListElasticDTO, IConstant.ELASTIC_SEARCH);*/
		return id;
	}

	private CurrencyRate getCurrencyRate(final String courseCurrency) {
		log.debug("Inside getCurrencyRate() method");
		log.info("Calling DAO layer to getCurrencyRate from DB having currencyCode = "+courseCurrency);
		CurrencyRate currencyRate = currencyDAO.getCurrencyRate(courseCurrency);
		return currencyRate;
	}

//	private Country getCountry(final String countryId) {
//		Country country = null;
//		if (countryId != null) {
//			country = countryDAO.get(countryId);
//		}
//		return country;
//	}

//	private City getCity(final String cityId) {
//		City city = null;
//		if (cityId != null) {
//			city = cityDAO.get(cityId);
//		}
//		return city;
//	}

	private Faculty getFaculty(final String facultyId) {
		Faculty faculty = null;
		if (facultyId != null) {
			faculty = facultyDAO.get(facultyId);
		}
		return faculty;
	}

	private Institute getInstititute(final String instituteId) {
		Institute institute = null;
		if (instituteId != null) {
			institute = iInstituteDAO.get(instituteId);
		}
		return institute;
	}

	@Override
	public PaginationResponseDto getAllCourse(final Integer pageNumber, final Integer pageSize) {
		log.debug("Inside getAllCourse() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			log.info("Getting total count for course to calculate pagination");
			int totalCount = iCourseDAO.findTotalCount();
			int startIndex = (pageNumber - 1) * pageSize;
			log.info("Calling DAO layer to fetch courses from DB with limit = " + startIndex + ", " + pageSize);
			List<CourseRequest> courses = iCourseDAO.getAll(startIndex, pageSize);
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Courses fetched from DB hence start iterating courses");
				List<CourseRequest> resultList = new ArrayList<>();
				courses.stream().forEach(course -> {
					try {
						log.info("Calling Storage service to fetch course images");
						List<StorageDto> storageDTOList = iStorageService.getStorageInformation(course.getInstituteId(), ImageCategory.INSTITUTE.toString(), null, "en");
						course.setStorageList(storageDTOList);
					} catch (ValidationException e) {
						log.error("Error invoking Storage service exception {}",e);
					}
					log.info("Fetching courseAdditionalInfo from DB having courseId = "+course.getId());
					List<CourseAdditionalInfoDto> courseAdditionalInfoDtos = courseAdditionalInfoProcessor.getCourseAdditionalInfoByCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseAdditionalInfoDtos)) {
						log.info("courseAdditionalInfo is fetched from DB, hence adding courseAdditionalInfo in response");
						course.setCourseAdditionalInfo(courseAdditionalInfoDtos);
					}
					
					log.info("Fetching courseEnglishEligibility from DB having courseId = "+course.getId());
					List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos = courseEnglishEligibilityProcessor.getAllEnglishEligibilityByCourse(course.getId());
					if(!CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {
						log.info("courseEnglishEligibility is fetched from DB, hence adding englishEligibilities in response");
						course.setEnglishEligibility(courseEnglishEligibilityDtos);
					}
					resultList.add(course);
				});
				log.info("Calculating pagination based on startIndex, pageSize, totalCount");
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(totalCount);
				paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
				paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
			} else {
				log.error("No Course found in DB");
				throw new NotFoundException("No Course found in DB");
			}
		} catch (Exception exception) {
			log.error("Exception while fetching all courses from DB having exception = ",exception);
		}
		return paginationResponseDto;
	}

	@Override
	public void deleteCourse(final String courseId) {
		log.debug("Inside deleteCourse() method");
		try {
			log.info("Fetching course from DB having courseId = "+ courseId);
			Course course = iCourseDAO.get(courseId);
			if (!ObjectUtils.isEmpty(course)) {
				log.info("Course found hence making course inactive");
				course.setIsActive(false);
				course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				course.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
				course.setIsDeleted(true);
				log.info("Calling DAO layer to update existing course and make in in-active");
				iCourseDAO.update(course);

				CourseDTOElasticSearch elasticSearchCourseDto = new CourseDTOElasticSearch();
				elasticSearchCourseDto.setId(courseId);
				List<CourseDTOElasticSearch> courseDtoESList = new ArrayList<>();
				courseDtoESList.add(elasticSearchCourseDto);
				log.info("Calling elastic service to update course having entityId = "+ courseId);
				elasticSearchService.deleteCourseOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_COURSE, 
						SeekaEntityType.COURSE.name().toLowerCase(), courseDtoESList, IConstant.ELASTIC_SEARCH);
			} else {
				log.error("Course not found for courseId = "+ courseId);
				throw new NotFoundException("Course not found for courseId = "+ courseId);
			}
		} catch (Exception exception) {
			log.error("Exception while delete existing course from DB having exception = "+ exception);
		}
	}

	@Override
	public Map<String, Object> addUserCourses(@Valid final UserCourse userCourse) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (userCourse.getCourses() != null && !userCourse.getCourses().isEmpty()) {
				for (String courseId : userCourse.getCourses()) {
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
	public Map<String, Object> getUserCourse(final String userId, final Integer pageNumber, final Integer pageSize, final String currencyCode,
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
	public void addUserCompareCourse(@Valid final UserCourse userCourse) {
		log.debug("Inside addUserCompareCourse() method");
		try {
			if (userCourse.getCourses() != null && !userCourse.getCourses().isEmpty()) {
				String compareValue = "";
				log.info("Iterating courseIds comin in request and add in string");
				for (String courseId : userCourse.getCourses()) {
					compareValue += courseId + ",";
				}
				UserCompareCourse compareCourse = new UserCompareCourse();
				compareCourse.setCompareValue(compareValue);
				compareCourse.setCreatedBy(userCourse.getCreatedBy());
				compareCourse.setCreatedOn(new Date());
				compareCourse.setUpdatedBy(userCourse.getUpdatedBy());
				compareCourse.setUpdatedOn(new Date());
				compareCourse.setUserId(userCourse.getUserId());
				log.info("Calling DAO layer to save user compare courses");
				iCourseDAO.saveUserCompareCourse(compareCourse);
				for (String courseId : userCourse.getCourses()) {
					UserCompareCourseBundle compareCourseBundle = new UserCompareCourseBundle();
					compareCourseBundle.setUserId(userCourse.getUserId());
					compareCourseBundle.setCompareCourse(compareCourse);
					compareCourseBundle.setCourse(iCourseDAO.get(courseId));
					log.info("Calling DAO layer to add user compare course bundle in DB");
					iCourseDAO.saveUserCompareCourseBundle(compareCourseBundle);
				}
			}
		} catch (Exception exception) {
			log.error("Error invoke while adding user compare courses in DB having exception = "+exception);
		}
	}

	@Override
	public List<UserCompareCourseResponse> getUserCompareCourse(final String userId) throws NotFoundException {
		log.debug("Inside getUserCompareCourse() method");
		List<UserCompareCourseResponse> compareCourseResponses = new ArrayList<>();
		log.info("Fetching user compare courses from DB having userId = "+userId);
		List<UserCompareCourse> compareCourses = iCourseDAO.getUserCompareCourse(userId);
		if(!CollectionUtils.isEmpty(compareCourses)) {
			log.info("User compare courses found in DB star iterating data to make response");
			compareCourses.stream().forEach(compareCourse -> {
				UserCompareCourseResponse courseResponse = new UserCompareCourseResponse();
				courseResponse.setUserCourseCompareId(compareCourse.getId().toString());
				try {
					log.info("Fetching course information from DB having courseId = "+compareCourse.getCompareValue());
					courseResponse.setCourses(getCourses(compareCourse.getCompareValue()));
				} catch (ValidationException e) {
					log.error("Exception while fetching course from DB for courseId = "+compareCourse.getCompareValue());
				}
				compareCourseResponses.add(courseResponse);
			});
		} else {
			log.error("No user course found in BD for userId = "+userId);
			throw new NotFoundException("No user course found in BD for userId = "+userId);
		}
		return compareCourseResponses;
	}

	private List<CourseRequest> getCourses(final String compareValue) throws ValidationException {
		List<CourseRequest> courses = new ArrayList<>();
		String[] compareValues = compareValue.split(",");
		for (String id : compareValues) {
			CourseRequest courseRequest = iCourseDAO.getCourseById(id);
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(), null, "en");
			courseRequest.setStorageList(storageDTOList);
			courses.add(courseRequest);
		}
		return courses;
	}

	@Override
	public Course getCourseData(final String courseId) {
		return iCourseDAO.getCourseData(courseId);
	}

	@Override
	public Map<String, Object> getAllServices() {
		Map<String, Object> response = new HashMap<>();
		List<com.seeka.app.bean.Service> services = new ArrayList<>();
		try {
			services = iInstituteDAO.getAllServices();
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
		log.debug("Inside advanceSearch() method");
		
		log.info("Calling DAO layer to fetch courses from DB based on passed filters");
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.advanceSearch(courseSearchDto);
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			log.info("No courses found in DB for passed filters");
			return new ArrayList<>();
		}
		log.info("Filtering distinct courses based on courseId and collect in list");
		List<CourseResponseDto> courseResponseFinalResponse = courseResponseDtos.stream().filter(CommonUtil.distinctByKey(CourseResponseDto::getId))
				.collect(Collectors.toList());
		
		log.info("Filtering distinct courseIds");
		List<String> courseIds = courseResponseDtos.stream().filter(CommonUtil.distinctByKey(CourseResponseDto::getId)).map(CourseResponseDto::getId).collect(Collectors.toList());
		
		log.info("Fetching user view data from DB having userId = "+courseSearchDto.getUserId());
		List<String> viewedCourseIds = iViewService.getUserViewDataBasedOnEntityIdList(courseSearchDto.getUserId(), "COURSE", courseIds);
		
		log.info("Calling Storage service to get images based on entityId");
		List<StorageDto> storageDTOList = iStorageService.getStorageInformationBasedOnEntityIdList(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), ImageCategory.INSTITUTE.toString(), null, "en");
		
		log.info("Fetching institute google review from DB based on instituteId");
		Map<String, Double> googleReviewMap = instituteGoogleReviewProcessor
				.getInstituteAvgGoogleReviewForList(courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()));
		Map<String, Double> seekaReviewMap = null;
		try {
			log.info("Calling review service to fetch user average review for instituteId");
			seekaReviewMap = iUserReviewService.getUserAverageReviewBasedOnDataList(
					"INSTITUTE", courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()));
		} catch (Exception e) {
			log.error("Error invoking review service having exception = "+e);
		}
		
		if(!CollectionUtils.isEmpty(courseResponseFinalResponse)) {
			log.info("Courses coming in response, now start iterating courses");
			for (CourseResponseDto courseResponseDto : courseResponseFinalResponse) {
				if (storageDTOList != null && !storageDTOList.isEmpty()) {
					log.info("Storage data is coming hence iterating storage data and set it in response");
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
				
				log.info("Grouping course delivery modes data and add it in final response");
				List<CourseAdditionalInfoDto> additionalInfoDtos = courseResponseDto.getCourseAdditionalInfo().stream().
						filter(x -> x.getCourseId().equals(courseResponseDto.getId())).collect(Collectors.toList());
				courseResponseDto.setCourseAdditionalInfo(additionalInfoDtos);

				log.info("Fetching courseIntake from DB having courseIds = "+courseIds);
				List<CourseIntake> courseIntake = iCourseDAO.getCourseIntakeBasedOnCourseId(courseResponseDto.getId());
				if (courseIntake != null && !courseIntake.isEmpty()) {
					log.info("Filtering courseIntakes data based on courseId");
					courseResponseDto.setIntake(courseIntake.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				} else {
					courseResponseDto.setIntake(new ArrayList<>());
				}
				
				log.info("Fetching courseLanguages from DB having courseIds = "+courseIds);
				List<CourseLanguage> courseLanguages = iCourseDAO.getCourseLanguageBasedOnCourseId(courseResponseDto.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					log.info("Filtering courseLanguages data based on courseId");
					courseResponseDto.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				} else {
					courseResponseDto.setLanguage(new ArrayList<>());
				}
				
				log.info("Calculating average review rating based on reviews");
				courseResponseDto
						.setStars(calculateAverageRating(googleReviewMap, seekaReviewMap, courseResponseDto.getStars(), courseResponseDto.getInstituteId()));
				
				if(!ObjectUtils.isEmpty(courseSearchDto.getLatitude()) && !ObjectUtils.isEmpty(courseSearchDto.getLongitude()) && 
						!ObjectUtils.isEmpty(courseResponseDto.getLatitude()) && !ObjectUtils.isEmpty(courseResponseDto.getLongitude())) {
					log.info("Calculating distance between User lat and long and institute lat and long");
					double distanceInKM = CommonUtil.getDistanceFromLatLonInKm(courseSearchDto.getLatitude(), courseSearchDto.getLongitude(), 
							courseResponseDto.getLatitude(), courseResponseDto.getLongitude());
					courseResponseDto.setDistance(distanceInKM);
				}
			}
		}
		return courseResponseFinalResponse;
	}

	@Override
	public double calculateAverageRating(final Map<String, Double> googleReviewMap, final Map<String, Double> seekaReviewMap, final Double courseStar,
			final String instituteId) {
		log.debug("Inside calculateAverageRating() method");
		Double courseStars = 0d;
		Double googleReview = 0d;
		Double seekaReview = 0d;
		log.info("Calculating avearge rating based on googleReview, seekaReview and course rating");
		int count = 0;
		if (courseStar != null) {
			courseStars = courseStar;
			count++;
		}
		log.info("course Rating = "+ courseStar );
		if (googleReviewMap.get(instituteId) != null) {
			googleReview = googleReviewMap.get(instituteId);
			count++;
		}
		log.info("course Google Rating" + googleReview);
		if (seekaReviewMap.get(instituteId) != null) {
			seekaReview = seekaReviewMap.get(instituteId);
			count++;
		}
		log.info("course Seeka Rating" + seekaReview);
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
	public PaginationResponseDto courseFilter(final CourseFilterDto courseFilter) {
		log.debug("Inside courseFilter() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			log.info("fetched total count of courses based on passed filters");
			int totalCount = iCourseDAO.findTotalCountCourseFilter(courseFilter);
			int startIndex = (courseFilter.getPageNumber() - 1) * courseFilter.getMaxSizePerPage();
			log.info("Fetching course data from DB based on filters and pagination");
			List<CourseRequest> courses = iCourseDAO.courseFilter(startIndex, courseFilter.getMaxSizePerPage(), courseFilter);

			List<CourseRequest> resultList = new ArrayList<>();
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Course are coming from DB hence start making final response");
				courses.stream().forEach(courseRequest -> {
					try {
						log.info("Start invoking Storage service to fetch images");
						List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(), null, "en");
						courseRequest.setStorageList(storageDTOList);
					} catch (ValidationException e) {
						log.error("Error invoking Storage service having exception = "+e);
					}
					log.info("Fetching course additional info from DB having courseId = "+courseRequest.getId());
					courseRequest.setCourseAdditionalInfo(courseAdditionalInfoProcessor.getCourseAdditionalInfoByCourseId(courseRequest.getId()));
					log.info("Fetching course intakes from DB having courseId = "+courseRequest.getId());
					courseRequest.setIntake(iCourseDAO.getCourseIntakeBasedOnCourseId(courseRequest.getId())
								.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
					log.info("Fetching course languages from DB having courseId = "+courseRequest.getId());
					courseRequest.setLanguage(iCourseDAO.getCourseLanguageBasedOnCourseId(courseRequest.getId())
							.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
					
					resultList.add(courseRequest);
				});
				log.info("Calculating pagination based on startIndex, pageSize and totalCount");
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseFilter.getMaxSizePerPage(), totalCount);
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(totalCount);
				paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
				paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
			}
		} catch (Exception exception) {
			log.error("Exception while fetching courses from DB having exception = "+exception);
		}
		return paginationResponseDto;
	}

	@Override
	public PaginationResponseDto autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		log.debug("Inside autoSearch() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			List<CourseRequest> resultList = new ArrayList<>();
			log.info("Fetching total count of courses from DB for searchKey = "+searchKey);
			Long totalCount = iCourseDAO.autoSearchTotalCount(searchKey);
			int startIndex = (pageNumber - 1) * pageSize;
			log.info("Fetching courses from DB based on pagination and having searchKeyword = "+searchKey);
			List<CourseRequest> courses = iCourseDAO.autoSearch(startIndex, pageSize, searchKey);
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Courses fetched from DB, hence start iterating data");
				courses.stream().forEach(course -> {
					try {
						log.info("Calling storage service to fetch course images");
						List<StorageDto> storageDTOList = iStorageService.getStorageInformation(course.getInstituteId(), ImageCategory.INSTITUTE.toString(), null, "en");
						course.setStorageList(storageDTOList);
					} catch (ValidationException e) {
						log.error("Error invoking Storage service having exception = "+e);
					}
					log.info("Fetching courseAdditionalInfo from DB having courseId = "+course.getId());
					List<CourseAdditionalInfoDto> courseAdditionalInfoDtos = courseAdditionalInfoProcessor.getCourseAdditionalInfoByCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseAdditionalInfoDtos)) {
						log.info("courseAdditionalInfo is fetched from DB, hence adding courseAdditionalInfo in response");
						course.setCourseAdditionalInfo(courseAdditionalInfoDtos);
					}
					
					log.info("Fetching courseEnglishEligibility from DB having courseId = "+course.getId());
					List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos = courseEnglishEligibilityProcessor.getAllEnglishEligibilityByCourse(course.getId());
					if(!CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {
						log.info("courseEnglishEligibility is fetched from DB, hence adding englishEligibilities in response");
						course.setEnglishEligibility(courseEnglishEligibilityDtos);
					}
					
					log.info("Fetching courseLanguage from DB having courseId = "+course.getId());
					List<CourseLanguage> courseLanguages = iCourseDAO.getCourseLanguageBasedOnCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseLanguages)) {
						log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
						course.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
					}
					
					log.info("Fetching courseIntake from DB having courseId = "+course.getId());
					List<CourseIntake> courseIntakes = iCourseDAO.getCourseIntakeBasedOnCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseIntakes)) {
						log.info("courseIntake is fetched from DB, hence adding englishEligibilities in response");
						course.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
					}
					resultList.add(course);
				});
				
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(totalCount.intValue());
				paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
				paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
			} else {
				log.error("Course not found for searchKeyword = "+searchKey);
				throw new NotFoundException("Course not found for searchKeyword = "+searchKey);
			}
		} catch (Exception exception) {
			log.error("Exception while fetching courses exception = "+exception);
		}
		return paginationResponseDto;
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
		log.debug("Inside convertDtoToCourseMinRequirement() method");
		Integer i = 0;
		GradeDto gradeDto = new GradeDto();
		List<String> subjectGrade = new ArrayList<>();
		for (String subject : courseMinRequirementDto.getSubject()) {
			System.out.println(subject);
			CourseMinRequirement courseMinRequirement = new CourseMinRequirement();
			courseMinRequirement.setCountryName(courseMinRequirementDto.getCountry());
			courseMinRequirement.setSystem(courseMinRequirementDto.getSystem());
			courseMinRequirement.setSubject(courseMinRequirementDto.getSubject().get(i));
			courseMinRequirement.setGrade(courseMinRequirementDto.getGrade().get(i));
			subjectGrade.add(courseMinRequirementDto.getGrade().get(i));
			courseMinRequirement.setCourse(iCourseDAO.get(courseMinRequirementDto.getCourse()));
			courseMinRequirementDao.save(courseMinRequirement);
			i++;
		}
		gradeDto.setCountryName(courseMinRequirementDto.getCountry());
		gradeDto.setEducationSystemId(courseMinRequirementDto.getSystem());
		gradeDto.setSubjectGrades(subjectGrade);
	}

	@Override
	public List<CourseMinRequirementDto> getCourseMinRequirement(final String course) {
		return convertCourseMinRequirementToDto(courseMinRequirementDao.get(course));
	}

	public List<CourseMinRequirementDto> convertCourseMinRequirementToDto(final List<CourseMinRequirement> courseMinRequirement) {
		List<CourseMinRequirementDto> resultList = new ArrayList<>();
		Set<String> countryIds = courseMinRequirement.stream().map(c -> c.getCountryName()).collect(Collectors.toSet());
		for (String countryId : countryIds) {
			List<String> subject = new ArrayList<>();
			List<String> grade = new ArrayList<>();
			List<CourseMinRequirement> filterList = courseMinRequirement.stream().filter(x -> x.getCountryName().equals(countryId))
					.collect(Collectors.toList());
			CourseMinRequirementDto courseMinRequirementDto = new CourseMinRequirementDto();
			for (CourseMinRequirement courseMinRequirements : filterList) {
				subject.add(courseMinRequirements.getSubject());
				grade.add(courseMinRequirements.getGrade());
				courseMinRequirementDto.setCountry(courseMinRequirements.getCountryName());
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
	public List<CourseRequest> autoSearchByCharacter(final String searchKey) throws NotFoundException {
		log.debug("Inside autoSearchByCharacter() method");
		List<CourseRequest> coursesRequests = new ArrayList<>();
		log.info("Calling DAO layer to fetch courses based having searchKey = "+searchKey);
		List<Course> courses = courseRepository.findByIsActiveAndDeletedOnAndNameContaining(PageRequest.of(0,50), true, null, searchKey);
		if(CollectionUtils.isEmpty(courses)) {
			log.error("No course found for searchKey = "+searchKey);
			throw new NotFoundException("No course found for searchKey = "+searchKey);
		} else {
			log.info("Get courses from DB, hence strat iterating data");
			courses.stream().forEach(course -> {
				CourseRequest courseRequest = CommonUtil.convertCourseDtoToCourseRequest(course);
				coursesRequests.add(courseRequest);
			});
		}
		return coursesRequests;
	}

	@Override
	public long checkIfCoursesPresentForCountry(final String country) {
		return iCourseDAO.getCourseCountForCountry(country);
	}

	@Override
	public List<Course> getTopRatedCoursesForCountryWorldRankingWise(final String country) {
		return iCourseDAO.getTopRatedCoursesForCountryWorldRankingWise(country);
	}

	@Override
	public List<Course> getAllCourseUsingFaculty(final String facultyId) {
		return iCourseDAO.getAllCourseForFacultyWorldRankingWise(facultyId);
	}

	@Override
	public List<String> getAllCourseUsingFacultyId(final String facultyId) {
		return iCourseDAO.getAllCourseForFacultyWorldRankingWises(facultyId);
	}

	@Override
	public List<String> getTopSearchedCoursesByOtherUsers(final String userId) {
		return viewDao.getOtherUserWatchCourse(userId, "COURSE");
	}

	@Override
	public List<Course> getCoursesById(final List<String> allSearchCourses) {
		return iCourseDAO.getCoursesFromId(allSearchCourses);
	}

	@Override
	public Map<String, String> facultyWiseCourseIdMapForInstitute(final List<Faculty> facultyList, final String instituteId) {
		return iCourseDAO.facultyWiseCourseIdMapForInstitute(facultyList, instituteId);
	}

	@Override
	public List<Course> getAllCoursesUsingId(final List<String> listOfRecommendedCourseIds) {
		return iCourseDAO.getAllCoursesUsingId(listOfRecommendedCourseIds);
	}

	@Override
	public List<String> getTopRatedCourseIdForCountryWorldRankingWise(final String country) {
		return iCourseDAO.getTopRatedCourseIdsForCountryWorldRankingWise(country);
	}

	@Override
	public List<String> getTopSearchedCoursesByUsers(final String userId) {
		return viewDao.getUserWatchCourseIds(userId, "COURSE");
	}

	@Override
	public Set<Course> getRelatedCoursesBasedOnPastSearch(final List<String> courseList) throws ValidationException {
		Set<Course> relatedCourses = new HashSet<>();
		for (String courseId : courseList) {
			relatedCourses.addAll(userRecommedationService.getRelatedCourse(courseId));
		}
		return relatedCourses;
	}

	@Override
	public Long getCountOfDistinctInstitutesOfferingCoursesForCountry(final UserDto userDto, final String country) {
		return iCourseDAO.getCountOfDistinctInstitutesOfferingCoursesForCountry(userDto, country);
	}

	@Override
	public List<String> getCountryForTopSearchedCourses(final List<String> topSearchedCourseIds) throws ValidationException {
		if (topSearchedCourseIds == null || topSearchedCourseIds.isEmpty()) {
			throw new ValidationException(messageByLocaleService.getMessage("no.course.id.specified", new Object[] {}));
		}
		return iCourseDAO.getDistinctCountryBasedOnCourses(topSearchedCourseIds);
	}

	private List<String> getCourseListBasedForCourseOnParameters(final String courseId, final String instituteId, final String facultyId,
			final String countryId, final String cityId) {
		List<String> courseIdList = iCourseDAO.getCourseListForCourseBasedOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		return courseIdList;
	}

	@Override
	public List<Long> getUserListForUserWatchCourseFilter(final String courseId, final String instituteId, final String facultyId,
			final String countryId, final String cityId) {
		List<String> courseIdList = getCourseListBasedForCourseOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		if (courseIdList == null || courseIdList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> userIdList = iCourseDAO.getUserListFromUserWatchCoursesBasedOnCourses(courseIdList);
		return userIdList;
	}

	@Override
	public List<String> courseIdsForCountry(final String country) {
		return iCourseDAO.getCourseIdsForCountry(country);
	}

	@Override
	public List<String> courseIdsForMigratedCountries(final String country) {
		List<GlobalData> countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList(country);
		List<String> otherCountryIds = new ArrayList<>();
		if (countryWiseStudentCountListForUserCountry == null || countryWiseStudentCountListForUserCountry.isEmpty()) {
			countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList("China");
		}

		for (GlobalData globalDataDto : countryWiseStudentCountListForUserCountry) {
				otherCountryIds.add(globalDataDto.getDestinationCountry());
		}
		if (!otherCountryIds.isEmpty()) {
			List<String> courseIds = iCourseDAO.getAllCoursesForCountry(otherCountryIds);
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
	public List<CourseDTOElasticSearch> getCoursesToBeRetriedForElasticSearch(final List<String> courseIds, final Integer startIndex, final Integer limit) {
		return iCourseDAO.getCoursesToBeRetriedForElasticSearch(courseIds, startIndex, limit);
	}

	@Override
	public List<CourseIntake> getCourseIntakeBasedOnCourseId(final String courseId) {
		return iCourseDAO.getCourseIntakeBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseDeliveryMethod> getCourseDeliveryMethodBasedOnCourseId(final String courseId) {
		return iCourseDAO.getCourseDeliveryMethodBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseLanguage> getCourseLanguageBasedOnCourseId(final String courseId) {
		return iCourseDAO.getCourseLanguageBasedOnCourseId(courseId);
	}

	@Override
	public List<CourseResponseDto> getCourseNoResultRecommendation(final String userCountry, final String facultyId, final String countryId,
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
				//Country country = countryDAO.getCountryByName(globalDatas.get(0).getDestinationCountry());
				courseSearchDto.setCountryNames(Arrays.asList(globalDatas.get(0).getDestinationCountry()));
				courseSearchDto.setMaxSizePerPage(pageSize - courseResponseDtos.size());
				List<CourseResponseDto> courseResponseDtos2 = iCourseDAO.getAllCoursesByFilter(courseSearchDto, null, null, startIndex, false);
				courseResponseDtos.addAll(courseResponseDtos2);
			}
		}
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);

	}

	@Override
	public List<String> getCourseKeywordRecommendation(final String facultyId, final String countryId, final String levelId,
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
			courseSearchDto.setCountryNames(Arrays.asList(countryId));
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

	public Map<String, Integer> getCourseCountByLevel() {
		log.debug("Inside getCourseCountByLevel() method");
		Map<String, Integer> courseLevelCount = new HashMap<>();
		log.info("Fetching all levels from DB");
		List<Level> levels = iLevelDao.getAll();
		if (!CollectionUtils.isEmpty(levels)) {
			log.info("Levels fetched from DB, now fetching course count for each level");
			levels.stream().forEach(level -> {
				Integer courseCount = null;
				if (!ObjectUtils.isEmpty(level.getId())) {
					log.info("Caliling DAO layer to get course count for levelId = "+level.getId());
					courseCount = iCourseDAO.getCoursesCountBylevelId(level.getId());
				}
				if (!ObjectUtils.isEmpty(courseCount)) {
					courseLevelCount.put(level.getName(), courseCount);
				}
			});
		}
		return courseLevelCount;
	}

	@Override
	public void addMobileCourse(String userId, String instituteId, CourseMobileDto courseMobileDto) throws Exception {
		log.debug("Inside addMobileCourse() method");
		log.info("Validate user id "+userId+ " have appropriate access for institute id "+instituteId);
		 // TODO validate user id have appropriate access for institute id 
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		Course course = new Course();
		course.setInstitute(institute);
		course.setName(courseMobileDto.getCourseName());
		course.setDescription(courseMobileDto.getCourseDescription());
//		course.setUsdDomasticFee(courseMobileDto.getUsdDomesticFee());
//		course.setUsdInternationFee(courseMobileDto.getUsdInternationalFee());
//		course.setDuration(courseMobileDto.getDuration());
//		course.setDurationTime(courseMobileDto.getDurationUnit());
		course.setIsActive(false);
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setGlobalGpa(courseMobileDto.getGpaRequired());
		log.info("Fetch faculty with faulty id "+courseMobileDto.getFacultyId());
		Faculty faculty = getFaculty(courseMobileDto.getFacultyId());
		if (ObjectUtils.isEmpty(faculty)) {
			log.error("No faculty found for faculty id "+courseMobileDto.getFacultyId());
			throw new NotFoundException("No faculty found for faculty id "+courseMobileDto.getFacultyId());
		}
		course.setFaculty(faculty);
		/*log.info("Creating course grade eligibility");
		CourseGradeEligibility courseGradeEligibility = new CourseGradeEligibility(courseMobileDto.getGpaRequired(), true,
				DateUtil.getUTCdatetimeAsDate(), DateUtil.getUTCdatetimeAsDate(), null, "API", "API", null, null);
		log.info("Association course grade eligibility with course");
		course.setCourseGradeEligibility(courseGradeEligibility);
		courseGradeEligibility.setCourse(course);*/
		iCourseDAO.save(course);
	}

	@Override
	public void updateMobileCourse(String userId, String courseId, CourseMobileDto courseMobileDto) throws Exception {
		log.debug("Inside updateMobileCourse() method");
		log.info("Getting course by course id "+courseId);
		Course course =iCourseDAO.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("No course found for course having id "+courseId);
			throw new NotFoundException("No course found for course having id "+courseId);
		}
		log.info("Getting institute id from course ");
		String instituteId = course.getInstitute().getId();
		log.info("Validate user id "+userId+ " have appropriate access for institute id "+instituteId);
		 // TODO validate user id have appropriate access for institute id 
		course.setName(courseMobileDto.getCourseName());
		course.setDescription(courseMobileDto.getCourseDescription());
//		course.setUsdDomasticFee(courseMobileDto.getUsdDomesticFee());
//		course.setUsdInternationFee(courseMobileDto.getUsdInternationalFee());
//		course.setDuration(courseMobileDto.getDuration());
//		course.setDurationTime(courseMobileDto.getDurationUnit());
		course.setCreatedBy("API");
		course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		course.setGlobalGpa(courseMobileDto.getGpaRequired());
		/*log.info("Updating course grade eligibility");
		CourseGradeEligibility courseGradeEligibilityFromDb = course.getCourseGradeEligibility();
		if (ObjectUtils.isEmpty(courseGradeEligibilityFromDb)) {
			CourseGradeEligibility courseGradeEligibility = new CourseGradeEligibility(courseMobileDto.getGpaRequired(), true,
					DateUtil.getUTCdatetimeAsDate(), DateUtil.getUTCdatetimeAsDate(), null, "API", "API", null, null);
			course.setCourseGradeEligibility(courseGradeEligibility);
		} else {
			courseGradeEligibilityFromDb.setGlobalGpa(courseMobileDto.getGpaRequired());
			courseGradeEligibilityFromDb.setUpdatedBy("API");
			courseGradeEligibilityFromDb.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		}*/
		iCourseDAO.save(course);
	} 
	
	@Override
	public List<CourseMobileDto> getAllMobileCourseByInstituteIdAndFacultyIdAndStatus(String userId, String instituteId, String facultyId, boolean status) throws Exception {
		List<CourseMobileDto> listOfCourseMobileDto = new ArrayList<CourseMobileDto>();
		log.debug("Inside getAllMobileCourseByInstituteIdAndFacultyId() method");
		 // TODO validate user id have appropriate access for institute id 
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		log.info("Getting course for institute id "+instituteId+ " having faculty id "+facultyId);
		List<Course> listOfCourse = iCourseDAO.getAllCourseByInstituteIdAndFacultyIdAndStatus(instituteId, facultyId, status);
		if (!CollectionUtils.isEmpty(listOfCourse)) {
			log.info("List of Course not empty creatng response DTO");
			listOfCourse.stream().forEach(course -> {
				CourseMobileDto courseMobileDto = new CourseMobileDto(course.getId(), course.getName(), course.getDescription(), course.getFaculty().getId(), 
						course.getFaculty().getName(), course.getGlobalGpa() , null /*course.getUsdDomasticFee()*/, null /*course.getUsdInternationFee()*/, null /*course.getDuration()*/, null /*course.getDurationTime()*/);
				listOfCourseMobileDto.add(courseMobileDto);
			});
		}
		return listOfCourseMobileDto;
	}
	
	@Override
	public List<CourseMobileDto> getPublicMobileCourseByInstituteIdAndFacultyId(String instituteId, String facultyId) throws Exception {
		List<CourseMobileDto> listOfCourseMobileDto = new ArrayList<CourseMobileDto>();
		log.debug("Inside getPublicMobileCourseByInstituteIdAndFacultyId() method");
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		log.info("Getting course for institute id "+instituteId+ " having faculty id "+facultyId + " and status active");
		List<Course> listOfCourse = iCourseDAO.getAllCourseByInstituteIdAndFacultyIdAndStatus(instituteId, facultyId, true);
		if (!CollectionUtils.isEmpty(listOfCourse)) {
			log.info("List of Course not empty creatng response DTO");
			listOfCourse.stream().forEach(course -> {
				CourseMobileDto courseMobileDto = new CourseMobileDto(course.getId(), course.getName(), course.getDescription(), course.getFaculty().getId(), 
						course.getFaculty().getName(), course.getGlobalGpa() , null /*course.getUsdDomasticFee()*/, null /*course.getUsdInternationFee()*/, null /*course.getDuration()*/, null /*course.getDurationTime()*/);
				listOfCourseMobileDto.add(courseMobileDto);
			});
		}
		return listOfCourseMobileDto;
	}
	
	@Override
	public void changeCourseStatus (String userId , String courseId, boolean status) throws Exception {
		log.debug("Inside updateMobileCourse() method");
		log.info("Getting course by course id "+courseId);
		Course course =iCourseDAO.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("No course found for course having id "+courseId);
			throw new NotFoundException("No course found for course having id "+courseId);
		}
		log.info("Getting institute id from course ");
		String instituteId = course.getInstitute().getId();
		log.info("Validate user id "+userId+ " have appropriate access for institute id "+instituteId);
		 // TODO validate user id have appropriate access for institute id 
		log.info("Changing course having Id "+courseId+ " from status "+course.getIsActive()+ " to "+status );
		course.setIsActive(status);
		iCourseDAO.save(course);
	}
	
	@Override
	public NearestCoursesDto getCourseByInstituteId(Integer pageNumber, Integer pageSize, String instituteId) throws NotFoundException {
		log.debug("Inside getCourseByInstituteId() method");
		List<CourseResponseDto> nearestCourseList = new ArrayList<>();
		log.info("fetching courses from DB for instituteID "+instituteId);
		Pageable paging = PageRequest.of(pageNumber - 1, pageSize);
		List<Course> courseList = courseRepository.findByInstituteId(paging, instituteId);
		Long totalCount = courseRepository.getTotalCountOfCourseByInstituteId(instituteId);
		if(!CollectionUtils.isEmpty(courseList)) {
			log.info("if course is not coming null then start iterating data");
			courseList.stream().forEach(course -> {
				CourseResponseDto nearestCourse = new CourseResponseDto();
				nearestCourse.setId(course.getId());
				nearestCourse.setName(course.getName());
				nearestCourse.setCourseRanking(course.getWorldRanking());
				nearestCourse.setCostRange(course.getCostRange());
				nearestCourse.setStars(Double.valueOf(course.getStars()));
				nearestCourse.setInstituteId(course.getInstitute().getId());
				nearestCourse.setInstituteName(course.getInstitute().getName());
				nearestCourse.setLocation(course.getInstitute().getCityName() + ", " + course.getInstitute().getCountryName());
				nearestCourse.setCountryName(course.getInstitute().getCountryName());
				nearestCourse.setCityName(course.getInstitute().getCityName());
				nearestCourse.setCurrencyCode(course.getCurrency());
				if(!ObjectUtils.isEmpty(course.getInstitute().getLatitude())) {
					nearestCourse.setLatitude(course.getInstitute().getLatitude());
				}
				if(!ObjectUtils.isEmpty(course.getInstitute().getLongitude())) {
					nearestCourse.setLongitude(course.getInstitute().getLongitude());
				}
				
				log.info("Fetching courseAdditionalInfo from DB having courseId = "+course.getId());
				List<CourseAdditionalInfoDto> courseAdditionalInfoDtos = courseAdditionalInfoProcessor.getCourseAdditionalInfoByCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseAdditionalInfoDtos)) {
					log.info("courseAdditionalInfo is fetched from DB, hence adding courseAdditionalInfo in response");
					nearestCourse.setCourseAdditionalInfo(courseAdditionalInfoDtos);
				}
				
				log.info("Fetching courseLanguage from DB having courseId = "+course.getId());
				List<CourseLanguage> courseLanguages = iCourseDAO.getCourseLanguageBasedOnCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
					nearestCourse.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				}
				
				log.info("Fetching courseIntake from DB having courseId = "+course.getId());
				List<CourseIntake> courseIntakes = iCourseDAO.getCourseIntakeBasedOnCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseIntakes)) {
					log.info("courseIntake is fetched from DB, hence adding englishEligibilities in response");
					nearestCourse.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
				}
				try {
					log.info("going to fetch logo from storage service for courseId "+course.getId());
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(course.getId(), 
							ImageCategory.COURSE.toString(), Type.LOGO.name(), "en");
					nearestCourse.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Exception in calling storage service "+e);
				}
				nearestCourseList.add(nearestCourse);
			});
		} else {
			log.error("No course found for instituteId "+instituteId);
			throw new NotFoundException("No course found for instituteId "+instituteId);
		}
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
		NearestCoursesDto nearestCourseResponse = new NearestCoursesDto();
		nearestCourseResponse.setNearestCourses(nearestCourseList);
		nearestCourseResponse.setPageNumber(paginationUtilDto.getPageNumber());
		nearestCourseResponse.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		nearestCourseResponse.setHasNextPage(paginationUtilDto.isHasNextPage());
		nearestCourseResponse.setTotalPages(paginationUtilDto.getTotalPages());
		nearestCourseResponse.setTotalCount(totalCount.intValue());
		return nearestCourseResponse;
	}
	
	@Override
	public NearestCoursesDto getNearestCourses(AdvanceSearchDto courseSearchDto)
			throws ValidationException {
		log.debug("Inside getNearestCourses() method");
		List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
		Boolean runMethodAgain = true;
		Integer initialRadius = courseSearchDto.getInitialRadius();
		Integer increaseRadius = 25, totalCount = 0;
		log.info("start getting nearest courses from DB for latitude " + courseSearchDto.getLatitude()
				+ " and longitude " + courseSearchDto.getLongitude() + " and initial radius is " + initialRadius);
		List<CourseResponseDto> nearestCourseDTOs = iCourseDAO.getNearestCourseForAdvanceSearch(courseSearchDto);
		log.info("going to fetch total count of courses from DB");
		totalCount = iCourseDAO.getTotalCountOfNearestCourses(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
		while (runMethodAgain) {
			if (initialRadius != maxRadius && CollectionUtils.isEmpty(nearestCourseDTOs)) {
				log.info("if data is comming as null from DB then increase radius, new radius is " + initialRadius);
				runMethodAgain = true;
				initialRadius = initialRadius + increaseRadius;
				courseSearchDto.setInitialRadius(initialRadius);
				log.info("for old radius data is not coming so start fetching nearest courses for new radius " + initialRadius);
				nearestCourseDTOs = iCourseDAO.getNearestCourseForAdvanceSearch(courseSearchDto);
				totalCount = iCourseDAO.getTotalCountOfNearestCourses(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
			} else {
				log.info("data is coming from DB for radius " + initialRadius);
				runMethodAgain = false;
				log.info("start iterating data which is coming from DB");
				for (CourseResponseDto nearestCourseDTO : nearestCourseDTOs) {
					CourseResponseDto nearestCourse = new CourseResponseDto();
					BeanUtils.copyProperties(nearestCourseDTO, nearestCourse);
					nearestCourse.setDistance(Double.valueOf(initialRadius));
					log.info("fetching institute logo from storage service for instituteID " + nearestCourseDTO.getId());
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(nearestCourseDTO.getId(),
							ImageCategory.COURSE.toString(), Type.LOGO.name(), "en");
					nearestCourse.setStorageList(storageDTOList);
					courseResponseDtoList.add(nearestCourse);
				}
			}
		}
		Integer startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		NearestCoursesDto coursePaginationResponseDto = new NearestCoursesDto();
		coursePaginationResponseDto.setNearestCourses(courseResponseDtoList);
		coursePaginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		coursePaginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		if(initialRadius != maxRadius) {
			coursePaginationResponseDto.setHasNextPage(true);
		} else {
			coursePaginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		}
		coursePaginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		coursePaginationResponseDto.setTotalCount(totalCount);
		return coursePaginationResponseDto;
	}
}

