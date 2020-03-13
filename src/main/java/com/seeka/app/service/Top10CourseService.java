package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Top10Course;
import com.seeka.app.dao.ITop10CourseDAO;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class Top10CourseService implements ITop10CourseService {

	private static List<String> top10CourseIds = null;

	@Autowired
	private ITop10CourseDAO iTop10CourseDao;

	@Autowired
	private IFacultyService iFacultyService;

	@Autowired
	private IStorageService iStorageService;

	@Override
	public void saveTop10Courses(final Top10Course top10Course) {
		top10Course.setUpdatedBy("API");
		top10Course.setCreatedBy("API");
		top10Course.setCreatedOn(new Date());
		top10Course.setUpdatedOn(new Date());
		iTop10CourseDao.save(top10Course);
	}

	@Override
	public void deleteAllTop10Courses() {
		iTop10CourseDao.deleteAll();
	}

	@Override
	public List<String> getAllDistinctFaculty() {
		return iTop10CourseDao.getAllDistinctFaculty();
	}

	@Override
	public List<String> getTop10CourseKeyword(final String facultyId) {
		Faculty faculty = iFacultyService.get(facultyId);
		return iTop10CourseDao.getTop10CourseKeyword(faculty.getName()).stream().map(Top10Course::getCourse).collect(Collectors.toList());
	}

	@Override
	public List<CourseResponseDto> getTop10RandomCoursesForGlobalSearchLandingPage() throws ValidationException {
		List<String> countryListForCourses = IConstant.COUNTRY_LIST_FOR_COURSES_GLOBAL_SEARCH_LANDING_PAGE;
		List<String> levelList = IConstant.LEVEL_LIST_FOR_COURSES_GLOBAL_SEARCH_LANDING_PAGE;
		List<CourseResponseDto> listOfTop10Course = new ArrayList<>();

		List<String> responseCourseIds = new ArrayList<>();

		for (String countryName : countryListForCourses) {
			if ((top10CourseIds == null) || top10CourseIds.isEmpty()) {
				setTop10CourseIdList();
			}
			List<Course> courseList = iTop10CourseDao.getRandomCourseFromTop10Course(countryName, levelList, top10CourseIds);
			if ((courseList != null) && !courseList.isEmpty()) {
				for (Course course : courseList) {
					CourseResponseDto courseResponseDto = new CourseResponseDto();
					BeanUtils.copyProperties(course, courseResponseDto);
					courseResponseDto.setFacultyName(course.getFaculty() != null ? course.getFaculty().getName() : null);
					courseResponseDto.setFacultyId(course.getFaculty() != null ? course.getFaculty().getId() : null);
					courseResponseDto.setInstituteName(course.getInstitute() != null ? course.getInstitute().getName() : null);
					courseResponseDto.setInstituteId(course.getInstitute() != null ? course.getInstitute().getId() : null);
					listOfTop10Course.add(courseResponseDto);
					responseCourseIds.add(course.getId());
				}
			}
		}
		if ((responseCourseIds != null) && !responseCourseIds.isEmpty()) {
			List<StorageDto> storageList = iStorageService.getStorageInformationBasedOnEntityIdList(responseCourseIds, "COURSE", null, "en");
			for (CourseResponseDto courseResponseDto : listOfTop10Course) {
				List<StorageDto> s = new ArrayList<>();
				for (StorageDto storageDto : storageList) {
					if (courseResponseDto.getId().equals(storageDto.getEntityId())) {
						s.add(storageDto);
					}
				}
				courseResponseDto.setStorageList(storageList);
			}
		}

		return listOfTop10Course;
	}

	public void setTop10CourseIdList() {
		// Write code to populate the list from DB here.
		top10CourseIds = iTop10CourseDao.getCourseIdsOfTop10CoursesFromEveryFaculty();
	}

	public static List<String> getTop10CourseIdList() {
		return top10CourseIds;
	}

}
