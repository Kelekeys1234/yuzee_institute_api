package com.yuzee.app.processor;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.yuzee.app.bean.AccrediatedDetail;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.bean.CourseCurriculum;
import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.bean.CourseKeywords;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.CourseSubject;
import com.yuzee.app.bean.Faculty;
import com.yuzee.app.bean.GlobalData;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.Level;
import com.yuzee.app.bean.OffCampusCourse;
import com.yuzee.app.dao.AccrediatedDetailDao;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.dao.CourseCurriculumDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseKeywordDao;
import com.yuzee.app.dao.FacultyDao;
import com.yuzee.app.dao.IGlobalStudentDataDAO;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.dao.TimingDao;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseCountDto;
import com.yuzee.app.dto.CourseDto;
import com.yuzee.app.dto.CourseFilterDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.NearestCoursesDto;
import com.yuzee.app.dto.UnLinkInsituteDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.entitylistener.CourseUpdateListener;
import com.yuzee.app.message.MessageByLocaleService;
import com.yuzee.app.repository.CourseRepository;
import com.yuzee.app.service.IGlobalStudentData;
import com.yuzee.app.service.ITop10CourseService;
import com.yuzee.app.service.UserRecommendationService;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.app.util.DTOUtils;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.PaginationUtilDto;
import com.yuzee.common.lib.dto.common.CurrencyRateDto;
import com.yuzee.common.lib.dto.eligibility.FundingResponseDto;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.dto.institute.CourseSyncDTO;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.dto.institute.CourseSubjectDto;
import com.yuzee.common.lib.dto.institute.OffCampusCourseDto;
import com.yuzee.common.lib.dto.review.ReviewStarDto;
import com.yuzee.common.lib.dto.storage.StorageDto;
import com.yuzee.common.lib.dto.transaction.UserViewCourseDto;
import com.yuzee.common.lib.dto.user.UserInitialInfoDto;
import com.yuzee.common.lib.enumeration.CourseTypeEnum;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.enumeration.StudentTypeEnum;
import com.yuzee.common.lib.enumeration.TransactionTypeEnum;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.IOException;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.CommonHandler;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.common.lib.handler.ReviewHandler;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.handler.ViewTransactionHandler;
import com.yuzee.common.lib.util.DateUtil;
import com.yuzee.common.lib.util.PaginationUtil;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private InstituteDao instituteDAO;

	@Autowired
	private FacultyDao facultyDAO;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;

	@Autowired
	private LevelProcessor levelProcessor;

	@Autowired
	private UserRecommendationService userRecommedationService;

	@Autowired
	private PublishSystemEventHandler publishSystemEventHandler;

	@Autowired
	private IGlobalStudentDataDAO iGlobalStudentDataDAO;

	@Autowired
	private ITop10CourseService iTop10CourseService;

	@Autowired
	private ReviewHandler reviewHandler;
	
	@Autowired
	private LevelDao levelDao;
	
	@Autowired
	private CourseRepository courseRepository;
		
	@Autowired
	private CourseDeliveryModesProcessor courseDeliveryModesProcessor;
	
	@Autowired
	private CourseEnglishEligibilityProcessor courseEnglishEligibilityProcessor;

	@Autowired
	private CourseCurriculumDao courseCurriculumDao;
	
	@Autowired
	private CommonHandler commonHandler;
	
	@Autowired
	private ViewTransactionHandler viewTransactionHandler;
	
	@Autowired
	private CoursePrerequisiteProcessor coursePrerequisiteProcessor;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CareerDao careerDao;

	@Autowired
	private TimingProcessor timingProcessor;

	@Autowired
	private TimingDao timingDao;

	@Autowired
	private AccrediatedDetailDao accrediatedDetailDao;

	@Autowired
	private CommonProcessor commonProcessor;
	
	@Autowired
	private ConversionProcessor conversionProcessor;

	@Autowired
	private CourseInstituteProcessor courseInstituteProcessor;
	
	@Autowired
	private CourseKeywordDao courseKeywordDao;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("importCourseJob")
	private Job job;
	
	@Value("${max.radius}")
	private Integer maxRadius;
	

	@Autowired
	@Qualifier("exportCourseToElastic")
	private Job exportCourseToElastic;
	
	@Autowired
	ReadableIdProcessor readableIdProcessor;

	@Transactional
	public List<CourseResponseDto> getAllCoursesByFilter(final CourseSearchDto courseSearchDto, final Integer startIndex, final Integer pageSize,
			final String searchKeyword, List<String> entityIds) throws ValidationException, InvokeException, NotFoundException {
		log.debug("Inside getAllCoursesByFilter() method");
		log.info("CAlling DAO layer to fetch courses based on passed filters and pagination");
		List<CourseResponseDto> courseResponseDtos = courseDao.getAllCoursesByFilter(courseSearchDto, searchKeyword, null, 
					startIndex, false, entityIds);
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);
	}

	@Transactional
	private List<CourseResponseDto> getExtraInfoOfCourseFilter(final CourseSearchDto courseSearchDto, 
				final List<CourseResponseDto> courseResponseDtos) throws ValidationException, InvokeException, NotFoundException {
		log.debug("Inside getExtraInfoOfCourseFilter() method");
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			log.info("Data is coming null from DB based on filter hence making new Object");
			return new ArrayList<>();
		}
		log.info("Filering course response if courseId is coming duplicate in response");
		List<CourseResponseDto> courseResponseFinalResponse = courseResponseDtos.stream().filter(Utils.distinctByKey(CourseResponseDto::getId))
				.collect(Collectors.toList());
	
		log.info("Collecting CourseIds by stream API and calling store it in list");
		List<String> courseIds = courseResponseFinalResponse.stream().map(CourseResponseDto::getId).collect(Collectors.toList());
		
		log.info("Calling Storage service to get institute images from DB");
		List<StorageDto> storageDTOList = storageHandler.getStorages(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), 
				EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
		
		if(!CollectionUtils.isEmpty(courseResponseFinalResponse)) {
			log.info("Courses are coming from DB hence start iterating data");
			courseResponseFinalResponse.stream().forEach(courseResponseDto -> {
				if (storageDTOList != null && !storageDTOList.isEmpty()) {
					log.info("Storage data is coming hence iterating storage data and set it in response");
					List<StorageDto> storageDTO = storageDTOList.stream().filter(x -> courseResponseDto.getInstituteId().equals(x.getEntityId()))
							.collect(Collectors.toList());
					courseResponseDto.setStorageList(storageDTO);
					storageDTOList.removeAll(storageDTO);
				} else {
					courseResponseDto.setStorageList(new ArrayList<>(1));
				}
				
				// Calling viewTransaction Service to fetch viewedCourse of User to set whether course is viewed by user or not
				log.info("Invoking viewTransaction service to fetched view course by user");
				try {
					UserViewCourseDto userViewCourseDto = viewTransactionHandler.getUserViewedCourseByEntityIdAndTransactionType(
							courseSearchDto.getUserId(), EntityTypeEnum.COURSE.name(), courseResponseDto.getId(), TransactionTypeEnum.VIEW.name());
					if(!ObjectUtils.isEmpty(userViewCourseDto)) {
						log.info("User view course data is coming for courseId = " + courseResponseDto.getId() + " ,hence marking course as viewed");
						courseResponseDto.setIsViewed(true);
					} else {
						courseResponseDto.setIsViewed(false);
					}
				} catch (InvokeException e) {
					log.error("Exception while fetching user view courseshaving exception =" + e);
				}
				log.info("Filtering course additional info by matching courseIds");
				List<CourseDeliveryModesDto> additionalInfoDtos = courseResponseDto.getCourseDeliveryModes().stream().
							filter(x -> x.getCourseId().equals(courseResponseDto.getId())).collect(Collectors.toList());
				courseResponseDto.setCourseDeliveryModes(additionalInfoDtos);
				
				log.info("Fetching courseIntake from DB having courseIds = "+courseIds);
				List<CourseIntake> courseIntakes = courseDao.getCourseIntakeBasedOnCourseId(courseResponseDto.getId());
				if (!CollectionUtils.isEmpty(courseIntakes)) {
					log.info("Filtering courseIntakes data based on courseId.");
					courseResponseDto.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
				} else {
					courseResponseDto.setIntake(new ArrayList<>());
				}
				
				log.info("Fetching courseLanguages from DB having courseIds = "+courseIds);
				List<CourseLanguage> courseLanguages = courseDao.getCourseLanguageBasedOnCourseId(courseResponseDto.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					log.info("Filtering courseLanguages data based on courseId");
					courseResponseDto.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				} else {
					courseResponseDto.setLanguage(new ArrayList<>());
				}
			});
		}
		return courseResponseFinalResponse;
	}

	@Transactional
	public int getCountforNormalCourse(final CourseSearchDto courseSearchDto, final String searchKeyword, List<String> entityIds) {
		return courseDao.getCountforNormalCourse(courseSearchDto, searchKeyword, entityIds);
	}

	@Transactional
	public List<CourseResponseDto> getAllCoursesByInstitute(final String instituteId, final CourseSearchDto courseSearchDto) {
		return courseDao.getAllCoursesByInstitute(instituteId, courseSearchDto);
	}

	@Transactional
	public List<CourseResponseDto> getCouresesByFacultyId(final String facultyId) throws NotFoundException {
		log.debug("Inside getCouresesByFacultyId() method");
		log.info("fetching courses from DB for facultyId = "+facultyId);
		List<CourseResponseDto> courseResponseDtos = courseDao.getCouresesByFacultyId(facultyId);
		if(!CollectionUtils.isEmpty(courseResponseDtos)) {
			log.info("Courses coming from DB start iterating data to make final response");
			courseResponseDtos.stream().forEach(courseResponseDto -> {
				log.info("Fetching course additional info from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setCourseDeliveryModes(courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(courseResponseDto.getId()));
				log.info("Fetching course intakes from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setIntake(courseDao.getCourseIntakeBasedOnCourseId(courseResponseDto.getId())
							.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
				log.info("Fetching course languages from DB having courseId = "+courseResponseDto.getId());
				courseResponseDto.setLanguage(courseDao.getCourseLanguageBasedOnCourseId(courseResponseDto.getId())
						.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
			});
		} else {
			log.error("No Courses found in DB for facultyId = "+facultyId);
			throw new NotFoundException("No Courses found in DB for facultyId = "+facultyId);
		}
		return courseResponseDtos;
	}

	@Transactional
	public Course prepareCourseModelFromCourseRequest(String loggedInUserId, String instituteId, String courseId, @Valid final CourseRequest courseDto)
			throws ValidationException, NotFoundException, ForbiddenException, InvokeException {
		log.info("inside CourseProcessor.prepareCourseModelFromCourseRequest");
		Course course = null;
		if (StringUtils.isEmpty(courseId)) {
			course = new Course();
			course.setName(courseDto.getName());
			readableIdProcessor.setReadableIdForCourse(course);
		} else {
			course = courseDao.get(courseId);
			Course copyCourse = new Course();
			BeanUtils.copyProperties(course, copyCourse);
			CourseUpdateListener.putCourseInTransaction(course.getId(), copyCourse);
			if (ObjectUtils.isEmpty(course)) {
				log.error("invalid course id: {}", courseId);
				throw new NotFoundException("invalid course id: " + courseId);
			}
			if (!course.getCreatedBy().equals(loggedInUserId)) {
				log.error("User has no access to edit the course with id: {}", courseId);
				throw new ForbiddenException("User has no access to edit the course with id: "+ courseId);
			}
		}
		log.info("Fetching institute details from DB for instituteId = ", instituteId);
		course.setInstitute(getInstititute(instituteId));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		course.setIsActive(true);

		log.info("Fetching faculty details from DB for facultyId = " + courseDto.getFacultyId());
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		if (!StringUtils.isEmpty(courseDto.getLevelId())) {
			log.info("Fetching level details from DB for levelId = " + courseDto.getLevelId());
			course.setLevel(levelProcessor.getLevel(courseDto.getLevelId()));
		}
		
		if (!StringUtils.isEmpty(courseDto.getWorldRanking())) {
			log.info("World Ranking is present adding it in course bean class");
			course.setWorldRanking(Integer.valueOf(courseDto.getWorldRanking()));
		}
		if (!StringUtils.isEmpty(courseDto.getCurriculumId())) {
			log.info("curriculum is present adding it in course bean class");
			Optional<CourseCurriculum> courseCurriculumOptional = courseCurriculumDao.getById(courseDto.getCurriculumId());
			if (courseCurriculumOptional.isPresent()) {
				course.setCourseCurriculum(courseCurriculumOptional.get());	
			}else {
				log.error("invalid course curriculum id");
				throw new NotFoundException("invalid course curriculum id");
			}
		}

		// Adding course details in bean class
		course.setRemarks(courseDto.getRemarks() != null ? courseDto.getRemarks() : null);
		course.setWebsite(courseDto.getWebsite() != null ? courseDto.getWebsite() : null);
		course.setPhoneNumber(courseDto.getPhoneNumber() != null ? courseDto.getPhoneNumber() : null);
		course.setAvailabilty(courseDto.getAvailability() != null ? courseDto.getAvailability() : null);
		course.setRecognition(courseDto.getRecognition() != null ? courseDto.getRecognition() : null);
		course.setAbbreviation(courseDto.getAbbreviation() != null ? courseDto.getAbbreviation() : null);
		course.setCurrencyTime(courseDto.getCurrencyTime() != null ? courseDto.getCurrencyTime() : null);
		course.setCurrency(courseDto.getCurrency() != null ? courseDto.getCurrency() : null);
		course.setRecognitionType(courseDto.getRecognitionType() != null ? courseDto.getRecognitionType() : null);
		course.setExaminationBoard(courseDto.getExaminationBoard());
		
		course.setDomesticApplicationFee(courseDto.getDomesticApplicationFee());
		course.setInternationalApplicationFee(courseDto.getInternationalApplicationFee());
		course.setDomesticEnrollmentFee(courseDto.getDomesticEnrollmentFee());
		course.setInternationalEnrollmentFee(courseDto.getInternationalEnrollmentFee());
		CurrencyRateDto currencyRate = null;
		if (!StringUtils.isEmpty(courseDto.getCurrency())) {
			// Here we convert price in USD and everywhere we used USD price column only.
			course.setCurrency(courseDto.getCurrency());
			log.info("Currency code is not null, hence fetching currencyRate from DB having currencyCode = "
					+ courseDto.getCurrency());
			currencyRate = getCurrencyRate(courseDto.getCurrency());
			if (currencyRate == null) {
				log.error("Invalid currency, no USD conversion exists for this currency");
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
			populateCourseUsdPrices(currencyRate, course);	
		}
		
		course.setRecDate(courseDto.getRecDate());
		course.setContent(courseDto.getContent());
		course.setGlobalGpa(courseDto.getGlobalGpa());
		course.setEmail(courseDto.getEmail());
		course.setEntranceExam(courseDto.getEntranceExam());
		
		
		course.setAuditFields(loggedInUserId);
		course.setIsActive(true);
		saveUpdateCourseIntakes(loggedInUserId, course, courseDto.getIntake());
		
		saveUpdateCourseLanguages(loggedInUserId, course, courseDto.getLanguage());

		saveUpdateCourseEnglishEligibilities(loggedInUserId, course, courseDto.getEnglishEligibility());
		
		saveUpdateCourseDeliveryModes(loggedInUserId, course, currencyRate, courseDto.getCourseDeliveryModes());
		
		saveUpdateCourseSubjects(loggedInUserId, course, courseDto.getCourseSubjects());

		saveUpdateCourseCareerOutcomes(loggedInUserId, course, courseDto.getCourseCareerOutcomes());
		
		saveUpdateOffCampusCourse(loggedInUserId, course, courseDto.getOffCampusCourse());
		
		saveUpdateCourseFundings(loggedInUserId, course, courseDto.getCourseFundings());
		
		return course;			
	}
	
	
	private void saveUpdateOffCampusCourse(String userId, Course course, OffCampusCourseDto offCampusCourseDto) {
		log.info("inside courseProcessor.saveUpdateOffCampusCourse");
		if (!ObjectUtils.isEmpty(offCampusCourseDto)) {
			OffCampusCourse offCampusCourse = course.getOffCampusCourse();
			if (ObjectUtils.isEmpty(offCampusCourse)) {
				offCampusCourse = new OffCampusCourse();
			}
			course.setCourseType(CourseTypeEnum.OFF_CAMPUS);
			String existingOffCampusCourseId = offCampusCourse.getId();
			BeanUtils.copyProperties(offCampusCourseDto, offCampusCourse);
			offCampusCourse.setId(existingOffCampusCourseId);
			offCampusCourse.setAuditFields(userId);
			course.setOffCampusCourse(offCampusCourse);
			course.setCode(UUID.randomUUID().toString());
			offCampusCourse.setCourse(course);
		}
	}
	
	private void saveUpdateCourseIntakes(String userId, Course course, List<Date> courseIntakeDates)
			throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseIntakes");
		List<CourseIntake> dbIntakes = course.getCourseIntakes();
		Map<Date, CourseIntake> dbIntakeByDate = dbIntakes.stream()
				.collect(Collectors.toMap(CourseIntake::getIntakeDate, e -> e));
		if (!CollectionUtils.isEmpty(courseIntakeDates)) {
			log.info("Course intakes is not null, start iterating data");

			courseIntakeDates.stream().forEach(intake -> {

				CourseIntake courseIntake = dbIntakeByDate.get(intake);
				if (ObjectUtils.isEmpty(courseIntake)) {
					courseIntake = new CourseIntake();
				}
				courseIntake.setIntakeDate(intake);
				courseIntake.setAuditFields(userId);
				courseIntake.setCourse(course);
				if (StringUtils.isEmpty(courseIntake.getId())) {
					dbIntakes.add(courseIntake);
				}
			});
		} else if (!CollectionUtils.isEmpty(dbIntakes)) {
			dbIntakes.clear();
		}
		// intakes to be removed
		dbIntakes.removeIf(e -> courseIntakeDates.stream().noneMatch(r -> e.getIntakeDate().compareTo(r) == 0));
	}

	private void saveUpdateCourseLanguages(String userId, Course course, List<String> courseLanguages)
			throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseLanguages");
		List<CourseLanguage> dbLanguages = course.getCourseLanguages();
		if (!CollectionUtils.isEmpty(courseLanguages)) {
			log.info("Course languages is not null, start iterating data");

			courseLanguages.stream().forEach(language -> {
				Optional<CourseLanguage> optionalCouseLanguage = dbLanguages.stream()
						.filter(e -> e.getLanguage().equalsIgnoreCase(language)).findAny();
				CourseLanguage courseLanguage = optionalCouseLanguage.isPresent() ? optionalCouseLanguage.get() : null;
				if (courseLanguage == null) {
					courseLanguage = new CourseLanguage();
				}
				courseLanguage.setLanguage(language);
				courseLanguage.setAuditFields(userId);
				courseLanguage.setCourse(course);
				if (StringUtils.isEmpty(courseLanguage.getId())) {
					dbLanguages.add(courseLanguage);
				}
			});
		} else if (!CollectionUtils.isEmpty(dbLanguages)) {
			dbLanguages.clear();
		}
		// intakes to be removed
		dbLanguages.removeIf(e -> !courseLanguages.stream().anyMatch(r -> e.getLanguage().equalsIgnoreCase(r)));
	}

	private void saveUpdateCourseEnglishEligibilities(String loggedInUserId, Course course,
			List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos) throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseEnglishEligibilities");
		List<CourseEnglishEligibility> dbEnglishEligibilities = course.getCourseEnglishEligibilities();
		if (!CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {

			log.info("Creating the list to save/update course subjects in DB");
			dbEnglishEligibilities.removeIf(e -> courseEnglishEligibilityDtos.stream()
					.noneMatch(t -> e.getEnglishType().equalsIgnoreCase(t.getEnglishType())));

			courseEnglishEligibilityDtos.stream().forEach(e -> {
				Optional<CourseEnglishEligibility> existingCousrseEnglishEligibilityOp = dbEnglishEligibilities.stream()
						.filter(t -> e.getEnglishType().equalsIgnoreCase(t.getEnglishType())).findAny();
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				String existingId = null;
				if (existingCousrseEnglishEligibilityOp.isPresent()) {
					courseEnglishEligibility = existingCousrseEnglishEligibilityOp.get();
					existingId = courseEnglishEligibility.getId();
				}
				BeanUtils.copyProperties(e, courseEnglishEligibility);
				courseEnglishEligibility.setId(existingId);
				courseEnglishEligibility.setCourse(course);
				if (StringUtils.isEmpty(courseEnglishEligibility.getId())) {
					dbEnglishEligibilities.add(courseEnglishEligibility);
				}
				courseEnglishEligibility.setAuditFields(loggedInUserId);
			});

		} else {
			dbEnglishEligibilities.clear();
		}
	}

	private void saveUpdateCourseDeliveryModes(String loggedInUserId, Course course, CurrencyRateDto currencyRate,
			List<CourseDeliveryModesDto> courseDeliveryModeDtos) throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseDeliveryModes");
		List<CourseDeliveryModes> dbCourseDeliveryModes = course.getCourseDeliveryModes();
		if (!CollectionUtils.isEmpty(courseDeliveryModeDtos) && !ObjectUtils.isEmpty(currencyRate)) {

			log.info("Creating the list to save/update course subjects in DB");
			dbCourseDeliveryModes.removeIf(e -> courseDeliveryModeDtos.stream()
					.noneMatch(t -> e.getStudyMode().equalsIgnoreCase(t.getStudyMode())
							&& e.getDeliveryType().equalsIgnoreCase(t.getDeliveryType())));

			courseDeliveryModeDtos.stream().forEach(e -> {
				Optional<CourseDeliveryModes> existingCourseDeliveryModeOp = dbCourseDeliveryModes.stream()
						.filter(t -> t.getStudyMode().equalsIgnoreCase(e.getStudyMode())
								&& t.getDeliveryType().equalsIgnoreCase(e.getDeliveryType()))
						.findAny();
				CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();
				if (existingCourseDeliveryModeOp.isPresent()) {
					courseDeliveryMode = existingCourseDeliveryModeOp.get();
				}
				if (course.getCurrency() != null) {
					if (e.getDomesticFee() != null) {
						log.info("converting domestic fee into usdDomestic fee having conversionRate {}",currencyRate.getConversionRate());
						Double convertedRate = e.getDomesticFee() / currencyRate.getConversionRate();
						if (convertedRate != null) {
							courseDeliveryMode.setUsdDomesticFee(convertedRate);
						}
					}
					if (e.getInternationalFee() != null) {
						log.info("converting international fee into usdInternational fee having conversionRate {}",currencyRate.getConversionRate());
						Double convertedRate = Double.valueOf(e.getInternationalFee()) / currencyRate.getConversionRate();
						if (convertedRate != null) {
							courseDeliveryMode.setUsdInternationalFee(convertedRate);
						}
					}
				}
				courseDeliveryMode.setCourse(course);
				log.info("Adding additional infos like deliveryType, studyMode etc");

				courseDeliveryMode.setDeliveryType(e.getDeliveryType());
				courseDeliveryMode.setDomesticFee(e.getDomesticFee());
				courseDeliveryMode.setInternationalFee(e.getInternationalFee());
				courseDeliveryMode.setStudyMode(e.getStudyMode());
				courseDeliveryMode.setDuration(e.getDuration());
				courseDeliveryMode.setDurationTime(e.getDurationTime());
				courseDeliveryMode.setCourse(course);
				if (StringUtils.isEmpty(courseDeliveryMode.getId())) {
					dbCourseDeliveryModes.add(courseDeliveryMode);
				}
				courseDeliveryMode.setAuditFields(loggedInUserId);
			});

		} else {
			dbCourseDeliveryModes.clear();
		}
	}

	private void saveUpdateCourseSubjects(String loggedInUserId, Course course,
			List<CourseSubjectDto> courseSubjectDtos) throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseSubjects");
		List<CourseSubject> courseSubjects = course.getCourseSubjects();
		if (!CollectionUtils.isEmpty(courseSubjectDtos)) {

			log.info("Creating the list to save/update course subjects in DB");
			
			Set<String> updateRequestIds = courseSubjectDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseSubjectDto::getId).collect(Collectors.toSet());
			courseSubjects.removeIf(e -> !updateRequestIds.contains(e.getId()));

			Map<String, CourseSubject> existingCourseSubjectsMap = courseSubjects.stream()
					.collect(Collectors.toMap(CourseSubject::getId, e -> e));
			courseSubjectDtos.stream().forEach(e -> {
				CourseSubject courseSubject = new CourseSubject();
				if (!StringUtils.isEmpty(e.getId())) {
					courseSubject = existingCourseSubjectsMap.get(e.getId());
					if (courseSubject == null) {
						log.error("invalid course subject id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course subject id : " + e.getId());
					}
				}
				BeanUtils.copyProperties(e, courseSubject);
				courseSubject.setCourse(course);
				if (StringUtils.isEmpty(courseSubject.getId())) {
					courseSubjects.add(courseSubject);
				}
				courseSubject.setAuditFields(loggedInUserId);
			});

		} else {
			courseSubjects.clear();
		}
	}

	private void saveUpdateCourseCareerOutcomes(String loggedInUserId, Course course,
			List<CourseCareerOutcomeDto> courseCareerOutcomeDtos) throws ValidationException {
		log.info("inside courseProcessor.saveUpdateCourseCareerOutcomes");
		List<CourseCareerOutcome> courseCareerOutcomes = course.getCourseCareerOutcomes();
		if (!CollectionUtils.isEmpty(courseCareerOutcomeDtos)) {

			log.info("Creating the list to save/update course career outcomes in DB");
			Set<String> careerIds = courseCareerOutcomeDtos.stream().map(CourseCareerOutcomeDto::getCareerId)
					.collect(Collectors.toSet());
			Map<String, Careers> careersMap = careerDao.findByIdIn(new ArrayList<>(careerIds)).stream()
					.collect(Collectors.toMap(Careers::getId, e -> e));
			if (careersMap.size() != careerIds.size()) {
				throw new ValidationException("one or more career ids are invalid");
			}

			Set<String> updateRequestIds = courseCareerOutcomeDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseCareerOutcomeDto::getId).collect(Collectors.toSet());
			courseCareerOutcomes.removeIf(e -> !updateRequestIds.contains(e.getId()));

			Map<String, CourseCareerOutcome> existingCourseCareerOutcomesMap = courseCareerOutcomes.stream()
					.collect(Collectors.toMap(CourseCareerOutcome::getId, e -> e));
			courseCareerOutcomeDtos.stream().forEach(e -> {
				CourseCareerOutcome courseCareerOutcome = new CourseCareerOutcome();
				if (!StringUtils.isEmpty(e.getId())) {
					courseCareerOutcome = existingCourseCareerOutcomesMap.get(e.getId());
					if (courseCareerOutcome == null) {
						log.error("invalid course career outcome id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course career outcome id : " + e.getId());
					}
				}
				courseCareerOutcome.setCareer(careersMap.get(e.getCareerId()));
				courseCareerOutcome.setCourse(course);
				if (StringUtils.isEmpty(courseCareerOutcome.getId())) {
					courseCareerOutcomes.add(courseCareerOutcome);
				}
				courseCareerOutcome.setAuditFields(loggedInUserId);
			});

		} else {
			courseCareerOutcomes.clear();
		}
	}
	
	private void saveUpdateCourseFundings(String loggedInUserId, Course course,
			List<CourseFundingDto> courseFundingDtos) throws NotFoundException, InvokeException {
		List<CourseFunding> courseFundings = course.getCourseFundings();
		if (!CollectionUtils.isEmpty(courseFundingDtos)) {

			log.info("Creating the list to save/update course fundings in DB");
			Set<String> fundingNameIds = courseFundingDtos.stream().map(CourseFundingDto::getFundingNameId)
					.collect(Collectors.toSet());

			log.info("going to check if funding name ids are valid");
			commonProcessor.validateAndGetFundingsByFundingNameIds(new ArrayList<>(fundingNameIds));

			log.info("see if some entitity ids are not present then we have to delete them.");
			Set<String> updateRequestIds = courseFundingDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseFundingDto::getId).collect(Collectors.toSet());
			courseFundings.removeIf(e -> !updateRequestIds.contains(e.getId()));

			Map<String, CourseFunding> existingCourseFundingsMap = courseFundings.stream()
					.collect(Collectors.toMap(CourseFunding::getId, e -> e));
			courseFundingDtos.stream().forEach(e -> {
				CourseFunding courseFunding = new CourseFunding();
				if (!StringUtils.isEmpty(e.getId())) {
					courseFunding = existingCourseFundingsMap.get(e.getId());
					if (courseFunding == null) {
						log.error("invalid course funding id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course funding id : " + e.getId());
					}
				}
				courseFunding.setFundingNameId(e.getFundingNameId());
				courseFunding.setCourse(course);
				if (StringUtils.isEmpty(courseFunding.getId())) {
					courseFundings.add(courseFunding);
				}
				courseFunding.setAuditFields(loggedInUserId);
			});

		} else {
			courseFundings.clear();
		}
	}

	private void populateCourseUsdPrices(CurrencyRateDto currencyRate, Course course) {
		log.info("inside courseProcessor.populateCourseUsdPrices");
		if (!ObjectUtils.isEmpty(course.getDomesticApplicationFee())) {
			Double convertedRate = currencyRate.getConversionRate();
			log.info("converting domestic application Fee on basis of conviersionRate {}", convertedRate);
			if (convertedRate != null && convertedRate != 0.0) {
				log.info("converting domesticApplicationFee to usdDomesticApplicationFee");
				Double usdDomesticApplicationFee = course.getDomesticApplicationFee() / convertedRate;
				course.setUsdDomesticApplicationFee(usdDomesticApplicationFee);
			}
		}
		if (!ObjectUtils.isEmpty(course.getInternationalApplicationFee())) {
			Double convertedRate = currencyRate.getConversionRate();
			log.info("converting international application Fee on basis of conviersionRate {}", convertedRate);
			if (convertedRate != null) {
				log.info("converting internationalApplicationFee into usdInternationalApplicationFee");
				Double usdInternationalApplicationFee = course.getInternationalApplicationFee() / convertedRate;
				course.setUsdInternationalApplicationFee(usdInternationalApplicationFee);
				
			}
		}	
		if (!ObjectUtils.isEmpty(course.getDomesticEnrollmentFee())) {
			Double convertedRate = currencyRate.getConversionRate();
			log.info("converting domestic enrollment Fee on basis of conviersionRate {}", convertedRate);
			if (convertedRate != null && convertedRate != 0.0) {
				log.info("converting domesticEnrollmentFee to usdDomesticEnrollmentFee");
				Double usdDomesticEnrollmentFee = course.getDomesticEnrollmentFee() / convertedRate;
				course.setUsdDomesticEnrollmentFee(usdDomesticEnrollmentFee);
			}
		}
		if (!ObjectUtils.isEmpty(course.getInternationalEnrollmentFee())) {
			Double convertedRate = currencyRate.getConversionRate();
			log.info("converting international Enrollment Fee on basis of conviersionRate {}", convertedRate);
			if (convertedRate != null) {
				log.info("converting internationalEnrollmentFee into usdInternationalEnrollmentFee");
				Double usdInternationalEnrollmentFee = course.getInternationalEnrollmentFee() / convertedRate;
				course.setUsdInternationalEnrollmentFee(usdInternationalEnrollmentFee);	
			}
		}
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public String saveOrUpdateCourse(final String loggedInUserId, String instituteId, final CourseRequest courseDto, final String id)
			throws ValidationException, NotFoundException, ForbiddenException, InvokeException {
		log.debug("Inside saveUpdateCourse() method");
		Course course = prepareCourseModelFromCourseRequest(loggedInUserId, instituteId, id, courseDto);
		course = courseDao.addUpdateCourse(course);
		timingProcessor.saveUpdateDeleteTimings(loggedInUserId, EntityTypeEnum.COURSE, courseDto.getCourseTimings(), course.getId());
		log.info("Calling elastic service to save/update course on elastic index having courseId: ", course.getId());
		publishSystemEventHandler.syncCourses(Arrays.asList(conversionProcessor.convertToCourseSyncDTOSyncDataEntity(course)));
		return course.getId();
	}
	
	private CurrencyRateDto getCurrencyRate(final String courseCurrency) {
		log.debug("Inside getCurrencyRate() method");
		log.info("Calling DAO layer to getCurrencyRate from DB having currencyCode = "+courseCurrency);
    	CurrencyRateDto currencyRate = commonHandler.getCurrencyRateByCurrencyCode(courseCurrency);
		return currencyRate;
	}

	private Faculty getFaculty(final String facultyId) throws NotFoundException {
		Faculty faculty = null;
		if (!StringUtils.isEmpty(facultyId)) {
			faculty = facultyDAO.get(facultyId);
			if (ObjectUtils.isEmpty(faculty)) {
				log.error("invalid faculty id: {}", facultyId);
				throw new NotFoundException("invalid faculty id: " + facultyId);
			}
		}
		return faculty;
	}

	private Institute getInstititute(final String instituteId) throws NotFoundException {
		Institute institute = null;
		if (!StringUtils.isEmpty(instituteId)) {
			institute = instituteDAO.get(instituteId);
			if (ObjectUtils.isEmpty(institute)) {
				log.error("invalid institute id: {}", instituteId);
				throw new NotFoundException("invalid institute id: " + instituteId);
			}
		}
		return institute;
	}

	@Transactional
	public PaginationResponseDto getAllCourse(final Integer pageNumber, final Integer pageSize) {
		log.debug("Inside getAllCourse() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			log.info("Getting total count for course to calculate pagination");
			int totalCount = courseDao.findTotalCount();
			Long startIndex = (Long.valueOf(pageNumber - 1)) * pageSize;
			log.info("Calling DAO layer to fetch courses from DB with limit = " + startIndex + ", " + pageSize);
			List<CourseRequest> courses = courseDao.getAll(startIndex.intValue(), pageSize);
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Courses fetched from DB hence start iterating courses");
				List<CourseRequest> resultList = new ArrayList<>();
				courses.stream().forEach(course -> {
					try {
						log.info("Calling Storage service to fetch course images");
						List<StorageDto> storageDTOList = storageHandler.getStorages(course.getInstituteId(), EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.IMAGES);
						course.setStorageList(storageDTOList);
					} catch (NotFoundException | InvokeException e) {
						log.error("Error invoking Storage service exception {}",e);
					}
					log.info("Fetching courseDeliveryModes from DB having courseId = "+course.getId());
					List<CourseDeliveryModesDto> courseDeliveryModesDtos = courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseDeliveryModesDtos)) {
						log.info("courseDeliveryMode is fetched from DB, hence adding courseDeliveryModesDto in response");
						course.setCourseDeliveryModes(new ValidList<>(courseDeliveryModesDtos));
					}
					
					log.info("Fetching courseEnglishEligibility from DB having courseId = "+course.getId());
					List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos = courseEnglishEligibilityProcessor.getAllEnglishEligibilityByCourse(course.getId());
					if(!CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {
						log.info("courseEnglishEligibility is fetched from DB, hence adding englishEligibilities in response");
						course.setEnglishEligibility(new ValidList<>(courseEnglishEligibilityDtos));
					}
					resultList.add(course);
				});
				log.info("Calculating pagination based on startIndex, pageSize, totalCount");
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(Long.valueOf(totalCount));
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
	
	@Transactional
	public void deleteCourse( String loggedInUserId, String courseId, List<String> linkedCourseIds) throws ForbiddenException, NotFoundException {
		log.debug("Inside deleteCourse() method");

		log.info("Fetching course from DB having courseId = " + courseId);
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			if (!course.getCreatedBy().equals(loggedInUserId)) {
				log.error("User has no access to delete the course with id: {}", courseId);
				throw new ForbiddenException("User has no delete to edit the course with id: " + courseId);
			}
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				log.info("going to unlink and delete the linked courses");
				List<UnLinkInsituteDto> unLinkRequest = linkedCourseIds.stream()
						.map(e -> new UnLinkInsituteDto(e, true)).collect(Collectors.toList());
				courseInstituteProcessor.unLinkInstitutes(loggedInUserId, courseId, unLinkRequest);
			}else {
				linkedCourseIds = new ArrayList<>();
			}
			linkedCourseIds.add(courseId);
			timingDao.deleteByEntityTypeAndEntityIdIn(EntityTypeEnum.COURSE, linkedCourseIds);
			log.info("going to remove all links of the course");
			courseInstituteProcessor.removeAllLinksWithInstitutes(course);
			courseDao.deleteCourse(courseId);
			log.info("Calling elastic service to update course having entityId = " + courseId);
		} else {
			log.error("Course not found for courseId = " + courseId);
			throw new NotFoundException("Course not found for courseId = " + courseId);
		}
	}

	@Transactional
	public List<CourseDto> getUserCourse(final List<String> courseIds, final String sortBy, final String sortAsscending) throws ValidationException, InvokeException {
		log.debug("Inside getUserCourse() method");
		log.info("Extracting courses from DB based on pagination and courseIds");
		List<CourseDto> courses = new ArrayList<>();
		if(!StringUtils.isEmpty(sortAsscending) && sortAsscending.equalsIgnoreCase("true")) {
			courses = courseDao.getUserCourse(courseIds, sortBy, true);
		} else {
			courses = courseDao.getUserCourse(courseIds, sortBy, false);
		}
		
		log.info("Filering course response if courseId is coming duplicate in response");
		courses = courses.stream().filter(Utils.distinctByKey(CourseDto::getId)).collect(Collectors.toList());
		
		if(!CollectionUtils.isEmpty(courses)) {
			log.info("Courses fetched from DB hence strat iterating data");
			courses.stream().forEach(courseRequest -> {
				log.info("Filtering course additional info by matching courseIds");
				List<CourseDeliveryModesDto> additionalInfoDtos = courseRequest.getCourseDeliveryModes().stream().
							filter(x -> x.getCourseId().equals(courseRequest.getId())).collect(Collectors.toList());
				courseRequest.setCourseDeliveryModes(additionalInfoDtos);
			});
		}
		return courses;
	}

	@Transactional
	public Course getCourseData(final String courseId) {
		return courseDao.getCourseData(courseId);
	}

	@Transactional
	public List<CourseResponseDto> advanceSearch(final AdvanceSearchDto courseSearchDto, List<String> entityIds) 
			throws ValidationException, InvokeException, NotFoundException {
		log.debug("Inside advanceSearch() method");
		
		log.info("Calling DAO layer to fetch courses from DB based on passed filters");
		List<CourseResponseDto> courseResponseDtos = courseDao.advanceSearch(entityIds, courseSearchDto);
		if (courseResponseDtos == null || courseResponseDtos.isEmpty()) {
			log.info("No courses found in DB for passed filters");
			return new ArrayList<>();
		}
		
		log.info("Filtering distinct courses based on courseId and collect in list");
		List<CourseResponseDto> courseResponseFinalResponse = courseResponseDtos.stream().filter(Utils.distinctByKey(CourseResponseDto::getId))
				.collect(Collectors.toList());
		
		//log.info("Filtering distinct courseIds");
		//List<String> courseIds = courseResponseDtos.stream().filter(CommonUtil.distinctByKey(CourseResponseDto::getId)).map(CourseResponseDto::getId).collect(Collectors.toList());
		
		log.info("Calling Storage service to get images based on entityId");
		List<StorageDto> storageDTOList = storageHandler.getStorages(
				courseResponseDtos.stream().map(CourseResponseDto::getInstituteId).collect(Collectors.toList()), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
		
		Map<String, ReviewStarDto> yuzeeReviewMap = null;
		try {
			log.info("Calling review service to fetch user average review for instituteId");
			yuzeeReviewMap = reviewHandler.getAverageReview("COURSE",
					courseResponseDtos.stream().map(CourseResponseDto::getId).collect(Collectors.toList()));
		} catch (Exception e) {
			log.error("Error invoking review service having exception = " + e);
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
				
				UserViewCourseDto userViewCourseDto = viewTransactionHandler.getUserViewedCourseByEntityIdAndTransactionType(courseSearchDto.getUserId(), 
						EntityTypeEnum.COURSE.name(), courseResponseDto.getId(), TransactionTypeEnum.VIEW.name());
				if(!ObjectUtils.isEmpty(userViewCourseDto)) {
					courseResponseDto.setIsViewed(true);
				} else {
					courseResponseDto.setIsViewed(false);
				}
				
				log.info("Grouping course delivery modes data and add it in final response");
				List<CourseDeliveryModesDto> additionalInfoDtos = courseResponseDto.getCourseDeliveryModes().stream().
						filter(x -> x.getCourseId().equals(courseResponseDto.getId())).collect(Collectors.toList());
				courseResponseDto.setCourseDeliveryModes(additionalInfoDtos);

				log.info("Fetching courseIntake from DB having courseId = "+courseResponseDto);
				List<CourseIntake> courseIntake = courseDao.getCourseIntakeBasedOnCourseId(courseResponseDto.getId());
				if (courseIntake != null && !courseIntake.isEmpty()) {
					log.info("Filtering courseIntakes data based on courseId");
					courseResponseDto.setIntake(courseIntake.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
				} else {
					courseResponseDto.setIntake(new ArrayList<>());
				}
				
				log.info("Fetching courseLanguages from DB having courseId = "+courseResponseDto);
				List<CourseLanguage> courseLanguages = courseDao.getCourseLanguageBasedOnCourseId(courseResponseDto.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					log.info("Filtering courseLanguages data based on courseId");
					courseResponseDto.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				} else {
					courseResponseDto.setLanguage(new ArrayList<>());
				}
				
				log.info("Calculating average review rating based on reviews");
				courseResponseDto
						.setStars(calculateAverageRating(yuzeeReviewMap, courseResponseDto.getStars(), courseResponseDto.getInstituteId()));
				
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

	@Transactional
	public double calculateAverageRating(final Map<String, ReviewStarDto> yuzeeReviewMap, final Double courseStar,
			final String instituteId) {
		log.debug("Inside calculateAverageRating() method");
		Double courseStars = 0d;
		Double googleReview = 0d;
		Double yuzeeReview = 0d;
		log.info("Calculating avearge rating based on googleReview, yuzeeReview and course rating");
		int count = 0;
		if (courseStar != null) {
			courseStars = courseStar;
			count++;
		}
		log.info("course Rating = ", courseStar );
		if (yuzeeReviewMap != null && yuzeeReviewMap.get(instituteId) != null && !ObjectUtils.isEmpty(yuzeeReviewMap.get(instituteId).getReviewStars())) {
			yuzeeReview = yuzeeReviewMap.get(instituteId).getReviewStars();
			count++;
		}
		log.info("course Yuzee Rating", yuzeeReview);
		Double rating = Double.sum(courseStars, googleReview);
		if (count != 0) {
			Double finalRating = Double.sum(rating, yuzeeReview);
			return finalRating / count;
		} else {
			return 0d;
		}
	}

	@Transactional
	public PaginationResponseDto courseFilter(final CourseFilterDto courseFilter) {
		log.debug("Inside courseFilter() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			log.info("fetched total count of courses based on passed filters");
			int totalCount = courseDao.findTotalCountCourseFilter(courseFilter);
			Long startIndex = (Long.valueOf(courseFilter.getPageNumber() - 1)) * courseFilter.getMaxSizePerPage();
			log.info("Fetching course data from DB based on filters and pagination");
			List<CourseRequest> courses = courseDao.courseFilter(startIndex.intValue(), courseFilter.getMaxSizePerPage(), courseFilter);

			List<CourseRequest> resultList = new ArrayList<>();
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Course are coming from DB hence start making final response");
				courses.stream().forEach(courseRequest -> {
					try {
						log.info("Start invoking Storage service to fetch images");
						List<StorageDto> storageDTOList = storageHandler.getStorages(courseRequest.getInstituteId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
						courseRequest.setStorageList(storageDTOList);
					} catch (NotFoundException | InvokeException e) {
						log.error("Error invoking Storage service having exception = "+e);
					}
					log.info("Fetching course additional info from DB having courseId = "+courseRequest.getId());
					courseRequest.setCourseDeliveryModes(new ValidList<>(courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(courseRequest.getId())));
					log.info("Fetching course intakes from DB having courseId = "+courseRequest.getId());
					courseRequest.setIntake(courseDao.getCourseIntakeBasedOnCourseId(courseRequest.getId())
								.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
					log.info("Fetching course languages from DB having courseId = "+courseRequest.getId());
					courseRequest.setLanguage(courseDao.getCourseLanguageBasedOnCourseId(courseRequest.getId())
							.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
					
					resultList.add(courseRequest);
				});
				log.info("Calculating pagination based on startIndex, pageSize and totalCount");
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseFilter.getMaxSizePerPage(), totalCount);
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(Long.valueOf(totalCount));
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

	@Transactional
	public PaginationResponseDto autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		log.debug("Inside autoSearch() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			List<CourseRequest> resultList = new ArrayList<>();
			log.info("Fetching total count of courses from DB for searchKey = "+searchKey);
			Long totalCount = courseDao.autoSearchTotalCount(searchKey);
			Long startIndex = (Long.valueOf(pageNumber - 1)) * pageSize;
			log.info("Fetching courses from DB based on pagination and having searchKeyword = "+searchKey);
			List<CourseRequest> courses = courseDao.autoSearch(startIndex.intValue(), pageSize, searchKey);
			if(!CollectionUtils.isEmpty(courses)) {
				log.info("Courses fetched from DB, hence start iterating data");
				courses.stream().forEach(course -> {
					try {
						log.info("Calling storage service to fetch course images");
						List<StorageDto> storageDTOList = storageHandler.getStorages(course.getInstituteId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
						course.setStorageList(storageDTOList);
					} catch (NotFoundException | InvokeException e) {
						log.error("Error invoking Storage service having exception = "+e);
					}
					log.info("Fetching courseDeliveryModes from DB having courseId = "+course.getId());
					List<CourseDeliveryModesDto> courseDeliveryModesDtos = courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseDeliveryModesDtos)) {
						log.info("courseDeliveryModes is fetched from DB, hence adding courseDeliveryModes in response");
						course.setCourseDeliveryModes(new ValidList<>(courseDeliveryModesDtos));
					}
					
					log.info("Fetching courseEnglishEligibility from DB having courseId = "+course.getId());
					List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos = courseEnglishEligibilityProcessor.getAllEnglishEligibilityByCourse(course.getId());
					if(!CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {
						log.info("courseEnglishEligibility is fetched from DB, hence adding englishEligibilities in response");
						course.setEnglishEligibility(new ValidList<>(courseEnglishEligibilityDtos));
					}
					
					log.info("Fetching courseLanguage from DB having courseId = "+course.getId());
					List<CourseLanguage> courseLanguages = courseDao.getCourseLanguageBasedOnCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseLanguages)) {
						log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
						course.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
					}
					
					log.info("Fetching courseIntake from DB having courseId = "+course.getId());
					List<CourseIntake> courseIntakes = courseDao.getCourseIntakeBasedOnCourseId(course.getId());
					if(!CollectionUtils.isEmpty(courseIntakes)) {
						log.info("courseIntake is fetched from DB, hence adding englishEligibilities in response");
						course.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
					}
					resultList.add(course);
				});
				
				PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
				paginationResponseDto.setResponse(resultList);
				paginationResponseDto.setTotalCount(totalCount);
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

	@Transactional
	public List<Course> facultyWiseCourseForInstitute(final List<Faculty> facultyList, final Institute institute) {
		return courseDao.facultyWiseCourseForTopInstitute(facultyList, institute);
	}

	@Transactional
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
				CourseRequest courseRequest = new CourseRequest();
				CommonUtil.copyCourseToCourseRequest(course, courseRequest);
				coursesRequests.add(courseRequest);
			});
		}
		return coursesRequests;
	}

	@Transactional
	public long checkIfCoursesPresentForCountry(final String country) {
		return courseDao.getCourseCountForCountry(country);
	}
	
	@Transactional
	public List<Course> getAllCoursesUsingId(final List<String> listOfRecommendedCourseIds) {
		return courseDao.getAllCoursesUsingId(listOfRecommendedCourseIds);
	}

	@Transactional
	public Set<Course> getRelatedCoursesBasedOnPastSearch(final List<String> courseList) throws ValidationException {
		log.debug("Inside getRelatedCoursesBasedOnPastSearch() method");
		Set<Course> relatedCourses = new HashSet<>();
		courseList.stream().forEach(courseId -> {
			try {
				log.info("Getting related course from DB having courseId = "+courseId);
				relatedCourses.addAll(userRecommedationService.getRelatedCourse(courseId));
			} catch (ValidationException e) {
				log.error("Exception while fetching related courses having exception = "+e);
			}
		});
		return relatedCourses;
	}

	@Transactional
	public Long getCountOfDistinctInstitutesOfferingCoursesForCountry(final UserDto userDto, final String country) {
		return courseDao.getCountOfDistinctInstitutesOfferingCoursesForCountry(userDto, country);
	}

	@Transactional
	public List<String> getCountryForTopSearchedCourses(final List<String> topSearchedCourseIds) throws ValidationException {
		if (topSearchedCourseIds == null || topSearchedCourseIds.isEmpty()) {
			throw new ValidationException(messageByLocaleService.getMessage("no.course.id.specified", new Object[] {}));
		}
		return courseDao.getDistinctCountryBasedOnCourses(topSearchedCourseIds);
	}

	@Transactional
	public List<String> courseIdsForCountry(final String country) {
		return courseDao.getCourseIdsForCountry(country);
	}

	@Transactional
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
			List<String> courseIds = courseDao.getAllCoursesForCountry(otherCountryIds);
			return courseIds;
		}
		return new ArrayList<>();
	}

	@Transactional
	public void updateCourseForCurrency(final CurrencyRateDto currencyRate) {
		courseDao.updateCourseForCurrency(currencyRate);
	}

	@Transactional
	public int getCountOfAdvanceSearch(final AdvanceSearchDto courseSearchDto, List<String> entityIds) 
			throws ValidationException, NotFoundException {
		return courseDao.getCountOfAdvanceSearch(entityIds, courseSearchDto);
	}

	@Transactional
	public List<CourseSyncDTO> getUpdatedCourses(final Date date, final Integer startIndex, final Integer limit) {
		return courseDao.getUpdatedCourses(date, startIndex, limit);
	}

	@Transactional
	public Integer getCountOfTotalUpdatedCourses(final Date utCdatetimeAsOnlyDate) {
		return courseDao.getCountOfTotalUpdatedCourses(utCdatetimeAsOnlyDate);
	}

	@Transactional
	public List<CourseSyncDTO> getCoursesToBeRetriedForElasticSearch(final List<String> courseIds, final Integer startIndex, final Integer limit) {
		return courseDao.getCoursesToBeRetriedForElasticSearch(courseIds, startIndex, limit);
	}

	@Transactional
	public List<CourseIntake> getCourseIntakeBasedOnCourseId(final String courseId) {
		return courseDao.getCourseIntakeBasedOnCourseId(courseId);
	}

	@Transactional
	public List<CourseLanguage> getCourseLanguageBasedOnCourseId(final String courseId) {
		return courseDao.getCourseLanguageBasedOnCourseId(courseId);
	}

	@Transactional
	public List<CourseResponseDto> getCourseNoResultRecommendation(final String userCountry, final String facultyId, final String countryId,
			final Integer startIndex, final Integer pageSize) throws ValidationException, InvokeException, NotFoundException {
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
				List<CourseResponseDto> courseResponseDtos2 = courseDao.getAllCoursesByFilter(courseSearchDto, null, null, startIndex, false, null);
				courseResponseDtos.addAll(courseResponseDtos2);
			}
		}
		return getExtraInfoOfCourseFilter(courseSearchDto, courseResponseDtos);

	}
	
	@Transactional
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
			List<CourseResponseDto> courseResponseDtos = courseDao.getAllCoursesByFilter(courseSearchDto, null, null, startIndex, true, null);
			courseKeywordRecommended = courseResponseDtos.stream().map(CourseResponseDto::getName).collect(Collectors.toList());
		}
		return courseKeywordRecommended;
	}

	@Transactional
	public int getDistinctCourseCount(String courseName) {
		return courseDao.getDistinctCourseCountbyName(courseName);
	}

	@Transactional
	public List<CourseResponseDto> getDistinctCourseList(Integer startIndex, Integer pageSize,String courseName) {
		return courseDao.getDistinctCourseListByName(startIndex, pageSize, courseName);
	}

	@Transactional
	public Map<String, Integer> getCourseCountByLevel() {
		log.debug("Inside getCourseCountByLevel() method");
		Map<String, Integer> courseLevelCount = new HashMap<>();
		log.info("Fetching all levels from DB");
		List<Level> levels = levelDao.getAll();
		if (!CollectionUtils.isEmpty(levels)) {
			log.info("Levels fetched from DB, now fetching course count for each level");
			levels.stream().forEach(level -> {
				Integer courseCount = null;
				if (!ObjectUtils.isEmpty(level.getId())) {
					log.info("Caliling DAO layer to get course count for levelId = "+level.getId());
					courseCount = courseDao.getCoursesCountBylevelId(level.getId());
				}
				if (!ObjectUtils.isEmpty(courseCount)) {
					courseLevelCount.put(level.getName(), courseCount);
				}
			});
		}
		return courseLevelCount;
	}

	@Transactional
	public void addMobileCourse(String userId, String instituteId, CourseMobileDto courseMobileDto) throws Exception {
		log.debug("Inside addMobileCourse() method");
		log.info("Validate user id "+userId+ " have appropriate access for institute id "+instituteId);
		 // TODO validate user id have appropriate access for institute id 
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(instituteId);
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
		courseDao.addUpdateCourse(course);
	}

	@Transactional
	public void updateMobileCourse(String userId, String courseId, CourseMobileDto courseMobileDto) throws Exception {
		log.debug("Inside updateMobileCourse() method");
		log.info("Getting course by course id "+courseId);
		Course course =courseDao.get(courseId);
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
		courseDao.addUpdateCourse(course);
	} 
	
	@Transactional
	public List<CourseMobileDto> getAllMobileCourseByInstituteIdAndFacultyIdAndStatus(String userId, String instituteId, String facultyId, boolean status) throws Exception {
		List<CourseMobileDto> listOfCourseMobileDto = new ArrayList<CourseMobileDto>();
		log.debug("Inside getAllMobileCourseByInstituteIdAndFacultyId() method");
		 // TODO validate user id have appropriate access for institute id 
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		log.info("Getting course for institute id "+instituteId+ " having faculty id "+facultyId);
		List<Course> listOfCourse = courseDao.getAllCourseByInstituteIdAndFacultyIdAndStatus(instituteId, facultyId, status);
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
	
	@Transactional
	public List<CourseMobileDto> getPublicMobileCourseByInstituteIdAndFacultyId(String instituteId, String facultyId) throws Exception {
		List<CourseMobileDto> listOfCourseMobileDto = new ArrayList<CourseMobileDto>();
		log.debug("Inside getPublicMobileCourseByInstituteIdAndFacultyId() method");
		log.info("Getting institute for institute id "+instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		log.info("Getting course for institute id "+instituteId+ " having faculty id "+facultyId + " and status active");
		List<Course> listOfCourse = courseDao.getAllCourseByInstituteIdAndFacultyIdAndStatus(instituteId, facultyId, true);
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
	
	@Transactional
	public void changeCourseStatus (String userId , String courseId, boolean status) throws Exception {
		log.debug("Inside updateMobileCourse() method");
		log.info("Getting course by course id "+courseId);
		Course course =courseDao.get(courseId);
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
		courseDao.addUpdateCourse(course);
		log.info("Calling elastic service to save/update course on elastic index having courseId: ", course.getId());
		publishSystemEventHandler.syncCourses(Arrays.asList(DTOUtils.convertToCourseDTOElasticSearchEntity(course)));
	}
	
	@Transactional
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
				
				log.info("Fetching courseDeliveryModes from DB having courseId = "+course.getId());
				List<CourseDeliveryModesDto> courseDeliveryModesDtos = courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseDeliveryModesDtos)) {
					log.info("courseDeliveryModes is fetched from DB, hence adding courseDeliveryModes in response");
					nearestCourse.setCourseDeliveryModes(courseDeliveryModesDtos);
				}
				
				log.info("Fetching courseLanguage from DB having courseId = "+course.getId());
				List<CourseLanguage> courseLanguages = courseDao.getCourseLanguageBasedOnCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseLanguages)) {
					log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
					nearestCourse.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
				}
				
				log.info("Fetching courseIntake from DB having courseId = "+course.getId());
				List<CourseIntake> courseIntakes = courseDao.getCourseIntakeBasedOnCourseId(course.getId());
				if(!CollectionUtils.isEmpty(courseIntakes)) {
					log.info("courseIntake is fetched from DB, hence adding englishEligibilities in response");
					nearestCourse.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
				}
				try {
					log.info("going to fetch logo from storage service for courseId "+course.getId());
					List<StorageDto> storageDTOList = storageHandler.getStorages(course.getId(), 
							EntityTypeEnum.COURSE,EntitySubTypeEnum.LOGO);
					nearestCourse.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Exception in calling storage service "+e);
				}
				nearestCourseList.add(nearestCourse);
			});
		} else {
			log.error("No course found for instituteId "+instituteId);
			throw new NotFoundException("No course found for instituteId "+instituteId);
		}
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
		NearestCoursesDto nearestCoursesPaginationDto = new NearestCoursesDto(nearestCourseList, totalCount, paginationUtilDto.getPageNumber(), 
				paginationUtilDto.isHasPreviousPage(), paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return nearestCoursesPaginationDto;
	}
	
	@Transactional
	public NearestCoursesDto getNearestCourses(AdvanceSearchDto courseSearchDto)
			throws ValidationException, NotFoundException, InvokeException {
		log.debug("Inside getNearestCourses() method");
		List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
		Boolean runMethodAgain = true;
		Integer initialRadius = courseSearchDto.getInitialRadius();
		Integer increaseRadius = 25, totalCount = 0;
		log.info("start getting nearest courses from DB for latitude " + courseSearchDto.getLatitude()
				+ " and longitude " + courseSearchDto.getLongitude() + " and initial radius is " + initialRadius);
		List<CourseResponseDto> nearestCourseDTOs = courseDao.getNearestCourseForAdvanceSearch(courseSearchDto);
		log.info("going to fetch total count of courses from DB");
		totalCount = courseDao.getTotalCountOfNearestCourses(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
		while (runMethodAgain) {
			if (initialRadius != maxRadius && CollectionUtils.isEmpty(nearestCourseDTOs)) {
				log.info("if data is comming as null from DB then increase radius, new radius is " + initialRadius);
				runMethodAgain = true;
				initialRadius = initialRadius + increaseRadius;
				courseSearchDto.setInitialRadius(initialRadius);
				log.info("for old radius data is not coming so start fetching nearest courses for new radius " + initialRadius);
				nearestCourseDTOs = courseDao.getNearestCourseForAdvanceSearch(courseSearchDto);
				totalCount = courseDao.getTotalCountOfNearestCourses(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
			} else {
				log.info("data is coming from DB for radius " + initialRadius);
				runMethodAgain = false;
				log.info("start iterating data which is coming from DB");
				
				log.info("Filering course response if ID is coming duplicate in response");
				List<CourseResponseDto> courseResponseFinalResponse = nearestCourseDTOs.stream().filter(Utils.distinctByKey(CourseResponseDto::getId))
						.collect(Collectors.toList());
				log.info("Collecting CourseIds by stream API and calling store it in list");
				
				for (CourseResponseDto nearestCourseDTO : courseResponseFinalResponse) {
					CourseResponseDto nearestCourse = new CourseResponseDto();
					BeanUtils.copyProperties(nearestCourseDTO, nearestCourse);
					nearestCourse.setDistance(Double.valueOf(initialRadius));
					log.info("fetching institute logo from storage service for instituteID " + nearestCourseDTO.getId());
					List<StorageDto> storageDTOList = storageHandler.getStorages(nearestCourseDTO.getId(),
							EntityTypeEnum.COURSE, EntitySubTypeEnum.LOGO);
					nearestCourse.setStorageList(storageDTOList);
					
					log.info("Filtering course additional info by matching courseId");
					List<CourseDeliveryModesDto> additionalInfoDtos = nearestCourseDTO.getCourseDeliveryModes().stream().
								filter(x -> x.getCourseId().equals(nearestCourseDTO.getId())).collect(Collectors.toList());
					nearestCourse.setCourseDeliveryModes(additionalInfoDtos);
					
					log.info("Fetching courseIntake from DB having courseId = "+nearestCourseDTO.getId());
					List<CourseIntake> courseIntakes = courseDao.getCourseIntakeBasedOnCourseId(nearestCourseDTO.getId());
					if (!CollectionUtils.isEmpty(courseIntakes)) {
						log.info("Filtering courseIntakes data based on courseId");
						nearestCourse.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
					} else {
						nearestCourse.setIntake(new ArrayList<>());
					}
					
					log.info("Fetching courseLanguages from DB having courseId = "+nearestCourseDTO.getId());
					List<CourseLanguage> courseLanguages = courseDao.getCourseLanguageBasedOnCourseId(nearestCourseDTO.getId());
					if(!CollectionUtils.isEmpty(courseLanguages)) {
						nearestCourse.setLanguage(courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
					} else {
						nearestCourse.setLanguage(new ArrayList<>());
					}
					courseResponseDtoList.add(nearestCourse);
				}
			}
		}
		Long startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		Boolean hasNextPage = false;
		if(initialRadius != maxRadius) {
			hasNextPage = true;
		} 
		NearestCoursesDto nearestCoursesPaginationDto = new NearestCoursesDto(courseResponseDtoList, Long.valueOf(totalCount), paginationUtilDto.getPageNumber(), 
				paginationUtilDto.isHasPreviousPage(), hasNextPage, paginationUtilDto.getTotalPages());
		return nearestCoursesPaginationDto;
	}

	@Transactional
	public CourseRequest getCourseById(String userId, String id, boolean isReadableId) throws Exception {
		log.debug("Inside getCourseById() method");
		log.info("Fetching course from DB having courseId = "+id);
		Course course = null;
		if (isReadableId) {
			course = courseDao.findByReadableId(id);
		}else {
			course = courseDao.get(id);
		}
		if (course == null) {
			log.error("Course not found for courseId = "+id);
			throw new NotFoundException("Course not found for courseId = "+id);
		}
		
		log.info("Course fetched from data start copying bean class data to DTO class");
		CourseRequest courseRequest = modelMapper.map(course, CourseRequest.class);
		CommonUtil.copyCourseToCourseRequest(course, courseRequest);
		if (course.getCreatedBy().equals(userId)) {
			courseRequest.setHasEditAccess(true);
		}else {
			courseRequest.setHasEditAccess(false);
		}
		Map<String, ReviewStarDto> yuzeeReviewMap = null;
			log.info(
					"Calling review service to fetch user average review based on courseID  to calculate average review");
			yuzeeReviewMap = reviewHandler.getAverageReview(EntityTypeEnum.COURSE.name(),
					Arrays.asList(courseRequest.getId()));
			
			courseRequest
					.setStars(calculateAverageRating(yuzeeReviewMap, courseRequest.getStars(), courseRequest.getId()));
			
			ReviewStarDto reviewStarDto = yuzeeReviewMap.get(courseRequest.getId());
			if (!ObjectUtils.isEmpty(reviewStarDto)) {
				courseRequest.setReviewsCount(reviewStarDto.getReviewsCount());
			}
			
			if(courseRequest.getStars() != null && courseRequest.getInstituteId() != null) {
			log.info("Calculating average review based on instituteGoogleReview and userReview and stars");
		}
		
		UserViewCourseDto userViewCourseDto = viewTransactionHandler.getUserViewedCourseByEntityIdAndTransactionType(
				userId, EntityTypeEnum.COURSE.name(), courseRequest.getId(), TransactionTypeEnum.FAVORITE.name());
		if (!ObjectUtils.isEmpty(userViewCourseDto)) {
			courseRequest.setFavoriteCourse(true);
		} else {
			courseRequest.setFavoriteCourse(false);
		}
		
		log.info("Fetching courseIntake for courseId = "+id);
		courseRequest.setIntake(course.getCourseIntakes().stream()
				.map(CourseIntake::getIntakeDate).collect(Collectors.toList()));
		
		log.info("Fetching courseLanguage for courseId = "+id);
		courseRequest.setLanguage(course.getCourseLanguages().stream()
				.map(CourseLanguage::getLanguage).collect(Collectors.toList()));
		
		log.info("Fetching courseDeliveryModes for courseId = "+id);
		courseRequest.setCourseDeliveryModes(new ValidList<>(course.getCourseDeliveryModes().stream().map(e->modelMapper.map(e,CourseDeliveryModesDto.class)).collect(Collectors.toList())));
		
		log.info("Fetching coursePrerequisites for courseId = "+id);
		courseRequest.setPrerequisiteSubjects(new ValidList<>(coursePrerequisiteProcessor.getCoursePrerequisiteSubjectsByCourseId(id)));
		
		log.info("Fetching courseEnglish Eligibility from DB based on courseId = "+id);
		courseRequest.setEnglishEligibility(new ValidList<>(course.getCourseEnglishEligibilities().stream().map(e->modelMapper.map(e,CourseEnglishEligibilityDto.class)).collect(Collectors.toList())));
		
		if (course.getOffCampusCourse() != null) {
			courseRequest.setOffCampusCourse(modelMapper.map(course.getOffCampusCourse(), OffCampusCourseDto.class));
		}
		courseRequest.setCourseTimings(new ValidList<>(timingProcessor.getTimingRequestDtoByEntityTypeAndEntityIdIn(EntityTypeEnum.COURSE, Arrays.asList(course.getId()))));
		
		courseRequest.setCourseSubjects(new ValidList<>(course.getCourseSubjects().stream().map(e->modelMapper.map(e,CourseSubjectDto.class)).collect(Collectors.toList())));
		
		List<CourseFunding> courseFundings = course.getCourseFundings();
		courseRequest.setFundingsCount(courseFundings.size());
		courseRequest.setCourseFundings(new ValidList<>(courseFundings.stream().map(e->modelMapper.map(e, CourseFundingDto.class)).collect(Collectors.toList())));
		if (!CollectionUtils.isEmpty(courseFundings)) {
			List<String> fundingNameIds = courseRequest.getCourseFundings().stream().map(CourseFundingDto::getFundingNameId)
					.collect(Collectors.toList());

			log.info("going to get fundings");
			Map<String, FundingResponseDto> fundingsMap = commonProcessor.validateAndGetFundingsByFundingNameIds(fundingNameIds);
			if (!CollectionUtils.isEmpty(fundingsMap)) {
				courseRequest.getCourseFundings().stream().forEach(e->e.setFunding(fundingsMap.get(e.getFundingNameId())));
			}
		}

		List<CourseContactPersonDto> courseContactPersons = courseRequest.getCourseContactPersons();
		if (!CollectionUtils.isEmpty(courseContactPersons)) {
			Map<String, UserInitialInfoDto> users = commonProcessor.validateAndGetUsersByUserIds(
					userId,
					courseContactPersons.stream().map(CourseContactPersonDto::getUserId).collect(Collectors.toList()));
			courseContactPersons.stream().forEach(e->{
				e.setUser(users.get(e.getUserId()));
			});
		}
		
		courseRequest.setCourseCareerOutcomes(new ValidList<>(course.getCourseCareerOutcomes().stream()
				.map(e -> modelMapper.map(e, CourseCareerOutcomeDto.class)).collect(Collectors.toList())));

		log.info("Calling Storage Service to fetch institute images");
		List<StorageDto> storageDTOList = storageHandler.getStorages(Arrays.asList(course.getId()), EntityTypeEnum.COURSE, Arrays.asList(EntitySubTypeEnum.LOGO,EntitySubTypeEnum.COVER_PHOTO, EntitySubTypeEnum.MEDIA));
		courseRequest.setStorageList(storageDTOList);
		
		log.info("Fetching institute data from DB having instututeId = "+courseRequest.getInstituteId());
		return courseRequest;
	}
	
	@Transactional
	public NearestCoursesDto getCourseByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException {
		log.debug("Inside getCourseByCountryName() method");
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> nearestCourseResponse = new ArrayList<>();
		log.info("fetching courses from DB for countryName "+ countryName);
		List<CourseResponseDto> nearestCourseDTOs = courseDao.getCourseByCountryName(pageNumber, pageSize, countryName);
		Long totalCount = courseRepository.getTotalCountOfCourseByCountryName(countryName);
		if(!CollectionUtils.isEmpty(nearestCourseDTOs)) {
			log.info("get data of courses for countryName, so start iterating data");
			nearestCourseDTOs.stream().forEach(nearestCourseDTO -> {
				CourseResponseDto nearestCourse = new CourseResponseDto();
				BeanUtils.copyProperties(nearestCourseDTO, nearestCourse);
				log.info("going to fetch logo for courses from storage service for courseID "+nearestCourseDTO.getId());
				try {
					List<StorageDto> storageDTOList = storageHandler.getStorages(nearestCourseDTO.getId(), 
							EntityTypeEnum.COURSE, EntitySubTypeEnum.LOGO);
					nearestCourse.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				log.info("Fetching courseDeliveryModes for courseId = "+ nearestCourseDTO.getId());
				nearestCourse.setCourseDeliveryModes(courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(nearestCourseDTO.getId()));
				
				nearestCourseResponse.add(nearestCourse);
			});
		}
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
		NearestCoursesDto nearestCoursesPaginationDto = new NearestCoursesDto(nearestCourseResponse, Long.valueOf(totalCount), paginationUtilDto.getPageNumber(), 
				paginationUtilDto.isHasPreviousPage(), paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return nearestCoursesPaginationDto;
	}
	
	@Transactional
	public List<CourseDto> getCourseByMultipleId(List<String> courseIds) {
		log.debug("Inside getCourseByMultipleId() method");
		List<CourseDto> courseDtos = new ArrayList<>();
		log.info("Extracting courses from DB based on multiple courseIds");
		List<Course> courseDetails = courseDao.getAllCoursesUsingId(courseIds);
		if(!CollectionUtils.isEmpty(courseDetails)) {
			log.info("Courses fetched from DB, start iterating data to make final response");
			courseDetails.stream().forEach(courseDetail -> {
				CourseDto courseResponse = new CourseDto(courseDetail.getId(),(courseDetail.getLevel() != null) ? courseDetail.getLevel().getId() : null, 
						courseDetail.getName(), 
						((courseDetail.getWorldRanking() != null) ? courseDetail.getWorldRanking().toString() : null),
						((courseDetail.getStars() != null) ? courseDetail.getStars().toString() : null),
						courseDetail.getFaculty().getName(), (courseDetail.getLevel() != null) ? courseDetail.getLevel().getName() : null, null,
						courseDetail.getDescription(), courseDetail.getRemarks(),
						courseDetail.getInstitute().getName(),
						courseDeliveryModesProcessor.getCourseDeliveryModesByCourseId(courseDetail.getId()),
						courseDetail.getInternationalStudentProcedureId(),courseDetail.getDomesticStudentProcedureId());
				courseDtos.add(courseResponse);
			});
		}
		return courseDtos;
	}
	
	@Transactional
	public CourseCountDto getCourseCountByInstitute(String instituteId) {
		CourseCountDto courseCountDto = new CourseCountDto();
		log.debug("Inside getCourseCountByInstitute method () ");
		log.info("Getting course count for institute id "+instituteId);
		long courseCount = courseDao.getCourseCountByInstituteId(instituteId);
		log.info("Total number of course found for institute id " + instituteId+" is "+courseCount);
		courseCountDto.setCourseCount(courseCount);
		return courseCountDto;
	}

	@Transactional
	public List<StorageDto> getCourseGallery(String courseId) throws InternalServerException, NotFoundException {
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			List<EntitySubTypeEnum> entitySubTypeEnums = Arrays.asList(EntitySubTypeEnum.COVER_PHOTO,
					EntitySubTypeEnum.LOGO, EntitySubTypeEnum.MEDIA);
			List<String> entityIds = Arrays.asList(courseId);

			List<String> accredeationIds = accrediatedDetailDao.getAccrediationDetailByEntityId(courseId).stream()
					.map(AccrediatedDetail::getId).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(accredeationIds)) {
				entitySubTypeEnums.add(EntitySubTypeEnum.ACCREDIATED);
				entityIds.addAll(accredeationIds);
			}

			try {
				List<StorageDto> storages = storageHandler.getStorages(entityIds, EntityTypeEnum.COURSE,
						entitySubTypeEnums);
				List<StorageDto> instituteMediaStorages = storageHandler.getStorages(course.getInstitute().getId(),
						EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.MEDIA);
				if (!CollectionUtils.isEmpty(instituteMediaStorages)) {
					storages.addAll(instituteMediaStorages);
				}
				return storages;
			} catch (NotFoundException | InvokeException e) {
				log.error(e.getMessage());
				throw new InternalServerException(e.getMessage());
			}
		} else {
			log.error("Course not found for id: {}", courseId);
			throw new NotFoundException("Course not found for id: " + courseId);
		}
	}

	@Transactional
	public String saveOrUpdateBasicCourse(String loggedInUserId, String instituteId, CourseRequest courseDto, String courseId) throws ForbiddenException, NotFoundException, ValidationException, InvokeException {
		log.info("inside CourseProcessor.prepareCourseModelFromCourseRequest");
		Course course = null;
		if (StringUtils.isEmpty(courseId)) {
			course = new Course();
			course.setIsActive(true);
			course.setName(courseDto.getName());
			readableIdProcessor.setReadableIdForCourse(course);
		} else {
			course = courseDao.get(courseId);
			if(ObjectUtils.isEmpty(course)) {
				log.error("Course with courseid {} not found",courseId);
				throw new NotFoundException(String.format("Course with courseid %s not found", courseId));
			}
			Course copyCourse = new Course();
			List<CourseIntake> courseIntakes = new ArrayList<>(course.getCourseIntakes());
			List<CourseLanguage> courseLanguages = new ArrayList<>(course.getCourseLanguages());
			List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes().stream().map(courseDeliveryMode -> {
				CourseDeliveryModes clone = new CourseDeliveryModes();
				BeanUtils.copyProperties(courseDeliveryMode, clone);
				return clone;
			}).collect(Collectors.toList());
			OffCampusCourse copyOffCampusCourse = new OffCampusCourse();
			List<CourseFunding> courseFunding = course.getCourseFundings().stream().map(funding -> {
				CourseFunding clone = new CourseFunding();
				BeanUtils.copyProperties(funding, clone);
				return clone;
			}).collect(Collectors.toList());
			
			if(!ObjectUtils.isEmpty(course.getOffCampusCourse())) {
				BeanUtils.copyProperties(course.getOffCampusCourse(), copyOffCampusCourse);				
			}
			
			BeanUtils.copyProperties(course, copyCourse);
			copyCourse.setCourseIntakes(courseIntakes);
			copyCourse.setCourseLanguages(courseLanguages);
			copyCourse.setCourseDeliveryModes(courseDeliveryModes);
			copyCourse.setOffCampusCourse(copyOffCampusCourse);
			copyCourse.setCourseFundings(courseFunding);
			
			CourseUpdateListener.putCourseInTransaction(courseId, copyCourse);
			
			if (ObjectUtils.isEmpty(course)) {
				log.error("invalid course id: {}", courseId);
				throw new NotFoundException("invalid course id: " + courseId);
			}
			if (!course.getCreatedBy().equals(loggedInUserId)) {
				log.error("User has no access to edit the course with id: {}", courseId);
				throw new ForbiddenException("User has no access to edit the course with id: "+ courseId);
			}
		}
		log.info("Fetching institute details from DB for instituteId = ", courseDto.getInstituteId());
		course.setInstitute(getInstititute(instituteId));
		course.setDescription(courseDto.getDescription());
		course.setName(courseDto.getName());
		
		log.info("Fetching faculty details from DB for facultyId = ", courseDto.getFacultyId());
		course.setFaculty(getFaculty(courseDto.getFacultyId()));
		
		if (!StringUtils.isEmpty(courseDto.getLevelId())) {
			log.info("Fetching level details from DB for levelId = ", courseDto.getLevelId());
			course.setLevel(levelProcessor.getLevel(courseDto.getLevelId()));
		}
		
		CurrencyRateDto currencyRate = null;
		if (!StringUtils.isEmpty(courseDto.getCurrency())) {
			// Here we convert price in USD and everywhere we used USD price column only.
			course.setCurrency(courseDto.getCurrency());
			log.info("Currency code is not null, hence fetching currencyRate from DB having currencyCode = ", courseDto.getCurrency());
			currencyRate = getCurrencyRate(courseDto.getCurrency());
			if (currencyRate == null) {
				log.error("Invalid currency, no USD conversion exists for this currency");
				throw new ValidationException("Invalid currency, no USD conversion exists for this currency");
			}
			populateCourseUsdPrices(currencyRate, course);	
		}
		course.setAuditFields(loggedInUserId);
		saveUpdateCourseIntakes(loggedInUserId, course, courseDto.getIntake());
		
		saveUpdateCourseLanguages(loggedInUserId, course, courseDto.getLanguage());

		saveUpdateCourseDeliveryModes(loggedInUserId, course, currencyRate, courseDto.getCourseDeliveryModes());
		
		saveUpdateOffCampusCourse(loggedInUserId, course, courseDto.getOffCampusCourse());
		
		saveUpdateCourseFundings(loggedInUserId, course, courseDto.getCourseFundings());
		
		course = courseDao.addUpdateCourse(course);
		log.info("Calling elastic service to save/update course on elastic index having courseId: ", course.getId());
		publishSystemEventHandler.syncCourses(Arrays.asList(conversionProcessor.convertToCourseSyncDTOSyncDataEntity(course)));
		return course.getId();
	}
	
	@Transactional
	public Course validateAndGetCourseById(String courseId) throws NotFoundException {
		log.info("inside get course by id");
		log.debug("going to call db for getting course for id: {}", courseId);
		Course course = courseDao.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		} else {
			return course;
		}
	}
	
	@Transactional
	public List<Course> validateAndGetCourseByIds(List<String> courseIds) throws NotFoundException {
		log.info("inside validateAndGetInstituteByIds");

		List<Course> courses = courseDao.findAllById(courseIds);
		if (courses.size() != courseIds.size()) {
			log.error("one or more course_ids not found");
			throw new NotFoundException("one or more course_ids not found");
		}
		return courses;
	}
	
	@Async
	@CacheEvict(cacheNames = {"cacheLevelMap","cacheFacultyMap", "cacheCourseCurriculumList", "cacheInstituteMap"}, allEntries = true)
	public void uploadCourseData(final MultipartFile multipartFile) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		log.debug("Inside importCourse() method");
		try {
			log.info("Launching job to upload course");
			log.debug("Inside importCourse() method");
			
			File file = File.createTempFile("Courses", "csv");
			multipartFile.transferTo(file);
			
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("csv-file", file.getAbsolutePath());
			jobParametersBuilder.addString("execution-id", UUID.randomUUID().toString());
			jobLauncher.run(job, jobParametersBuilder.toJobParameters());

		} catch (java.io.IOException e) {
			log.error("Exception in Importing Course exception {} ", e);
			throw new IOException(e.getMessage());
		}
	}
	
	@Transactional
	public void importCourseKeyword(final MultipartFile multipartFile) {
		log.debug("Inside importCourseKeyword() method");
		try {
			InputStream inputStream = multipartFile.getInputStream();
			CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
			log.info("Start reading data from inputStream using CSV reader");
			Map<String, String> columnMapping = new HashMap<>();
			log.info("Start mapping columns to bean variables");
			columnMapping.put("keyword", "keyword");
			columnMapping.put("k_desc", "KDesc");

			HeaderColumnNameTranslateMappingStrategy<CourseKeywords> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
			beanStrategy.setType(CourseKeywords.class);
			beanStrategy.setColumnMapping(columnMapping);
			CsvToBean<CourseKeywords> csvToBean = new CsvToBean<>();
			log.info("Start parsing CSV to bean");
			List<CourseKeywords> courseKeywords = csvToBean.parse(beanStrategy, reader);
			log.info("Going to check in existing courseKeyword is present in DB or not");
			List<CourseKeywords> notExistingCourseKeywordList = checkIfAlreadyExistsCourseKeyword(courseKeywords,
					courseKeywordDao.getAll());
			if (notExistingCourseKeywordList != null && notExistingCourseKeywordList.size() > 0) {
				log.info("if existing country is not null or empty then start saving cousreKeyword data in DB");
				courseKeywordDao.saveAll(notExistingCourseKeywordList);
			}			log.info("Closing input stream");
			inputStream.close();
			log.info("Closing CSV reader");
			reader.close();
		} catch (Exception exception) {
			log.error("Exception in upload course keyword exception {} ", exception);
		}
	}
	
	@Transactional
	private List<CourseKeywords> checkIfAlreadyExistsCourseKeyword(final List<CourseKeywords> courseKeywords,
			final List<CourseKeywords> existingCourseKeywords) {
		log.debug("Inside checkIfAlreadyExistsCourseKeyword() method");
		List<CourseKeywords> pendingListToSave = new ArrayList<>();
		Map<String, CourseKeywords> map = new HashMap<>();
		Map<String, CourseKeywords> countryMap = new HashMap<>();
		List<String> existingCountryList = new ArrayList<>();
		for (CourseKeywords dbCourseKeyword : existingCourseKeywords) {
			if (dbCourseKeyword.getKeyword() != null) {
				log.info("if keyword in not null");
				countryMap.put(dbCourseKeyword.getKeyword(), dbCourseKeyword);
				existingCountryList.add(dbCourseKeyword.getKeyword());
			}
		}

		for (CourseKeywords csvCourseKeyword : courseKeywords) {
			if (!checkExistingCountry(existingCountryList, csvCourseKeyword.getKeyword())) {
				map.put(csvCourseKeyword.getKeyword(), csvCourseKeyword);
			}
		}
		for (CourseKeywords jobSites : map.values()) {
			pendingListToSave.add(jobSites);
		}
		return pendingListToSave;
	}
	
	private boolean checkExistingCountry(final List<String> existingCountryList, final String name) {
		log.debug("Inside checkExistingCountry() method");
		boolean status = false;
		log.info("Start iterating for existingCountryList having name : {}",name);
		for (String str : existingCountryList) {
			if (str.trim().equalsIgnoreCase(name)) {
				status = true;
				break;
			}
		}
		return status;
	}
	
	public void exportCourseToElastic() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.debug("Inside exportCourseToElastic() method");
		jobLauncher.run(exportCourseToElastic, new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis(), true).toJobParameters());

	}
	
	public void updateProcedureIdInCourse(List<String> courseIds,String studentType, String procedureId) {
		log.info("Processor CourseProcessor method updateProcedureIdInCourse courseIds : {}, studentType : {},procedureId : {}", courseIds, studentType, procedureId);
		List<Course> courses = new ArrayList<Course>();
		if(!org.apache.commons.collections.CollectionUtils.isEmpty(courseIds) && studentType.equals(StudentTypeEnum.INTERNATIONAL.name())){
			courseIds.stream().forEach(courseId -> {
				Course course = courseDao.get(courseId);
				course.setInternationalStudentProcedureId(procedureId);
				courses.add(course);
			});
		}
		if(!org.apache.commons.collections.CollectionUtils.isEmpty(courseIds) && studentType.equals(StudentTypeEnum.DOMESTIC.name())){
			courseIds.stream().forEach(courseId -> {
				Course course = courseDao.get(courseId);
				course.setDomesticStudentProcedureId(procedureId);
				courses.add(course);
			});
		}
		courseDao.saveAll(courses);
	}

	public void updateProcedureIdInCourseByInstituteId(String instituteId, String studentType, String procedureId) {
		log.info("Processor CourseProcessor method updateProcedureIdInCourseByInstituteId instituteId : {}, studentType : {},procedureId : {}", instituteId, studentType, procedureId);
		if(studentType.equals(StudentTypeEnum.INTERNATIONAL.name())){
			List<Course> courses = courseDao.findByInstituteId(instituteId);
			courses.stream().forEach(course -> {
				course.setInternationalStudentProcedureId(procedureId);
			});
			courseDao.saveAll(courses);
		}
		if(studentType.equals(StudentTypeEnum.DOMESTIC.name())){
			List<Course> courses = courseDao.findByInstituteId(instituteId);
			courses.stream().forEach(course -> {
				course.setDomesticStudentProcedureId(procedureId);
			});
			courseDao.saveAll(courses);
		}
	}
	
	
}
