package com.yuzee.app.processor;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseSemester;
import com.yuzee.app.bean.SemesterSubject;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseSemesterRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseSemesterDto;
import com.yuzee.common.lib.dto.institute.SemesterSubjectDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseSemesterProcessor {

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommonProcessor commonProcessor;

	@Transactional
	public void saveUpdateCourseSemesters(String userId, String courseId, CourseSemesterRequestWrapper request)
			throws NotFoundException, ValidationException {
		log.info("inside CourseSubjectProcessor.saveUpdateCourseSemesters");
 		List<CourseSemesterDto> courseSemesterDtos = request.getCourseSemesterDtos();
   
		Course course = courseDao.get(courseId);
		CourseSemester courseSemesters= new CourseSemester();
		log.info("preparing map of exsiting course subjects");
		Map<String, CourseSemester> existingCourseSubjectsMap = course.getCourseSemesters().stream()
				.collect(Collectors.toMap(CourseSemester::getCourseSemesterId, e -> e));
 
		if (!ObjectUtils.isEmpty(course)) {
          List<CourseSemester> courseSemester = course.getCourseSemesters();
        List<SemesterSubject> semesterSubject= new ArrayList<>();
      	
		log.info("loop the requested list to collect the entitities to be saved/updated");
	    courseSemesterDtos.stream().forEach(e -> {
	   	CourseSemester courseSubject = new CourseSemester();
			if (!StringUtils.isEmpty(e.getId())) {
				log.info(
						"entityId is present so going to see if it is present in db if yes then we have to update it");
				courseSubject = existingCourseSubjectsMap.get(e.getId());
				courseSemester.removeIf(a->a.getCourseSemesterId().equals(e.getId()));
		}
			e.getSubjects().stream().forEach(a->{
				SemesterSubject subject = new SemesterSubject(a.getName(),a.getDescription());
				semesterSubject.add(subject);
			});
			courseSemesters.setCourseSemesterId(e.getId());
			courseSemesters.setDescription(e.getDescription());
			courseSemesters.setName(e.getName());
			courseSemesters.setType(e.getType());
			courseSemesters.setSubjects(semesterSubject);
		    saveUpdateSubjects(userId, courseSemesters, e.getSubjects());
		    courseSemester.add(courseSemesters);
		    course.setCourseSemesters(courseSemester);
		});
		
		List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
		coursesToBeSavedOrUpdated.add(course);
		courseDao.saveAll(coursesToBeSavedOrUpdated);
		}else {

			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}
	

	@Transactional
	public void deleteByCourseSemesterIds(String userId, String courseId, List<String> courseSubjectIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseSubjectProcessor.deleteByCourseSubjectIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseSemester> courseSemesterSubjects = course.getCourseSemesters();
		if (!courseSemesterSubjects.isEmpty()) {
			courseSubjectIds.stream().forEach(a->{
				courseSemesterSubjects.removeIf(e->e.getCourseSemesterId().equals(a));
				List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
				coursesToBeSavedOrUpdated.add(course);
				courseDao.saveAll(coursesToBeSavedOrUpdated);	
			 commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
			});	
		} else {
			log.error("one or more invalid course_subject_ids");
			throw new NotFoundException("one or more invalid course_subject_ids");
		}

	}

	public void saveUpdateSubjects(String userId, CourseSemester courseSemester, List<SemesterSubjectDto> subjectDtos) {
		if (!CollectionUtils.isEmpty(subjectDtos)) {
			List<SemesterSubject> subjects = courseSemester.getSubjects();

			List<String> updateRequestIds = subjectDtos.stream().filter(e -> StringUtils.hasText(e.getName()))
					.map(SemesterSubjectDto::getName).collect(Collectors.toList());
			subjects.removeIf(e -> !Utils.contains(updateRequestIds, e.getName()));

			log.info("preparing map of exsiting course fees");
			Map<String, SemesterSubject> existingSubjectMap = subjects.stream()
					.filter(e -> StringUtils.hasText(e.getName()))
					.collect(Collectors.toMap(SemesterSubject::getName, e -> e));
			subjectDtos.stream().forEach(dto -> {
				SemesterSubject model = new SemesterSubject();
				if (StringUtils.hasText(dto.getName())) {
					log.info("id is present so going to see if it is present in db if yes then we have to update it");
					model = existingSubjectMap.get(dto.getName());
					if (ObjectUtils.isEmpty(model)) {
						log.error("invalid course fees id : {}", dto.getName());
						throw new RuntimeNotFoundException("invalid course fees id : " + dto.getName());
					}
				} else {
				model.setName(dto.getName());
				model.setDescription(dto.getDescription());
				courseSemester.setSubjects(subjects);
				}
				
				
			});

		} else {
			courseSemester.getSubjects().clear();
		}
	}
}