package com.yuzee.app.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseMinRequirement;
import com.yuzee.app.bean.CourseMinRequirementSubject;
import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseMinRequirementDao;
import com.yuzee.app.dao.EducationSystemDao;
import com.yuzee.app.dto.CourseMinRequirementDto;
import com.yuzee.app.dto.CourseMinRequirementSubjectDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseMinRequirementProcessor {
	@Autowired
	private CourseMinRequirementDao courseMinRequirementDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private EducationSystemDao eudcationSystemDao;

	@Autowired
	private ModelMapper modelMapper;

	public CourseMinRequirementDto saveCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CourseMinRequirementProcessor.saveCourseMinRequirement");
		Course course = validateAndGetCourse(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to create course min requirement");
			throw new ForbiddenException("no access to create course min requirement");
		}

		CourseMinRequirement courseMinRequirement = modelMapper.map(courseMinRequirementDto,
				CourseMinRequirement.class);
		courseMinRequirement
				.setEducationSystem(validateAndGetEducationSystem(courseMinRequirementDto.getEducationSystemId()));
		courseMinRequirement.setCourse(course);
		CourseMinRequirement finalCourseMinRequirement = courseMinRequirement;
		courseMinRequirement.getCourseMinRequirementSubjects().stream().forEach(e -> {
			e.setCourseMinRequirement(finalCourseMinRequirement);
			e.setAuditFields(userId);
		});
		courseMinRequirement.setAuditFields(userId);
		courseMinRequirement = courseMinRequirementDao.save(courseMinRequirement);
		return modelMapper.map(courseMinRequirement, CourseMinRequirementDto.class);
	}

	@Transactional
	public CourseMinRequirementDto updateCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto, String courseMinRequirementId)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CourseMinRequirementProcessor.updateCourseMinRequirement");

		Course course = validateAndGetCourse(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to update course min requirement");
			throw new ForbiddenException("no access to update course min requirement");
		}

		CourseMinRequirement courseMinRequirement = courseMinRequirementDao.findByCourseIdAndId(courseId,
				courseMinRequirementId);
		if (ObjectUtils.isEmpty(courseMinRequirement)) {
			log.error("invalid course min requirement id: {}", courseMinRequirementId);
			throw new NotFoundException("invalid course min requirement id: " + courseMinRequirementId);
		} else {
			courseMinRequirement.setCountryName(courseMinRequirementDto.getCountryName());
			courseMinRequirement.setStateName(courseMinRequirementDto.getStateName());
			courseMinRequirement.setGradePoint(courseMinRequirementDto.getGradePoint());
			courseMinRequirement
					.setEducationSystem(validateAndGetEducationSystem(courseMinRequirementDto.getEducationSystemId()));
			CourseMinRequirement finalCourseMinRequirement = courseMinRequirement;
			List<CourseMinRequirementSubject> dbCourseMinRequirementSubjects = courseMinRequirement
					.getCourseMinRequirementSubjects();
			List<CourseMinRequirementSubjectDto> courseMinRequirementSubjectDtos = courseMinRequirementDto
					.getMinRequirementSubjects();
			if (!CollectionUtils.isEmpty(courseMinRequirementSubjectDtos)) {

				log.info("see if some entitity ids are not present then we have to delete them.");
				Set<String> updateRequestIds = courseMinRequirementSubjectDtos.stream()
						.filter(e -> !StringUtils.isEmpty(e.getId())).map(CourseMinRequirementSubjectDto::getId)
						.collect(Collectors.toSet());
				dbCourseMinRequirementSubjects.removeIf(e -> !updateRequestIds.contains(e.getId()));

				Map<String, CourseMinRequirementSubject> existingCourseMinReqSubjectsMap = dbCourseMinRequirementSubjects
						.stream().collect(Collectors.toMap(CourseMinRequirementSubject::getId, e -> e));
				courseMinRequirementSubjectDtos.stream().forEach(e -> {
					CourseMinRequirementSubject courseMinRequirementSubject = new CourseMinRequirementSubject();
					if (!StringUtils.isEmpty(e.getId())) {
						courseMinRequirementSubject = existingCourseMinReqSubjectsMap.get(e.getId());
						if (courseMinRequirementSubject == null) {
							log.error("invalid course min requirement subject id : {}", e.getId());
							throw new RuntimeNotFoundException(
									"invalid course min requirement subject id : " + e.getId());
						}
					}
					courseMinRequirementSubject.setGrade(e.getGrade());
					courseMinRequirementSubject.setName(e.getName());
					courseMinRequirementSubject.setCourseMinRequirement(finalCourseMinRequirement);
					if (StringUtils.isEmpty(courseMinRequirementSubject.getId())) {
						dbCourseMinRequirementSubjects.add(courseMinRequirementSubject);
					}
					courseMinRequirementSubject.setAuditFields(userId);
				});

			} else {
				dbCourseMinRequirementSubjects.clear();
			}
			courseMinRequirement.setAuditFields(userId);
			courseMinRequirement = courseMinRequirementDao.save(courseMinRequirement);
			return modelMapper.map(courseMinRequirement, CourseMinRequirementDto.class);
		}
	}

	@Transactional
	public PaginationResponseDto getAllCourseMinimumRequirements(String userId, String courseId, Integer pageNumber,
			Integer pageSize) {
		log.info("inside CourseMinRequirementProcessor.getAllCourseMinimumRequirements");
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<CourseMinRequirement> courseMinRequirementPage = courseMinRequirementDao.findByCourseId(courseId,
				pageable);
		List<CourseMinRequirementDto> courseMinRequirementDtos = courseMinRequirementPage.getContent().stream()
				.map(e -> modelMapper.map(e, CourseMinRequirementDto.class)).collect(Collectors.toList());
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) courseMinRequirementPage.getTotalElements()).intValue(), courseMinRequirementDtos);

	}

	@Transactional
	public void deleteCourseMinRequirement(String userId, String courseId, String courseMinRequirementId)
			throws NotFoundException, ForbiddenException {
		log.info("inside CourseMinRequirementProcessor.deleteCourseMinRequirement");
		Course course = validateAndGetCourse(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to delete course min requirement");
			throw new ForbiddenException("no access to delete course min requirement");
		}
		courseMinRequirementDao.deleteByCourseIdAndId(courseId, courseMinRequirementId);

	}

	private Course validateAndGetCourse(String courseId) throws NotFoundException {
		Course course = courseDao.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
		return course;
	}

	private EducationSystem validateAndGetEducationSystem(String educationSystemId) throws NotFoundException {
		EducationSystem educationSystem = eudcationSystemDao.get(educationSystemId);
		if (!ObjectUtils.isEmpty(educationSystem)) {
			return educationSystem;
		} else {
			log.error("invalid education_system_id: {}", educationSystemId);
			throw new NotFoundException("invalid education_system_id: " + educationSystemId);
		}
	}
}
