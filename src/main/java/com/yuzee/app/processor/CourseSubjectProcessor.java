package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseSubject;
import com.yuzee.app.bean.Semester;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseSubjectDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.SemesterDao;
import com.yuzee.app.dto.CourseSubjectDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseSubjectProcessor {

	@Autowired
	CourseSubjectDao courseSubjectDao;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private SemesterDao semesterDao;

	public void saveUpdateCourseSubjects(String userId, String courseId,
			@Valid List<CourseSubjectDto> courseSubjectDtos) throws NotFoundException, ValidationException {
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			Set<String> semesterIds = courseSubjectDtos.stream().map(CourseSubjectDto::getSemesterId)
					.collect(Collectors.toSet());
			Map<String, Semester> semestersMap = getSemesterByIds(semesterIds.stream().collect(Collectors.toList()));

			Set<String> updateRequestIds = courseSubjectDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseSubjectDto::getId).collect(Collectors.toSet());

			Map<String, CourseSubject> existingCourseSubjectsMap = courseSubjectDao
					.findByIdIn(updateRequestIds.stream().collect(Collectors.toList())).stream()
					.collect(Collectors.toMap(CourseSubject::getId, e -> e));

			List<CourseSubject> courseSubjects = new ArrayList<>();
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
				courseSubject.setSemester(semestersMap.get(e.getSemesterId()));
				courseSubject.setCourse(course);
				courseSubject.setAuditFields(userId, courseSubject);
				courseSubjects.add(courseSubject);
			});
			courseSubjectDao.saveAll(courseSubjects);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteByCourseSubjectIds(String userId, List<String> intakeIds)
			throws NotFoundException, ForbiddenException {
		List<CourseSubject> courseSubjects = courseSubjectDao.findByIdIn(intakeIds);
		if (intakeIds.size() != courseSubjects.size()) {
			if (courseSubjects.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more subjects");
				throw new ForbiddenException("no access to delete one more subjects");
			}
			courseSubjectDao.deleteByIdIn(intakeIds);
		} else {
			log.error("one or more invalid course_subject_ids");
			throw new NotFoundException("one or more invalid course_subject_ids");
		}
	}

	private Map<String, Semester> getSemesterByIds(List<String> semesterIds) throws ValidationException {
		Map<String, Semester> semestersMap = semesterDao.findByIdIn(new ArrayList<>(semesterIds)).stream()
				.collect(Collectors.toMap(Semester::getId, e -> e));
		if (semestersMap.size() != semesterIds.size()) {
			log.error("one or more semester ids are invalid");
			throw new ValidationException("one or more semester ids are invalid");
		} else {
			return semestersMap;
		}
	}
}