package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Institute;
import com.seeka.app.dto.GlobalDataDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

@Service
public class RecommendationService implements IRecommendationService {

	@Autowired
	private IFacultyService iFacultyService;
	
	@Autowired
	private ITop10CourseService iTop10CourseService;
	
	@Autowired
	private IInstituteService iInstituteService;
	
	@Autowired
	private ICourseService icourseService;
	
	@Autowired
	private ICountryService icountryService;
	
	@Autowired
	private IUsersService iUsersService;
	
	@Autowired
	private IGlobalStudentData iGlobalStudentDataService;
	
	@Override
	public void getRecommendedInstitutes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOtherPeopleSearch() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Course> getRecommendedCourses(BigInteger userId) throws ValidationException{
		// TODO Auto-generated method stub
		
		/**
		 * Query Identity to get UserDto from userId.
		 */
//		String identityUrl = "http://" +  IConstant.IDENTITY+"/api/v1/users/"+userId;
//		ResponseEntity<UserDto> user = restTemplate.getForEntity(identityUrl, UserDto.class);
//		System.out.println(user);
//		UserDto userDto = user.getBody();
//
//		System.out.println(userDto);
		UserDto userDto = iUsersService.getUserById(userId);
		/**
		 * Validations of for user.
		 */
		if(userDto == null) {
			throw new ValidationException("Invalid User");
		} else if (userDto.getCitizenship()==null || userDto.getCitizenship().isEmpty()) {
			throw new ValidationException("User needs to have a citizenship");
		}
		
		/**
		 * Get country object based on citizenship
		 */
		Country country = getCountryBasedOnCitizenship(userDto.getCitizenship());
		
		if(country == null || country.getId() == null) {
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
		
		if(courseCount > 0) {
			/**
			 * Get all institutes based on world ranking for a particular country (of User) 
			 */
			List<Institute> allInstitutesRankingWise = getAllInstituteRankingWisePerCountry(country);
			
			/**
			 * This list contains the details of the courses that are to be returned
			 */
			List<Course> listOfRecommendedCourses = new ArrayList<>();
			Map<Long, Faculty> facultyMap = new HashMap<>();
			
			//allFaculty.stream().forEach(i -> facultyMap.put(i.getId().longValue(), i));
			
			facultyMap = allFaculty.stream().collect(Collectors.toMap(i -> i.getId().longValue(), i->i));
			Long count = 0L;
			
			System.out.println("insititute count -- "+allInstitutesRankingWise.size());
			
			for (Institute institute : allInstitutesRankingWise) {
				List<Course> listOfCourse = facultyWiseCourseForInstitute(/*allFaculty*/new ArrayList<Faculty>(facultyMap.values()), institute/* .getId().longValue() */);
				System.out.println(count++);
				System.out.println("CourseList -- "+listOfCourse.size());
				for (Course course : listOfCourse) {
					if(facultyMap.containsKey(course.getFaculty().getId().longValue())) {
						listOfRecommendedCourses.add(course);
						/**
						 * removing facultyIds from faculty map for which courses are already obtained so that we do not get any courses corresponding to those 
						 * faculty for the next intitutes, also we get only one course per faculty in our final list of courses.
						 */
						facultyMap.remove(course.getFaculty().getId().longValue());
					}
				}
				/**
				 * If we have obtained list of courses for all faculties then no need to further navigate through other 
				 * institutes and hence breaking the loop
				 */
				if(allFaculty.isEmpty()) {
					break;
				}
				
			}
			System.out.println("insititute count -- "+allInstitutesRankingWise.size());
			return listOfRecommendedCourses;
		} else {
			
			Map<String, List<Course>> mapOfCountryToItsCoursesList = new TreeMap<>();
			
			List<Course> recommendedCourseList = new ArrayList<>();
			
			long count = iGlobalStudentDataService.checkForPresenceOfUserCountryInGlobalDataFile(country.getName());
			/**
			 * If we dont get any country based on citizenship of user get data of China by default
			 */
			if(count == 0) {
				country = getCountryBasedOnCitizenship("China");
			}
			
			/**
			 * Get List of countries in which the people from user's country are interested in moving to. This data will be obtained from 
			 * the table that contains the data from GlobalStudentData.xlsx file uploaded.
			 */
			List<GlobalDataDto> countryWiseStudentCountListForUserCountry = iGlobalStudentDataService.getCountryWiseStudentList(country.getName());

			for (GlobalDataDto globalDataDto : countryWiseStudentCountListForUserCountry) {
				List<Course> courseList = getTopRatedCoursesForCountryWorldRankingWise(getCountryBasedOnCitizenship(globalDataDto.getDestinationCountry()));
				mapOfCountryToItsCoursesList.put(globalDataDto.getDestinationCountry(), courseList);
			}
			List<Integer> requiredCoursesPerCountry = new ArrayList<>();
			requiredCoursesPerCountry.add(new Integer(2));
			requiredCoursesPerCountry.add(new Integer(5));
			requiredCoursesPerCountry.add(new Integer(10));
			requiredCoursesPerCountry.add(new Integer(20));
			int i = 0;
			while(recommendedCourseList.size() < 20 && i < 3) {
				int courseCountPerCountry = requiredCoursesPerCountry.get(i);
				recommendedCourseList = new ArrayList<Course>();
				for (Entry<String, List<Course>> entry : mapOfCountryToItsCoursesList.entrySet()) {
					List<Course> cl = entry.getValue();
					if(cl.size() > 0) {
						recommendedCourseList.addAll(cl.subList(0, cl.size()>courseCountPerCountry?courseCountPerCountry:cl.size()-1));
					}
					if(recommendedCourseList.size() >= 20) {
						break;
					}
				}
				if(i == 3) {
					break;
				}
				i++;
			}
			
			System.out.println(recommendedCourseList);
			
			return recommendedCourseList;
		}
		
	}

	private List<Faculty> getAllFacultyIds(){
		List<String> facultyNames = iTop10CourseService.getAllDistinctFaculty();
		List<Faculty> facultyList = iFacultyService.getFacultyListByName(facultyNames);
//		List<Long> facultyIds = new ArrayList<>();
//		for (Faculty faculty : facultyList) {
//			facultyIds.add(faculty.getId().longValue());
//		}
		return facultyList;
	}
	
	private List<Institute> getAllInstituteRankingWisePerCountry(Country country){
		return iInstituteService.ratingWiseInstituteListByCountry(country);
	}
	
	private List<Course> facultyWiseCourseForInstitute(List<Faculty> facultyList, Institute instituteId){
		return icourseService.facultyWiseCourseForInstitute(facultyList, instituteId);
	}
	
	private Country getCountryBasedOnCitizenship(String citizenship) {
		return icountryService.getCountryBasedOnCitizenship(citizenship);
	}
	
	private long checkIfCoursesPresentForCountry(Country country) {
		return icourseService.checkIfCoursesPresentForCountry(country);
	}
	
	private List<Course> getTopRatedCoursesForCountryWorldRankingWise(Country country) {
		if(country != null) {
			return icourseService.getTopRatedCoursesForCountryWorldRankingWise(country);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Course> getTopSearchedCoursesForFaculty(BigInteger facultyId, BigInteger userId) {
		List<BigInteger> facultyWiseCourses = icourseService.getAllCourseUsingFaculty(facultyId);
		List<BigInteger> allSearchCourses = icourseService.getTopSearchedCoursesByOtherUsers(userId);
		allSearchCourses.retainAll(facultyWiseCourses);
		System.out.println("All Course Size -- "+allSearchCourses.size());
		if(allSearchCourses == null || allSearchCourses.size()==0) {
			allSearchCourses = facultyWiseCourses.size()>10 ? facultyWiseCourses.subList(0, 9):facultyWiseCourses;
		}
		
		return icourseService.getCoursesById(allSearchCourses);
	}
	
}
