package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.GlobalData;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.util.IConstant;

@Service
public class RecommendationService implements IRecommendationService {

	@Autowired
	private IFacultyService iFacultyService;

	@Autowired
	private ITop10CourseService iTop10CourseService;

	@Autowired
	private IInstituteService iInstituteService;

	@Autowired
	private ICourseService iCourseService;

	@Autowired
	private ICountryService iCountryService;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private IScholarshipService iScholarshipService;

	@Override
	public List<InstituteResponseDto> getRecommendedInstitutes(final BigInteger userId, /*
																						 * Long startIndex, final Long pageSize, Long pageNumber,
																						 */
			final String language) throws ValidationException, NotFoundException {

		UserDto userDto = iUsersService.getUserById(userId);
		if (userDto == null) {
			throw new NotFoundException(messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present", new Object[] { userId }, language));
		}

		/**
		 * Get Country Id Based on citizenship
		 */
		Country country = iCountryService.getCountryBasedOnCitizenship(userDto.getCitizenship());
		if (country == null || country.getId() == null) {
			throw new ValidationException(
					messageByLocalService.getMessage("invalid.citizenship.for.user", new Object[] { userDto.getCitizenship() }, language));
		}

		/**
		 * Final institute list to recommend
		 */
		Set<BigInteger> instituteListToRecommend = new HashSet<>();

		/**
		 * Get institutes based on Past User Search based on country of courses
		 */
		List<BigInteger> instituteBasedOnPastSearch = getInstituteIdsBasedOnPastSearch(userId);

		/**
		 * Get institutes Based on User's Country
		 */
		List<BigInteger> institutesBasedOnUserCountry = iInstituteService.getTopInstituteIdByCountry(country.getId());

		/**
		 * Get courses based on the country that other users from user's country are
		 * most interested to migrate to.
		 */

		List<GlobalData> globalDataDtoList = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());

		List<String> countryNames = globalDataDtoList.stream().map(i -> i.getDestinationCountry()).collect(Collectors.toList());
		List<BigInteger> allCountryIds = iCountryService.getCountryBasedOnCitizenship(countryNames);
		List<BigInteger> institutesById = iInstituteService.getRandomInstituteIdByCountry(allCountryIds);
		/**
		 * Select Random two courses from User Past Search
		 */
		instituteListToRecommend.addAll(instituteBasedOnPastSearch);
		instituteListToRecommend.addAll(institutesBasedOnUserCountry);
		instituteListToRecommend.addAll(institutesById);

		List<BigInteger> instList = new ArrayList<>();

		int count = 0;
		for (BigInteger id : instituteListToRecommend) {
			instList.add(id);
			if (count >= 20) {
				break;
			}
			count++;
		}

		return iInstituteService.getAllInstituteByID(instList);

		// return oldInstituteRecommendationLogic(userId, startIndex, pageSize,
		// pageNumber, language);
	}

	@Override
	public void getOtherPeopleSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	public List</* Course */CourseResponseDto> getRecommendedCourses(final BigInteger userId) throws ValidationException {
		// TODO Auto-generated method stub

		/**
		 * Query Identity to get UserDto from userId.
		 */

		UserDto userDto = iUsersService.getUserById(userId);
		/**
		 * Validations of for user.
		 */
		if (userDto == null) {
			throw new ValidationException("Invalid User");
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException("User needs to have a citizenship");
		}

		/**
		 * Get country object based on citizenship
		 */
		Country country = getCountryBasedOnCitizenship(userDto.getCitizenship());

		if (country == null || country.getId() == null) {
			throw new ValidationException("Invalid country citizenship for the user");
		}
		/**
		 * Final course list to recommend
		 */
		List<BigInteger> courseListToRecommend = new ArrayList<>();

		/**
		 * Get courses based on Past User Search based on country of courses
		 */
		List<BigInteger> coursesBasedOnPastSearch = iCourseService.getTopSearchedCoursesByUsers(userId);

		/**
		 * Get Courses Based on User's Country
		 */
		List<BigInteger> coursesBasedOnUserCountry = iCourseService.courseIdsForCountry(country);

		/**
		 * Get courses based on the country that other users from user's country are
		 * most interested to migrate to.
		 */
		List<BigInteger> coursesBasedOnUserMigrationCountry = iCourseService.courseIdsForMigratedCountries(country);
		/**
		 * Select Random two courses from User Past Search
		 */
		Random rand = new Random();
		if (coursesBasedOnPastSearch != null) {
			for (int i = 0; i < 2; i++) {
				if (coursesBasedOnPastSearch.size() > 0) {
					int randomIndex = rand.nextInt(coursesBasedOnPastSearch.size());
					BigInteger randomElement = coursesBasedOnPastSearch.get(randomIndex);
					coursesBasedOnPastSearch.remove(randomIndex);
					courseListToRecommend.add(randomElement);
				} else {
					break;
				}
			}
		}
		/**
		 * Select Random five courses from User's Country
		 */
		if (coursesBasedOnUserCountry != null) {
			for (int i = 0; i < 5; i++) {
				if (coursesBasedOnUserCountry.size() > 0) {
					int randomIndex = rand.nextInt(coursesBasedOnUserCountry.size());
					BigInteger randomElement = coursesBasedOnUserCountry.get(randomIndex);
					coursesBasedOnUserCountry.remove(randomIndex);
					courseListToRecommend.add(randomElement);
				} else {
					break;
				}
			}
		}

		/**
		 * Select remaining random courses from most migrated countries as per users
		 * current country.
		 */
		if (coursesBasedOnUserMigrationCountry != null) {
			while (20 - courseListToRecommend.size() > 0) {
				if (coursesBasedOnUserMigrationCountry.size() > 0) {
					int randomIndex = rand.nextInt(coursesBasedOnUserMigrationCountry.size());
					BigInteger randomElement = coursesBasedOnUserMigrationCountry.get(randomIndex);
					coursesBasedOnUserMigrationCountry.remove(randomIndex);
					courseListToRecommend.add(randomElement);
				} else {
					break;
				}
			}
		}
		/**
		 * If no recommended courses are available, return an empty array.
		 */
		if (courseListToRecommend == null || courseListToRecommend.isEmpty()) {
			return new ArrayList<>();
		}
		List<Course> courseList = iCourseService.getCoursesById(courseListToRecommend);
		List<CourseResponseDto> courseResponseDTOList = new ArrayList<>();
		for (Course course : courseList) {
			CourseResponseDto courseResponseDto = new CourseResponseDto();
			BeanUtils.copyProperties(course, courseResponseDto);
			courseResponseDto.setInstituteId(course.getInstitute().getId());
			courseResponseDto.setInstituteName(course.getInstitute().getName());
			courseResponseDto.setLocation(course.getCountry().getName());
			courseResponseDto.setCountryId(course.getCountry().getId());
			courseResponseDto.setCountryName(course.getCountry().getName());
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(course.getInstitute().getId(), ImageCategory.INSTITUTE.toString(), null,
					"en");
			courseResponseDto.setStorageList(storageDTOList);
			courseResponseDto.setCityId(course.getCity().getId());
			courseResponseDto.setCityName(course.getCity().getName());
			if (coursesBasedOnPastSearch != null && coursesBasedOnPastSearch.contains(course.getId())) {
				courseResponseDto.setIsViewed(true);
			}
			courseResponseDTOList.add(courseResponseDto);
		}

		return courseResponseDTOList;
	}

	private List<Faculty> getAllFacultyIds() {
		List<String> facultyNames = iTop10CourseService.getAllDistinctFaculty();
		List<Faculty> facultyList = iFacultyService.getFacultyListByName(facultyNames);
//		List<Long> facultyIds = new ArrayList<>();
//		for (Faculty faculty : facultyList) {
//			facultyIds.add(faculty.getId().longValue());
//		}
		return facultyList;
	}

	private List<Institute> getAllInstituteRankingWisePerCountry(final Country country) {
		return iInstituteService.ratingWiseInstituteListByCountry(country);
	}

	private List<Course> facultyWiseCourseForInstitute(final List<Faculty> facultyList, final Institute instituteId) {
		return iCourseService.facultyWiseCourseForInstitute(facultyList, instituteId);
	}

	private Map<BigInteger, BigInteger> facultyWiseCourseIdMapForInstitute(final List<Faculty> facultyList, final Institute instituteId) {
		return iCourseService.facultyWiseCourseIdMapForInstitute(facultyList, instituteId.getId());
	}

	private Country getCountryBasedOnCitizenship(final String citizenship) {
		return iCountryService.getCountryBasedOnCitizenship(citizenship);
	}

	private long checkIfCoursesPresentForCountry(final Country country) {
		return iCourseService.checkIfCoursesPresentForCountry(country);
	}

	private List<Course> getTopRatedCoursesForCountryWorldRankingWise(final Country country) {
		if (country != null) {
			return iCourseService.getTopRatedCoursesForCountryWorldRankingWise(country);
		} else {
			return new ArrayList<>();
		}
	}

	private List<BigInteger> getTopRatedCourseIdsForCountryWorldRankingWise(final Country country) {
		if (country != null) {
			return iCourseService.getTopRatedCourseIdForCountryWorldRankingWise(country);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Course> getTopSearchedCoursesForFaculty(final BigInteger facultyId, final BigInteger userId) {
		List<BigInteger> facultyWiseCourses = iCourseService.getAllCourseUsingFaculty(facultyId);
		List<BigInteger> allSearchCourses = iCourseService.getTopSearchedCoursesByOtherUsers(userId);
		allSearchCourses.retainAll(facultyWiseCourses);
		System.out.println("All Course Size -- " + allSearchCourses.size());
		if (allSearchCourses == null || allSearchCourses.size() == 0) {
			allSearchCourses = facultyWiseCourses.size() > 10 ? facultyWiseCourses.subList(0, 9) : facultyWiseCourses;
		}

		return iCourseService.getCoursesById(allSearchCourses);
	}

	@Override
	public Set<Course> displayRelatedCourseAsPerUserPastSearch(final BigInteger userId) throws ValidationException {
		// TODO Auto-generated method stub
		List<BigInteger> userSearchCourseIdList = iCourseService.getTopSearchedCoursesByUsers(userId);

		/**
		 * Keeping this code to get related courses only for top 3 courses searched.
		 */
		if (userSearchCourseIdList.size() > 0) {
			userSearchCourseIdList = userSearchCourseIdList.size() > 3 ? userSearchCourseIdList.subList(0, 2)
					: userSearchCourseIdList.subList(0, userSearchCourseIdList.size() - 1);
		} else {
			return new HashSet<>();
		}
		/**
		 * Logic ends
		 */
		Set<Course> userRelatedCourses = iCourseService.getRelatedCoursesBasedOnPastSearch(userSearchCourseIdList);
		return userRelatedCourses;
	}

	@Override
	public List<ScholarshipDto> getRecommendedScholarships(final BigInteger userId, final String language) throws ValidationException, NotFoundException {

		/**
		 * Get user details from userId
		 */
		UserDto userDto = iUsersService.getUserById(userId);
		if (userDto == null) {
			throw new NotFoundException(messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present", new Object[] { userId }, language));
		}
		/**
		 * Get courses based on Past User Search based on country of courses
		 */
		List<BigInteger> coursesBasedOnPastSearch = iCourseService.getTopSearchedCoursesByUsers(userId);

		List<BigInteger> recommendedScholarships = new ArrayList<>();
		if (coursesBasedOnPastSearch == null || coursesBasedOnPastSearch.isEmpty()) {
			/**
			 * If users donot have past search history run the below logic
			 */

			/**
			 * Get Lis of all countries that users as interested to migrate to based on
			 * user's country
			 */
			List<String> distinctCountryList = iGlobalStudentDataService.getDistinctMigratedCountryForUserCountry(userDto.getCitizenship());
			if (distinctCountryList != null && !distinctCountryList.isEmpty()) {
				List<Country> countries = iCountryService.getCountryListBasedOnCitizenship(distinctCountryList);
				List<BigInteger> countryIdList = countries.stream().map(Country::getId).collect(Collectors.toList());
				/**
				 * Get all scholarshipIds based on country
				 */
				recommendedScholarships.addAll(iScholarshipService.getScholarshipIdsByCountryId(countryIdList, IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE));
			}

			/**
			 * If recommended scholarships are not equal to 20 then fetch more on random
			 * basis from the database
			 */
			if (recommendedScholarships.size() < IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE) {
				recommendedScholarships
						.addAll(iScholarshipService.getRandomScholarShipIds(IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE - recommendedScholarships.size()));
			}
		} else {
			/**
			 * If users have past search history run the below logic.
			 */
			List<BigInteger> countryList = iCourseService.getCountryForTopSearchedCourses(coursesBasedOnPastSearch);

			/**
			 * Convert the countryList to Set to make it more random.
			 */
			Set<BigInteger> countrySet = new HashSet<>(countryList);
			for (BigInteger countryId : countrySet) {
				/**
				 * Add all scholarship Ids obtained to recommended scholarships
				 */
				List<BigInteger> countryListForScholarship = new ArrayList<>();
				countryListForScholarship.add(countryId);
				recommendedScholarships.addAll(
						iScholarshipService.getScholarshipIdsByCountryId(countryListForScholarship, IConstant.SCHOLARSHIPS_PER_COUNTRY_FOR_RECOMMENDATION));
				/**
				 * If recommended scholarships size exceeds 20, no need to consider other
				 * countries
				 */
				if (recommendedScholarships.size() >= IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE) {
					break;
				}
			}

			/**
			 * If the recommended scholarship list is less than 20, then we need to consider
			 * more scholarships as per the Global Student Data file.
			 */
			if (recommendedScholarships.size() < IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE) {
				List<String> distinctCountryList = iGlobalStudentDataService.getDistinctMigratedCountryForUserCountry(userDto.getCitizenship());
				List<Country> countries = iCountryService.getCountryListBasedOnCitizenship(distinctCountryList);
				// countries.stream().map(country ->
				// country.getId()).collect(Collectors.toList());
				List<BigInteger> countryIdList = countries.stream().map(Country::getId).collect(Collectors.toList());

				recommendedScholarships.addAll(iScholarshipService.getScholarshipIdsByCountryId(countryIdList,
						IConstant.TOTAL_SCHOLARSHIPS_PER_PAGE - recommendedScholarships.size()));
			}
		}
		/**
		 * Get Scholarship details for recommended scholarships
		 */
		List<ScholarshipDto> scholarshipDtoList = iScholarshipService.getAllScholarshipDetailsFromId(recommendedScholarships);

		return scholarshipDtoList;
	}

//	private List<BigInteger> getInstitutesAsPerGlobalData(final List<GlobalDataDto> globalDataDtoList, final Long pageNumber) throws ValidationException {
//		if (pageNumber == null) {
//			throw new ValidationException(messageByLocalService.getMessage("page.number.not.specified", new Object[] {}, "en"));
//		}
//
//		Long startCountry = pageNumber * IConstant.COUNTRY_PER_PAGE; // this is done as we display institutes corresponding to 10 countries in a page
//																		// at a time
//
//		if (globalDataDtoList.size() < startCountry + 1) {
//
//			/**
//			 * This condition if true indicates that 2 institutes have been displayed for
//			 * all countries where the other user's from users current country are willing
//			 * to go, and now th institutes should be displayed based on random logic for
//			 * the same.
//			 */
//
//			/**
//			 *
//			 * Display random institute.. we would start displaying top most institutes as
//			 * per ranking of the institute... this might result in case that insitutes are
//			 * duplicated but would be displayed based on ranking of institute....
//			 */
//
//			/**
//			 * Fetch as per country list obtained how many pages would be occupied by
//			 * countries corresponding to institute.
//			 */
//			long pagesCorrespondingToAllCountries = globalDataDtoList.size() / IConstant.COUNTRY_PER_PAGE;
//			long pageNumberForInstitute = pageNumber - pagesCorrespondingToAllCountries;
//
//			long startIndex = pageNumberForInstitute * IConstant.COUNTRY_PER_PAGE;
//			List<BigInteger> listInstituteIds = iInstituteService.getInstituteIdsBasedOnGlobalRanking(startIndex, IConstant.COUNTRY_PER_PAGE.longValue());
//			return listInstituteIds;
//		} else {
//			List<BigInteger> insitituteIds = new ArrayList<>();
//			/**
//			 * fetch all insitute Ids : i.e. 2 insitutes per country in this for loop.
//			 *
//			 */
//			for (Long i = pageNumber * IConstant.COUNTRY_PER_PAGE; i < globalDataDtoList.size() && i < (pageNumber + 1) * IConstant.INSITUTE_PER_COUNTRY; i++) {
//				/**
//				 * fetch country based on Name
//				 */
//				Country country = iCountryService.getCountryBasedOnCitizenship(globalDataDtoList.get(i.intValue()).getDestinationCountry());
//				/**
//				 * If country not found don't throw exception but countinue with next country.
//				 */
//				if (country == null || country.getId().equals(BigInteger.ZERO)) {
//					continue;
//				}
//
//				/**
//				 * Get 2 institutes per country
//				 */
//				insitituteIds.addAll(iInstituteService.getTopInstituteIdByCountry(
//						country.getId()/* , 0L, IConstant.INSITUTE_PER_COUNTRY.longValue() */));
//			}
//			return insitituteIds;
//		}
//	}

	private List<BigInteger> getInstituteIdsBasedOnPastSearch(final BigInteger userId) throws ValidationException {

		List<BigInteger> instituteList = new ArrayList<>();

		List<BigInteger> topSearchedCourseIds = iCourseService.getTopSearchedCoursesByUsers(userId);
		if (topSearchedCourseIds != null && !topSearchedCourseIds.isEmpty()) {
			List<BigInteger> countryForTopSearchedCourses = iCourseService.getCountryForTopSearchedCourses(topSearchedCourseIds);
			for (BigInteger countryId : countryForTopSearchedCourses) {
				List<BigInteger> insituteId = iInstituteService.getTopInstituteIdByCountry(countryId/* , 0L, 1L */);
				instituteList.addAll(insituteId);
			}
		}

		return instituteList;
	}

	private List<Course> oldLogic(final BigInteger userId) throws ValidationException {
		UserDto userDto = iUsersService.getUserById(userId);
		/**
		 * Validations of for user.
		 */
		if (userDto == null) {
			throw new ValidationException("Invalid User");
		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException("User needs to have a citizenship");
		}

		/**
		 * Get country object based on citizenship
		 */
		Country country = getCountryBasedOnCitizenship(userDto.getCitizenship());

		if (country == null || country.getId() == null) {
			throw new ValidationException("Invalid country citizenship for the user");
		}

		/**
		 * Get all facultyIds from the excel uploaded by Top10Courses
		 */
		List<Faculty> allFaculty = getAllFacultyIds();

		/**
		 * Check if the courses are present for the user's citizenship in our database.
		 */
		long courseCount = checkIfCoursesPresentForCountry(country);

		if (courseCount > 0) {
			/**
			 * Get all institutes based on world ranking for a particular country (of User)
			 */
			List<Institute> allInstitutesRankingWise = getAllInstituteRankingWisePerCountry(country);

			/**
			 * This list contains the details of the courses that are to be returned
			 */
			List<BigInteger> listOfRecommendedCourseIds = new ArrayList<>();
			List<Course> listOfRecommendedCourses = new ArrayList<>();
			Map<Long, Faculty> facultyMap = new HashMap<>();

			// allFaculty.stream().forEach(i -> facultyMap.put(i.getId().longValue(), i));

			facultyMap = allFaculty.stream().collect(Collectors.toMap(i -> i.getId().longValue(), i -> i));
			Long count = 0L;

			System.out.println("insititute count -- " + allInstitutesRankingWise.size());

			for (Institute institute : allInstitutesRankingWise) {
				List<Course> listOfCourse = facultyWiseCourseForInstitute(/* allFaculty */new ArrayList<>(facultyMap.values()),
						institute/* .getId().longValue() */);
				Map<BigInteger, BigInteger> mapFacultyIdCourseId = facultyWiseCourseIdMapForInstitute(new ArrayList<>(facultyMap.values()), institute);
				System.out.println(count++);
				System.out.println("CourseList -- " + listOfCourse.size());

				for (Entry<BigInteger, BigInteger> entry : mapFacultyIdCourseId.entrySet()) {
					if (facultyMap.containsKey(entry.getValue().longValue())) {
						listOfRecommendedCourseIds.add(entry.getKey());
						/**
						 * removing facultyIds from faculty map for which courses are already obtained
						 * so that we do not get any courses corresponding to those faculty for the next
						 * intitutes, also we get only one course per faculty in our final list of
						 * courses.
						 */
						facultyMap.remove(entry.getValue().longValue());
					}
				}

				/**
				 * If we have obtained list of courses for all faculties then no need to further
				 * navigate through other institutes and hence breaking the loop
				 */
				if (facultyMap.isEmpty()) {
					break;
				}

			}
			System.out.println("insititute count -- " + allInstitutesRankingWise.size());

			listOfRecommendedCourses = iCourseService.getAllCoursesUsingId(listOfRecommendedCourseIds);
			return listOfRecommendedCourses;
		} else {

			// Map<String, List<Course>> mapOfCountryToItsCoursesList = new TreeMap<>();
			Map<String, List<BigInteger>> mapOfCountryToItsCoursesList = new TreeMap<>();

			List<Course> recommendedCourseList = new ArrayList<>();

			List<BigInteger> recommendedCourseListIds = new ArrayList<>();

			long count = iGlobalStudentDataService.checkForPresenceOfUserCountryInGlobalDataFile(country.getName());
			/**
			 * If we dont get any country based on citizenship of user get data of China by
			 * default
			 */
			if (count == 0) {
				country = getCountryBasedOnCitizenship("China");
			}

			/**
			 * Get List of countries in which the people from user's country are interested
			 * in moving to. This data will be obtained from the table that contains the
			 * data from GlobalStudentData.xlsx file uploaded.
			 */
			List<GlobalData> countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());

			for (GlobalData globalDataDto : countryWiseStudentCountListForUserCountry) {
				// List<Course> courseList =
				// getTopRatedCoursesForCountryWorldRankingWise(getCountryBasedOnCitizenship(globalDataDto.getDestinationCountry()));
				List<BigInteger> courseList = getTopRatedCourseIdsForCountryWorldRankingWise(
						getCountryBasedOnCitizenship(globalDataDto.getDestinationCountry()));
				mapOfCountryToItsCoursesList.put(globalDataDto.getDestinationCountry(), courseList);
			}
			List<Integer> requiredCoursesPerCountry = new ArrayList<>();
			requiredCoursesPerCountry.add(2);
			requiredCoursesPerCountry.add(new Integer(5));
			requiredCoursesPerCountry.add(new Integer(10));
			requiredCoursesPerCountry.add(new Integer(20));
			int i = 0;
			while (recommendedCourseList.size() < 20 && i < 3) {
				int courseCountPerCountry = requiredCoursesPerCountry.get(i);
				recommendedCourseList = new ArrayList<>();

				for (Entry<String, List<BigInteger>> entry : mapOfCountryToItsCoursesList.entrySet()) {
					List<BigInteger> cl = entry.getValue();
					if (cl.size() > 0) {
						recommendedCourseListIds.addAll(cl.subList(0, cl.size() > courseCountPerCountry ? courseCountPerCountry : cl.size() - 1));
					}
					if (recommendedCourseListIds.size() >= 20) {
						break;
					}
				}
				if (i == 3) {
					break;
				}
				i++;
			}

			recommendedCourseList = iCourseService.getCoursesById(recommendedCourseListIds);

			System.out.println(recommendedCourseList);

			return recommendedCourseList;
		}
	}

	@Override
	public List<InstituteResponseDto> getinstitutesBasedOnOtherPeopleSearch(final BigInteger userId) {

		/**
		 * Get all distinct courseIds searched by other users
		 */
		List<BigInteger> distinctCourseIds = iCourseService.getTopSearchedCoursesByOtherUsers(userId);
		try {
			/**
			 * Get all distinct country Ids for the course ids
			 */
			List<BigInteger> distinctCountryIds = iCourseService.getCountryForTopSearchedCourses(distinctCourseIds);

			/**
			 * Get all institutes for the various countries that other users have searched
			 * for
			 */
			List<BigInteger> allInstituteIds = iInstituteService.getInstituteIdsFromCountry(distinctCountryIds);

			/**
			 * If we dont get any institutes return an empty list
			 */
			if (allInstituteIds == null || allInstituteIds.isEmpty()) {
				return new ArrayList<>();
			}

			/**
			 * If the list is less than 20 return all the institutes available
			 */
			if (allInstituteIds.size() < 20) {
				return iInstituteService.getAllInstituteByID(allInstituteIds);
			}
			/**
			 * If greater than 20 have a random logic for the same
			 */
			else {
				List<BigInteger> randomInstituteIds = new ArrayList<>();
				Random rand = new Random();
				while (randomInstituteIds.size() <= 20) {
					if (allInstituteIds.size() > 0) {
						int randomIndex = rand.nextInt(allInstituteIds.size());
						BigInteger randomElement = allInstituteIds.get(randomIndex);
						allInstituteIds.remove(randomIndex);
						randomInstituteIds.add(randomElement);
					} else {
						break;
					}
				}
				return iInstituteService.getAllInstituteByID(randomInstituteIds);
			}

		} catch (ValidationException e) {
			return new ArrayList<>();
		}
	}

	private List<InstituteResponseDto> oldInstituteRecommendationLogic(final BigInteger userId, final Long startIndex, final Long pageSize,
			final Long pageNumber, final String language) {
//		List<BigInteger> instituteIdList = new ArrayList<>();
//		/**
//		 * Check for user country
//		 */
//		UserDto userDto = iUsersService.getUserById(userId);
//		if (userDto == null) {
//			throw new NotFoundException(messageByLocalService.getMessage("user.not.found", new Object[] { userId }, language));
//		} else if (userDto.getCitizenship() == null || userDto.getCitizenship().isEmpty()) {
//			throw new ValidationException(messageByLocalService.getMessage("user.citizenship.not.present", new Object[] { userId }, language));
//		}
//
//		/**
//		 * Get Country Id Based on citizenship
//		 */
//		Country country = iCountryService.getCountryBasedOnCitizenship(userDto.getCitizenship());
//		if (country == null || country.getId() == null) {
//			throw new ValidationException(
//					messageByLocalService.getMessage("invalid.citizenship.for.user", new Object[] { userDto.getCitizenship() }, language));
//		}
//
//		/**
//		 * Check if user search history exists, if exists show institutes for the
//		 * country in which he has searched courses for based on his past search and
//		 * pagination if no records found continue with the below logic.
//		 */
//
//		/**
//		 * Fetch all the institute Ids based on its past search history
//		 */
//
//		List<BigInteger> instituteIdsBasedPastSearch = getInstituteIdsBasedOnPastSearch(userId);
//		if (!(instituteIdsBasedPastSearch == null || instituteIdsBasedPastSearch.isEmpty())) {
//			/**
//			 * Get the start index to display the instituteIds
//			 */
//			Long startI = pageSize * (pageNumber - 1) + 1;
//
//			if (instituteIdsBasedPastSearch.size() > startI) {
//				Long endIndex = startI + pageSize;
//				/**
//				 * if the institutes are greater than the end index fetch all the instituteIds
//				 * and return the response back
//				 */
//				if (instituteIdsBasedPastSearch.size() > endIndex) {
//					instituteIdList.addAll(instituteIdsBasedPastSearch.subList((int) (startI - 1), (int) (endIndex - 1)));
//					return iInstituteService.getAllInstituteByID(instituteIdList);
//				} else {
//					/**
//					 * If not and there are left overs from previous search get them and then add
//					 * other Ids as per other logic to the existing Ids.
//					 */
//					instituteIdList.addAll(instituteIdsBasedPastSearch.subList((int) (startI - 1), instituteIdsBasedPastSearch.size() - 1));
//				}
//			}
//
//			/**
//			 * Change the pageNumber for the below record as per the displayed records
//			 * according to above logic.
//			 */
//			pageNumber = pageNumber - instituteIdsBasedPastSearch.size() / pageSize;
//		}
//
//		/**
//		 * Check if courses are available for user country
//		 */
//		Long count = iCourseService.getCountOfDistinctInstitutesOfferingCoursesForCountry(userDto, country);
//
//		if (!(count == null || count.equals(0L))) {
//
//			/**
//			 * Display top universities for the country
//			 */
//			if (startIndex == null) {
//				startIndex = (pageNumber - 1) * pageSize + 1;
//			 	instituteIdList.addAll(iInstituteService.getTopInstituteIdByCountry(country.getId(), startIndex, pageSize));
//			} else {
//				instituteIdList.addAll(iInstituteService.getTopInstituteIdByCountry(country.getId(), startIndex, pageSize));
//			}
//		}
//		/**
//		 * Logic to fetch international institutes for user
//		 */
//		List<GlobalDataDto> globalDataDtoList = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());
//
//		if (pageNumber != null) {
//			instituteIdList.addAll(getInstitutesAsPerGlobalData(globalDataDtoList, pageNumber - 1));
//		} else {
//			throw new ValidationException(messageByLocalService.getMessage("page.number.mandatory", new Object[] {}, language));
//		}
//		/**
//		 * Above line is written to merge both domestic and international institutes
//		 */
//
//		/**
//		 * if the obtained institutes are greater than the page size. strip off the
//		 * remaining institutes and return the institutes equal to the page size
//		 * specified.
//		 */
//		if (instituteIdList.size() > pageSize) {
//			instituteIdList.subList(0, pageSize.intValue());
//		}
//
//		/**
//		 * fetch all the institute details corresponding to the ids obtained.
//		 */
//		return iInstituteService.getAllInstituteByID(instituteIdList);
		return null;
	}

}
