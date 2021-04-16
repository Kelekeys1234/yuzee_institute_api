package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.CoursePrerequisite;
import com.yuzee.app.bean.CoursePrerequisiteSubjects;
import com.yuzee.app.dao.CoursePrerequisiteDao;
import com.yuzee.common.lib.dto.institute.CoursePrerequisiteSubjectDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class CoursePrerequisiteProcessor {

	@Autowired
	private CoursePrerequisiteDao coursePrerequisiteDao;
	
	public List<CoursePrerequisiteSubjectDto> getCoursePrerequisiteSubjectsByCourseId(String courseId) {
		log.debug("Inside getCoursePrerequisite() method");
		List<CoursePrerequisiteSubjectDto> coursePrerequisiteSubjectDtos = new ArrayList<>();
		log.info("Extracting Course Prerequisites from DB for courseId "+ courseId);
		List<CoursePrerequisite> coursePrerequisites = coursePrerequisiteDao.getCoursePrerequisite(courseId);
		if(!CollectionUtils.isEmpty(coursePrerequisites)) {
			log.info("Course Prerequisites fetched, start iterating data to fetch subjects");
			coursePrerequisites.stream().forEach(coursePrerequisite -> {
				log.info("Extracting Course Prerequisites Subjects for coursePrerequisiteId "+coursePrerequisite.getId());
				List<CoursePrerequisiteSubjects> coursePrerequisiteSubjects = coursePrerequisiteDao.getCoursePrerequisiteSubjects(
						coursePrerequisite.getId());
				if(!CollectionUtils.isEmpty(coursePrerequisiteSubjects)) {
					log.info("Course Prerequisite Subjects fetched, start iterating data to make final response");
					coursePrerequisiteSubjects.stream().forEach(coursePrerequisiteSubject -> {
						CoursePrerequisiteSubjectDto coursePrerequisiteSubjectDto = new CoursePrerequisiteSubjectDto(coursePrerequisiteSubject.getCoursePrerequisite().getId(),
								coursePrerequisiteSubject.getSubjectName(), coursePrerequisiteSubject.getPrerequisiteGrade(), coursePrerequisiteSubject.getType());
						coursePrerequisiteSubjectDtos.add(coursePrerequisiteSubjectDto);
					});
				}
			});
		}
		return coursePrerequisiteSubjectDtos;
	}
}
