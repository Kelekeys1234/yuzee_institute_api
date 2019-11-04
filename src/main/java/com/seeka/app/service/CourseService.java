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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.bean.CourseGradeEligibility;
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
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IUserMyCourseDAO;
import com.seeka.app.dao.UserRecommendationDao;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseFilterDto;
import com.seeka.app.dto.CourseMinRequirementDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.GlobalDataDto;
import com.seeka.app.dto.GradeDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserCompareCourseResponse;
import com.seeka.app.dto.UserCourse;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.ImageCategory;
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
	private UserRecommendationDao userRecommendationDao;

	@Autowired
	private UserRecommendationService userRecommendationService;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;

	@Autowired
	private ICountryService iCountryService;

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
	public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(final CourseSearchDto courseSearchDto, final Currency currency,
			final String oldCurrencyCode) {
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
			if (courseDto.getDuration() != null && !courseDto.getDuration().isEmpty()) {
				course.setDuration(Integer.valueOf(courseDto.getDuration()));
			}
			course.setFaculty(getFaculty(courseDto.getFacultyId()));
			course.setCity(getCity(courseDto.getCityId()));
			course.setLanguage(courseDto.getLanguage());
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
			course.setIntake(courseDto.getIntake());
			course.setLink(courseDto.getLink());
			course.setDomesticFee(courseDto.getDomasticFee());
			course.setInternationalFee(courseDto.getInternationalFee());
			course.setCampusLocation(courseDto.getCampusLocation());
			if (courseDto.getCurrency() != null) {
				course.setCurrency(courseDto.getCurrency());
				List<Currency> currencies = currencyDAO.getAll();
				Currency toCurrency = currencyDAO.getCurrencyByCode("USD");
				BigInteger toCurrencyId = null;
				if (toCurrency != null) {
					toCurrencyId = toCurrency.getId();
				}

				CurrencyRate currencyRate = getCurrencyRate(courseDto.getCurrency(), currencies);
				BigInteger fromCurrencyId = getCurrencyId(currencies, courseDto.getCurrency());
				if (currencyRate == null) {
					currencyRate = currencyDAO.saveCurrencyRate(fromCurrencyId, courseDto.getCurrency());
				}
				if (currencyRate != null) {
					if (toCurrencyId != null) {
						if (courseDto.getDomasticFee() != null) {
							Double convertedRate = currencyDAO.getConvertedCurrency(currencyRate, toCurrencyId, Double.valueOf(courseDto.getDomasticFee()));
							if (convertedRate != null) {
								course.setUsdDomasticFee(convertedRate);
							}
						}
						if (courseDto.getInternationalFee() != null) {
							Double convertedRate = currencyDAO.getConvertedCurrency(currencyRate, toCurrencyId,
									Double.valueOf(courseDto.getInternationalFee()));
							if (convertedRate != null) {
								course.setUsdInternationFee(convertedRate);
							}
						}
					}
				}
			}
			iCourseDAO.save(course);

			if (courseDto.getEnglishEligibility() != null) {
				for (CourseEnglishEligibility e : courseDto.getEnglishEligibility()) {
					e.setCourse(course);
					e.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
					courseEnglishEligibilityDAO.save(e);
				}
			}
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.COURSE_ADD_SUCCESS);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> update(@Valid final CourseRequest courseDto, final BigInteger id) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Fetching the previous object
			Course course = new Course();
			course = iCourseDAO.get(id);
			course.setId(id);
			course.setInstitute(getInstititute(courseDto.getInstituteId()));
			course.setDescription(courseDto.getDescription());
			course.setName(courseDto.getName());
			course.setcId(courseDto.getcId());
			course.setFaculty(getFaculty(courseDto.getFacultyId()));
			course.setCity(getCity(courseDto.getCityId()));
			course.setLanguage(courseDto.getLanguage());
			course.setCountry(getCountry(courseDto.getCountryId()));
			course.setIsActive(true);
			course.setCreatedBy("API");
			course.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			course.setUpdatedBy("API");
			course.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			if (courseDto.getDuration() != null && !courseDto.getDuration().isEmpty()) {
				course.setDuration(Integer.valueOf(courseDto.getDuration()));
			}
			if (courseDto.getStars() != null && !courseDto.getStars().isEmpty()) {
				course.setStars(Integer.valueOf(courseDto.getStars()));
			}

			// Course Details
			course.setIntake(courseDto.getIntake());
			course.setLink(courseDto.getLink());
			course.setDomesticFee(courseDto.getDomasticFee());
			course.setInternationalFee(courseDto.getInternationalFee());
			course.setCampusLocation(courseDto.getCampusLocation());
			if (courseDto.getCurrency() != null) {
				course.setCurrency(courseDto.getCurrency());
				List<Currency> currencies = currencyDAO.getAll();
				Currency toCurrency = currencyDAO.getCurrencyByCode("USD");
				BigInteger toCurrencyId = null;
				if (toCurrency != null) {
					toCurrencyId = toCurrency.getId();
				}

				CurrencyRate currencyRate = getCurrencyRate(courseDto.getCurrency(), currencies);
				BigInteger fromCurrencyId = getCurrencyId(currencies, courseDto.getCurrency());
				if (currencyRate == null) {
					currencyRate = currencyDAO.saveCurrencyRate(fromCurrencyId, courseDto.getCurrency());
				}
				if (currencyRate != null) {
					if (toCurrencyId != null) {
						if (courseDto.getDomasticFee() != null) {
							Double convertedRate = currencyDAO.getConvertedCurrency(currencyRate, toCurrencyId, Double.valueOf(courseDto.getDomasticFee()));
							if (convertedRate != null) {
								course.setUsdDomasticFee(convertedRate);
							}
						}
						if (courseDto.getInternationalFee() != null) {
							Double convertedRate = currencyDAO.getConvertedCurrency(currencyRate, toCurrencyId,
									Double.valueOf(courseDto.getInternationalFee()));
							if (convertedRate != null) {
								course.setUsdInternationFee(convertedRate);
							}
						}
					}
				}
			}
			iCourseDAO.update(course);
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
			response.put("status", HttpStatus.OK.value());
			response.put("message", "Course updated successfully");
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	private CurrencyRate getCurrencyRate(final String courseCurrency, final List<Currency> currencies) {
		BigInteger fromCurrencyId = getCurrencyId(currencies, courseCurrency);
		CurrencyRate currencyRate = currencyDAO.getCurrencyRate(fromCurrencyId);
		return currencyRate;
	}

	private BigInteger getCurrencyId(final List<Currency> currencies, final String currency) {
		BigInteger id = null;
		for (Currency c : currencies) {
			if (c.getCode().equalsIgnoreCase(currency)) {
				id = c.getId();
				break;
			}
		}
		return id;
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
	public Map<String, Object> searchCourseBasedOnFilter(final BigInteger countryId, final BigInteger instituteId, final BigInteger facultyId,
			final String name, final String languauge) {
		Map<String, Object> response = new HashMap<>();
		List<CourseRequest> courses = new ArrayList<>();
		List<CourseRequest> resultList = new ArrayList<>();
		try {
			String sqlQuery = "select c.id ,c.c_id, c.institute_id, c.country_id , c.city_id, c.faculty_id, c.name , "
					+ "cd.description, cd.intake,c.duration, c.language,cd.domestic_fee,cd.international_fee,"
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
				sqlQuery += " and c.language  = '" + languauge + "'";
			}
			sqlQuery += " ORDER BY c.created_on DESC";
			courses = iCourseDAO.searchCoursesBasedOnFilter(sqlQuery);

			for (CourseRequest courseRequest : courses) {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(),
						null, "en");
				courseRequest.setStorageList(storageDTOList);
				resultList.add(courseRequest);
			}

			if (resultList != null && !resultList.isEmpty()) {
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
		response.put("courses", resultList);
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
			final String sortBy, final Boolean sortAsscending) {
		Map<String, Object> response = new HashMap<>();
		String status = IConstant.SUCCESS;
		List<CourseRequest> courses = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		List<CourseRequest> resultList = new ArrayList<>();
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
			for (CourseRequest courseRequest : courses) {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseRequest.getInstituteId(), ImageCategory.INSTITUTE.toString(),
						null, "en");
				courseRequest.setStorageList(storageDTOList);
				resultList.add(courseRequest);
			}
		} catch (Exception exception) {
			status = IConstant.FAIL;
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
	public Map<String, Object> advanceSearch(final AdvanceSearchDto courseSearchDto) throws ValidationException {
		Map<String, Object> response = new HashMap<>();
		List<CourseResponseDto> courseResponseDtos = iCourseDAO.advanceSearch(courseSearchDto);
		for (CourseResponseDto courseResponseDto : courseResponseDtos) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseResponseDto.getInstituteId(), ImageCategory.INSTITUTE.toString(),
					null, "en");
			courseResponseDto.setStorageList(storageDTOList);
		}
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
		// TODO Auto-generated method stub
		return iCourseDAO.facultyWiseCourseForTopInstitute(facultyList, institute);
	}

	@Override
	public void saveCourseMinrequirement(final CourseMinRequirementDto courseMinRequirementDto) {
		try {
			convertDtoToCourseMinRequirement(courseMinRequirementDto);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
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
	public CourseMinRequirementDto getCourseMinRequirement(final BigInteger course) {
		return convertCourseMinRequirementToDto(courseMinRequirementDao.get(course));
	}

	public CourseMinRequirementDto convertCourseMinRequirementToDto(final List<CourseMinRequirement> courseMinRequirement) {
		List<String> subject = new ArrayList<>();
		List<String> grade = new ArrayList<>();
		CourseMinRequirementDto courseMinRequirementDto = new CourseMinRequirementDto();
		for (CourseMinRequirement courseMinRequirements : courseMinRequirement) {
			subject.add(courseMinRequirements.getSubject());
			grade.add(courseMinRequirements.getGrade());
			courseMinRequirementDto.setCountry(courseMinRequirements.getCountry().getId());
			courseMinRequirementDto.setSystem(courseMinRequirements.getSystem());
			courseMinRequirementDto.setCourse(courseMinRequirements.getCourse().getId());
		}
		courseMinRequirementDto.setSubject(subject);
		courseMinRequirementDto.setGrade(grade);
		return courseMinRequirementDto;
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
		return userRecommendationDao.getOtherUserWatchCourse(userId);
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
		return userRecommendationDao.getUserWatchCourseIds(userId);
	}

	@Override
	public Set<Course> getRelatedCoursesBasedOnPastSearch(final List<BigInteger> courseList) throws ValidationException {
		Set<Course> relatedCourses = new HashSet<>();
		for (BigInteger courseId : courseList) {
			relatedCourses.addAll(userRecommendationService.getRelatedCourse(courseId));
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
	public List<Long> getUserListBasedOnLikedCourseOnParameters(final BigInteger courseId, final BigInteger instituteId, final BigInteger facultyId,
			final BigInteger countryId, final BigInteger cityId) {
		List<BigInteger> courseIdList = getCourseListBasedForCourseOnParameters(courseId, instituteId, facultyId, countryId, cityId);
		if (courseIdList == null || courseIdList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> userIdList = iCourseDAO.getUserListFromMyCoursesBasedOnCourses(courseIdList);
		return userIdList;
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
		List<GlobalDataDto> countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());
		List<BigInteger> otherCountryIds = new ArrayList<>();
		if (countryWiseStudentCountListForUserCountry == null || countryWiseStudentCountListForUserCountry.isEmpty()) {
			countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList("China");
		}

		for (GlobalDataDto globalDataDto : countryWiseStudentCountListForUserCountry) {
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
}
