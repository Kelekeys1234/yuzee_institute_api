package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseMinRequirement;
import com.yuzee.app.bean.CourseMinRequirementSubject;
import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseMinRequirementDao;
import com.yuzee.app.dao.EducationSystemDao;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementSubjectDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseMinRequirementProcessor {
	@Autowired
	private CourseMinRequirementDao courseMinRequirementDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private EducationSystemDao eudcationSystemDao;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommonProcessor commonProcessor;

	@Transactional
	public CourseMinRequirementDto saveCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CourseMinRequirementProcessor.saveCourseMinRequirement for courseId : {}", courseId);
		Course course = courseProcessor.validateAndGetCourseById(courseId);
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
		courseMinRequirement.setStudyLanguages(courseMinRequirementDto.getStudyLanguages());
		log.info("going to save record in db");
		
		course.getCourseMinRequirements().add(courseMinRequirement);
		List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
		coursesToBeSavedOrUpdated.add(course);
		if (!CollectionUtils.isEmpty(courseMinRequirementDto.getLinkedCourseIds())) {
			List<CourseMinRequirementDto> dtosToReplicate = course.getCourseMinRequirements().stream()
					.map(e -> dtoToModel(e)).collect(Collectors.toList());
			coursesToBeSavedOrUpdated.addAll(replicateCourseMinRequirements(userId,
					courseMinRequirementDto.getLinkedCourseIds(), dtosToReplicate));
		}
		List<Course> savedCourses = courseDao.saveAll(coursesToBeSavedOrUpdated);
		
		log.info("Send notification for course content updates");
		commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
		
		commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		return dtoToModel(savedCourses.get(0).getCourseMinRequirements()
				.get(savedCourses.get(0).getCourseMinRequirements().size() - 1));
	}

	@Transactional
	public CourseMinRequirementDto updateCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto, String courseMinRequirementId)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CourseMinRequirementProcessor.updateCourseMinRequirement for upading min requirement ");

		Course course = courseProcessor.validateAndGetCourseById(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to update course min requirement");
			throw new ForbiddenException("no access to update course min requirement");
		}

		if (!course.getCourseMinRequirements().stream().map(CourseMinRequirement::getId).collect(Collectors.toSet())
				.contains(courseMinRequirementId)) {
			log.error("invalid course min requirement id: {}", courseMinRequirementId);
			throw new NotFoundException("invalid course min requirement id: " + courseMinRequirementId);
		} else {
			log.info("starting process to prepare model");
			CourseMinRequirement courseMinRequirement = course.getCourseMinRequirements().get(0);
			
			CourseMinRequirement courseMinRequirementBeforeUpdate = new CourseMinRequirement();
			BeanUtils.copyProperties(courseMinRequirement, courseMinRequirementBeforeUpdate);
			List<CourseMinRequirementSubject> subjectsBeforeUpdate = courseMinRequirement.getCourseMinRequirementSubjects().stream().map(subject -> {
				CourseMinRequirementSubject clone = new CourseMinRequirementSubject();
				BeanUtils.copyProperties(subject, clone);
				return clone;
			}).collect(Collectors.toList());

			courseMinRequirement.setCountryName(courseMinRequirementDto.getCountryName());
			courseMinRequirement.setStateName(courseMinRequirementDto.getStateName());
			courseMinRequirement.setGradePoint(courseMinRequirementDto.getGradePoint());
			courseMinRequirement
					.setEducationSystem(validateAndGetEducationSystem(courseMinRequirementDto.getEducationSystemId()));
			courseMinRequirement.setStudyLanguages(courseMinRequirementDto.getStudyLanguages());
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
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(courseMinRequirementDto.getLinkedCourseIds())) {
				List<CourseMinRequirementDto> dtosToReplicate = course.getCourseMinRequirements().stream()
						.map(e -> dtoToModel(e)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(replicateCourseMinRequirements(userId,
						courseMinRequirementDto.getLinkedCourseIds(), dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			if(!(courseMinRequirementBeforeUpdate.equals(courseMinRequirement)
					&& subjectsBeforeUpdate.equals(courseMinRequirement.getCourseMinRequirementSubjects()))) {
				log.info("Send notification for course content updates");
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			}
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
			return dtoToModel(courseMinRequirement);
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
				.map(e -> dtoToModel(e)).collect(Collectors.toList());
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) courseMinRequirementPage.getTotalElements()).intValue(), courseMinRequirementDtos);

	}

	@Transactional
	public void deleteCourseMinRequirement(String userId, String courseId, String courseMinRequirementId,
			List<String> linkedCourseIds) throws NotFoundException, ForbiddenException, ValidationException {
		log.info("inside CourseMinRequirementProcessor.deleteCourseMinRequirement");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to delete course min requirement");
			throw new ForbiddenException("no access to delete course min requirement");
		}

		List<CourseMinRequirement> courseMinRequirements = course.getCourseMinRequirements();
		courseMinRequirements.removeIf(e -> e.getId().equalsIgnoreCase(courseMinRequirementId));
		List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
		coursesToBeSavedOrUpdated.add(course);
		if (!CollectionUtils.isEmpty(linkedCourseIds)) {
			List<CourseMinRequirementDto> dtosToReplicate = courseMinRequirements.stream()
					.map(e -> dtoToModel(e)).collect(Collectors.toList());
			coursesToBeSavedOrUpdated.addAll(replicateCourseMinRequirements(userId, linkedCourseIds, dtosToReplicate));
		}
		courseDao.saveAll(coursesToBeSavedOrUpdated);
		commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

		commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
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

	private List<Course> replicateCourseMinRequirements(String userId, List<String> courseIds,
			List<CourseMinRequirementDto> courseMinRequirementDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseMinRequirements");

		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseMinRequirement> courseMinRequirements = course.getCourseMinRequirements();
				if (CollectionUtils.isEmpty(courseMinRequirementDtos)) {
					courseMinRequirements.clear();
				} else {
					courseMinRequirements.removeIf(e -> !contains(courseMinRequirementDtos, e));
					courseMinRequirementDtos.stream().forEach(dto -> {
						Optional<CourseMinRequirement> existingMinRequirementOp = courseMinRequirements.stream()
								.filter(e -> e.getCountryName().equalsIgnoreCase(dto.getCountryName())
										&& e.getStateName().equalsIgnoreCase(dto.getStateName())
										&& e.getEducationSystem().getId().equals(dto.getEducationSystem().getId()))
								.findAny();
						CourseMinRequirement courseMinRequirement = null;
						if (existingMinRequirementOp.isPresent()) {
							courseMinRequirement = existingMinRequirementOp.get();
						} else {
							courseMinRequirement = new CourseMinRequirement();
							courseMinRequirement.setCourse(course);
							courseMinRequirements.add(courseMinRequirement);
						}
						final CourseMinRequirement finalMinRequirement = courseMinRequirement;
						if (!CollectionUtils.isEmpty(dto.getMinRequirementSubjects())) {
							List<CourseMinRequirementSubject> dbSubjects = courseMinRequirement
									.getCourseMinRequirementSubjects();
							List<CourseMinRequirementSubjectDto> subjectDtos = dto.getMinRequirementSubjects();
							subjectDtos.stream().forEach(subjectDto -> {
								Optional<CourseMinRequirementSubject> existingSubjectOp = dbSubjects.stream()
										.filter(e -> e.getName().equalsIgnoreCase(subjectDto.getName())).findAny();
								CourseMinRequirementSubject subject = null;
								if (existingSubjectOp.isPresent()) {
									subject = existingSubjectOp.get();
								} else {
									subject = new CourseMinRequirementSubject();
									subject.setName(subjectDto.getName());
									subject.setCourseMinRequirement(finalMinRequirement);
									dbSubjects.add(subject);
								}
							});
						} else {
							courseMinRequirement.getCourseMinRequirementSubjects().clear();
						}

						courseMinRequirement.setStudyLanguages(dto.getStudyLanguages());
						courseMinRequirement.setAuditFields(userId);
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}

	public static boolean contains(List<CourseMinRequirementDto> lst, CourseMinRequirement target) {
		return lst.stream()
				.anyMatch(e -> e.getCountryName().equalsIgnoreCase(target.getCountryName())
						&& e.getStateName().equalsIgnoreCase(target.getStateName())
						&& e.getEducationSystem().getId().equals(target.getEducationSystem().getId()));
	}
	
	private CourseMinRequirementDto dtoToModel(CourseMinRequirement source) {
		CourseMinRequirementDto courseMinRequirement = modelMapper.map(source, CourseMinRequirementDto.class);
		courseMinRequirement.setStudyLanguages(source.getStudyLanguages());
		return courseMinRequirement;
	}
}
